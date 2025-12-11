package edu.grinnell.csc207.TextAdventure;


import java.util.List;

public interface Room {

    public static Room currentRoom = null;

    //wait in the room for one turn
    public void waitResponse(); 

    //go in the given cardinal direction
    public Room goResponse(String direction);

    //talk to the given object found in the room
    public void talkToResponse();

    //pick up the given item found in the room
    public void pickUpResponse();

    //use the given item found in the player's inventory
    public void useResponse();

    //attack the given object found in the room
    public void attackResponse();
    
    //look at the given object found in the room
    public void lookUpResponse();

    //shows how much health the player has
    public void showHealth(int hp);

}
