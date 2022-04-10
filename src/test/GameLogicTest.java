package test;

import gui.GameUIConstants;
import model.GameConstants;
import model.GameLogic;
import model.PlayerTurn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    @Test
    public void testNewGame(){
        GameLogic gameLogic = new GameLogic();
        GameUIConstants gameConstants = new GameUIConstants();
        gameLogic.newGame(gameConstants.GAMEAREA_HEIGHT_canBeDividedBy,gameConstants.GAMEAREA_WIDTH_canBeDividedBy,"asd");
        assertEquals(gameConstants.GAMEAREA_HEIGHT_canBeDividedBy, gameLogic.getRow());
        assertEquals(gameConstants.GAMEAREA_WIDTH_canBeDividedBy, gameLogic.getColumn());
        //assertEquals(null, gameLogic.getGrids());
    }

    /*@Test
    public void testInitAttackPhase(){
        GameLogic gameLogic = new GameLogic();
        GameUIConstants gameConstants = new GameUIConstants();
        gameLogic.newGame(gameConstants.GAMEAREA_HEIGHT_canBeDividedBy,gameConstants.GAMEAREA_WIDTH_canBeDividedBy,"asd");
        gameLogic.initAttackPhase();
    }

    @Test
    public void testNextAttackPhase(){
        GameLogic gameLogic = new GameLogic();
        gameLogic.newGame(100,100,"asd");
        gameLogic.initAttackPhase();
        gameLogic.nextAttackPhase();
    }*/

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