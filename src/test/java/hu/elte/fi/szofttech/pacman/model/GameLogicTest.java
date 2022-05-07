package test.java.hu.elte.fi.szofttech.pacman.model;

import main.java.hu.elte.fi.szofttech.pacman.gui.GameUIConstants;
import main.java.hu.elte.fi.szofttech.pacman.model.GameLogic;
import main.java.hu.elte.fi.szofttech.pacman.model.PlayerTurn;


//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import static org.junit.Assert.*;


class GameLogicTest {
    @Test
    public void testMoney(){
        GameLogic gameLogic = new GameLogic();
        assertEquals(1000, gameLogic.getPlayer1Gold());
        assertEquals(1000, gameLogic.getPlayer2Gold());
    }

    @Test
    public void testRemoveMoney(){
        GameLogic gameLogic = new GameLogic();
        gameLogic.removeMoney(100, PlayerTurn.PLAYER1);
        assertEquals(900, gameLogic.getPlayer1Gold());
        gameLogic.removeMoney(100, PlayerTurn.PLAYER2);
        assertEquals(900, gameLogic.getPlayer2Gold());
    }

    @Test
    public void testAddMoney(){
        GameLogic gameLogic = new GameLogic();
        gameLogic.addMoney(100, 1);
        assertEquals(1100, gameLogic.getPlayer1Gold());
        gameLogic.addMoney(100, 2);
        assertEquals(1100, gameLogic.getPlayer2Gold());
    }
}