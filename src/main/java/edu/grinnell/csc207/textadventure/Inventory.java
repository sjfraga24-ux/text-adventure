package edu.grinnell.csc207.textadventure;

import java.util.ArrayList;
import java.util.List;

public class Inventory{
    List<Item> inventory = new ArrayList<>();

    public Inventory(List<Item> inventory){
        this.inventory = inventory;
    }

    public void add(Item newItm){
        inventory.add(newItm);
    }

    public boolean inInven(String item){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).getTitle().toLowerCase().equals(item)){
                return true;
            }
        }
        return false;
    }

    public Item getItem(String name){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).getTitle().toLowerCase().equals(name)){
                return inventory.get(i);
            }
        }
        return null;
    }

    public void removeItem(Item itm){
        inventory.remove(inventory.indexOf(itm));
        System.out.println(itm.getTitle() + " has been removed.");
    }


    public void checkInventory(){
        System.out.println("Inventory:");
        for(int i =0; i < inventory.size(); i++){
            System.out.println(inventory.get(i).getTitle());
            System.out.println("Item Description: " + inventory.get(i).getDes());
        }
    }
}