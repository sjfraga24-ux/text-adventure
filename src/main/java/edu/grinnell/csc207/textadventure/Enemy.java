package edu.grinnell.csc207.textadventure;

import java.util.Scanner;

public class Enemy implements Interactable{
    private int atk;
    private int def;
    private int hp;
    private String des;
    private boolean living;
    
    public Enemy(int atk, int def, int hp, String des){
        this.atk = atk;
        this.def = def;
        this.des = des;
        this.hp = hp;
    }

    //Returns true if the interactable is an item
    public boolean isItem(){
        return false;
    }

    //gives a description of the interactable
    public String getDes(){
        if(living){
            return des;
        } else{
            String str = "The corpse you are looking is already starting to rot. The smell is unbearable.";
            return str;
        }
    }


    //gets the response to the player talking to the interactable
    public Item response(Scanner scan, int gold){
        System.out.println("There is no response. Your opponents have lost all semblence of humanity in their lust for battle.");
        return null;

    }

    public int damageTaken(int playerAtk){
        hp -= playerAtk-def;
        int dmg = playerAtk-def;
        if(dmg < 0){
            return 0;
        }
        return dmg;
    }

    public int damageDone(int playerDef){
        return atk-playerDef;
    }

    public void setHP(int newHP){
        hp = newHP;
    }

    public boolean getAlive(){
        if(hp > 0 ){
            living = true;
            return living;
        } else{
            living = false;
            return living;
        }
    }

}
