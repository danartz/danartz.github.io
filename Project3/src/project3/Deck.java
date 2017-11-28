package project3;

import java.util.ArrayDeque;
import java.util.Collections;
import project3.Cards;
//import java.util.ArrayList;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dan
 */
public class Deck extends Cards {

    private LinkedList<Cards> drawPile = new LinkedList<>();
    private LinkedList<Cards> humanHand = new LinkedList<>();
    private ArrayDeque<Cards> discardPile = new ArrayDeque<>();
    // add stack here

    public ArrayDeque<Cards> getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(ArrayDeque<Cards> discardPile) {
        this.discardPile = discardPile;
    }

   // deck constructor loads cards into draw pile
    public Deck() {

        //Cards c1 = new Cards().setValue("Test");
        for (int i = 0; i < 53; i++) {
            drawPile.add(new Cards());
        }
        for (int i = 0; i < 13; i++) {
            drawPile.get(i).setSuit("Clubs");
            if (i == 9) {
                drawPile.get(i).setValue("Jack");
            } else if (i == 10) {
                drawPile.get(i).setValue("Queen");
            } else if (i == 11) {
                drawPile.get(i).setValue("King");
            } else if (i == 12) {
                drawPile.get(i).setValue("Ace");
            } else {
                String sInt = Integer.toString(i + 2);
                drawPile.get(i).setValue(sInt);
            }

        }
        for (int i = 13; i < 26; i++) {
            drawPile.get(i).setSuit("Diamonds");
            if (i == 22) {
                drawPile.get(i).setValue("Jack");
            } else if (i == 23) {
                drawPile.get(i).setValue("Queen");
            } else if (i == 24) {
                drawPile.get(i).setValue("King");
            } else if (i == 25) {
                drawPile.get(i).setValue("Ace");
            } else {

                String sInt = Integer.toString(i - 11);
                drawPile.get(i).setValue(sInt);
            }
        }
        for (int i = 26; i < 39; i++) {
            drawPile.get(i).setSuit("Hearts");

            if (i == 35) {
                drawPile.get(i).setValue("Jack");
            } else if (i == 36) {
                drawPile.get(i).setValue("Queen");
            } else if (i == 37) {
                drawPile.get(i).setValue("King");
            } else if (i == 38) {
                drawPile.get(i).setValue("Ace");
            } else {
                String sInt = Integer.toString(i - 24);
                drawPile.get(i).setValue(sInt);
            }
        }
        for (int i = 39; i < 52; i++) {
            drawPile.get(i).setSuit("Spades");

            if (i == 48) {
                drawPile.get(i).setValue("Jack");
            } else if (i == 49) {
                drawPile.get(i).setValue("Queen");
            } else if (i == 50) {
                drawPile.get(i).setValue("King");
            } else if (i == 51) {
                drawPile.get(i).setValue("Ace");
            } else {
                String sInt = Integer.toString(i - 37);
                drawPile.get(i).setValue(sInt);
            }
        }
        
        // loads image path for each card sequentially
        for (int i = 0; i < 52; i++) {
            drawPile.get(i).setFileName("file:resources/" + i + ".png");
        }

    }

    // shuffle deck draw pile function
    public void Shuffle(){
        Collections.shuffle(drawPile);
    }

    public LinkedList<Cards> getDrawPile() {
        return drawPile;
    }

    public void setHumanHand(LinkedList<Cards> humanHand) {
        this.humanHand = humanHand;
    }


    public LinkedList<Cards> getHumanHand() {
        return humanHand;
    }

  // Prints draw pile values for debugging purposes
    public void printDrawPile() {
        for (int i = 0; i < this.drawPile.size(); i++) {
            System.out.println(drawPile.get(i).getValue());
        }

    }

}
