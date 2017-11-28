/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

import java.util.Collections;
import java.util.LinkedList;
import javafx.scene.image.ImageView;

/**
 *
 * // * @author dan
 */
public class AiPlayer extends Deck {

    public AiPlayer() {

    }

    private LinkedList<Cards> aiHand = new LinkedList<>();
    private boolean tookDiscarded = false;

    public boolean isTookDiscarded() {
        return tookDiscarded;
    }

    public void setTookDiscarded(boolean tookDiscarded) {
        this.tookDiscarded = tookDiscarded;
    }

    public LinkedList<Cards> getAiHand() {
        return aiHand;
    }

    public void setAiHand(LinkedList<Cards> aiHand) {
        this.aiHand = aiHand;
    }

    //Updates ai card graphics depending on the situation. Defaults to showing back of cards unless the AI or player wins
    public void updateAiCard(Deck d1, ImageView imageView6, ImageView imageView7,
            ImageView imageView8, ImageView imageView9, ImageView imageView10, boolean cardEmpty,
            ImageView imageViewDiscard) {

        if ((d1.getHumanHand().get(0).getValue().equals(d1.getHumanHand().get(1).getValue())) && (d1.getHumanHand().get(1).getValue().equals(d1.getHumanHand().get(2).getValue()))
                && (d1.getHumanHand().get(2).getValue().equals(d1.getHumanHand().get(3).getValue()))) {
            imageView6.setStyle("-fx-image: url(\"" + aiHand.get(0).getFileName() + "\");");

            imageView7.setStyle("-fx-image: url(\"" + aiHand.get(1).getFileName() + "\");");

            imageView8.setStyle("-fx-image: url(\"" + aiHand.get(2).getFileName() + "\");");

            imageView9.setStyle("-fx-image: url(\"" + aiHand.get(3).getFileName() + "\");");

            imageView10.setStyle("-fx-image: url(\"" + "file:resources/empty.png" + "\");");
            if(d1.getDiscardPile().size() == 0){
                imageViewDiscard.setStyle("-fx-image: url(\"" + "file:resources/empty.png" + "\");");
            }

        } else if ((aiHand.get(0).getValue().equals(aiHand.get(1).getValue())) && (aiHand.get(1).getValue().equals(aiHand.get(2).getValue()))
                && (aiHand.get(2).getValue().equals(aiHand.get(3).getValue()))) {
            imageView6.setStyle("-fx-image: url(\"" + aiHand.get(0).getFileName() + "\");");

            imageView7.setStyle("-fx-image: url(\"" + aiHand.get(1).getFileName() + "\");");

            imageView8.setStyle("-fx-image: url(\"" + aiHand.get(2).getFileName() + "\");");

            imageView9.setStyle("-fx-image: url(\"" + aiHand.get(3).getFileName() + "\");");

            imageView10.setStyle("-fx-image: url(\"" + "file:resources/empty.png" + "\");");
            if (d1.getDiscardPile().size() > 0) {
                String newCard5 = d1.getDiscardPile().peek().getFileName();
                imageViewDiscard.setStyle("-fx-image: url(\"" + newCard5 + "\");");
            } else {
                imageViewDiscard.setStyle("-fx-image: url(\"" + "file:resources/empty.png" + "\");");
            }

        } else {

            imageView6.setStyle("-fx-image: url(\"" + "file:resources/cardBack.png" + "\");");

            imageView7.setStyle("-fx-image: url(\"" + "file:resources/cardBack.png" + "\");");

            imageView8.setStyle("-fx-image: url(\"" + "file:resources/cardBack.png" + "\");");

            imageView9.setStyle("-fx-image: url(\"" + "file:resources/cardBack.png" + "\");");

            if (cardEmpty == true) {

                imageView10.setStyle("-fx-image: url(\"" + "file:resources/empty.png" + "\");");
            } else if (cardEmpty == false) {
                imageView10.setStyle("-fx-image: url(\"" + "file:resources/cardBack.png" + "\");");
            }

            if (d1.getDiscardPile().size() > 0) {
                String newCard5 = d1.getDiscardPile().peek().getFileName();
                imageViewDiscard.setStyle("-fx-image: url(\"" + newCard5 + "\");");
            } else {
                imageViewDiscard.setStyle("-fx-image: url(\"" + "file:resources/empty.png" + "\");");
            }
        }

    }

    // Ai move function. AI draws based on various decisions and then discards a card based on various decisions it makes
    public boolean aiMove(Deck d1, ImageView imageView6, ImageView imageView7,
            ImageView imageView8, ImageView imageView9, ImageView imageView10, ImageView imageViewDiscard) {
        tookDiscarded = false;

        int chooseRand = (int) (0 + Math.random() * 4);
        int chooseDrawDiscard = (int) (1 + Math.random() * 2);
        boolean match1 = false;
        boolean match2 = false;
        String strMatch1 = "";
        String strMatch2 = "";
        String strMatch3 = "";
        int decision = 0;

        //Search for match 1
        for (int i = 0; i < aiHand.size(); i++) {
            for (int j = 0; j < aiHand.size(); j++) {
                if ((aiHand.get(j).getValue().equals(aiHand.get(i).getValue())) && i != j) {
                    strMatch1 = aiHand.get(j).getValue();
                    match1 = true;

                }
            }
        }
        //Search for match 2
        for (int i = 0; i < aiHand.size(); i++) {
            for (int j = 0; j < aiHand.size(); j++) {
                if ((aiHand.get(j).getValue().equals(aiHand.get(i).getValue())) && (i != j) && (!(aiHand.get(j).getValue().equals(strMatch1)))) {
                    strMatch2 = aiHand.get(j).getValue();
                    match2 = true;

                }
            }
        }
        // Search for 1 card matching discard pile 
        for (int i = 0; i < aiHand.size(); i++) {
            if (aiHand.get(i).getValue().equals(d1.getDiscardPile().peek().getValue()) && d1.getDiscardPile().size() > 0) {
                strMatch3 = d1.getDiscardPile().peek().getValue();
            }
        }

        // Draw a card. I broke drawing up from discarding so if the last card is drawn, the deck is shuffled immediately 
        if (strMatch1.equals(d1.getDiscardPile().peek().getValue()) && match1 == true && d1.getDiscardPile().size() > 0) {
            if (d1.getDrawPile().size() > 0 && aiHand.size() < 5) {
                aiHand.add(d1.getDiscardPile().pop());
                decision = 1;

            }
        } else if (strMatch2.equals(d1.getDiscardPile().peek().getValue()) && match2 == true && d1.getDiscardPile().size() > 0) {
            aiHand.add(d1.getDiscardPile().pop());
            decision = 2;
        } else if (!(strMatch1.equals(d1.getDiscardPile().peek().getValue())) && (!(strMatch2.equals(d1.getDiscardPile().peek().getValue())))
                && (match1 == true) && (match2 == true) && d1.getDiscardPile().size() > 0) {
            int randDec = (int) (1 + Math.random() * 2);
            if (randDec == 1) {
                int randDec2 = (int) (1 + Math.random() * 2);
                if (randDec2 == 1) {

                    aiHand.add(d1.getDiscardPile().pop());
                    decision = 3;
                } else if (randDec2 == 2) {

                    aiHand.add(d1.getDrawPile().pollLast());
                    decision = 4;
                }
            } else if (randDec == 2) {
                int randDec2 = (int) (1 + Math.random() * 2);
                if (randDec2 == 1) {

                    aiHand.add(d1.getDiscardPile().pop());
                    decision = 5;
                } else if (randDec2 == 2) {

                    aiHand.add(d1.getDrawPile().pollLast());
                    decision = 6;
                }
            }
        } else if (match1 == true) {

            if (strMatch1.equals(d1.getDiscardPile().peek().getValue()) && d1.getDiscardPile().size() > 0) {
                aiHand.add(d1.getDiscardPile().pop());
                decision = 7;
            } else if (strMatch1.equals(d1.getDiscardPile().peek().getValue()) && d1.getDiscardPile().size() == 0) {
                aiHand.add(d1.getDrawPile().pollLast());
                decision = 7;
            } else {
                decision = 10; // added after the fact
                int randDec2 = (int) (1 + Math.random() * 2);
                if (d1.getDiscardPile().size() == 0) {
                    aiHand.add(d1.getDrawPile().pollLast());
                } else if (randDec2 == 1) {
                    aiHand.add(d1.getDiscardPile().pop());
                } else if (randDec2 == 2) {
                    aiHand.add(d1.getDrawPile().pollLast());
                }
            }
        } else if (match2 == true) {
            if (strMatch2.equals(d1.getDiscardPile().peek().getValue()) && d1.getDiscardPile().size() > 0) {
                aiHand.add(d1.getDiscardPile().pop());
                decision = 8;
            } else if (strMatch2.equals(d1.getDiscardPile().peek().getValue()) && d1.getDiscardPile().size() == 0) {
                aiHand.add(d1.getDrawPile().pollLast());
                decision = 8;
            } else {
                decision = 11; // added after the fact
                int randDec2 = (int) (1 + Math.random() * 2);
                if (d1.getDiscardPile().size() == 0) {
                    aiHand.add(d1.getDrawPile().pollLast());
                } else if (randDec2 == 1) {
                    aiHand.add(d1.getDiscardPile().pop());
                } else if (randDec2 == 2) {
                    aiHand.add(d1.getDrawPile().pollLast());
                }
            }
        } else if ((aiHand.get(0).getValue().equals(d1.getDiscardPile().peek().getValue()))
                || (aiHand.get(1).getValue().equals(d1.getDiscardPile().peek().getValue()))
                || (aiHand.get(2).getValue().equals(d1.getDiscardPile().peek().getValue()))
                || (aiHand.get(3).getValue().equals(d1.getDiscardPile().peek().getValue())) && d1.getDiscardPile().size() > 0) {
            for (int i = 0; i < 4; i++) {
                if (aiHand.get(i).getValue().equals(d1.getDiscardPile().peek().getValue())) {
                    aiHand.add(d1.getDiscardPile().pop());
                    break;
                }
            }
            decision = 9;
        } else {
            int randDec2 = (int) (1 + Math.random() * 2);
            if (d1.getDiscardPile().size() == 0) {
                aiHand.add(d1.getDrawPile().pollLast());
            } else if (randDec2 == 1) {
                aiHand.add(d1.getDiscardPile().pop());
            } else if (randDec2 == 2) {
                aiHand.add(d1.getDrawPile().pollLast());
            }
        }

        // Shuffle KEEP THIS
        if (d1.getDrawPile().size() == 0) {

            for (int i = 0; i < 43; i++) {
                d1.getDrawPile().push(d1.getDiscardPile().pop());
                if (d1.getDiscardPile().size() == 0) {
                    break;
                }
            }

            Collections.shuffle(d1.getDrawPile());
            for (int i = 0; i < d1.getDrawPile().size(); i++) {
                if (d1.getDrawPile().get(i).getValue() == null) {
                    d1.getDrawPile().remove(i);
                }
            }

        }

        // Discard based on flagging
        if (decision == 1 || decision == 10) {
            for (int i = 0; i < aiHand.size(); i++) {
                if (!(aiHand.get(i).getValue().equals(strMatch1))) {
                    d1.getDiscardPile().push(aiHand.get(i));
                    aiHand.remove(i);
                    break;
                }
            }
        } else if (decision == 2 || decision == 11) {
            for (int i = 0; i < aiHand.size(); i++) {
                if (!(aiHand.get(i).getValue().equals(strMatch2))) {
                    d1.getDiscardPile().push(aiHand.get(i));
                    aiHand.remove(i);
                    break;
                }
            }
        } else if (decision == 3) {
            for (int i = 0; i < aiHand.size(); i++) {
                if ((aiHand.get(i).getValue().equals(strMatch1))) {
                    d1.getDiscardPile().push(aiHand.get(i));
                    aiHand.remove(i);
                    break;
                }
            }
        } else if (decision == 4) {
            for (int i = 0; i < aiHand.size(); i++) {
                if ((aiHand.get(i).getValue().equals(strMatch1))) {
                    d1.getDiscardPile().push(aiHand.get(i));
                    aiHand.remove(i);
                    break;
                }
            }
        } else if (decision == 5) {
            for (int i = 0; i < aiHand.size(); i++) {
                if ((aiHand.get(i).getValue().equals(strMatch2))) {
                    d1.getDiscardPile().push(aiHand.get(i));
                    aiHand.remove(i);
                    break;
                }
            }
        } else if (decision == 6) {
            for (int i = 0; i < aiHand.size(); i++) {
                if ((aiHand.get(i).getValue().equals(strMatch2))) {
                    d1.getDiscardPile().push(aiHand.get(i));
                    aiHand.remove(i);
                    break;
                }
            }
        } else if (decision == 7) {
            for (int i = 0; i < aiHand.size(); i++) {
                if (!(aiHand.get(i).getValue().equals(strMatch1))) {
                    d1.getDiscardPile().push(aiHand.get(i));
                    aiHand.remove(i);
                    break;
                }
            }
        } else if (decision == 8) {
            for (int i = 0; i < aiHand.size(); i++) {
                if ((aiHand.get(i).getValue().equals(strMatch2))) {
                    d1.getDiscardPile().push(aiHand.get(i));
                    aiHand.remove(i);
                    break;
                }
            }
        } else if (decision == 9) {
            for (int i = 0; i < aiHand.size(); i++) {
                if (!(aiHand.get(i).getValue().equals(strMatch3))) {
                    d1.getDiscardPile().push(aiHand.get(i));
                    aiHand.remove(i);
                    break;
                }
            }
        } else {
            d1.getDiscardPile().push(aiHand.get(chooseRand));
            aiHand.remove(chooseRand);
        }

        // If AI wins return true
        if ((aiHand.get(0).getValue().equals(aiHand.get(1).getValue())) && (aiHand.get(1).getValue().equals(aiHand.get(2).getValue()))
                && (aiHand.get(2).getValue().equals(aiHand.get(3).getValue()))) {

            return true;

        }

        return false;

    }

}
