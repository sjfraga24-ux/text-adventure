package edu.grinnell.csc207.textadventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;



public class TextAdventure {
    


    public static void main(String[] args){
        int totalGold = 10;
        int totalHP = 20;
        int currHP = 20;
        int atk = 2;
        int def = 4;
        int floorCount = 1;
        Random rand = new Random();
        Inventory inventory = new Inventory(new ArrayList<>());
        Map<String,Room> Directions = new HashMap<>();
        Room currRoom = new BreakFloor(Directions, inventory, 0, totalGold);
        Room nextRoom = new BattleFloor(1, 4, 1, inventory, Directions);
        nextRoom.genNPCs();
        Directions.put("up", nextRoom);
        Room prevRoom = null;
        boolean running = true;
        boolean win = false;
        boolean earlyExit = false;
        int waitCount = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Tower!");
        System.out.println("In this text-adventure game, you will be climbing a tower filled with battles and resting areas.");
        System.out.println("Your goal is to reach the top of the tower.");
        System.out.println("The only directions that you can go in are up and down.");
        System.out.println("You start at the first floor of the tower, which is a resting area, with a total of 10 health points!");

        while(running){
            System.out.println("What do you want to do?");
                String input = scan.nextLine().toLowerCase();
                if(input.equals("exit")){
                    running = false;
                    earlyExit = true;
                } else if(input.equals("go up")){
                    waitCount = 0;
                    if(currRoom.getType().equals("b") && ((BattleFloor) currRoom).enemiesLeft()>0){
                        System.out.println("You can't ascend yet. There are still enemies blocking your way!");
                    }
                    else if(floorCount == 9){
                        running = false;
                        win = true;
                        System.out.println("You've finally made it to the top of the tower!");
                        System.out.println("The view before you is truly stunning, you can even catch a glimpse of your house in the distance.");
                        System.out.println("There is no treasure up here. No people either.");
                        System.out.println("It's sorta depressing.");
                        System.out.println("You decide to stop thinking about the vast loneliness of the world, and head back down the tower, getting handed a lovely laminated certificate on your way out.");
                    }else{
                    if(currRoom.getType().equals("b")){
                        atk++;
                        def++;
                    }
                    floorCount++;
                    prevRoom = currRoom;
                    currRoom = currRoom.goResponse("up");
                    if(rand.nextInt(11)>5){
                        nextRoom = new BreakFloor(Directions, inventory, rand.nextInt(floorCount), totalGold);
                    }else{
                        nextRoom = new BattleFloor(floorCount/2, atk, def, inventory, Directions);
                    }
                    nextRoom.genNPCs();
                    Directions.put("up", nextRoom);
                    Directions.put("down", prevRoom);
                    System.out.println("You have successfully made it to floor number " + floorCount + "!");
                    }
                } else if(input.equals("go down")){
                    if(floorCount > 1){
                    floorCount--;
                    nextRoom = currRoom;
                    currRoom = prevRoom;
                    prevRoom = prevRoom.goResponse("down");
                    Directions.put("up", nextRoom);
                    Directions.put("down", prevRoom);
                    System.out.println("You have successfully made it to floor number " + floorCount + "!");
                    }else{
                        System.out.println("You're already at the bottom of the Tower.");
                    }
                    
                } else if(input.equals("wait")){
                    
                    if(waitCount < 4 && currHP < totalHP){
                        currRoom.waitResponse();
                        currHP++;
                    }else{
                        System.out.println("Nothing happens.");
                    }
                    waitCount++;
                } else if(input.equals("use item")){
                    System.out.println("What item would you like to use?");
                    input = scan.nextLine().toLowerCase();
                    if(inventory.inInven(input)){
                        Item usingItem = inventory.getItem(input);
                        if(usingItem.usable){
                            if(usingItem.getTitle().equals("Health Potion")){
                                if(currHP <= totalHP - 5){
                                    currHP += 5;
                                    inventory.removeItem(usingItem);
                                    System.out.println("You used a Health Potion.");
                                    System.out.println("Your current health is now" + currHP +"/" + totalHP +".");
                                } else if(currHP < totalHP){
                                    currHP = totalHP;
                                    inventory.removeItem(usingItem);
                                    System.out.println("You used a Health Potion.");
                                    System.out.println("Your current health is now" + currHP +"/" + totalHP +".");
                                } else{
                                    inventory.removeItem(usingItem);
                                    System.out.println("You used a Health Potion.");
                                    System.out.println("It has no effect.");
                                }
                            }else if(usingItem.getTitle().equals("Vase")||
                                     usingItem.getTitle().equals("Weird Rock")){
                                if(currRoom.getType().equals("b")){
                                    System.out.println("You ready your " + usingItem.getTitle() + " and prepare to smash the daylights out of your enemies.");
                                    ((BattleFloor)currRoom).buffAtk();
                                    if(currRoom.attackResponse(scan)){
                                        totalGold+=5;
                                    }
                                    ((BattleFloor)currRoom).dbuffAtk();
                                    inventory.removeItem(usingItem);
                                }else{
                                    System.out.println("The guards will escort you from the tower if you make too much of a fuss.");
                                    System.out.println("Maybe this isn't the right place to use this object.");
                                }
                            }else{
                                System.out.println("How did you even find something like that?");
                            }
                        }else{
                            System.out.println("This item is not usable.");
                        }
                    }else{
                        System.out.println("You do not own this item.");
                    }
                }else if(input.equals("attack")){
                     if(currRoom.getType().equals("b")){
                            if(currRoom.attackResponse(scan)){
                                totalGold+=5;
                                atk+= 2 + rand.nextInt(atk);
                            }else{
                                currHP = ((BattleFloor)currRoom).enemyAttack(currHP);
                                if(currHP <= 0){
                                    running = false;
                                    win = false;
                                }else{
                                    System.out.println("You have " + currHP + " hp remaining.");
                                }
                            }
                        }else{
                            currRoom.attackResponse(scan);
                        }
                }else if(input.equals("talk")){
                    if(currRoom.getType().equals("b")){
                        currRoom.talkToResponse(scan);
                    }else{
                        Item newItem = currRoom.talkToResponse(scan);
                        if(newItem != null){
                            inventory.add(newItem);
                            String eff = newItem.getEffect();
                            if(eff != ""){
                                if(eff.substring(0, eff.length()-1).equals("atk")){
                                    if(eff.contains("+")){
                                        atk++;
                                    }else{
                                        atk--;
                                    }
                                }else{
                                    if(eff.contains("+")){
                                        def++;
                                    }else{
                                        def--;
                                    }
                                }
                            }
                        }
                        totalGold = ((BreakFloor)currRoom).getGold();
                        scan.nextLine();
                    }
                }else if(input.equals("look")){
                    System.out.println("What do you want to look at?[NPC/room/inventory]");
                    input = scan.nextLine().toLowerCase();
                    if(input.equals("room")){
                        currRoom.roomDes();
                    }else if(input.equals("npc")){
                        if(currRoom.getType().equals("b") && ((BattleFloor)currRoom).tot_num_enemies > 0){
                            System.out.println("Who do you want to look at?");
                            for(int i = 0; i < ((BattleFloor)currRoom).allEnemies.size(); i++){
                                if(((BattleFloor)currRoom).allEnemies.get(i).getAlive()){
                                    System.out.println("Enemy " + (i+1));
                                }
                            }
                            input = scan.nextLine().toLowerCase();
                            ((BattleFloor)currRoom).lookUpResponse(Integer.parseInt(input)-1);

                        }else if(currRoom.getType().equals("r") && ((BreakFloor)currRoom).numNPC > 0){
                            System.out.println("Who do you want to talk to?");
                            for(int i = 0; i < ((BreakFloor)currRoom).allNPCs.size(); i++){
                                    System.out.println("NPC " + (i+1));
                            }
                            input = scan.nextLine().toLowerCase();
                            ((BreakFloor)currRoom).lookUpResponse(Integer.parseInt(input)-1);
                        }else{
                            System.out.println("There is no one to look at here.");
                        }
                    }else if(input.equals("inventory")){
                        inventory.checkInventory();
                        System.out.println("Gold: " + totalGold);
                    }
                    
                } else if(input.equals("get stats")){
                    System.out.println("Current HP: " + currHP + "/" + totalHP);
                    System.out.println("Attack: " + atk);
                    System.out.println("Defence: " + def);
                    System.out.println("Gold: " + totalGold);

                } else if(input.equals("help")){
                    System.out.println("Here is the list of commands you can use:");
                    System.out.println("go up: This will move you to the next floor of the tower");
                    System.out.println("go down: This will move you the previous floor of the tower");
                    System.out.println("exit: This command quits the game");
                    System.out.println("wait: This command lets the player take a breather and regain a little health, but for a limited amount of times");
                    System.out.println("use item: This command uses an item that is in the player's inventory");
                    System.out.println("attack: This command is used to attack enemies");
                    System.out.println("talk: This command is used to communicate with NPCs");
                    System.out.println("look: This command can be used to look at the player's surroundings and the people in their surroundings, or it can be used to check the player's inventory");
                    System.out.println("get stats: This command shows the player's current stats");
                } else{
                    System.out.println("That is not a valid command. For a list of possible commands, type 'help'");
                }
        }

        
        
        if(win){
            System.out.println("Congratulations! You won the game!");
            System.out.println("Thank you for playing!");
        }else if(earlyExit){
            System.out.println("You made it a few floors up the tower before deciding the whole thing was a waste of time and returning home.");
            System.out.println("Thank you for playing!");
        }else{
            System.out.println("Too bad, it looks like you were slaughtered in the rabid competition for glory.");
            System.out.println("Your bloated corpse is retrieved by the guards and your belongings are sent back to the shopkeepers.");
            System.out.println("Maybe someone else will use them to reach victory one day.");
            System.out.println("Thank you for playing!");
        }
    }
}
