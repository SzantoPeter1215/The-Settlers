package path_finders.dijkstra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class GraphTest {

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

    @Test
    public void testShortestPath4() {
        int[][] matrix= {
                {1,1,1},
                {1,1,1},
                {1,1,1},
                {1,1,1}};
        Graph graph = this.constructGraph(matrix);
        List<String> result = graph.shortestPath("(0, 0)", "(2, 3)");
        System.out.println("shortest path between (0, 0) and (2, 3): " + result);
    }

    /**
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
     *
     */
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

    private Graph constructGraph(int[][] matrix) {

        Graph graph = new Graph();

        // Converting table to nodes
        for(int y = 0; y < matrix.length; ++y) {
            for(int x = 0; x < matrix[y].length; ++x) {

                String thisNodeName = "(" + x + ", " + y + ")";

                if (y > 0) {
                    //int top = matrix[y - 1][x];
                    String destNodeName = "(" + x + ", " + (y - 1) + ")";
                    graph.addEdge(thisNodeName, destNodeName, matrix[y][x]);
                }

                if (y < matrix.length - 1) {
                    //int bottom = matrix[y + 1][x];
                    String destNodeName = "(" + (x) + ", " + (y + 1) + ")";
                    graph.addEdge(thisNodeName, destNodeName, matrix[y][x]);
                }

                if (x < matrix[y].length - 1) {
                    //int right = matrix[y][x + 1];
                    String destNodeName = "(" + (x + 1) + ", " + (y) + ")";
                    graph.addEdge(thisNodeName, destNodeName, matrix[y][x]);
                }

                if (x > 0) {
                    //int left = matrix[y][x - 1];
                    String destNodeName = "(" + (x - 1) + ", " + (y) + ")";
                    graph.addEdge(thisNodeName, destNodeName, matrix[y][x]);
                }

            }
        }
        return graph;
    }
}
