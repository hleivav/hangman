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
    static int attempts;
    static int attemptsLeft;
    static String currentWord;
    static String hiddenWord;
    static boolean isGameInProgress = true;
    static List<String> countryList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    // ANSI escape-codes for colors
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static class GameHelper {

        public static void  start(){

            print(ANSI_YELLOW + "Wellcome to Hangman!");
            print(ANSI_YELLOW + "What's your name?");
            String playerName = scanner.nextLine();
            print(ANSI_CYAN + "Welcome " + playerName + "!");
            attempts = getValidIntInput("How many attempts do you need to guess a country?");

            populateCountryList();
            guessNewCountry();
            do {
                playTheGame();
            } while (isGameInProgress);
        }

        public static int getValidIntInput(String question){ //check if the int isn't empty and is a numeric value
            int userInput = 0;
            boolean validInput = false;

            while (!validInput) {
                print(ANSI_CYAN + question);
                String input = scanner.nextLine();

                try {
                    if (input.trim().isEmpty()){
                        throw new IllegalArgumentException(ANSI_RED + "The input can not be empty. Try again.");
                    }
                    userInput = Integer.parseInt(input);
                    validInput = true;
                } catch (NumberFormatException e) {
                    print(ANSI_RED + "You must enter a number. Try again. ");
                } catch (IllegalArgumentException e) {
                    print(e.getMessage());
                }
            }
            return userInput;
        }

        public static char getValidCharInput(String question){ // check if the char isn't empty and is just one character long.
            char userInput = '\0'; //initialize with nothing.
            boolean validInput = false;

            while (!validInput){
                print(question);
                String input = scanner.nextLine();

                try {
                    if (input.trim().isEmpty() || input.length() != 1) { //neither empty nor several characters
                        throw new IllegalArgumentException(ANSI_RED + "You must enter a letter. Try again: ");
                    }
                    userInput = input.charAt(0);
                    validInput = true;
                } catch (IllegalArgumentException e) {
                    print(e.getMessage());
                }
            }
            return userInput;
        }

        public static void guessNewCountry(){
            currentWord = pickACountry();
            hiddenWord = currentWord;
            attemptsLeft = attempts;
            hideCurrentCountry();
        }

        public static void playTheGame(){
            if (checkIfWordIsCorrect()){
                print(ANSI_GREEN + "Well done!");
                if (anotherGame()) {
                    guessNewCountry();
                } else {
                    isGameInProgress = false;
                    return;
                }
            }
            if (attemptsLeft == 0 ) {
                print(ANSI_PURPLE + "I'm sorry. You couldn't guess the country. ");
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
            return  currentWord.equalsIgnoreCase(hiddenWord);
        }

        public static void hideCurrentCountry(){
            StringBuilder sb = new StringBuilder(currentWord);
            for (int i = 0; i < currentWord.length() ; i++) {
                sb.setCharAt(i, '-');
            }
            hiddenWord = sb.toString();
            print(hiddenWord);
        }

        public static void findLetterInWord(){
            char userLetter = getValidCharInput("Enter a letter you think this country has: ");
            StringBuilder sb = new StringBuilder(hiddenWord);
            boolean success = false;
            for (int i = 0; i < currentWord.length() ; i++) {
                if (Character.toLowerCase(userLetter) == Character.toLowerCase(currentWord.charAt(i))){
                    sb.setCharAt(i, userLetter);
                    success = true;
                }
            }
            hiddenWord = sb.toString();
            if (success){
                print(ANSI_GREEN + "Good guessed. You have " + attemptsLeft + " attempts. ");
            } else {
                attemptsLeft -= 1;
                print(ANSI_PURPLE + "Unfortunately that letter isn't in that country's name. You have " + attemptsLeft + " attempts left. ");
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
        }

        public static void print (String stringToPrint){
            System.out.println(stringToPrint);
        }
    }
}
