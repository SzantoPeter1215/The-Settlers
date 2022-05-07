package main.java.hu.elte.fi.szofttech.pacman.model.dijkstra;

import main.java.hu.elte.fi.szofttech.pacman.model.Field;

import java.util.ArrayList;
import java.util.List;

public class GraphUtils {

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
