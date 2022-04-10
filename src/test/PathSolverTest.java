package test;

import gui.GameUIConstants;
import model.GameLogic;
import model.dijkstra.Graph;
import model.dijkstra.GraphUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PathSolverTest {
    @Test
    public void testShortestPath1(){
        GameLogic gameLogic = new GameLogic();
        GameUIConstants gameConstants = new GameUIConstants();
        gameLogic.newGame(gameConstants.GAMEAREA_HEIGHT_canBeDividedBy,gameConstants.GAMEAREA_WIDTH_canBeDividedBy,"asd");
        Graph graph[] = GraphUtils.constructGraph(gameLogic.getGrids());
        Graph graphForRegular = graph[0];
        ArrayList<Integer>[] result = GraphUtils.getPath(graphForRegular, 0,7,0,15);
        //assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, 0]", result[0].toString());
        //assertEquals("[7, 8, 9, 10, 11, 12, 13, 14, 15]", result[1].toString());
    }
}