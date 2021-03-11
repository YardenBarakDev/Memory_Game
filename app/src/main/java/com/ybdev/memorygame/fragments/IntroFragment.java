package com.ybdev.memorygame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.ybdev.memorygame.R;
import com.ybdev.memorygame.callback.GameCallBack;

public class IntroFragment extends Fragment {

    protected View view;
    private GameCallBack gameCallBack;
    private MaterialButton intro_BTN_easy;
    private MaterialButton intro_BTN_hard;
    private MaterialButton intro_BTN_extreme;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_intro, container, false);
        }
        findViewsById();
        setClickListeners();
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int difficulty;
            switch(view.getTag().toString()){
                case "intro_BTN_easy":
                    difficulty = 8;
                    gameCallBack.getDifficulty(difficulty);
                    break;
                case "intro_BTN_hard":
                    difficulty = 12;
                    gameCallBack.getDifficulty(difficulty);
                    break;
                case "intro_BTN_extreme":
                    difficulty = 16;
                    gameCallBack.getDifficulty(difficulty);
                    break;
            }
        }
    };

    public void setActivityCallBack(GameCallBack gameCallBack){
        this.gameCallBack = gameCallBack;
    }

    private void setClickListeners() {
        intro_BTN_easy.setOnClickListener(onClickListener);
        intro_BTN_hard.setOnClickListener(onClickListener);
        intro_BTN_extreme.setOnClickListener(onClickListener);
    }

    private void findViewsById() {
        intro_BTN_easy = view.findViewById(R.id.intro_BTN_easy);
        intro_BTN_hard = view.findViewById(R.id.intro_BTN_hard);
        intro_BTN_extreme = view.findViewById(R.id.intro_BTN_extreme);
    }
}