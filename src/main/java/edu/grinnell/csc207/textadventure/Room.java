package edu.grinnell.csc207.textadventure;

import java.util.Scanner;

public interface Room {

    public static Room currentRoom = null;

    //wait in the room for one turn
    public void waitResponse(); 

    //gets room type
    public String getType();

    //gets room description
    public void roomDes();

    //go in the given cardinal direction
    public Room goResponse(String direction);

    //talk to the given object found in the room
    public Item talkToResponse(Scanner scan);

    //attack the given object found in the room
    public boolean attackResponse(Scanner input);
    
    //look at the given object found in the room
    public void lookUpResponse(int idx);

    //shows how much health the player has
    public void showHealth(int hp);

    //generates the NPCs of the room
    public void genNPCs();

}
