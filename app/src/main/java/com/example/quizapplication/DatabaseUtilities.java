package com.example.quizapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quizapplication.callbacks.JapaneseDataCallBack;
import com.example.quizapplication.callbacks.UserExistCallback;
import com.example.quizapplication.record.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

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
                    Toast.makeText(applicationContext, "Kanji already exists", Toast.LENGTH_SHORT).show();
                } else {
                    kanjiRef.push().setValue(new JapaneseData(kanji, furigana, english))
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
    public static void registerUser(String username, String password, Context applicationContext, final UserExistCallback callback) {
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
                    userRef.push().setValue(new User(username, hashedPassword))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Data added successfully
                                        Toast.makeText(applicationContext, "User Added", Toast.LENGTH_SHORT).show();
                                        callback.onUserExistChecked(true);
                                    } else {
                                        // Failed to add data
                                        Toast.makeText(applicationContext, "Failed to add User", Toast.LENGTH_SHORT).show();
                                        Log.e("Firebase", "Error adding User", task.getException());
                                        callback.onUserExistChecked(false);
                                    }
                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onUserExistChecked(false);
                // Handle onCancelled event
            }
        });
        } catch (Exception e) {
            callback.onUserExistChecked(false);
            e.printStackTrace();
        }
    }
    public static void signInUser(String username, String password, Context applicationContext,
                                  final UserExistCallback callback) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        System.out.println(userRef.toString());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Inside `ondatachange`!");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String randomId = userSnapshot.getKey(); // Get the random ID
                        String passwordInDatabase = userSnapshot.child("password").getValue(String.class);
                        String usernameInDatabase = userSnapshot.child("username").getValue(String.class);
                        Log.d("Firebase", "Random ID: " + randomId);
                        Log.d("Firebase", "Username: " + usernameInDatabase);
                        Log.d("Firebase", "Password: " + passwordInDatabase);
                        if (usernameInDatabase.equals(username) && BCrypt.checkpw(password, passwordInDatabase)) {
                            callback.onUserExistChecked(true);
                        }
                    }
                } else {
                    callback.onUserExistChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onUserExistChecked(false);
            }
        });
    }
}