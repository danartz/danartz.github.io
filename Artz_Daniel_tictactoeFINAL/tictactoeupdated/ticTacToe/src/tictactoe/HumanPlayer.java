/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Scanner;

/**
 *
 * @author dan
 */
public class HumanPlayer extends Player {

    private String name;

    public HumanPlayer(String name, char marker) {
        super.setMarker(marker);
        this.name = name;
    }

    // Human move function
    @Override
    public int move(Board theBoard, char playerMarker) {
        Scanner input = new Scanner(System.in);
        System.out.println("Select a position between 1 and 9 to move to. If you");
        System.out.println("type 2 characters EX: 15 then the first character 1 will be used:");
        char playerMoveC = input.next().charAt(0); // If the user types something that isn't an integer the program breaks so I'm using a char specifically for input
        int playerMove = (int) playerMoveC - 48; // converts input to integer
        int attemptedMove = playerMove;
        boolean moveValid = isValidMove(attemptedMove, theBoard); // calls function that validates move
        while (moveValid == false) {  // if move is invalid then user is forced to repeat move
            System.out.println("Invalid move. Choose an empty space between 1 and 9");
            System.out.println("Select a position between 1 and 9 to move to. If you");
            System.out.println("type 2 characters EX: 15 then the first character 1 will be used:");
            playerMoveC = input.next().charAt(0);
            playerMove = (int) playerMoveC - 48;
            attemptedMove = playerMove;
            moveValid = isValidMove(attemptedMove, theBoard);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // added 48 to for the ascii conversion since our outline stated move had to be an int
                // when the board was a character array
                if (theBoard.getPosition(i, j) == (char) attemptedMove + 48) {
                    theBoard.assignBoard(i, j, getMarker());  // fixing encapsulation issue
                }
            }
        }
        return playerMove;
    }
    // Sets player's name
    public void setName(String name) {
        this.name = name;
    }
    // gets player's name
    public String getName() {
        return name;
    }

}
