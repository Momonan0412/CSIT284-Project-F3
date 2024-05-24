package com.example.quizapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.example.quizapplication.R;
import com.example.quizapplication.fragments.Level;

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
//        display = findViewById(R.id.vlDisplay);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(1);


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(2);


            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(3);


            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(4);


            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(5);


            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(6);


            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(7);


            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(8);


            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(9);


            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(10);

            }
        });
        // TODO: Implement this activity like flashcard ish
//>>>>>>> Stashed changes

    }
    private void openFragment(int lvl){
        level = lvl;
        FragmentManager manage = getSupportFragmentManager();
        manage.beginTransaction()
                .replace(R.id.fgDisplay, Level.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

    }


}