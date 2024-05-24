package com.example.quizapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.Study;
import com.example.quizapplication.utils.DatabaseUtilities;
import com.google.firebase.annotations.concurrent.Background;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Level#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Level extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout layout;

    TextView kanji, furina, english,mnemonic;
    //    LinearLayout displayStuff;
    ScrollView scroll,displayStuff;
    JSONArray jsonArray;

    public Level() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Level1.
     */
    // TODO: Rename and change types and number of parameters
    public static Level newInstance(String param1, String param2) {
        Level fragment = new Level();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_level, container, false);
    }

    //Reference ======== https://stackoverflow.com/questions/6495898/findviewbyid-in-fragment =========
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = getView().findViewById(R.id.orient);
        english = getView().findViewById(R.id.fgEnglish);
        furina = getView().findViewById(R.id.fgTxtReading);
        kanji = getView().findViewById(R.id.frgTxtKanji);
        mnemonic = getView().findViewById(R.id.fgMnemonic);
        scroll = getView().findViewById(R.id.fgScrollView);
        displayStuff = getView().findViewById(R.id.fgDisplayScrollView);
//        displayStuff = getView().findViewById(R.id.llDisplayStudy);

//        layout.setGravity(Gravity.CENTER);
        displayStuff.setClickable(false);
        displayStuff.setVisibility(View.INVISIBLE);
        layout.setBackground(getResources().getDrawable(R.drawable.rounded_corner));

//        displayStuff.setBackground();
//        displayStuff.setPadding(2,2,2,2);
//        displayStuff.setBackgroundColor(Color.GRAY);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layout.getLayoutParams();
        int marginDP = 500;
        float scale = getResources().getDisplayMetrics().density;
        int marginPX = (int) (marginDP * scale + 0.5f);
        params.topMargin = marginPX;

        System.out.println(layout.isActivated());
        DatabaseUtilities.getKanji("Level "+ Study.getLevel(),getContext(),(data)->{
            this.jsonArray = data;
            Button[] btnArray = new Button[jsonArray.length()];
            try{
                for(int i=0; i<jsonArray.length();i++){
                    Button btn = new Button(getContext());

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String kanjiSymbol = jsonObject.getString("kanji");
                    btn.setText(kanjiSymbol);
                    btn.setTextSize(30);
//                    btn.setPadding(10, 10, 10, 10);
                    btn.setId(i);
                    btnArray[i] = btn;


                    layout.addView(btn);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }



            for(int i = 0; i< jsonArray.length();i++){
                int idkWtfImStillDoing = i;
                btnArray[i].setTextColor(Color.BLACK);
                btnArray[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(idkWtfImStillDoing);

                            String kanjiSymbol = jsonObject.getString("kanji");
                            String furigana = jsonObject.getString("furigana");
                            String englishMeaning = jsonObject.getString("english");
                            String mnemonic1 = jsonObject.getString("mnemonic");
                            System.out.println(mnemonic1+" Button "+btnArray[idkWtfImStillDoing].getId()+"is pressed");

                            displayStuff.setClickable(true);
                            displayStuff.setVisibility(View.VISIBLE);
                            scroll.setVisibility(View.INVISIBLE);
                            kanji.setTextSize(50);
                            kanji.setText(kanjiSymbol);
                            english.setText(englishMeaning);
                            furina.setText(furigana);
                            mnemonic.setText(mnemonic1);

//                            kanji.setGravity(Gravity.CENTER);
//                            english.setGravity(Gravity.CENTER);
//                            furina.setGravity(Gravity.CENTER);
//                            mnemonic.setGravity(Gravity.CENTER);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                btnArray[i].setBackground(getResources().getDrawable(R.drawable.rounded_scroll));
            }
        });



    }

}