package edu.grinnell.csc207.TextAdventure;

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
        Map<String,Room> Directions;
        Room currRoom = new breakFloor(Directions, inventory, 0);
        Room nextRoom = new battleFloor(1, 4, 1, inventory, Directions);
        Directions.put("up", nextRoom);
        Room prevRoom;
        boolean running = true;
        boolean win = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Tower!");
        System.out.println("In this text-adventure game, you will be climbing a tower filled with battles and resting areas.");
        System.out.println("Your goal is to reach the top of the tower.");
        System.out.println("The only directions that you can go in are up and down.");
        System.out.println("You start at the first floor of the tower, which is a reating area, with a total of 10 health points!");

        while(running){
            System.out.println("What do you want to do?");
            while(scan.hasNext()){
                String input = scan.next().toLowerCase();
                if(input.equals("exit")){
                    running = false;
                } else if(input.equals("go up")){
                    floorCount++;
                    prevRoom = currRoom;
                    currRoom = currRoom.goResponse(input);
                    if(rand.nextInt(11)>6){
                        nextRoom = new breakFloor(Directions, inventory, rand.nextInt(floorCount));
                    }else{
                        nextRoom = new battleFloor(floorCount/2,rand.nextInt(atk)+1, rand.nextInt(def) +1, inventory, Directions);
                    }
                    Directions.put("up", nextRoom);
                    Directions.put("down", prevRoom);
                } else if(input.equals("go down")){
                    floorCount--;
                    nextRoom = currRoom;
                    currRoom = prevRoom;
                    prevRoom = prevRoom.goResponse("down");
                    Directions.put("up", nextRoom);
                    Directions.put("down", prevRoom);
                }
            }
        }
    }
}
