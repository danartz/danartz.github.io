/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author dan
 */
public abstract class Player extends Board {
    char marker;
    
    // Empty constructor
    public Player ()
    {
        
    }
    
    // abstract move function implemented differently for human and AI classes
    public abstract int move(Board theBoard, char playerMarker);
   
    // Returns marker
    public char getMarker() {
        return marker;
    }
    
    // sets marker
    public void setMarker(char marker) {
        this.marker = marker;
    }
    
    
}
