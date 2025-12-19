package edu.grinnell.csc207.textadventure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class NPC implements Interactable{
    boolean isShopkeep;
    boolean friendly;
    String des;
    List<Item> wares = new ArrayList<>();
    List<Item> gifts = new ArrayList<>();
    boolean inRoom = true;

    public NPC(boolean isShop, boolean mood, String des){
        isShopkeep = isShop;
        friendly = mood;
        this.des = des;
    }

    //Returns true if the interactable is an item
    public boolean isItem(){
        return false;
    }

    //gives a description of the NPC
    public String getDes(){
        return des;
    }

    public boolean stillHere(){
        return inRoom;
    }

    //gets the response to the player talking to the NPC
    public Item response(Scanner scan, int gold){
        Random rand = new Random();
        String str;
        if(friendly && isShopkeep){
            Item selling = wares.get(rand.nextInt(wares.size()));
            System.out.println("Shopkeep: Good day competitor! Are you looking to purchase a " + selling.getTitle() + "?");
                str = scan.next().toLowerCase();
                if(str.equals("yes") || str.equals("y")){
                    if(gold < 5){
                        System.out.println("Shopkeep: It seems like you're a couple coins short, friend. I'm afraid I can't give this to you for cheap.");
                    }else{
                        System.out.println("Shopkeep: Would you like to examine the " + selling.getTitle() + " I have for sale today?");
                        str = scan.next().toLowerCase();
                        if(str.equals("yes") || str.equals("y")){
                            System.out.println("The shopkeep shows off the " + selling.getTitle() + ". It does look pretty impressive.");
                            System.out.println("Item: " + selling.getTitle());
                            System.out.println("Description: " + selling.getDes());
                            System.out.print("Do you want to purchase this item?");
                            str = scan.next().toLowerCase();
                            if(str.equals("yes") || str.equals("y")){
                                System.out.println("Shopkeep: Thank you for your purchase!");
                                inRoom = false;
                                return selling;
                            } else if(str.equals("no") || str.equals("n")){
                                System.out.println("Shopkeep: That's too bad...");
                                return null;
                            } else {
                                return null;
                            }
                        } else if(str.equals("no") || str.equals("n")){
                            System.out.print("Very well! Do you wish to purchase this " + selling.getTitle() + "?");
                            str = scan.next().toLowerCase();
                            if(str.equals("yes") || str.equals("y")){
                                System.out.println("Shopkeep: Thank you for your purchase!");
                                inRoom = false;
                                return selling;
                            } else if(str.equals("no") || str.equals("n")){
                                System.out.println("Shopkeep: That's too bad...");
                                return null;
                            } else {
                                return null;
                            }
                        } else{
                            return null;
                        }
                    }
                }else if(str.equals("no") || str.equals("n")){
                    System.out.println("Shopkeep: That's too bad...");
                    return null;
                } else {
                    return null;
                }
            

        } else if(!friendly && isShopkeep){
            Item selling = wares.get(rand.nextInt(wares.size()));
            System.out.println("Shopkeep: You looking to buy something?");
            str = scan.next().toLowerCase();
            if(str.equals("yes") || str.equals("y")){
                System.out.println("Shopkeep: Show me the gold then.");
                if(gold < 5){
                    System.out.println("Shopkeep: No money no deal. Now get out of my face.");
                    return null;
                }else{
                    System.out.println("Once you've paid, the shopkeep hurls a raggedy sack at you before shuffling off into the crowd.");
                    System.out.println("You are now the proud owner of a " + selling.getTitle() + ". Congratulations!");
                    inRoom = false;
                    return selling;
                }
            }else if(str.equals("no") || str.equals("n")){
                System.out.println("Shopkeep: Well get out of my way then.");
                inRoom = false;
                return null;
            }
            
        }else if(friendly && !isShopkeep){
            Item charm = gifts.get(rand.nextInt(gifts.size()));
            System.out.println("Climber: Greetings, fellow competitor! On a scale of 1-10, how fare you today?");
            int mood = Integer.parseInt(scan.next());
            if(mood>5){
                System.out.println("Climber: Feeling confident then? I'd better keep my lucky charm close, just in case we end up on opposite sides of the battlefield.");
                inRoom = false;
                return null;
            } else if( mood == 5){
                System.out.println("Climber: Just a typical day for you then it seems.");
                inRoom = false;
                return null;
            }else{
                System.out.println("Climber: Don't be so down! Here, have this.");
                System.out.println("The Climber shoves something into your hand.");
                System.out.println("Climber: It's my lucky charm, hopefully it'll help you feel a bit better!");
                inRoom = false;
                return charm;
            }
            
        }else{
            System.out.println("Climber: Why have you come to this place? For glory, fortune, or entertainment?");
            str = scan.next().toLowerCase();
            if(str.equals("glory")){
                System.out.println("Climber: If you think you'll find fame here, you're gravely mistaken.");
                inRoom = false;
            }else if(str.equals("fortune")){
                System.out.println("Climber: So you wish to become rich off of the corpses of others. How admirable.");
                inRoom = false;
            }else if(str.equals("entertainment")){
                System.out.println("Climber: I despise people like you.");
                inRoom = false;
            } else{
                System.out.println("Climber: I shouldn't have expected a reasonable answer from your type.");
                inRoom = false;
            }
            return null;
        }
        return null;
    }

    public void fillWares(){
        wares.add(new Item("Health Potion", 
                            "A potion that will heal minor injuries and restore some HP", 
                            true));
        wares.add(new Item("Vase", 
                            "A fairly heavy vase, you can hit people with it.", 
                            true));
        wares.add(new Item("Amulet of Anubis", 
                            "Once this amulet is in your inventory, all of your attacks will be weaker.", 
                            false, "atk-"));
        wares.add(new Item("Helmet of Hope", 
                            "Once in your inventory, this helmet will increase your attack. It has a tiny duck engraved on the front. Truly inspiring.", 
                            false, "atk+"));
        wares.add(new Item("Chestplate of Despair", 
                            "Once in your inventory, this chestplate will decrease your defense. Its metal was forged in the fires that fuel the tower and shatters upon impact, impaling the wearer.", 
                            false, "def-"));

    }

    public void fillGifts(){
        gifts.add(new Item("Stick", "Its a stick. For some reason it was special to someone.", false));
        gifts.add(new Item("Leather Bracelet", "Its a worn bracelet with the words 'May your judgement be wise' engraved upon it.", false, "def+"));
        gifts.add(new Item("Weird Rock", "It really is a weird rock. You can throw it at people, but why would you want to?", true));
    }

}
