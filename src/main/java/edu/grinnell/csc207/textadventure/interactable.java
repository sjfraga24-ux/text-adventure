package edu.grinnell.csc207.textadventure;

public interface interactable {
    
    //Returns true if the interactable is an item
    public boolean isItem();

    //gives a description of the interactable
    public String getDes();

    //gets the response to the player talking to the interactable
    public item response();


}
