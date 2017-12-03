/* 
 * 
 * 
 * 
 */
package tictactoe;

/**
 *
 * @author danar
 */
public class Board {

    private char[][] theBoard = new char[3][3];

    // Calls the fillboard constructor
    public Board() {
        fillBoard();
    }

    // Fills the board character array with numbers 1-9
    public void fillBoard() {
        char boardPosition = '1';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                theBoard[i][j] = boardPosition;
                boardPosition++;

            }

        }
    }

    // Gets the current position of the array and returns it
    public char getPosition(int row, int col) {
        return theBoard[row][col];
    }
    
    // Prints the board in a formated style
    public void displayBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%3c",theBoard[i][j]);

            }
            System.out.println("");
        }
    }

    // Locates specific location in array and assigns the marker to that position
    public void assignBoard(int row, int collum, char marker) {

        theBoard[row][collum] = marker;
    }

    // Checks if the move is valid by checking it's within range and isn't selecting an already filled space
    public boolean isValidMove(int attemptedMove, Board b1) {
        char storedSpot = '/';

        if (attemptedMove < 1 || attemptedMove > 9) {
            return false;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char moveConvert = (char) (attemptedMove + 48);
                if (b1.getPosition(i, j) == moveConvert) {
                    return true;
                }

            }
        }

        return false;

    }

    // Winning Conditions, simply checks for 3 in a row in every possible win condition and returns the character in that spot
    public char gameStatus() {
        if (theBoard[0][0] == theBoard[0][1] && theBoard[0][1] == theBoard[0][2]) {
            return theBoard[0][0];
        }
        if (theBoard[1][0] == theBoard[1][1] && theBoard[1][1] == theBoard[1][2]) {
            return theBoard[1][0];
        }
        if (theBoard[2][0] == theBoard[2][1] && theBoard[2][1] == theBoard[2][2]) {
            return theBoard[2][0];
        }
        if (theBoard[0][0] == theBoard[1][0] && theBoard[1][0] == theBoard[2][0]) {
            return theBoard[0][0];
        }
        if (theBoard[0][1] == theBoard[1][1] && theBoard[1][1] == theBoard[2][1]) {
            return theBoard[0][1];
        }
        if (theBoard[0][2] == theBoard[1][2] && theBoard[1][2] == theBoard[2][2]) {
            return theBoard[0][2];
        }
        if (theBoard[0][0] == theBoard[1][1] && theBoard[1][1] == theBoard[2][2]) {
            return theBoard[0][0];
        }
        if (theBoard[0][2] == theBoard[1][1] && theBoard[1][1] == theBoard[2][0]) {
            return theBoard[0][2];
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (theBoard[i][j] != 'X' && theBoard[i][j] != 'O') {
                    return 'N';
                }
            }
        }

        return 'T';
    }
}
