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

    /**
     * @returns whether the interactable is an item
     */
    public boolean isItem(){
        return false;
    }

    /**
     * Gives a description of the interactable
     */
    public String getDes(){
        if(living){
            return des;
        } else{
            String str = "The corpse you are looking is already starting to rot. The smell is unbearable.";
            return str;
        }
    }


    /**
     * gets the response to the player talking to the interactable
     */
    public Item response(Scanner scan, int gold){
        System.out.println("There is no response. Your opponents have lost all semblence of humanity in their lust for battle.");
        return null;

    }

    /**
     * Returns the damage that an enemy will take from a player
     * @param playerAtk : the player's attack
     * @returns the amount of damage the enemy takes
     */
    public int damageTaken(int playerAtk){
        hp -= playerAtk-def;
        int dmg = playerAtk-def;
        if(dmg < 0){
            return 0;
        }
        return dmg;
    }

    /**
     * Returns the amount of damage an enemy will do to a player
     * @param playerDef : the player's defense
     * @returns the amount of damage the player will take.
     */
    public int damageDone(int playerDef){
        return atk-playerDef;
    }

    /**
     * Sets the enemy's hp to a new value
     * @param newHP : the new hp
    */
    public void setHP(int newHP){
        hp = newHP;
    }

    /**
     * @returns whether an enemy is alive or not.
     */
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
