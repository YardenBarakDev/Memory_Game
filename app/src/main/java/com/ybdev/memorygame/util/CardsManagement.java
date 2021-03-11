package com.ybdev.memorygame.util;

import com.ybdev.memorygame.R;

import java.util.Random;

public class CardsManagement {

    private final int [] imagesArray;
    private final int[] cardsImages = {R.drawable.cat, R.drawable.dog, R.drawable.owl, R.drawable.horse, R.drawable.elephant, R.drawable.fish, R.drawable.pen, R.drawable.sun};
    private final int numOfCards;

    public CardsManagement(int numOfCards) {
        this.numOfCards = numOfCards;
        imagesArray = new int[numOfCards];
        addImages();
        shuffle();
    }

    private void addImages(){
        for (int i = 0; i < imagesArray.length/2; i++) {
            imagesArray[i] = cardsImages[i];
            imagesArray[imagesArray.length - i - 1] = cardsImages[i];
        }
    }

    private void shuffle() {
        Random random = new Random();
        int card1;
        int card2;
        int temp;
        for (int i = 0; i < numOfCards; i++) {
            card1 = random.nextInt(numOfCards);
            card2 = random.nextInt(numOfCards);
            temp = imagesArray[card1];
            imagesArray[card1] = imagesArray[card2];
            imagesArray[card2] = temp;
        }
    }

    public boolean checkMatch(int card1, int card2){
        return  imagesArray[card1] == imagesArray[card2];
    }

   public int getImage(int index){
        return imagesArray[index];
   }
}
