package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
        System.out.println("Wellcome to Hangman!");
        System.out.println("What's your name?");
        String playerName = scanner.nextLine();
        System.out.println("How many attempts do you need to guess a country?");
        int neededAttempts = Integer.parseInt(scanner.nextLine());
        Game game = new Game(playerName, neededAttempts);
        game.start();
    }
}