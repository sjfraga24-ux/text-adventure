package main.java.edu.grinnell.cscs207.textadventure.Rooms;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class battleFloor implements Room{
    int num_enemies;
    int player_atk;
    int player_def;
    int curr_def = player_atk;
    List<item> invtry;
    public Map<String, Room> directions;

    public battleFloor(int num, int atk, int def, List<item> invtry, Map<String, Room> directions){
        num_enemies = num;
        player_atk = atk;
        player_def = def;
        this. invtry = invtry;
        this.directions = directions;
    }

     //wait in the room for one turn
    public void waitResponse(){
        curr_def++;
    }

    //go in the given cardinal direction
    public Room goResponse(String direction){
        Room ret;
        if(directions.containsKey(direction)){
            ret = directions.get(direction);
            return ret;
        } else{
            throw new IllegalArgumentException();
        }
    }

    //talk to the given object found in the room
    public void talkToResponse(Interactable object){
        object.response();
    }

    //use the given item found in the player's inventory
    public int useResponse(){
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
    }

    //attack the given object found in the room
    public boolean attackResponse(enemy opponent){
        boolean killed;
        opponent.damageTaken(player_atk);
        if(!opponent.getAlive()){

            killed = true;
        }else{
            killed = false;
        }
        return killed;
    }
    
    //look at the given object found in the room
    public void lookUpResponse(){
        System.out.println(enemy.getDes);
    }

    //shows how much health the player has
    public void showHealth(int hp){
        System.out.println("You have " + hp + " hit points.");
    }

    //returns a list of all enemies in the room
    public List<enemy> allEnemies(){
        Random rand = new Random();
        ArrayList enemyLst = new ArrayList();
        for(int i = 0; i < num_enemies; i++){
            enemyLst.add(new enemy(player_atk-rand.nextInt(i), player_def+rand.nextInt(i), player_atk+player_def, 
                        "Another Climber that's trying to brawl their way to the top of the tower. Who knows why."));
        }
        return enemyLst;
    }
}
