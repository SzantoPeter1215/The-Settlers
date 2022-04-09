package model;

import java.util.*;

// A queue node used in BFS
class Node
{
    // (x, y) represents coordinates of a cell in the matrix
    int x, y;

    // maintain a parent node for printing the final path
    Node parent;

    Node(int x, int y, Node parent)
    {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }
}

public class PathSolver
{
    // Below arrays detail all four possible movements from a cell
    private static int[] row = { -1, 0, 0, 1 };
    private static int[] col = { 0, -1, 1, 0 };
    private static int[][] matrix;

    public void initMatrix(int N) {
        matrix = new int[N][N];
        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                matrix[i][j] = 1;
            }
        }
    }

    // The function returns false if (x, y) is not a valid position
    private static boolean isValid(int x, int y, int N) {
        return (x >= 0 && x < N) && (y >= 0 && y < N);
    }

    // Utility function to find path from source to destination
    private static void findPath(Node node, List<String> path)
    {
        if (node != null) {
            findPath(node.parent, path);
            path.add(node.toString());
        }
    }

    public void setMatrixField(int x, int y, int value) {
        matrix[x][y] = value;
    }

    // Find the shortest route in a matrix from source cell (x, y) to
    // destination cell (N-1, N-1)
    private static List<String> findPath(int[][] matrix, int x, int y, int destX, int destY)
    {

        // list to store shortest path
        List<String> path = new ArrayList<>();

        // base case
        if (matrix == null || matrix.length == 0) {
            return path;
        }

        // `N × N` matrix
        int N = matrix.length;
        //System.out.println("Matrix length: " + N);

        // create a queue and enqueue the first node
        Queue<Node> q = new ArrayDeque<>();
        Node src = new Node(x, y, null);
        q.add(src);

        // set to check if the matrix cell is visited before or not
        Set<String> visited = new HashSet<>();

        String key = src.x + "," + src.y;
        visited.add(key);

        // loop till queue is empty
        while (!q.isEmpty())
        {
            // dequeue front node and process it
            Node curr = q.poll();
            int i = curr.x, j = curr.y;

            // return if the destination is found
            if (i == destX && j == destY) {
                findPath(curr, path);
                return path;
            }

            // value of the current cell
            int n = matrix[i][j];

            // check all four possible movements from the current cell
            // and recur for each valid movement
            for (int k = 0; k < row.length; k++)
            {
                // get next position coordinates using the value of the current cell
                x = i + row[k] * n;
                y = j + col[k] * n;

                // check if it is possible to go to the next position
                // from the current position
                if (isValid(x, y, N))
                {
                    // construct the next cell node
                    Node next = new Node(x, y, curr);

                    key = next.x + "," + next.y;

                    // if it isn't visited yet
                    if (!visited.contains(key))
                    {
                        // enqueue it and mark it as visited
                        q.add(next);
                        visited.add(key);
                    }
                }
            }
        }

        // we reach here if the path is not possible
        return path;
    }

    public ArrayList<Integer>[] getPath(int startX, int startY, int endX, int endY)
    {
        // Find a route in the matrix from source cell (0, 0) to
        // destination cell (N-1, N-1)
        List<String> path = findPath(matrix, startX, startY, endX, endY);

        //System.out.println("Start: " + startX + ", " + startY + "\nEnd: " + endX + ", " + endY);
        //System.out.println(Arrays.deepToString(matrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        //System.out.println(path.toString());
        if (path != null && path.size() > 0) {
            //System.out.print("The shortest path is " + path);

            int pathSize = path.size();

            ArrayList<Integer> XChords = new ArrayList<>();
            ArrayList<Integer> YChords = new ArrayList<>();

            String[] raw = path.get(0).split(", ");
            XChords.add(Integer.parseInt(String.valueOf(raw[0].split("\\(")[1])));
            YChords.add(Integer.parseInt(String.valueOf(raw[1].split("\\)")[0])));

            for(int i = 1; i < pathSize; ++i) {
                raw = path.get(i).split(", ");
                XChords.add(Integer.parseInt(String.valueOf(raw[0].split("\\(")[1])));
                YChords.add(Integer.parseInt(String.valueOf(raw[1].split("\\)")[0])));

                //TODO: use this filler if we need steps greater than 1 or 0!!!
                /*
                if(Objects.equals(XChords.get(i - 1), XChords.get(i))) {
                    int diff = YChords.get(i-1) - YChords.get(i);
                    if(YChords.get(i-1) < YChords.get(i)) {
                        if(diff < -1) {
                            for(int j = 1; j<Math.abs(diff); ++j) {
                                XChords.add(XChords.get(i) + j + 1);
                                YChords.add(YChords.get(i));
                            }
                        }
                    } else {
                        if(diff > 1) {
                            for(int j = 1; j<Math.abs(diff); ++j) {
                                XChords.add(XChords.get(i) - j - 1);
                                YChords.add(YChords.get(i));
                            }
                        }
                    }
                } else {
                    int diff = XChords.get(i-1) - XChords.get(i);
                    if(XChords.get(i-1) < XChords.get(i)) {
                        if(diff < -1) {
                            for(int j = 0; j<Math.abs(diff); ++j) {
                                YChords.add(YChords.get(i) + j + 1);
                                XChords.add(XChords.get(i) + 2);
                            }
                        }
                    } else {
                        if(diff > 1) {
                            for(int j = 0; j<Math.abs(diff); ++j) {
                                YChords.add(YChords.get(i) - j - 1);
                                XChords.add(XChords.get(i) - 1);
                            }
                        }
                    }
                }
                */
            }

            //System.out.print("\n\n");
            for(int x = 0; x < matrix.length && false; ++x) {
                for(int y = 0; y < matrix.length; ++y) {
                    boolean found = false;
                    for(int p = 0; p < XChords.size(); ++p) {
                        if(XChords.get(p) == x && YChords.get(p) == y) {
                            System.out.print(" # ");
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        System.out.print(" · ");
                    }
                }
                System.out.print("\n");
            }

            ArrayList<Integer>[] res = new ArrayList[2];
            res[0] = XChords;
            res[1] = YChords;

            return res;
        } else {

            System.out.println("Destination is not found");
            return null;
        }
    }
}

// https://en.wikipedia.org/wiki/Breadth-first_search