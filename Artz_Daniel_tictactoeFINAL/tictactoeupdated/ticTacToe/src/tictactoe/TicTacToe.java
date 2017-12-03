/* THIS IS THE MAIN DRIVER
 * 
 * Daniel Artz
 * Trent Church
 * Jack Keller
 * Alan Fleming
 */
package tictactoe;

import java.util.Scanner;
import java.lang.*;

/**
 *
 * @author danar
 */
public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("What is your name?");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        System.out.println("Do you wish to be X or O? X's go first");
        char markerPre = input.next().charAt(0);
        char marker = Character.toUpperCase(markerPre); // converts x or o to uppercase to avoid user error
        // checks to make sure the player typed X or O and not an invalid character
        while (marker != 'X' && marker != 'O') {
            System.out.println("You must choose X or O.");
            System.out.println("Do you wish to be X or O? X's go first");
            markerPre = input.next().charAt(0);
            marker = Character.toUpperCase(markerPre);
        }

        System.out.println("Normal Difficulty or Expert? Type 0 for Normal, 1 for Expert.");
        char difficultyC = input.next().charAt(0); // If the user types something that isn't an integer the program breaks so I'm using a char specifically for input
        int difficulty = (int) difficultyC - 48; // converting char to int
        while (difficultyC < '0' || difficultyC > '1') {
            System.out.println("Select a valid difficulty level.");
            System.out.println("Normal Difficulty or Expert? Type 0 for Normal, 1 for Expert.");
            difficultyC = input.next().charAt(0);
            difficulty = (int) difficultyC - 48;
        }
        HumanPlayer p1 = new HumanPlayer(name, marker); // creates new player
        AIPlayer ai = new AIPlayer(marker, difficulty);  // creates new ai object
        System.out.println("You have chosen " + ai.convertDifficulty() + " Good luck " + p1.getName() + "!");
        Board b1 = new Board(); // creates new board object

        // Checks difficulty to do some output and checks if you're O and X to tell you to go second if you're O
        if (difficulty == 1) {
            System.out.println("I am The Machine 2.0 and I will DESTROY you... At tic tac toe.");
        }
        if (marker == 'O') {
            System.out.println("The Computer goes first!");
        }
        //Game loop
        while (b1.gameStatus() == 'N') {
            //if p1 == X go first else AI goes first
            if (marker == 'X') {  // if human picks X then they go first
                System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                b1.displayBoard();
                System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Now it's your turn!");
                p1.move(b1, marker);// calls player move function
                // checks if game ends after player moves
                if (b1.gameStatus() == p1.getMarker() || b1.gameStatus() == ai.getMarker() || b1.gameStatus() == 'T') {
                    System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    b1.displayBoard();
                    System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    continue;
                }
                System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                b1.displayBoard();
                System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Now it's the computer's turn!");
                ai.move(b1, marker); // calls AI move function
                // checks if game ends after AI moves
                if (b1.gameStatus() == p1.getMarker() || b1.gameStatus() == ai.getMarker() || b1.gameStatus() == 'T') {
                    System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    b1.displayBoard();
                    System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    continue;
                }
            } else {  // If player picks O and computer goes first
                System.out.println("Now it's the computer's turn!");
                ai.move(b1, marker);
                // checks if game ends after AI moves
                if (b1.gameStatus() == p1.getMarker() || b1.gameStatus() == ai.getMarker() || b1.gameStatus() == 'T') {
                    System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    b1.displayBoard();
                    System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    continue;
                }
                System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                b1.displayBoard();
                System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Now it's your turn!");
                p1.move(b1, marker);
                // checks if game ends after player moves
                if (b1.gameStatus() == p1.getMarker() || b1.gameStatus() == ai.getMarker() || b1.gameStatus() == 'T') {
                    System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    b1.displayBoard();
                    System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    continue;
                }
                System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                b1.displayBoard();
                System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            }

        }

        // Outputs text based on who won the match, or if there was a tie
        if (b1.gameStatus() == p1.getMarker()) {
            System.out.println("You win! Congrats!!! You beat The Machine ^_^");
        } else if (b1.gameStatus() == ai.getMarker() && difficulty == 0) {
            System.out.println("The Machine won, really sad! =(");
        } else if (b1.gameStatus() == ai.getMarker() && difficulty == 1) {
            System.out.println("The Machine 2.0 won, no surprise! =(");
        } else {
            System.out.println("You tied, try harder.");
        }

    }

}
