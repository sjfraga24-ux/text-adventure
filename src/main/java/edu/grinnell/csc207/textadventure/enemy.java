package edu.grinnell.csc207.textadventure;

public class enemy implements interactable{
    private int atk;
    private int def;
    private int hp;
    private String des;

    
    public enemy(int atk, int def, int hp, String des){
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
        return des;
    }


    //gets the response to the player talking to the interactable
    public item response(){
        System.out.println("There is no response. Your opponents have lost all semblence of humanity in their lust for battle.");
        return null;

    }

    public void damageTaken(int playerAtk){
        hp -= playerAtk-def;
    }

    public int damageDone(int playerDef){
        return atk-playerDef;
    }

    public void setHP(int newHP){
        hp = newHP;
    }

    public boolean getAlive(){
        if(hp > 0 ){
            return true;
        } else{
            return false;
        }
    }

}
