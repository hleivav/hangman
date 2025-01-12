package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    String name;
    int attempts;
    String currentWord;
    int attemptsLeft;
    boolean isGameInProgress = true;
    static String[] countryArray;

    // ANSI escape-codes for colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //constructor
    /*public Game (String name, int attempts){
        this.name = name;
        this.attempts = attempts;
    }*/

    public static class GameHelper {
        public static void  start(){
            Scanner scanner = new Scanner(System.in);
            GameHelper.print("Wellcome to Hangman!");
            GameHelper.print("What's your name?");
            String playerName = scanner.nextLine();
            GameHelper.print("How many attempts do you need to guess a country?");
            int neededAttempts = Integer.parseInt(scanner.nextLine());
            //Game game = new Game(playerName, neededAttempts);

            GameHelper.populateCountryList();
        /*do {


        } while (isGameInProgress);*/
        }

        public static void populateCountryList() {
            String currentDirectory = System.getProperty("user.dir");
            String filePath = currentDirectory + "/countries.txt";
            List<String> countryList = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.isEmpty()){
                        countryList.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            countryArray = countryList.toArray(new String[0]);
            for ( String c : countryArray){
                GameHelper.print(c);
            }
        }

        public static void print (char charToPrint){
            System.out.println(charToPrint);
        }

        public static void print (String stringToPrint){
            System.out.println(stringToPrint);
        }
    }
}
