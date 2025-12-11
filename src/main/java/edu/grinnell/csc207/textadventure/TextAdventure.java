package edu.grinnell.csc207.textadventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;



public class TextAdventure {
    


    public void main(String[] args){
        int totalHP = 10;
        int currHP = 10;
        int atk = 2;
        int def = 3;
        int floorCount = 1;
        Random rand = new Random();
        List<item> inventory = new ArrayList<>();
        Map<String,Room> Directions = new HashMap<>();
        Room currRoom = new breakFloor(Directions, inventory, 0);
        Room nextRoom = new battleFloor(1, 4, 1, inventory, Directions);
        Directions.put("up", nextRoom);
        Room prevRoom = null;
        boolean running = true;
        boolean win = false;
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
                } else if(input.equals("go up")){
                    if(floorCount == 9){
                        running = false;
                        win = true;
                        System.out.println("You've finally made it to the top of the tower!");
                        System.out.println("The view before you is truly stunning, you can even catch a glimpse of your house in the distance.");
                        System.out.println("There is no treasure up here. No people either.");
                        System.out.println("It's sorta depressing.");
                        System.out.println("You decide to stop thinking about the vast loneliness of the world, and head back down the tower, getting handed a lovely laminated certificate on your way out.");
                    }else{
                    floorCount++;
                    prevRoom = currRoom;
                    currRoom = currRoom.goResponse("up");
                    if(rand.nextInt(11)>6){
                        nextRoom = new breakFloor(Directions, inventory, rand.nextInt(floorCount));
                    }else{
                        nextRoom = new battleFloor(floorCount/2,rand.nextInt(atk)+1, rand.nextInt(def) +1, inventory, Directions);
                    }
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
                    currRoom.waitResponse();
                    if(currHP < totalHP){
                        currHP++;
                    }
                } 
        }
    }
}
