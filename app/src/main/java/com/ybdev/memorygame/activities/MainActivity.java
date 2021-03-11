package com.ybdev.memorygame.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.ybdev.memorygame.R;
import com.ybdev.memorygame.callback.GameCallBack;
import com.ybdev.memorygame.fragments.GameFragment;
import com.ybdev.memorygame.fragments.IntroFragment;

public class MainActivity extends AppCompatActivity {

    private IntroFragment introFragment;
    private GameFragment gameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        addFragments();
    }


    private void initFragments() {
        introFragment = new IntroFragment();
        introFragment.setActivityCallBack(gameCallBack);
        gameFragment = new GameFragment(-1);
    }

    private void addFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //add fragments to layout
        transaction.add(R.id.MainActivity_Lay_Fragment, introFragment);
        transaction.add(R.id.MainActivity_Lay_Fragment, gameFragment);

        //set intro fragment to be the first one
        getSupportFragmentManager().beginTransaction().hide(gameFragment).commit();

        //commit
        transaction.commit();
    }

    GameCallBack gameCallBack = this::startGame;

        private void startGame(int difficulty) {
            gameFragment.setDifficulty(difficulty);
            getSupportFragmentManager().beginTransaction().show(gameFragment).commit();
            getSupportFragmentManager().beginTransaction().hide(introFragment).commit();
            gameFragment.crateGame();
        }
}