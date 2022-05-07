package test.java.hu.elte.fi.szofttech.pacman.model;

import main.java.hu.elte.fi.szofttech.pacman.model.dijkstra.Graph;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//import org.junit.Test;
//import static org.junit.Assert.*;

class PathSolverTest {
    /*
    @Test
    public void testShortestPath0(){
        GameLogic gameLogic = new GameLogic();
        GameUIConstants gameConstants = new GameUIConstants();
        gameLogic.newGame(gameConstants.GAMEAREA_HEIGHT_canBeDividedBy,gameConstants.GAMEAREA_WIDTH_canBeDividedBy,"asd");
        Graph graph[] = GraphUtils.constructGraph(gameLogic.getGrids());
        Graph graphForRegular = graph[0];
        ArrayList<Integer>[] result = GraphUtils.getPath(graphForRegular, 0,7,0,15);
        //assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, 0]", result[0].toString());
        //assertEquals("[7, 8, 9, 10, 11, 12, 13, 14, 15]", result[1].toString());
        System.out.println(result[0].toString());
        System.out.println(result[1].toString());
    }*/

    /*
     * Create a graph for testing
     *
     *
     *         B    1     D   6
     *    5  .-+---------.'-------F
     *    .-'  |       .' |
     * A.'     |2    .'   |
     *   `.    |   .'4    |3
     *    1`-. | .'       |
     *        `.'---------+
     *         C     8    E
     *
     */

    @Test
    public void testShortestPath1() {
        Graph graph = this.constructGraph();
        List<String> result = graph.shortestPath("A", "F");
        System.out.println("shortest path between A and F: " + result);
        assertEquals(result.size(), 5);
        assertEquals(result.get(0), "A");
        assertEquals(result.get(1), "C");
        assertEquals(result.get(2), "B");
        assertEquals(result.get(3), "D");
        assertEquals(result.get(4), "F");
    }

    @Test
    public void testShortestPath2() {
        Graph graph = this.constructGraph();
        List<String> result = graph.shortestPath("D", "A");
        System.out.println("shortest path between D and A: " + result);
        assertEquals(result.size(), 4);
        assertEquals(result.get(0), "D");
        assertEquals(result.get(1), "B");
        assertEquals(result.get(2), "C");
        assertEquals(result.get(3), "A");
    }

    @Test
    public void testShortestPath3() {
        Graph graph = this.constructGraph();
        List<String> result = graph.shortestPath("C", "F");
        System.out.println("shortest path between C and F: " + result);
        assertEquals(result.size(), 4);
        assertEquals(result.get(0), "C");
        assertEquals(result.get(1), "B");
        assertEquals(result.get(2), "D");
        assertEquals(result.get(3), "F");
    }

    private Graph constructGraph() {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "C", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("B", "D", 1);
        graph.addEdge("C", "D", 4);
        graph.addEdge("C", "E", 8);
        graph.addEdge("D", "E", 3);
        graph.addEdge("D", "F", 6);
        return graph;
    }



}


