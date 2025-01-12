package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Game {
    //variables
    static String name;
    static int attempts;
    static String currentWord;
    static List<Character> hiddenWord = new ArrayList<>();
    static boolean isGameInProgress = true;
    static List<String> countryList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    // ANSI escape-codes for colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static class GameHelper {
        public static void  start(){

            GameHelper.print("Wellcome to Hangman!");
            GameHelper.print("What's your name?");
            String playerName = scanner.nextLine();
            print("Welcome " + playerName + "!");
            GameHelper.print("How many attempts do you need to guess a country?");
            attempts = Integer.parseInt(scanner.nextLine());
            populateCountryList();
            currentWord = pickACountry();
            hideCurrentCountry(currentWord);

            do {

                playTheGame();
            } while (isGameInProgress);
        }

        public static void playTheGame(){
            //check if the user got all the letters correctly.
            //if yes, ask if the player wants to continue.
            if (attempts > 0 ) {
                findLetterInWord();
            } else {
                // ask if the player wants to continue.
                // if not, next line
                isGameInProgress = false;
            }

        }

        public static boolean checkIfWordIsCorrect(){

            return true;
        }

        public static void hideCurrentCountry(String country){

            for (int i = 0; i < country.length(); i++) {
                hiddenWord.add('-');
            }
            for (char letter : hiddenWord){
                printSameLine('-');
            }
            print("");
            print("Enter a letter you think this country has: ");
        }

        public static void findLetterInWord(){
            char userLetter = scanner.nextLine().charAt(0);

            boolean success = false;
            for (int i = 0; i < currentWord.length() ; i++) {
                if (userLetter == currentWord.charAt(i)){
                    hiddenWord.set(i, userLetter);
                    success = true;
                }
            }
            if (success){
                print("Good guessed. You have " + attempts + " attempts. ");
            } else {
                attempts -= 1;
                print("Unfortunately that letter isn't in that country's name. You have " + attempts + " attempts left. ");
            }
            for (char letter : hiddenWord){
                GameHelper.printSameLine(letter);
            }
            print("");
        }

        public static String pickACountry(){
            Random randomNo = new Random();
            int randomInt = randomNo.nextInt(countryList.size());
            currentWord = countryList.get(randomInt);
            countryList.remove(randomInt);
            return currentWord;
        }

        public static void populateCountryList() {
            String currentDirectory = System.getProperty("user.dir");
            String filePath = currentDirectory + "/countries.txt";

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
            //countryArray = countryList.toArray(new String[0]);
        }

        public static void print (char charToPrint){
            System.out.println(charToPrint);
        }

        public static void print (String stringToPrint){
            System.out.println(stringToPrint);
        }

        public static void printSameLine(char charToPrint){
            System.out.print(charToPrint);
        }
    }
}
