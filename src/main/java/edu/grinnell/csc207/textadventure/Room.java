package edu.grinnell.csc207.textadventure;


public interface Room {

    public static Room currentRoom = null;

    //wait in the room for one turn
    public void waitResponse(); 

    //go in the given cardinal direction
    public Room goResponse(String direction);

    //talk to the given object found in the room
    public void talkToResponse(interactable object);

    //attack the given object found in the room
    public boolean attackResponse(interactable object);
    
    //look at the given object found in the room
    public void lookUpResponse(interactable object);

    //shows how much health the player has
    public void showHealth(int hp);

}
