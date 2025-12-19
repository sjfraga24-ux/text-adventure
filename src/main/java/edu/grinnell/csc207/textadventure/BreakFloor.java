package edu.grinnell.csc207.textadventure;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class BreakFloor implements Room{
    public Map<String, Room> directions;
    public Inventory invtry;
    public int numNPC;
    public List<NPC> allNPCs;
    public int gold;

    public BreakFloor(Map<String, Room> directions, Inventory invtry, int numNPC, int gold){
        this.directions = directions;
        this.invtry = invtry;
        this.numNPC = numNPC;
        this.gold = gold;
    }

    public static Room currentRoom = null;

    /**
     * Prints out the dialogue for the wait command
     */
    public void waitResponse(){
        System.out.println("You wait around for a few seconds, regaining your strength after your battles.");
        System.out.println("You regain 1 health point!");
    } 

    /**
     * Creates a list of NPCs that holds all NPCs on the floor
     */
    public void genNPCs(){
        allNPCs = allNPCs();
    }

    /**
     * Returns the type of the current room, returns b if battleroom
     */
    public String getType(){
        String ret = "r";
        return ret;
    }

    /**
     * moves the player in the specified direction.
     * @parm direction : The direction the player is moving in. 
     */
    public Room goResponse(String direction) throws IllegalArgumentException{
        Room ret;
        if(directions.containsKey(direction)){
            ret = directions.get(direction);
            return ret;
        } else{
            throw new IllegalArgumentException();
        }
        
    }

    /**
     * @returns the current Gold amount
     */
    public int getGold(){
        return gold;
    }

    /**
     * Lets the plyer choose an NPC to talk to.
     * @parm scan : The scanner that takes user input
     */
    public Item talkToResponse(Scanner scan){
        int count = 0;
        if(numNPC > 0){
            System.out.println("Who do you want to talk to?");
            for(int i = 0; i < allNPCs.size(); i++){
                    if(allNPCs.get(i).stillHere()){
                        System.out.println("NPC " + (i+1));
                    }else{
                        count++;
                    }
            }
            if(numNPC - count <= 0){
                System.out.println("There's nobody here that wishes to talk with you.");
                return null;
            }
            int entered = Integer.parseInt(scan.nextLine())-1;
            if(allNPCs.get(entered).stillHere() == false){
                System.out.println("That person has already left this area");
                return null;
            } else if(entered < numNPC && entered >= 0){
                allNPCs.get(entered).fillGifts();
                allNPCs.get(entered).fillWares();
                Item newItem = allNPCs.get(entered).response(scan, gold);
                if(allNPCs.get(entered).isShopkeep && newItem != null){
                    gold -= 5;
                }
                return newItem;
            }else{
                System.out.println("That is not a valid input");
            }
        }else{
            System.out.println("There is no one here.");
        }
        return null;
    }

    /**
     * Uses the given item so long as it's in the player's inventory.
     * @param using : the item being used
     * @return the number that corelates to the item.
     */
    public int useResponse(Item using){
        if(invtry.inventory.contains(using) && using.usable){
            if(using.getTitle().equals("Health Potion")){
                return 1;    
            }else if(using.getTitle().equals("Vase") || using.getTitle().equals("Weird Rock")){
                return 2;
            }else{
                System.out.println("This item is either not in your inventory or unusable.");
                return 0;
            }
        }
        return 0;
    }

    /**
     *Prints out the response to attacking on a breakfloor
     */
    public boolean attackResponse(Scanner input){
        System.out.println("You are on a safe floor of the tower, no weapons are allowed to be drawn. You can feel the guard's eyes as your fingers twitch towards your weapon of choice.");
        System.out.println("You decide it's not worth the risk of getting kicked out.");
        return false;
    }
    
    /**
     * Prints out a description of an NPC
     */
    public void lookUpResponse(int idx){
        System.out.println(allNPCs.get(idx).getDes());
    }

    /**
     * Prints out a description of the room
     */
    public void roomDes(){
        if(numNPC>0){
            System.out.println("You are in what seems to be a market. There are a couple of people bustling around, maybe some of them are selling something useful?");
        }else if(numNPC >8){
            System.out.println("You are in a remarkably crowded shopping area. People shove you this way and that as they rush to proceed to the next floor.");
        }else{
            System.out.println("There is no one else around. You almost feel as though you are the only person left on this planet in the stifling silence.");
        }
        
    }

    /**
     * Prints out the player's remaining health.
     */
    public void showHealth(int hp){
        System.out.println("You have " + hp + " hit points.");
    }

    /**
     * creates a list of NPCs in this room.
     * @param num : the number of NPCs
     * @returns a list of Enemy objects.
     */
    public List<NPC> allNPCs(){
        Random rand = new Random();
        
        ArrayList<NPC> NPCLst = new ArrayList<>();
        for(int i = 0; i < numNPC; i++){
            int chance = rand.nextInt(11);
            if(chance > 5){
                if(chance > 7){
                    NPCLst.add(new NPC(true, true,"A smiling shopkeep dressed in gaudy robes and chatting with every Climber that crosses their path."));
                }else{
                    NPCLst.add(new NPC(false, true,"A Climber that seems perfectly happy to take their time and watch the world go by. They are fiddling with something as they wait for the crowd to pass through."));

                }
                
            }else{
                if(chance > 3){
                    NPCLst.add(new NPC(true, false,"A shifty shopkeep dressed in expensive silks and bartering with any who'll lend them an ear."));
                } else{
                    NPCLst.add(new NPC(false, false,"A Climber that is standing near the corner of the room, sneering at all who pass them. They seem to be looking for something."));
                }
            }
            
        }
        return NPCLst;
    }
}
