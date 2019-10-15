package org.improving;

import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static int numRows = 8;
    public static int numCols = 8;
    public static int computerShips;
    public static List<Ship> ships = new ArrayList<>();
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];

    public static void main(String[] args){
        System.out.println("**** Welcome to Battle Ships game ****");
        System.out.println("Right now, sea is empty\n");

        //Step 1 – Create the ocean map
        createOceanMap();

        //Step 2 - Deploy computer's ships
        deployComputerShips();

        //Step 3 Battle
        do {
            Battle();
        }while(Main.computerShips != 0);

        //Step 5 - Game over
        gameOver();
    }

    public static void createOceanMap(){
        //First section of Ocean Map
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        //Middle section of Ocean Map
        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
                if (j == 0)
                    System.out.print(i + "|" + grid[i][j]);
                else if (j == grid[i].length - 1)
                    System.out.print(grid[i][j] + "|" + i);
                else
                    System.out.print(grid[i][j]);
            }
            System.out.println();
        }

        //Last section of Ocean Map
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }

    public static void deployComputerShips(){
        System.out.println("\nComputer is deploying ships");

        //Create ships
        var five = new Ship(5);
        var four = new Ship(4);
        var three = new Ship(3);
        var two = new Ship(2);

        Main.ships.add(five);
        Main.ships.add(four);
        Main.ships.add(three);
        Main.ships.add(two);

        //Deploying five ships for computer

        for (Ship ship: ships) {
            int x = 0;
            int y = 0;

            boolean isValidCoordinate = true;
            while (isValidCoordinate) {
                x = (int)(Math.random() * 8);
                y = (int)(Math.random() * 8);

                // If is coordinate is valid, print an X on the grid and exit the while loop
                if (isValidCoordinate(x, y) && isValidPlacement(ship, x, y)) {
                    grid[x][y] = "x";
                    for (int i = 0; i < ship.xLength; i++) {
                        if (y - i >= 0) {
                            grid[x][y - i] = "x";
                        }
                    }
                    isValidCoordinate = false;
                } else {
                    // Otherwise generate new coordinates
                    continue;
                }
            }


//            boolean isValidPlacement = true;
//            while (isValidPlacement) {
//                if (isValidPlacement(ship, x, y)) {
//                    for (int i = 0; i < ship.xLength - 1; i++) {
//                        grid[x][y - i] = "x";
//                        isValidPlacement = false;
//                    }
//                }
//            }

        }

//        for (int i = 1; i <= Main.computerShips; ) {
//            boolean isValid = false;
//            while (isValid) {
//                int x = (int)(Math.random() * 8);
//                for (int coordinate :  )
//                int y = (int)(Math.random() * 8);
//
//                if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
//                {
//                    grid[x][y] =   "x";
//                    System.out.println(i + ". ship DEPLOYED");
//                    i++;
//                }
//            }
//
//        }
        printOceanMap();
    }

    public static void Battle(){
        playerTurn();

        printOceanMap();

        System.out.println();
        System.out.println("Computer ships: " + Main.computerShips);
        System.out.println();
    }

    public static void playerTurn(){
        System.out.println("\nYOUR TURN");
        int x = -1, y = -1;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter coordinates (A1)");
            String input = scanner.nextLine();

            var letter = String.valueOf(input.charAt(0));
            System.out.println("Letter: " + letter);
            char[] alphabet = "ABCDEFGH".toCharArray();
            Map<String, Integer> indexedLetters = new HashMap<>();
            int i=0;
            for (char l : alphabet) {
                indexedLetters.put(String.valueOf(l), i++);
            }

            x = indexedLetters.get(letter);
            y = (int)input.charAt(1) - 48;

            System.out.println(input.charAt(1));
            System.out.println("C: " + x + ", " + y);


            // Coordinates:
//            System.out.print("Enter X coordinate: ");
//            x = scanner.nextInt();
//            System.out.print("Enter Y coordinate: ");
//            y = scanner.nextInt();

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
            {
                if (grid[x][y] == "x") //if computer ship is already there; computer loses ship
                {
                    System.out.println("Boom! You sunk the ship!");
                    grid[x][y] = "!"; //Hit mark
                    --Main.computerShips;
                }
                else if (grid[x][y] == " ") {
                    System.out.println("Sorry, you missed");
                    grid[x][y] = "-";
                }
            }
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  //invalid guess
                System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }


    public static void gameOver(){
        System.out.println("Computer ships: " + Main.computerShips);
        if(Main.ships.size() <= 0) {
            System.out.println("Hooray! You won the battle :)");
        } else {
            System.out.println("Sorry, you lost the battle");
        }
        System.out.println();
    }

    public static void printOceanMap(){
        System.out.println();
        //First section of Ocean Map
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        //int to letter
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        //Middle section of Ocean Map
        for(int x = 0; x < grid.length; x++) {
            System.out.print(alphabet[x] + "|");

            for (int y = 0; y < grid[x].length; y++){
                System.out.print(grid[x][y]);
            }

            System.out.println("|" + alphabet[x]);
        }

        //Last section of Ocean Map
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }

    public static boolean isValidCoordinate(int x, int y) {
        if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " ")) return true;
        else return false;
    }

    public static boolean isValidPlacement(Ship ship, int x, int y) {
        if (ship.orientation == 0) {
            // If the ship goes outside the grid boarders, return false
            if (y - ship.xLength >= 0) {
                // Check if another ship exists there
                for (int i = 0; i < ship.xLength; i++) {
                    if (grid[x][i] == "x") return false;
                }
                // If no ship is in the way, return true
                return true;
            }
            return false;
        } else if (ship.orientation == 1) {
            if (x - ship.xLength >= 0) {
                // Check if another ship exists there
                for (int i = 0; i < ship.xLength; i++) {
                    if (grid[x][i] == "x") return false;
                }
                // If no ship is in the way, return true
                return true;
            }
        }
        return false;
    }
}