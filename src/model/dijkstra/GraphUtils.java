package model.dijkstra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Field;

import java.util.ArrayList;
import java.util.List;

public class GraphUtils {

    /*
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
        assertEquals(result.toString(), "[(0, 0), (0, 1), (0, 2), (1, 2), (2, 2), (2, 3)]");
    }



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
    */



    public static Graph[] constructGraph(Field[][] matrix) {

        Graph graph1 = new Graph();
        Graph graph2 = new Graph();

        ArrayList<String> blockList = new ArrayList<>();
        ArrayList<String> hillList = new ArrayList<>();


        for(int y = 0; y < matrix.length; ++y) {
            for(int x = 0; x < matrix[y].length; ++x) {

                Field current = matrix[y][x];
                String thisNodeName = "(" + y + ", " + x + ")";

                if(current.getWater() || current.isTowerOnTheField()) {
                    blockList.add(thisNodeName);
                    //System.out.println(thisNodeName);
                }
                if(current.getHill()) {
                    hillList.add(thisNodeName);
                    //System.out.print(" " + thisNodeName);
                }
            }
        }


        for(int y = 0; y < matrix.length; ++y) {
            for(int x = 0; x < matrix[y].length; ++x) {

                //Field current = matrix[y][x];


                String thisNodeName = "(" + y + ", " + x + ")";

                if (y > 0) {
                    String destNodeName = "(" + (y - 1) + ", " + x + ")";
                    if(!thisNodeName.equals(destNodeName)) {
                        String added = thisNodeName + destNodeName;
                        String addedReverse = destNodeName + thisNodeName;
                        if(!blockList.contains(thisNodeName) && !blockList.contains(destNodeName) && !blockList.contains(added) && !blockList.contains(addedReverse)) {
                            blockList.add(added);
                            graph2.addEdge(thisNodeName, destNodeName, 1);
                            Field toAdd = matrix[y - 1][x];
                            if(!hillList.contains(thisNodeName) && !hillList.contains(destNodeName)) {
                                graph1.addEdge(thisNodeName, destNodeName, 1);
                            } else {
                                //System.out.println("Hill: " + thisNodeName + " " + destNodeName);

                            }
                        }
                    } else {
                        System.out.println("equals");
                    }

                    //System.out.print(thisNodeName + "-" + destNodeName + ", " + (weightRegular + weightToAdd) + " | ");
                }

                if (y < matrix.length - 1) {
                    String destNodeName = "(" + (y + 1) + ", " + x + ")";
                    if(!thisNodeName.equals(destNodeName)) {
                        String added = thisNodeName + destNodeName;
                        String addedReverse = destNodeName + thisNodeName;
                        if(!blockList.contains(thisNodeName) && !blockList.contains(destNodeName) && !blockList.contains(added) && !blockList.contains(addedReverse)) {
                            blockList.add(added);
                            graph2.addEdge(thisNodeName, destNodeName, 1);
                            Field toAdd = matrix[y + 1][x];
                            if(!hillList.contains(thisNodeName) && !hillList.contains(destNodeName)) {
                                graph1.addEdge(thisNodeName, destNodeName, 1);
                            } else {
                                //System.out.println("Hill: " + thisNodeName + " " + destNodeName);

                            }
                        }
                    } else {
                        System.out.println("equals");
                    }
                    //System.out.print(thisNodeName + "-" + destNodeName + ", " + (weightRegular + weightToAdd) + " | ");

                }

                if (x < matrix[y].length - 1) {
                    String destNodeName = "(" + y + ", " + (x + 1) + ")";
                    if(!thisNodeName.equals(destNodeName)) {
                        String added = thisNodeName + destNodeName;
                        String addedReverse = destNodeName + thisNodeName;
                        if(!blockList.contains(thisNodeName) && !blockList.contains(destNodeName) && !blockList.contains(added) && !blockList.contains(addedReverse)) {
                            blockList.add(added);
                            graph2.addEdge(thisNodeName, destNodeName, 1);
                            Field toAdd = matrix[y][x + 1];
                            if(!hillList.contains(thisNodeName) && !hillList.contains(destNodeName)) {
                                graph1.addEdge(thisNodeName, destNodeName, 1);
                            } else {
                                //System.out.println("Hill: " + thisNodeName + " " + destNodeName);

                            }
                        }
                    } else {
                        System.out.println("equals");
                    }
                    //System.out.print(thisNodeName + "-" + destNodeName + ", " + (weightRegular + weightToAdd) + " | ");

                }

                if (x > 0) {
                    String destNodeName = "(" + y + ", " + (x - 1) + ")";
                    if(!thisNodeName.equals(destNodeName)) {
                        String added = thisNodeName + destNodeName;
                        String addedReverse = destNodeName + thisNodeName;
                        if(!blockList.contains(thisNodeName) && !blockList.contains(destNodeName) && !blockList.contains(added) && !blockList.contains(addedReverse)) {
                            blockList.add(added);
                            graph2.addEdge(thisNodeName, destNodeName, 1);
                            Field toAdd = matrix[y][x - 1];
                            if(!hillList.contains(thisNodeName) && !hillList.contains(destNodeName)) {
                                graph1.addEdge(thisNodeName, destNodeName, 1);
                            } else {
                                //System.out.println("Hill: " + thisNodeName + " " + destNodeName);

                            }

                        }
                    } else {
                        System.out.println("equals");
                    }
                }

            }
        }

        for(int y = 0; y < matrix.length && false; ++y) {
            System.out.print(y + ": ");
            for(int x = 0; x < matrix[y].length; ++x) {
                System.out.print(x + " ");
            }
            System.out.print("\n");
        }
        Graph[] res = new Graph[2];
        res[0] = graph1;
        res[1] = graph2;

        return res;
    }

    public static ArrayList<Integer>[] getPath(Graph tree, int startX, int startY, int endX, int endY) {

        List<String> result = tree.shortestPath(    "(" + startX + ", " + startY + ")",
                                                    "(" + endX +", " + endY + ")");

        if(result!=null && result.size() > 0) {
            System.out.println("shortest path between (" + startX +", " + startY + ") and " +
                    "(" + endX + ", " + endY + "): " + result);

            ArrayList<Integer> XChords = new ArrayList<>();
            ArrayList<Integer> YChords = new ArrayList<>();

            for(String chords : result ) {
                String[] raw = chords.split(", ");
                XChords.add(Integer.parseInt(String.valueOf(raw[0].split("\\(")[1])));
                YChords.add(Integer.parseInt(String.valueOf(raw[1].split("\\)")[0])));
            }
            ArrayList<Integer>[] res = new ArrayList[2];
            res[0] = XChords;
            res[1] = YChords;

            return res;
        } else {
            System.out.println("NO ROUTE");
            return null;
        }
    }
}

//https://github.com/zhaohuabing/shortest-path-weighted-graph-dijkstra-java
