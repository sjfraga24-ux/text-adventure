package edu.grinnell.csc207.textadventure;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class breakFloor implements Room{
    public Map<String, Room> directions;
    public List<item> invtry;
    public int numNPC;

    public breakFloor(Map<String, Room> directions, List<item> invtry, int numNPC){
        this.directions = directions;
        this.invtry = invtry;
        this.numNPC = numNPC;
    }

    public static Room currentRoom = null;

    //wait in the room for one turn
    public void waitResponse(){
        System.out.println("You wait around for a few seconds, regaining your strength after your battles.");
        System.out.println("You regain 1 health point!");
    } 

    //go in the given direction
    public Room goResponse(String direction) throws IllegalArgumentException{
        Room ret;
        if(directions.containsKey(direction)){
            ret = directions.get(direction);
            return ret;
        } else{
            throw new IllegalArgumentException();
        }
        
    }

    //talk to the given object found in the room
    public void talkToResponse(interactable object){
        if(numNPC > 0){
            object.response();
        }else{
            System.out.println("There is no one here.");
        }
    }

    //use the given item found in the player's inventory
    public int useResponse(item using){
        if(invtry.contains(using) && using.usable){
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

    public List<item> removeItem(item itm){
        invtry.remove(invtry.indexOf(itm));
        System.out.println(itm.getTitle() + " has been removed.");
        return invtry;
    }

    //attack the given object found in the room
    public boolean attackResponse(interactable object){
        System.out.println("You are on a safe floor of the tower, no weapons are allowed to be drawn. You can feel the guard's eyes as your fingers twitch towards your weapon of choice.");
        System.out.println("You decide it's not worth the risk of getting kicked out.");
        return false;
    }
    
    //look at the given object found in the room
    public void lookUpResponse(interactable object){
        System.out.println(object.getDes());
    }

    public void roomDes(){
        if(numNPC>0){
            System.out.println("You are in what seems to be a market. There are a couple of people bustling around, maybe some of them are selling something useful?");
        }else if(numNPC >8){
            System.out.println("You are in a remarkably crowded shopping area. People shove you this way and that as they rush to proceed to the next floor.");
        }else{
            System.out.println("There is no one else around. You almost feel as though you are the only person left on this planet in the stifling silence.");
        }
        
    }

    public void showHealth(int hp){
        System.out.println("You have " + hp + " hit points.");
    }

    public List<NPC> allNPCs(){
        Random rand = new Random();
        
        ArrayList<NPC> NPCLst = new ArrayList<>();
        for(int i = 0; i < numNPC; i++){
            int chance = rand.nextInt(11);
            if(chance > 7){
                if(chance == 8){
                    NPCLst.add(new NPC(true, true,"A smiling shopkeep dressed in gaudy robes and chatting with every Climber that crosses their path."));
                }else{
                    NPCLst.add(new NPC(false, true,"A Climber that seems perfectly happy to take their time and watch the world go by. They are fiddling with something as they wait for the crowd to pass through."));

                }
                
            }else{
                if(chance > 4){
                    NPCLst.add(new NPC(true, false,"A shifty shopkeep dressed in expensive silks and bartering with any who'll lend them an ear."));
                } else{
                    NPCLst.add(new NPC(true, true,"A Climber that is standing near the corner of the room, sneering at all who pass them. They seem to be looking for something."));
                }
            }
            
        }
        return NPCLst;
    }
}
