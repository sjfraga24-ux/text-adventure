package main.java.edu.grinnell.cscs207.textadventure.Interactions;

public class item implements interactable{
    public boolean usable;
    private String name;
    private String des;
    private String effect;

    public item(String name, String des, boolean usable){
        this.usable = usable;
        this.name = name;
        this.des = des;
    }

    public item(String name, String des, boolean usable, String effect){
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
    public void response(){
        System.out.println("This" + name + "does not have a mouth and therefore cannot speak.");
    }
    
}
