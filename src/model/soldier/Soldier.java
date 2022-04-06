package model.soldier;
import model.PathSolver;
import model.Type;

import java.util.ArrayList;

public class Soldier {
    private int health;
    private int damage;
    private int price;
    private int tax;
    private boolean canClimb;
    private Type type;

    private int x;
    private int y;

    private ArrayList<Integer> currentPathX;
    private ArrayList<Integer> currentPathY;

    private boolean climber;

    public Soldier(SoldierType solder, int x, int y) {
        this.x = x;
        this.y = y;

        currentPathX = new ArrayList<>();
        currentPathY = new ArrayList<>();

        switch (solder) {
            case REGULAR: {
                this.health = 100;
                this.damage = 100;
                this.price = 100;
                this.tax = 100;
                this.type = Type.SOLDER1;


                this.canClimb = false;

                break;
            }
            case CLIMBER: {
                this.health = 90;
                this.damage = 95;
                this.price = 110;
                this.tax = 105;
                this.type = Type.SOLDER2;

                this.canClimb = false;

                break;
            }

        }
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getPos() {
        int[] res = new int[2];
        res[0] = this.x;
        res[1] = this.y;
        return res;
    }

    public void createPath(PathSolver path, int castleX, int castleY) {
        //System.out.println(x + " " + y + " " + castleX + " " + castleY);
        ArrayList<Integer>[] currentPath = path.getPath(x, y, castleX, castleY);
        currentPathX = currentPath[0];
        currentPathY = currentPath[1];
    }

    public void step() {
        currentPathX.remove(0);
        currentPathY.remove(0);
        this.x = currentPathX.get(0);
        this.y = currentPathY.get(0);
    }

    public Type getType() {
        return this.type;
    }

    public int getTax() {
        return this.tax;
    }


    public int getHealth() { return health; }

    public void setHealth(int health) { this.health = health; }
}
