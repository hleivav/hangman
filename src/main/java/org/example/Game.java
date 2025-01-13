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
    static int attemptsLeft;
    static String currentWord;
    static String hiddenWord;
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

            print("Wellcome to Hangman!");
            print("What's your name?");
            String playerName = scanner.nextLine();
            print("Welcome " + playerName + "!");
            print("How many attempts do you need to guess a country?");
            attempts = Integer.parseInt(scanner.nextLine());
            populateCountryList();

            guessNewCountry();
            do {
                playTheGame();
            } while (isGameInProgress);
        }

        public static void guessNewCountry(){
            currentWord = pickACountry();
            hiddenWord = currentWord;
            attemptsLeft = attempts;
            hideCurrentCountry();

        }

        public static void playTheGame(){
            if (checkIfWordIsCorrect()){
                print("Well done!");
                if (anotherGame()) {
                    guessNewCountry();
                } else {
                    isGameInProgress = false;
                    return;
                }
            }
            if (attemptsLeft == 0 ) {
                print("I'm sorry. You couldn't guess the country. ");
                if (anotherGame()) {
                    guessNewCountry();
                } else {
                    isGameInProgress = false;
                }
            } else {
                findLetterInWord();
            }
        }

        public static boolean anotherGame(){
            boolean result  = false;
            print("Do you want to guess another word?");
            if (scanner.nextLine().equalsIgnoreCase("Y")){
                result = true;
            }
            return result;
        }

        public static boolean checkIfWordIsCorrect(){
            boolean result = false;
            if (currentWord.equals(hiddenWord)){
                return true;
            }
            return result;
        }

        public static void hideCurrentCountry(){
            StringBuilder sb = new StringBuilder(currentWord);
            for (int i = 0; i < currentWord.length() ; i++) {
                sb.setCharAt(i, '-');
            }
            hiddenWord = sb.toString();
            print(hiddenWord);
            print("");
            print("Enter a letter you think this country has: ");
        }

        public static void findLetterInWord(){
            char userLetter = scanner.nextLine().charAt(0);
            StringBuilder sb = new StringBuilder(hiddenWord);
            boolean success = false;
            for (int i = 0; i < currentWord.length() ; i++) {
                if (userLetter == currentWord.charAt(i)){
                    sb.setCharAt(i, userLetter);
                    success = true;
                }
            }
            hiddenWord = sb.toString();
            if (success){
                print("Good guessed. You have " + attempts + " attempts. ");
            } else {
                attemptsLeft -= 1;
                print("Unfortunately that letter isn't in that country's name. You have " + attemptsLeft + " attempts left. ");
            }
            print(hiddenWord);
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
