package com.ybdev.memorygame.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.ybdev.memorygame.R;
import com.ybdev.memorygame.util.CardsManagement;

import java.util.ArrayList;

public class GameFragment extends Fragment {

    protected View view;
    private CardsManagement cardsManagement;
    private ArrayList<LinearLayout> linearLayouts;
    private TextView game_LBL_score;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private int score = 0;
    private int difficulty;
    private int numOfColumns;
    private int turn = 1;
    private int card1 = -1;
    private int card2 = -1;
    private int card1Row;
    private int card2Row;
    private boolean flag = true;

    public GameFragment(int difficulty) {
        this.difficulty = difficulty;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_game, container, false);
        }
        findViewsById();
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View cardView) {
            new Thread(() -> {
                if (flag) {
                    flag = false;
                    if (turn == 1) {
                        card1 = cardView.getId();
                        card1Row = Integer.parseInt(cardView.getTag().toString());
                        getActivity().runOnUiThread(() -> changeCardImage(card1, card1Row));
                        turn++;
                        flag = true;
                    } else if (turn == 2) {
                        card2 = cardView.getId();
                        card2Row = Integer.parseInt(cardView.getTag().toString());
                        getActivity().runOnUiThread(() -> changeCardImage(card2, card2Row));
                        delay();
                        getActivity().runOnUiThread(() -> checkMatch());
                    }
                }

            }).start();
        }
    };

    public void checkMatch() {          //col * row + index
        if (cardsManagement.checkMatch((numOfColumns * card1Row) + card1, (numOfColumns * card2Row) + card2)) {//same image
            //remove btn from view
            linearLayouts.get(card1Row).getChildAt(card1).setVisibility(View.GONE);
            linearLayouts.get(card2Row).getChildAt(card2).setVisibility(View.GONE);

        } else //different images
        {
            //fold the cards
            setBackImage(linearLayouts.get(card1Row).getChildAt(card1));
            setBackImage(linearLayouts.get(card2Row).getChildAt(card2));
        }
        //update score
        updateScore();
        turn = 1;
        flag = true;
    }

    private void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //flip the card and show the image
    public void changeCardImage(int card, int row) {
        Glide.with(getContext())
                .load(cardsManagement.getImage((numOfColumns * row) + card))
                .into((ImageView) linearLayouts.get(row).getChildAt(card));
    }

    public void crateGame() {
        linearLayouts = new ArrayList<>();
        linearLayouts.add(linearLayout1);
        linearLayouts.add(linearLayout2);
        linearLayouts.add(linearLayout3);
        linearLayouts.add(linearLayout4);

        createLayout();
        cardsManagement = new CardsManagement(difficulty);
        updateScore();
    }

    @SuppressLint("SetTextI18n")
    private void updateScore() {
        game_LBL_score.setText("" + score);
        score++;
    }

    private void createLayout() {
        int divider = 4;
        for (int i = 0; i < linearLayouts.size(); i++) {
            for (int j = 0; j < difficulty / divider; j++) {

                ImageButton btn = new ImageButton(getActivity());
                btn.setLayoutParams(new ViewGroup.LayoutParams(250, 250));

                //set click listener
                btn.setOnClickListener(onClickListener);

                //the column
                btn.setId(j);
                //the row
                btn.setTag(i);

                //add the button to your linear layout
                linearLayouts.get(i).addView(btn);

                //set background image
                setBackImage(linearLayouts.get(i).getChildAt(j));
            }
        }
    }

    private void setBackImage(View btn) {
        Glide.with(getContext())
                .load(R.drawable.card_back)
                .into((ImageView) btn);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;

        //set the number of columns
        numOfColumns = difficulty / 4;
    }

    private void findViewsById() {
        game_LBL_score = view.findViewById(R.id.game_LBL_score);
        linearLayout1 = view.findViewById(R.id.linearLayout1);
        linearLayout2 = view.findViewById(R.id.linearLayout2);
        linearLayout3 = view.findViewById(R.id.linearLayout3);
        linearLayout4 = view.findViewById(R.id.linearLayout4);
    }
}