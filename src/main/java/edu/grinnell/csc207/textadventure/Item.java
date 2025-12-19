package edu.grinnell.csc207.textadventure;

import java.util.Scanner;

public class Item implements Interactable{
    public boolean usable;
    private String name;
    private String des;
    private String effect = "";

    public Item(String name, String des, boolean usable){
        this.usable = usable;
        this.name = name;
        this.des = des;
    }

    public Item(String name, String des, boolean usable, String effect){
        this.usable = usable;
        this.name = name;
        this.des = des;
        this.effect = effect;
    }

    //Returns true if the interactable is an item
    public boolean isItem(){
        return true;
    }

    //gives a description of the interactable
    public String getDes(){
        return des;
    }

    //gets the effect of the interactable
    public String getEffect(){
        return effect;
    }

    //gets the name/title of an interactable
    public String getTitle(){
        return name;
    }

    //gets the response to the player talking to the interactable
    public Item response(Scanner scan, int gold){
        System.out.println("This" + name + "does not have a mouth and therefore cannot speak.");
        return null;
    }
    
}
