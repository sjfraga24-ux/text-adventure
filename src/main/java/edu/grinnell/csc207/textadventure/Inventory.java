package edu.grinnell.csc207.textadventure;

import java.util.ArrayList;
import java.util.List;

public class Inventory{
    List<Item> inventory = new ArrayList<>();

    /**
     * creates a new inventory object
     */
    public Inventory(List<Item> inventory){
        this.inventory = inventory;
    }

    /**
     * Adds a new item to the inventory
     * @param newItm : the item being added
     */
    public void add(Item newItm){
        inventory.add(newItm);
    }

    /**
     * Checks if item is in inventory
     * @param item : the item that's being checked
     * @returns whether the item is in the inventory
     */
    public boolean inInven(String item){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).getTitle().toLowerCase().equals(item)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param name : the name of the item
     * @returns the item in the inventory
     */
    public Item getItem(String name){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).getTitle().toLowerCase().equals(name)){
                return inventory.get(i);
            }
        }
        return null;
    }

    /**
     * removes the item from inventory
     * @param itm : the item being removed
     */
    public void removeItem(Item itm){
        inventory.remove(inventory.indexOf(itm));
        System.out.println(itm.getTitle() + " has been removed.");
    }

    /**
     * prints out the contents of inventory
     */
    public void checkInventory(){
        System.out.println("Inventory:");
        for(int i =0; i < inventory.size(); i++){
            System.out.println(inventory.get(i).getTitle());
            System.out.println("Item Description: " + inventory.get(i).getDes());
        }
    }
}