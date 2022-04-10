package model.soldier;
import gui.GameUIConstants;
import model.PlayerTurn;
import model.Type;
import model.dijkstra.Graph;
import model.dijkstra.GraphUtils;

import java.util.ArrayList;

public class Soldier {
    public int health;
    private int damage;
    private int price;
    private final int tax;
    private boolean canClimb;
    private Type type;
    public PlayerTurn OwnerPlayer;
    public Type SoldierType;
    public int x;
    public int y;

    private ArrayList<Integer> currentPathX;
    private ArrayList<Integer> currentPathY;

    private boolean climber;

    public Soldier(PlayerTurn isPlayer1Owened,int health,Type SoldierType, int x, int y){
        this.OwnerPlayer = isPlayer1Owened;
        this.health = health;
        this.SoldierType = SoldierType;
        this.x = x;
        this.y = y;

        this.tax = 40;
    }

    /*
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
    }*/

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

    public void createPath(Graph tree, int castleX, int castleY) {
        System.out.println(x + " " + y + " " + castleX + " " + castleY);
        ArrayList<Integer>[] currentPath = GraphUtils.getPath(tree, x, y, castleX, castleY);

        assert currentPath != null;
        currentPathX = currentPath[0];
        currentPathY = currentPath[1];
    }

    public void step() {
        currentPathX.remove(0);
        currentPathY.remove(0);
        this.x = currentPathX.get(0);
        this.y = currentPathY.get(0);
    }
    public String getSoliderImage(){
        if(OwnerPlayer==PlayerTurn.PLAYER1&&SoldierType==Type.PLAYER1_SOLDIER1){
            return GameUIConstants.player1Soldier1;
        }
        else if(OwnerPlayer==PlayerTurn.PLAYER1&&SoldierType==Type.PLAYER1_SOLDIER2){
            return GameUIConstants.player1Soldier2;
        }
        else if(OwnerPlayer==PlayerTurn.PLAYER2&&SoldierType==Type.PLAYER2_SOLDIER1){
            return GameUIConstants.player2Soldier1;
        }
        else if(OwnerPlayer==PlayerTurn.PLAYER2&&SoldierType==Type.PLAYER2_SOLDIER2){
            return GameUIConstants.player2Soldier2;
        }
        return "";
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
