/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.battleconsole.player;

import com.github.cc007.battleconsole.BattleConsole;
import com.github.cc007.battleshipsapi.Player;
import com.github.cc007.battleshipsapi.ShipDirection;
import com.github.cc007.battleshipsapi.ShipKind;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class ConsolePlayer implements BCPlayer {

    private final Player consolePlayer;
    private final Scanner in = new Scanner(System.in);

    public ConsolePlayer(Player consolePlayer) {
        this.consolePlayer = consolePlayer;
    }

    @Override
    public void setupShips() {
        Random r = new Random(System.currentTimeMillis());
        int shipsPlaced = 0;
        while (true) {
            ShipKind kind;
            switch (shipsPlaced) {
                case 0:
                    kind = ShipKind.BATTLESHIP;
                    break;
                case 1:
                    kind = ShipKind.CARRIER;
                    break;
                case 2:
                    kind = ShipKind.CRUISER;
                    break;
                case 3:
                    kind = ShipKind.DESTROYER;
                    break;
                case 4:
                    kind = ShipKind.SUBMARINE;
                    break;
                default:
                    consolePlayer.ready();
                    return;
            }
            char startCol = (char) ('a' + r.nextInt(10));
            int startRow = 1 + r.nextInt(10);
            ShipDirection direction = r.nextBoolean() ? ShipDirection.HORIZONTAL : ShipDirection.VERTICAL;
            if (consolePlayer.placeShip(kind, startCol, startRow, direction)) {
                shipsPlaced++;
            }
        }
    }

    @Override
    public void nextShot() {
        BattleConsole.printGrid(consolePlayer);
        System.out.println("Time to shoot! Type the location of the next shot");
        String input;
        char col;
        int row;
        while (true) {
            input = in.nextLine().toLowerCase();

            if (input.length() != 2 && input.length() != 3) {
                System.out.println("Input of wrong length! The input needs to contain 1 letter between a and j and one number between 1 and 10.");
                continue;
            }

            col = input.charAt(0);
            if (col < 'a' || col > 'j') {
                System.out.println("Invalid letter! The input needs to contain 1 letter between a and j and one number between 1 and 10.");
                continue;
            }
            try {
                row = Integer.parseInt(input.substring(1));
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number! The input needs to contain 1 letter between a and j and one number between 1 and 10.");
                continue;
            }

            if (row < 1 || row > 10) {
                System.out.println("Invalid number! The input needs to contain 1 letter between a and j and one number between 1 and 10.");
                continue;
            }
            
            if(!consolePlayer.shootOpponent(col, row)){
                System.out.println("You cannot shoot at that field. Try another field.");
                continue;
            }
            break;
        }
    }

}
