package edu.grinnell.csc207.textadventure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class BattleFloor implements Room{
    int curr_num_enemies;
    int tot_num_enemies;
    int player_atk;
    int player_def;
    Inventory invtry;
    List<Enemy> allEnemies;
    List<Enemy> dedEnemies = new ArrayList<>();
    public Map<String, Room> directions;

    public class Pair<T, U>{
        T l_value;
        U r_value;
        Pair(T l_val, U r_val){
            l_value = l_val;
            r_value = r_val;
        }
    }

    public BattleFloor(){}

    public BattleFloor(int num, int atk, int def, Inventory invtry, Map<String, Room> directions){
        tot_num_enemies = num;
        curr_num_enemies = num;
        player_atk = atk;
        player_def = def;
        this. invtry = invtry;
        this.directions = directions;
    }

    //creates a list of enemies
    public void genNPCs(){
        allEnemies = allEnemies(tot_num_enemies);
    }

    //gets room description
    public void roomDes(){
        if(tot_num_enemies > 1){
            System.out.println("This floor of the tower is nearly rotting away.");
            System.out.println("The only living beings here are the " + tot_num_enemies + " Climbers that are competing to take your head.");
        }else{
            System.out.println("This floor of the tower is slightly less destroyed than usual.");
            System.out.println("There is only 1 Climber challenging you.");
        }
    }

     //wait in the room for one turn
    public void waitResponse(){
        System.out.println("You wait around for a few seconds, regaining your strength after your battles.");
        System.out.println("You regain 1 health point!");
    }

    //gets current room type
    public String getType(){
        String ret = "b";
        return ret;
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

    //buffs player attack
    public void buffAtk(){
        player_atk += 5;
    }

    //debuffs player attack
    public void dbuffAtk(){
        player_atk -= 5;
    }

    //talk to the given object found in the room
    public Item talkToResponse(Scanner scan){
        if(curr_num_enemies>0){
           allEnemies.get(0).response(scan, 0); 
        }else{
            System.out.println("Why are you talking to a pile of corpses?");
        }
        return null;
    }

    //use the given item found in the player's inventory
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

    //attack the given enemy found in the room
    public boolean attackResponse(Scanner input){
        boolean killed = false;
        int response = -1;
        if(curr_num_enemies > 0){
            System.out.println("Who do you want to attack? Please enter the number relevent to that enemy.");
            for(int i = 0; i < allEnemies.size(); i++){
                if(allEnemies.get(i).getAlive()){
                    System.out.println("Enemy " + (i+1));
                }
            }
            
            String check = input.nextLine();
            if(check.length() > 1 || check == "" || !Character.isDigit(check.charAt(0))){
                System.out.println("This is an invalid input. Please try again.");
               
            }else{
                response = Integer.parseInt(check)-1;
                if(response < tot_num_enemies && response>=0){
                    int dmg = allEnemies.get(response).damageTaken(player_atk);
                    if(dedEnemies.contains(allEnemies.get(response))){
                        System.out.println("You have already slaughtered this enemy. There's no point in stabbing their decaying corpse.");
                        killed = false;
                       
                    }else if(!(allEnemies.get(response)).getAlive()){
                        killed = true;
                        System.out.println("You killed Enemy " + (response+1) + " and got 5 gold!");
                        curr_num_enemies--;
                        dedEnemies.add(allEnemies.get(response));
                     
                    }else{
                        System.out.println("You attacked Enemy " + (response+1) + " and did " + dmg + " points of damage!");
                        killed = false;
                    
                    }
                }else{
                    System.out.println("That is not a valid input. Please input the number of the enemy you are attacking.");
                    killed = false;
                
                }
            }              
        return killed;
        }else{
            System.out.println("There are no more enemies on this floor.");
        }
            
        return false;  
    }

    //The enemy attacks the player
    public int enemyAttack(int hp){
        List<Pair<Enemy, Integer>> attacking = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < allEnemies.size(); i++){
            if(allEnemies.get(i).getAlive()){
                attacking.add(new Pair<>(allEnemies.get(i), i));
            }
        }

        int[] total_dmg = new int[attacking.size()];
        for(int i =0; i < attacking.size(); i++){
            int chance = rand.nextInt(tot_num_enemies);
            if(chance > 1) {
                total_dmg[i] = -1;
            }else{
                total_dmg[i] = attacking.get(i).l_value.damageDone(player_def);
            }
        }

        int remainingHP = hp;

        for(int i = 0; i < total_dmg.length; i++){
            if(total_dmg[i] == -1){
                System.out.println("Enemy " + (attacking.get(i).r_value+1) + " tries to hit you, but you slip out of reach, just barely evading the hit.");
            } else if(remainingHP > 0){
                remainingHP -= total_dmg[i];
                System.out.println("Enemy " +  (attacking.get(i).r_value+1) + " slams into you, knocking the breath from your lungs and doing " + total_dmg[i] + " damage.");
            }
            if (remainingHP <= 0){
                System.out.println("Your lungs collapse in on themselves. You choke for air as you collapse, each of your shattered bones screaming in pain.");
                System.out.println("As you manage one last wheeze of air, you curse this tower and its meaningless challenge. Then, your joints stiffen and you are released into the cold embrace of death.");
                return remainingHP;
            }
        }
        return remainingHP;
    }
    
    //Returns the number of enemies left on the floor
    public int enemiesLeft(){
        return curr_num_enemies;
    }

    //look at the given object found in the room
    public void lookUpResponse(int idx){
        System.out.println(allEnemies.get(idx).getDes());
    }

    //shows how much health the player has
    public void showHealth(int hp){
        System.out.println("You have " + hp + " hit points.");
    }

    //returns a list of all enemies in the room
    public List<Enemy> allEnemies(int num){
        ArrayList<Enemy> enemyLst = new ArrayList<>();
        if(num>0){
        Random rand = new Random();
        for(int i = 1; i <= num; i++){
            enemyLst.add(new Enemy(player_atk+rand.nextInt(i), player_atk - player_def - rand.nextInt(i), player_atk+rand.nextInt(player_def) +1, 
                        "Another Climber that's trying to brawl their way to the top of the tower. Who knows why."));
        }
        }
        return enemyLst;

    }
}
