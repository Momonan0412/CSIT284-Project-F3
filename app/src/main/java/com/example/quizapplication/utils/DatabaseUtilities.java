package com.example.quizapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.quizapplication.callbacks.DateAndStreakCallback;
import com.example.quizapplication.callbacks.FrequencyLevelCallback;
import com.example.quizapplication.callbacks.InsertSuccessCallback;
import com.example.quizapplication.callbacks.JapaneseDataCallBack;
import com.example.quizapplication.callbacks.UserExistCallback;
import com.example.quizapplication.callbacks.UserProfileUpdateCallback;
import com.example.quizapplication.callbacks.UserReviewDataCallBack;
import com.example.quizapplication.designpattern.User;
import com.example.quizapplication.models.JapaneseData;
import com.example.quizapplication.models.UserRegistrationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DatabaseUtilities {
    private DatabaseUtilities (){}
    public static void addKanji(String level, String kanji, String furigana, String english, Context applicationContext) {
        DatabaseReference kanjiRef = FirebaseDatabase.getInstance().getReference().child("kanji").child(level);
        // Querying the database to check if the email already exists to prevent duplication
        Query query = kanjiRef.orderByChild("kanji").equalTo(kanji);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    Toast.makeText(applicationContext, "Kanji already exists", Toast.LENGTH_SHORT).show();
                    Toast.makeText(applicationContext, "Kanji " + level + " Is Already Up to Date", Toast.LENGTH_SHORT).show();
                } else {
                    kanjiRef.push().setValue(new JapaneseData(kanji, english, furigana))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Data added successfully
                                        Toast.makeText(applicationContext, "Kanji Added", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Failed to add data
                                        Toast.makeText(applicationContext, "Failed to add Kanji", Toast.LENGTH_SHORT).show();
                                        Log.e("Firebase", "Error adding Kanji", task.getException());
                                    }
                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });
    }
    public static void getKanji(String level, Context applicationContext, final JapaneseDataCallBack callBack) {
        DatabaseReference kanjiRef = FirebaseDatabase.getInstance().getReference().child("kanji").child(level);
        kanjiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                JSONArray jsonArray = new JSONArray();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get each JapaneseData object
                    JapaneseData japaneseData = snapshot.getValue(JapaneseData.class);
                    if (japaneseData != null) {
                        // Create a JSONObject for each JapaneseData object
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("kanji", japaneseData.getKanji());
                            jsonObject.put("furigana", japaneseData.getFurigana());
                            jsonObject.put("english", japaneseData.getEnglish());
                            jsonArray.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                callBack.onJapaneseDataCallBack(jsonArray);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }
    public static void registerUser(String username, String password, Context applicationContext, final InsertSuccessCallback callback) {
        try{
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        // Querying the database to check if the email already exists to prevent duplication
        Query query = userRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(applicationContext, "User already exists", Toast.LENGTH_SHORT).show();
                } else {
                    userRef.push().setValue(new UserRegistrationData(username, hashedPassword))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Data added successfully
                                        Toast.makeText(applicationContext, "User Added", Toast.LENGTH_SHORT).show();
                                        callback.onInsertSuccessChecked(true);
                                    } else {
                                        // Failed to add data
                                        Toast.makeText(applicationContext, "Failed to add User", Toast.LENGTH_SHORT).show();
                                        Log.e("Firebase", "Error adding User", task.getException());
                                        callback.onInsertSuccessChecked(false);
                                    }
                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onInsertSuccessChecked(false);
                // Handle onCancelled event
            }
        });
        } catch (Exception e) {
            callback.onInsertSuccessChecked(false);
            e.printStackTrace();
        }
    }
    // TODO: THINK OF A WAY TO UPDATE "STREAK"
    public static void updateUsersDateAndStreak(String username, int userStreak) {
        String currentDate = AndroidUtil.currentDateGetter();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");

        Query query = userRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                    DatabaseReference currentUserRef = userSnapshot.getRef();
                    currentUserRef.child("date").setValue(currentDate);
                    currentUserRef.child("streak").setValue(userStreak);
                } else {
                    // Handle case where user with the given username doesn't exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
    public static void signInUser(String username, String password, Context applicationContext,
                                  final UserExistCallback callback) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Inside `ondatachange`!");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String randomId = userSnapshot.getKey(); // Get the random ID
                        String passwordInDatabase = userSnapshot.child("hashedPassword").getValue(String.class);
                        String usernameInDatabase = userSnapshot.child("username").getValue(String.class);
                        Log.d("Firebase", "Random ID: " + randomId);
                        Log.d("Firebase", "Username: " + usernameInDatabase);
                        Log.d("Firebase", "Password: " + passwordInDatabase);
                        if (usernameInDatabase.equals(username)) {
                            if (passwordInDatabase != null && BCrypt.checkpw(password, passwordInDatabase)) {
                                // Password is not null and matches
                                User user = User.getInstance();
                                user.setUsername(username);
                                DatabaseUtilities.profilePictureUpdateChecker(user.getUsername(), applicationContext, (profilePictureURL) -> {
                                    if (profilePictureURL != null) {
                                        user.setProfilePictureURL(profilePictureURL);
                                    }
                                });
                                callback.onUserExistChecked(user);
                            } else {
                                // Password is null or does not match
                                callback.onUserExistChecked(null);
                            }
                        } else {
                            // Username does not match
                            callback.onUserExistChecked(null);
                        }
                    }
                } else {
                    callback.onUserExistChecked(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onUserExistChecked(null);
            }
        });
    }
    public static void uploadImageToStorage(Bitmap bitmap, String username,
                                            Context applicationContext) {
        // Create a reference to the Firebase Storage location where the image will be stored
        StorageReference storageRef = FirebaseStorage
                .getInstance()
                .getReference()
                .child("profile_pictures")
                .child(username + ".jpg");
        // Convert Bitmap to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        // Upload the image data to Firebase Storage
        UploadTask uploadTask = storageRef.putBytes(imageData);

        // Listen for upload success/failure
        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Image upload success
                // Get the download URL for the image
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Save the image URL to Firebase Realtime Database
                    Toast.makeText(applicationContext,
                            "Uploaded profile picture to Firebase Realtime Database with success!",
                            Toast.LENGTH_SHORT).show();
                    insertUserProfilePicture(uri.toString(), username);
                });
            } else {
                // Image upload failed
                Toast.makeText(applicationContext, "Failed to upload image to Firebase Storage",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static void insertUserProfilePicture(String imageUrl, String username) {
        try {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
            // Attach a listener to the "users" node to traverse all child nodes
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Iterate through each child node under "users"
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        // Get the username of the current user
                        String currentUserUsername = userSnapshot.child("username").getValue(String.class);
                        // Check if the current user's username matches the given username
                        if (currentUserUsername != null && currentUserUsername.equals(username)) {
                            Log.d("Firebase", "Username: " + currentUserUsername);
                            // If a match is found, get a reference to the profile_picture node
                            DatabaseReference profilePictureRef = userSnapshot.child("profile_picture").getRef();
                            // Set the image URL as the value in the Realtime Database under the profile_picture node
                            profilePictureRef.setValue(imageUrl)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            // TODO: Handle success
                                        } else {
                                            // TODO: Handle failure
                                        }
                                    });
                            // Exit the loop since we found the matching user
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled event
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void profilePictureUpdateChecker (String username, Context applicationContext, final UserProfileUpdateCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = usersRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Get the profile picture link from the user's node
                    String imageUrl = userSnapshot.child("profile_picture").getValue(String.class);
                    if (imageUrl != null) {
                        User user = User.getInstance();
                        // Load the profile picture into the user's ImageView
//                        Picasso.get().load(imageUrl).into(user.getUserProfilePicture());
                        // Glide fixed the the auto load image problem? or nah

                        callback.onUserProfileUpdateChecked(imageUrl);

//                        Glide.with(applicationContext).load(imageUrl).into(user.getUserProfilePicture());
                        break; // No need to continue iterating if the username is unique
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });
    }
    public static void saveJapaneseData(String level, String username, HashSet<JapaneseData> japaneseData) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = userRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                    DatabaseReference userDataRef = userSnapshot.getRef().child("japaneseReviewData").child(level);
                    List<Map<String, String>> serializedData = serializeJapaneseData(japaneseData);
                    userDataRef.setValue(serializedData); // REPLACE ALL THE THE CURRENT LEVEL
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
    public static void getReviewData(String username, final UserReviewDataCallBack callback) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = userRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                    DatabaseReference reviewDataRef = userSnapshot.getRef().child("japaneseReviewData");
                    reviewDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<JapaneseData> japaneseDataList = new ArrayList<>();
                            for (DataSnapshot levelSnapshot : dataSnapshot.getChildren()) {
                                String level = levelSnapshot.getKey();
                                for (DataSnapshot itemSnapshot : levelSnapshot.getChildren()) {
                                    JapaneseData japaneseData = itemSnapshot.getValue(JapaneseData.class);
                                    Log.d("ReviewData", "Level: " + level + ", Data: " + japaneseData);
                                    japaneseDataList.add(japaneseData);
                                }
                            }
                            callback.onUserReviewDataCallBack(japaneseDataList);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle possible errors.
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }

    private static List<Map<String, String>> serializeJapaneseData(HashSet<JapaneseData> japaneseData) {
        List<Map<String, String>> serializedData = new ArrayList<>();
        for (JapaneseData data : japaneseData) {
            Map<String, String> serializedItem = new HashMap<>();
            serializedItem.put("kanji", data.getKanji());
            serializedItem.put("english", data.getEnglish());
            serializedItem.put("furigana", data.getFurigana());
            serializedData.add(serializedItem);
        }
        return serializedData;
    }
    public static void dateAndStreakChecker(String username, final DateAndStreakCallback callback) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = userRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                    String date = userSnapshot.child("date").getValue(String.class);
                    Integer streakValue = userSnapshot.child("streak").getValue(Integer.class);
                    int streak = (streakValue != null) ? streakValue : 0;
                    Log.d("Date and Streak",  "Date: " + date + ", Streak: " + streak);
                    callback.onUserDateAndStreakCallBack(date, streak);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
    public static void updateUsersLevelFrequency(String username, String level) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = userRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                    DatabaseReference currentUserRef = userSnapshot.getRef();
                    DatabaseReference levelFrequencyRef = currentUserRef.child("LevelFrequency").child(level);
                    levelFrequencyRef.runTransaction(new Transaction.Handler() {

                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                            Integer data = currentData.getValue(Integer.class);
                            User user = User.getInstance();
                            user.setLevelFrequency(new int[10]);
                            int index = Integer.parseInt(level.substring(level.length() - 1));
                            if (data == null) {
                                user.getLevelFrequency()[index - 1] += 1;
                                currentData.setValue(1);
                            } else {
                                user.getLevelFrequency()[index - 1] += data + 1;
                                currentData.setValue(data + 1);
                            }
                            return Transaction.success(currentData);
                        }
                        @Override
                        public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                            if (error != null) {
                                Log.e("Firebase", "Transaction failed.", error.toException());
                            } else if (committed) {
                                assert currentData != null;
                                Log.d("Firebase", "Transaction successful: " + currentData.getValue());
                            }
                        }
                    });
                } else {
                    Log.e("Firebase", "User with username " + username + " does not exist.");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Query cancelled.", error.toException());
            }
        });
    }
    public static void populateFrequency(String username, final FrequencyLevelCallback callback) {
        int[] freq = new int[10];
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = userRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                    DatabaseReference currentUserRef = userSnapshot.getRef();
                    DatabaseReference levelFrequencyRef = currentUserRef.child("LevelFrequency");
                    levelFrequencyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot levelSnapshot : snapshot.getChildren()) {
                                    if(levelSnapshot != null){
                                        String level = levelSnapshot.getKey();
                                        assert level != null;
                                        int index = Integer.parseInt(level.substring(level.length() - 1));
                                        freq[index - 1] = levelSnapshot.getValue(Integer.class);
                                    }
                                }
                            }
                            // TODO: ADD CALLBACK HERE
                            callback.onFrequencyLevelReceived(freq);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle possible errors.
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
}