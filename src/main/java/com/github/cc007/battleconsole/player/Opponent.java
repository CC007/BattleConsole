/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.battleconsole.player;

import com.github.cc007.battleshipsapi.Player;
import com.github.cc007.battleshipsapi.ShipDirection;
import com.github.cc007.battleshipsapi.ShipKind;
import java.util.Random;

/**
 *
 * @author Rik Schaaf aka CC007 (http://coolcat007.nl/)
 */
public class Opponent implements BCPlayer{

    private char col = 'a';
    private int row = 1;
    private final Player aiPlayer;

    /**
     * create an ai opponent
     *
     * @param aiPlayer the provided player for the ai in the battleships game
     */
    public Opponent(Player aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    /**
     * randomly place ships
     */
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
                    aiPlayer.ready();
                    return;
            }
            char startCol = (char) ('a' + r.nextInt(10));
            int startRow = 1 + r.nextInt(10);
            ShipDirection direction = r.nextBoolean() ? ShipDirection.HORIZONTAL : ShipDirection.VERTICAL;
            if (aiPlayer.placeShip(kind, startCol, startRow, direction)) {
                shipsPlaced++;
            }
        }
    }

    /**
     * The most simple battleships shot call ai you'll ever find
     */
    @Override
    public void nextShot() {
        aiPlayer.shootOpponent(col, row);

        row = (row % 10) + 1;
        if (row == 1) {
            col++;
        }

    }
}
