/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.battleconsole;

import com.github.cc007.battleconsole.player.BCPlayer;
import com.github.cc007.battleconsole.player.ConsolePlayer;
import com.github.cc007.battleconsole.player.Opponent;
import com.github.cc007.battleshipsapi.FieldState;
import com.github.cc007.battleshipsapi.Game;
import com.github.cc007.battleshipsapi.Grid;
import com.github.cc007.battleshipsapi.Player;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class BattleConsole {

    public static final Game game = new Game();
    public static BCPlayer consolePlayer = new ConsolePlayer(game.getPlayer1());
    public static BCPlayer opponent = new Opponent(game.getPlayer2());

    public static void main(String[] args) {
        boolean playGame = true;
        while (playGame) {
            consolePlayer.setupShips();
            opponent.setupShips();
            while (!game.isGameOver()) {
                consolePlayer.nextShot();
                if (game.isGameOver()) {
                    printGrid(game.getPlayer1());
                    break;
                }
                opponent.nextShot();
            }
            System.out.println("The game is over.");
            if (game.getPlayer1().hasWon()) {
                System.out.println("You won the game!");
            } else {
                System.out.println("You lost the game.");
            }
            playGame = false;
        }
    }

    public static void printGrid(Player player) {
        printGrid(player.getOpponentGrid());
        System.out.println("----------------------");
        printGrid(player.getOwnGrid());
    }

    private static void printGrid(Grid grid) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        System.out.println(" +-+-+-+-+-+-+-+-+-+-+");
        for (int i = 0; i < 10; i++) {
            System.out.print(((char) ('A' + i)) + "|");
            for (int j = 1; j < 11; j++) {
                FieldState state = grid.get((char) ('a' + i)).get(j).getState();
                switch (state) {
                    case EMPTY:
                        System.out.print(" ");
                        break;
                    case HIT:
                        System.out.print("X");
                        break;
                    case MISS:
                        System.out.print("O");
                        break;
                    case OCCUPIED:
                        System.out.print(".");
                        break;
                }
                System.out.print("|");
            }
            System.out.println("\n +-+-+-+-+-+-+-+-+-+-+");
        }
    }
}
