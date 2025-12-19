package edu.grinnell.csc207.textadventure;

import java.util.Scanner;

public interface Interactable {
    
    /**
     * Returns true if the interactable is an item
     */
    public boolean isItem();

    /**
     * gives a description of the interactable
     */
    public String getDes();

    /**
     * gets the response to the player talking to the interactable
     */
    public Item response(Scanner scan, int gold);


}
