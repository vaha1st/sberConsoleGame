package com.sber;

import com.sber.initialization.GameInitializer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App
{
    static GameInitializer gameInitializer;

    public static void main( String[] args )
    {
        showMenu(true);
    }

    private static void showMenu(boolean firstRun) {

        Scanner scanner = new Scanner(System.in);
        int mapSize = 0;

        System.out.println("---------------------- MENU -------------------------");
        System.out.println("The goal is to collect " +
                (firstRun ? "enought" : gameInitializer.getGoal()) +
                " money and not to die.\n");

        if (!firstRun) {
            System.out.println("To continue type            \"Resume\"");
        }
        System.out.println("For the new game type       \"New\"");
        System.out.println("To load the game type       \"Load\"");
        if (!firstRun) {
            System.out.println("To save the game type       \"Save\"");
        }
        System.out.println("For exit type               \"Exit\"");
        System.out.println("\n-----------------------------------------------------");

        String command = "";
        while (!command.equals("exit")) {
            command = scanner.nextLine().strip().toLowerCase();
            switch (command) {
                case "new":
                    command = "exit";
                    System.out.println("Please enter size of the map");
                    try {
                        mapSize = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Wrong mapsize");
                    }
                    if (mapSize > 0) {
                        gameInitializer = new GameInitializer(mapSize);
                        play(true);
                    }
                    break;
                case "load":
                    command = "exit";
                    break;
                case "save":
                    if (!firstRun) {
                        command = "exit";
                        play(false);
                    }
                    break;
                case "resume":
                    command = "exit";
                    play(false);
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }

    }

    private static void play(boolean newGame) {

        final String commands =
                "Lookaround             - description of current location\n" +
                        "Inventory              - list of items you have\n" +
                        "Move <Direction>       - to move next direction. Example: Move south\n" +
                        "Pickup <item>          - put an item from location to inventory\n" +
                        "Use <object> <subject> - try to make something else from components\n" +
                        "Menu                   - show menu\n" +
                        "Exit                   - stop the game and quit\n";
        String command = "";
        Player player = gameInitializer.getPlayer();

        if (newGame) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Hello adventurer! Welcome to the game. " +
                    "Input \"help\" to see command list");
        } else {
            System.out.println("Welcome back!");
            player.lookAround();
        }

        Scanner scanner = new Scanner(System.in);

        while (!command.toLowerCase().equals("exit")) {
            command = scanner.nextLine().toLowerCase();
            String[] splitedCommand = command.toLowerCase().strip().split("\\s+");
            switch (splitedCommand[0]) {
                case "help":
                    System.out.println(commands);
                    break;
                case "inventory":
                    player.inventory();
                    break;
                case "lookaround":
                    player.lookAround();
                    break;
                case "move":
                    player.go(command.substring(4).strip());
                    break;
                case "go":
                    player.go(command.substring(2).strip());
                    break;
                case "pickup":
                    player.take(command.substring(6).strip());
                    break;
                case "use":
                    if (splitedCommand.length >= 3) {
                        player.use(splitedCommand[1], splitedCommand[2]);
                    } else {
                        player.use(command.substring(3).strip());
                    }
                    break;
                case "menu":
                    command = "exit";
                    showMenu(false);
                    break;
                case "exit":
                    System.out.println("GAME OVER!\n");
                    break;
                default:
                    System.out.println("Command not found. Input \"help\" to show the list\n");
            }
            System.out.println();
            if (won(player, gameInitializer.getGoal())) {
                command = "exit";
                System.out.println("YOU WON!\n");
            }
            if (lost(player)) {
                command = "exit";
            }
        }
    }

    private static boolean won(Player player, int goal) {
        return player.getGold() >= goal;
    }

    private static boolean lost(Player player) {
        return player.getHealth() <= 0;
    }
}
