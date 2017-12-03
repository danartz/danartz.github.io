/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Random;

/**
 *
 * @author danar
 */
public class AIPlayer extends Player {

    private int difficultyLevel;

    // Constructor checks the marker of the human player and then makes the AI whatever marker the human isnt
    public AIPlayer(char marker, int difficulty) {
        if (marker == 'X') {
            super.setMarker('O');
        } else {
            super.setMarker('X');
        }

        this.difficultyLevel = difficulty;
    }

    @Override
    public int move(Board theBoard, char playerMarker) {
        int rando = (int) (1 + Math.random() * 3); // randomly generates a number between 1 and 3 so when set to beginner, the checks only happen 1/3 of the time
        char emptySlot = 'f'; // used to store the empty space before 2 in a row
        int playerCount = 0, aiCount = 0;
        boolean endOfTurn = false;  // used as a flag so the AI doesn't go multiple times in 1 turn

        // If user selects Expert no randomizer
        if (this.difficultyLevel == 1) {

            // checks horizontals for AI 
            if (endOfTurn == false) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (theBoard.getPosition(i, j) >= 49 && theBoard.getPosition(i, j) <= 57) {
                            emptySlot = theBoard.getPosition(i, j); // gets position of the empty slot
                        } else if (theBoard.getPosition(i, j) == getMarker()) {
                            aiCount++;
                        }
                        //if AI detects 2 in a row of its own marker it will end the game
                        if ((aiCount == 2) && (emptySlot > 48 && emptySlot < 58)) {
                            for (int k = 0; k < 3; k++) {
                                if (theBoard.getPosition(i, k) == emptySlot) {
                                    theBoard.assignBoard(i, k, getMarker());  // assigns marker where empty slot was if 2 in a row are detected after it
                                    endOfTurn = true;
                                    break;
                                }

                            }
                            
                        }
                    }
                    playerCount = 0;
                    aiCount = 0;
                    if (endOfTurn == true) {
                        break;
                    }
                }
            }

            // checks horizontals for human
            if (endOfTurn == false) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (theBoard.getPosition(i, j) >= 49 && theBoard.getPosition(i, j) <= 57) {
                            emptySlot = theBoard.getPosition(i, j);
                        } else if (theBoard.getPosition(i, j) == playerMarker) {
                            playerCount++;
                        }
                        //if AI detects 2 in a row of the human marker it will block it
                        if ((playerCount == 2) && (emptySlot > 48 && emptySlot < 58)) {
                            for (int k = 0; k < 3; k++) {
                                if (theBoard.getPosition(i, k) == emptySlot) {
                                    theBoard.assignBoard(i, k, getMarker()); // assigns marker where empty slot was if 2 in a row are detected after it
                                    endOfTurn = true;
                                    break;
                                }

                            }
                        }
                    }
                    playerCount = 0;
                    aiCount = 0;
                    if (endOfTurn == true) {
                        break;
                    }
                }
            }

            // checks ai verticals - same type of algorithm as the code for horizontals
            if (endOfTurn == false) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (theBoard.getPosition(j, i) >= 49 && theBoard.getPosition(j, i) <= 57) {
                            emptySlot = theBoard.getPosition(j, i);
                        } else if (theBoard.getPosition(j, i) == getMarker()) {
                            aiCount++;

                        }
                        if ((aiCount == 2) && (emptySlot > 48 && emptySlot < 58)) {
                            for (int k = 0; k < 3; k++) {
                                if (theBoard.getPosition(k, i) != 'X' && theBoard.getPosition(k, i) != 'O') {

                                    theBoard.assignBoard(k, i, getMarker());
                                    endOfTurn = true;
                                    break;
                                }

                            }

                        }
                    }
                    aiCount = 0;
                    playerCount = 0;
                    if (endOfTurn == true) {
                        break;
                    }
                }
            }

            // checks human verticals
            if (endOfTurn == false) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (theBoard.getPosition(j, i) >= 49 && theBoard.getPosition(j, i) <= 57) {
                            emptySlot = theBoard.getPosition(j, i);
                        } else if (theBoard.getPosition(j, i) == playerMarker) {
                            playerCount++;
                        }
                        if ((playerCount == 2) && (emptySlot > 48 && emptySlot < 58)) {
                            for (int k = 0; k < 3; k++) {
                                if (theBoard.getPosition(k, i) != 'X' && theBoard.getPosition(k, i) != 'O') {

                                    theBoard.assignBoard(k, i, getMarker());
                                    endOfTurn = true;
                                    break;
                                }

                            }
                        }
                    }
                    aiCount = 0;
                    playerCount = 0;
                    if (endOfTurn == true) {
                        break;
                    }
                }
            }

            // Down diagonal right  Checks every combination of empty spaces for down diagonal right and puts a marker where there is one if 2 in a row are detected
            if (endOfTurn == false) {
                if ((theBoard.getPosition(0, 2) == playerMarker && theBoard.getPosition(1, 1) == playerMarker && theBoard.getPosition(2, 0) != 'O' && theBoard.getPosition(2, 0) != 'X')
                        || (theBoard.getPosition(0, 2) == 'X' && theBoard.getPosition(1, 1) == 'X' && theBoard.getPosition(2, 0) != 'X' && theBoard.getPosition(2, 0) != 'O')) {
                    theBoard.assignBoard(2, 0, getMarker());
                    endOfTurn = true;
                } else if ((theBoard.getPosition(0, 2) == 'O' && theBoard.getPosition(1, 1) != 'O' && theBoard.getPosition(1, 1) != 'X' && theBoard.getPosition(2, 0) == 'O')
                        || (theBoard.getPosition(0, 2) == 'X' && theBoard.getPosition(1, 1) != 'X' && theBoard.getPosition(1, 1) != 'O' && theBoard.getPosition(2, 0) == 'X')) {
                    theBoard.assignBoard(1, 1, getMarker());
                    endOfTurn = true;
                } else if ((theBoard.getPosition(0, 2) != 'O' && theBoard.getPosition(0, 2) != 'X' && theBoard.getPosition(1, 1) == 'O' && theBoard.getPosition(2, 0) == 'O')
                        || (theBoard.getPosition(0, 2) != 'X' && theBoard.getPosition(0, 2) != 'O' && theBoard.getPosition(1, 1) == 'X' && theBoard.getPosition(2, 0) == 'X')) {
                    theBoard.assignBoard(0, 2, getMarker());
                    endOfTurn = true;
                }
            }

            

            // Down diagonal left Checks every combination of empty spaces for down diagonal left and puts a marker where there is one if 2 in a row are detected
            if (endOfTurn == false) {
                if ((theBoard.getPosition(0, 0) == 'O' && theBoard.getPosition(1, 1) == 'O' && theBoard.getPosition(2, 2) != 'O' && theBoard.getPosition(2, 2) != 'X')
                        || (theBoard.getPosition(0, 0) == 'X' && theBoard.getPosition(1, 1) == 'X' && theBoard.getPosition(2, 2) != 'X' && theBoard.getPosition(2, 2) != 'O')) {
                    theBoard.assignBoard(2, 2, getMarker());
                    endOfTurn = true;
                } else if ((theBoard.getPosition(0, 0) == 'O' && theBoard.getPosition(1, 1) != 'O' && theBoard.getPosition(1, 1) != 'X' && theBoard.getPosition(2, 2) == 'O')
                        || (theBoard.getPosition(0, 0) == 'X' && theBoard.getPosition(1, 1) != 'X' && theBoard.getPosition(1, 1) != 'O' && theBoard.getPosition(2, 2) == 'X')) {
                    theBoard.assignBoard(1, 1, getMarker());
                    endOfTurn = true;
                } else if ((theBoard.getPosition(0, 0) != 'O' && theBoard.getPosition(0, 0) != 'X' && theBoard.getPosition(1, 1) == 'O' && theBoard.getPosition(2, 2) == 'O')
                        || (theBoard.getPosition(0, 0) != 'X' && theBoard.getPosition(0, 0) != 'O' && theBoard.getPosition(1, 1) == 'X' && theBoard.getPosition(2, 2) == 'X')) {
                    theBoard.assignBoard(0, 0, getMarker());
                    endOfTurn = true;
                }
            }
            
           

            // Check diagonals to be full, if they are full place a marker in adjacent corner
            if (endOfTurn == false) {
                if (((theBoard.getPosition(0, 2) == playerMarker || theBoard.getPosition(0, 2) == getMarker()) && (theBoard.getPosition(1, 1) == playerMarker
                        || theBoard.getPosition(1, 1) == getMarker())
                        && (theBoard.getPosition(2, 0) == playerMarker || theBoard.getPosition(2, 0) == getMarker()))) {
                    if (theBoard.getPosition(2, 2) != playerMarker && theBoard.getPosition(2, 2) != getMarker() &&((theBoard.getPosition(0,1) ==playerMarker
                            || theBoard.getPosition(0, 1) == getMarker()) && (theBoard.getPosition(1, 1) ==playerMarker || theBoard.getPosition(1, 1) ==getMarker()))) {
                        theBoard.assignBoard(2, 2, getMarker());
                        endOfTurn = true;
                    } else if (theBoard.getPosition(0, 0) != playerMarker && theBoard.getPosition(0, 0) != getMarker() &&((theBoard.getPosition(0,1) ==playerMarker
                            || theBoard.getPosition(0, 1) == getMarker()) && (theBoard.getPosition(1, 1) ==playerMarker || theBoard.getPosition(1, 1) ==getMarker()))) {
                        theBoard.assignBoard(0, 0, getMarker());
                        endOfTurn = true;
                    }
                }
            }
            // Check diagonal to be full, if they are full place a marker in adjacent corner
            if (endOfTurn == false) {
                if (((theBoard.getPosition(0, 0) == playerMarker || theBoard.getPosition(0, 0) == getMarker()) && (theBoard.getPosition(1, 1) == playerMarker
                        || theBoard.getPosition(1, 1) == getMarker())
                        && (theBoard.getPosition(2, 2) == playerMarker || theBoard.getPosition(2, 2) == getMarker()))) {
                    if (theBoard.getPosition(0, 2) != playerMarker && theBoard.getPosition(0, 2) != getMarker() && ((theBoard.getPosition(0, 1) == playerMarker
                            || theBoard.getPosition(0, 1) == getMarker()) && (theBoard.getPosition(1,1) == playerMarker || theBoard.getPosition(1,1) == getMarker()))) {
                        theBoard.assignBoard(0, 2, getMarker());
                        endOfTurn = true;
                    } else if (theBoard.getPosition(2, 0) != playerMarker && theBoard.getPosition(2, 0) != getMarker() && ((theBoard.getPosition(0, 1) == playerMarker
                            || theBoard.getPosition(0, 1) == getMarker()) && (theBoard.getPosition(1, 1) == playerMarker || theBoard.getPosition(1, 1) == getMarker()))) {
                        theBoard.assignBoard(2, 0, getMarker());
                        endOfTurn = true;
                    }
                }
            }
            
             // new corner cases check center and corner then place marker in adjacent 2 spot to block potential win
           if (endOfTurn == false) {
                if ((theBoard.getPosition(2, 0) == playerMarker && theBoard.getPosition(1, 1) == getMarker() && theBoard.getPosition(0, 2)
                        == playerMarker && (theBoard.getPosition(0, 1) != playerMarker && theBoard.getPosition(0,1) != getMarker()))) {
                    theBoard.assignBoard(0,1, getMarker());
                    endOfTurn = true;
                }else if ((theBoard.getPosition(0, 0) == playerMarker && theBoard.getPosition(1, 1) == getMarker() && theBoard.getPosition(2, 2)
                        == playerMarker && (theBoard.getPosition(0, 1) != playerMarker && theBoard.getPosition(0,1) != getMarker()))) {
                    theBoard.assignBoard(0,1, getMarker());
                    endOfTurn = true;
                }
            }
           
           // New case if player picks the 5 and 9 position first, then AI counters them by placing a marker in the 3 slot
           if (endOfTurn == false) {
                if ((theBoard.getPosition(0, 0) != playerMarker && theBoard.getPosition(0, 2) != playerMarker && theBoard.getPosition(1, 1) == playerMarker && theBoard.getPosition(2, 2)
                        == playerMarker && (theBoard.getPosition(0, 1) != playerMarker && theBoard.getPosition(0, 1) != getMarker()))) {
                    theBoard.assignBoard(0, 2, getMarker());
                    System.out.println("working");
                    endOfTurn = true;
                }
            }
           
           
            // If character picks O then computer's second move is 3 instead of 2, closing a winning strategy
            if (endOfTurn == false) {
                if (playerMarker == 'O' && theBoard.getPosition(0, 2) != playerMarker && theBoard.getPosition(0, 2) != getMarker() 
                        && theBoard.getPosition(1, 1) == getMarker()) {
                    theBoard.assignBoard(0, 2, getMarker());
                    endOfTurn = true;
                }
            }

            // If there's nothing for the computer to counter it fills the first empty space
            if (endOfTurn == false) {
                // Puts marker in center spot to make it more difficult for the human to win
                if (theBoard.getPosition(1, 1) != playerMarker && theBoard.getPosition(1, 1) != getMarker()) {
                    theBoard.assignBoard(1, 1, getMarker());
                    endOfTurn = true;

                }
                else {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (theBoard.getPosition(i, j) != 'X' && theBoard.getPosition(i, j) != 'O') {
                                theBoard.assignBoard(i, j, getMarker());
                                endOfTurn = true;
                                break;
                            }
                        }
                        if (endOfTurn == true) {
                            break;
                        }
                    }
                }
            }

        } // If user selects Beginner it randomizes chances of doing checks to make it less intelligent - very dumbed down ai
        else {
            if (rando == 1) {
                // checks horizontals with weaker algorithm to make the AI a bit dumber 
                if (endOfTurn == false) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (theBoard.getPosition(i, j) >= 49 && theBoard.getPosition(i, j) <= 57) {
                                emptySlot = theBoard.getPosition(i, j);
                            } else if (theBoard.getPosition(i, j) == getMarker()) {
                                aiCount++;
                            } else if (theBoard.getPosition(i, j) == playerMarker) {
                                playerCount++;
                            }
                            //if AI detects 2 in a row of its own marker it will end the game
                            if ((aiCount == 2) && (emptySlot > 48 && emptySlot < 58)) {
                                for (int k = 0; k < 3; k++) {
                                    if (theBoard.getPosition(i, k) == emptySlot) {
                                        theBoard.assignBoard(i, k, getMarker());
                                        endOfTurn = true;
                                        break;
                                    }

                                }
                                // Checks if opponent has 2 in a row secondly
                            } else if ((playerCount == 2) && (emptySlot > 48 && emptySlot < 58)) {
                                for (int k = 0; k < 3; k++) {
                                    if (theBoard.getPosition(i, k) == emptySlot) {
                                        theBoard.assignBoard(i, k, getMarker());
                                        endOfTurn = true;
                                        break;
                                    }

                                }
                            }
                        }
                        playerCount = 0;
                        aiCount = 0;
                        if (endOfTurn == true) {
                            break;
                        }
                    }
                }
                // checks verticals REMEMBER TO CONVERT TO NEW ALGORITHM
                if (endOfTurn == false) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (theBoard.getPosition(j, i) >= 49 && theBoard.getPosition(j, i) <= 57) {
                                emptySlot = theBoard.getPosition(j, i);
                            } else if (theBoard.getPosition(j, i) == getMarker()) {
                                aiCount++;

                            } else if (theBoard.getPosition(j, i) == playerMarker) {
                                playerCount++;
                            }
                            if ((aiCount == 2) && (emptySlot > 48 && emptySlot < 58)) {
                                for (int k = 0; k < 3; k++) {
                                    if (theBoard.getPosition(k, i) != 'X' && theBoard.getPosition(k, i) != 'O') {

                                        theBoard.assignBoard(k, i, getMarker());
                                        endOfTurn = true;
                                        break;
                                    }

                                }

                            } else if ((playerCount == 2) && (emptySlot > 48 && emptySlot < 58)) {
                                for (int k = 0; k < 3; k++) {
                                    if (theBoard.getPosition(k, i) != 'X' && theBoard.getPosition(k, i) != 'O') {

                                        theBoard.assignBoard(k, i, getMarker());
                                        endOfTurn = true;
                                        break;
                                    }

                                }
                            }
                        }
                        aiCount = 0;
                        playerCount = 0;
                        if (endOfTurn == true) {
                            break;
                        }
                    }
                }

                // Down diagonal right
                if (endOfTurn == false) {
                    if ((theBoard.getPosition(0, 2) == 'O' && theBoard.getPosition(1, 1) == 'O' && theBoard.getPosition(2, 0) != 'O' && theBoard.getPosition(2, 0) != 'X')
                            || (theBoard.getPosition(0, 2) == 'X' && theBoard.getPosition(1, 1) == 'X' && theBoard.getPosition(2, 0) != 'X' && theBoard.getPosition(2, 0) != 'O')) {
                        theBoard.assignBoard(2, 0, getMarker());
                        endOfTurn = true;
                    } else if ((theBoard.getPosition(0, 2) == 'O' && theBoard.getPosition(1, 1) != 'O' && theBoard.getPosition(1, 1) != 'X' && theBoard.getPosition(2, 0) == 'O')
                            || (theBoard.getPosition(0, 2) == 'X' && theBoard.getPosition(1, 1) != 'X' && theBoard.getPosition(1, 1) != 'O' && theBoard.getPosition(2, 0) == 'X')) {
                        theBoard.assignBoard(1, 1, getMarker());
                        endOfTurn = true;
                    } else if ((theBoard.getPosition(0, 2) != 'O' && theBoard.getPosition(0, 2) != 'X' && theBoard.getPosition(1, 1) == 'O' && theBoard.getPosition(2, 0) == 'O')
                            || (theBoard.getPosition(0, 2) != 'X' && theBoard.getPosition(0, 2) != 'O' && theBoard.getPosition(1, 1) == 'X' && theBoard.getPosition(2, 0) == 'X')) {
                        theBoard.assignBoard(0, 2, getMarker());
                        endOfTurn = true;
                    }
                }

                // Down diagonal left
                if (endOfTurn == false) {
                    if ((theBoard.getPosition(0, 0) == 'O' && theBoard.getPosition(1, 1) == 'O' && theBoard.getPosition(2, 2) != 'O' && theBoard.getPosition(2, 2) != 'X')
                            || (theBoard.getPosition(0, 0) == 'X' && theBoard.getPosition(1, 1) == 'X' && theBoard.getPosition(2, 2) != 'X' && theBoard.getPosition(2, 2) != 'O')) {
                        theBoard.assignBoard(2, 2, getMarker()); // weird error computer cheated
                        endOfTurn = true;
                    } else if ((theBoard.getPosition(0, 0) == 'O' && theBoard.getPosition(1, 1) != 'O' && theBoard.getPosition(1, 1) != 'X' && theBoard.getPosition(2, 2) == 'O')
                            || (theBoard.getPosition(0, 0) == 'X' && theBoard.getPosition(1, 1) != 'X' && theBoard.getPosition(1, 1) != 'O' && theBoard.getPosition(2, 2) == 'X')) {
                        theBoard.assignBoard(1, 1, getMarker());
                        endOfTurn = true;
                    } else if ((theBoard.getPosition(0, 0) != 'O' && theBoard.getPosition(0, 0) != 'X' && theBoard.getPosition(1, 1) == 'O' && theBoard.getPosition(2, 2) == 'O')
                            || (theBoard.getPosition(0, 0) != 'X' && theBoard.getPosition(0, 0) != 'O' && theBoard.getPosition(1, 1) == 'X' && theBoard.getPosition(2, 2) == 'X')) {
                        theBoard.assignBoard(0, 0, getMarker());
                        endOfTurn = true;
                    }
                }

                // AI fills first empty slot
                if (endOfTurn == false) {
                    // If center isn't occupied AI fills slot to make it more difficult for the human
                    if (theBoard.getPosition(1, 1) != 'O' && theBoard.getPosition(1, 1) != 'X') {
                        theBoard.assignBoard(1, 1, getMarker());
                        endOfTurn = true;
                    } else {
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                if (theBoard.getPosition(i, j) != 'X' && theBoard.getPosition(i, j) != 'O') {
                                    theBoard.assignBoard(i, j, getMarker());
                                    endOfTurn = true;
                                    break;
                                }
                            }
                            if (endOfTurn == true) {
                                break;
                            }
                        }
                    }
                }
            } else { // If a 1 or 2 come back from random number generator then the AI doesn't look for 2 in a row to block, it simply fills the first empty space and fills the center slot
                if (endOfTurn == false) {
                    if (theBoard.getPosition(1, 1) != 'O' && theBoard.getPosition(1, 1) != 'X') {
                        theBoard.assignBoard(1, 1, getMarker());
                        endOfTurn = true;
                    } else {
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                if (theBoard.getPosition(i, j) != 'X' && theBoard.getPosition(i, j) != 'O') {
                                    theBoard.assignBoard(i, j, getMarker());
                                    endOfTurn = true;
                                    break;
                                }
                            }
                            if (endOfTurn == true) {
                                break;
                            }
                        }
                    }
                }
            }
        }

        return 1;

    }
    // Returns difficulty
    public int getDifficultyLevel() {
        return difficultyLevel;
    }
    // sets difficulty
    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    // Outputs a string describing the difficulty selected
    public String convertDifficulty() {
        int aiLevel = getDifficultyLevel();
        if (aiLevel == 0) {
            return "Normal";
        } else {
            return "Expert";
        }
    }

}
