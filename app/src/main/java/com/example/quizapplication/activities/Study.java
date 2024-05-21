package com.example.quizapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.example.quizapplication.Fragments.Level1;
import com.example.quizapplication.R;
import com.example.quizapplication.utils.DatabaseUtilities;

import org.json.JSONArray;
import org.json.JSONException;

public class Study extends AppCompatActivity {
    Button btn1, btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btnExit;
    LinearLayout display;
    FragmentContainerView view;
    private static int level;

    public static int getLevel() {
        return level;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);


//=======
        btn1 = findViewById(R.id.btnStudy1);
        btn2 = findViewById(R.id.btnStudy2);
        btn3 = findViewById(R.id.btnStudy3);
        btn4 = findViewById(R.id.btnStudy4);
        btn5 = findViewById(R.id.btnStudy5);
        btn6 = findViewById(R.id.btnStudy6);
        btn7 = findViewById(R.id.btnStudy7);
        btn8 = findViewById(R.id.btnStudy8);
        btn9 = findViewById(R.id.btnStudy9);
        btn10 = findViewById(R.id.btnStudy10);

        view = findViewById(R.id.fgDisplay);
        display = findViewById(R.id.vlDisplay);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 1;
                System.out.println("this is clicked");
                FragmentManager manage = getSupportFragmentManager();
                manage.beginTransaction()
                        .replace(R.id.fgDisplay,Level1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 2;
                System.out.println("this is clicked");
                FragmentManager manage = getSupportFragmentManager();
                manage.beginTransaction()
                        .replace(R.id.fgDisplay,Level1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 3;
                System.out.println("this is clicked");
                FragmentManager manage = getSupportFragmentManager();
                manage.beginTransaction()
                        .replace(R.id.fgDisplay,Level1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 4;
                System.out.println("this is clicked");
                FragmentManager manage = getSupportFragmentManager();
                manage.beginTransaction()
                        .replace(R.id.fgDisplay,Level1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 5;
                System.out.println("this is clicked");
                FragmentManager manage = getSupportFragmentManager();
                manage.beginTransaction()
                        .replace(R.id.fgDisplay,Level1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 6;
                System.out.println("this is clicked");
                FragmentManager manage = getSupportFragmentManager();
                manage.beginTransaction()
                        .replace(R.id.fgDisplay,Level1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 7;
                System.out.println("this is clicked");
                FragmentManager manage = getSupportFragmentManager();
                manage.beginTransaction()
                        .replace(R.id.fgDisplay,Level1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 8;
                System.out.println("this is clicked");
                FragmentManager manage = getSupportFragmentManager();
                manage.beginTransaction()
                        .replace(R.id.fgDisplay,Level1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 9;
                System.out.println("this is clicked");
                FragmentManager manage = getSupportFragmentManager();
                manage.beginTransaction()
                        .replace(R.id.fgDisplay,Level1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 10;
                System.out.println("this is clicked");
                FragmentManager manage = getSupportFragmentManager();
                manage.beginTransaction()
                        .replace(R.id.fgDisplay,Level1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            }
        });
        // TODO: Implement this activity like flashcard ish
//>>>>>>> Stashed changes

    }


}