package main.java.hu.elte.fi.szofttech.pacman.model.soldier;
import main.java.hu.elte.fi.szofttech.pacman.gui.GameUIConstants;
import main.java.hu.elte.fi.szofttech.pacman.model.Field;
import main.java.hu.elte.fi.szofttech.pacman.model.PlayerTurn;
import main.java.hu.elte.fi.szofttech.pacman.model.Type;
import main.java.hu.elte.fi.szofttech.pacman.model.dijkstra.Graph;
import main.java.hu.elte.fi.szofttech.pacman.model.dijkstra.GraphUtils;

import java.util.ArrayList;

public class Soldier {
    private int health;
    private int damage;
    private int price;
    private final int tax;
    private boolean canClimb;
    private Type type;
    public PlayerTurn OwnerPlayer;
    public Type SoldierType;
    public int x;
    public int y;

    public int energy;

    private ArrayList<Integer> currentPathX;
    private ArrayList<Integer> currentPathY;

    private boolean climber;

    public Soldier(PlayerTurn isPlayer1Owened,int health,Type SoldierType, int x, int y){
        this.OwnerPlayer = isPlayer1Owened;
        this.health = health;
        this.SoldierType = SoldierType;
        this.x = x;
        this.y = y;
        this.energy = 10;
        this.damage = 15;
        this.price = 50;

        this.tax = 40;
    }
    public boolean minusHealth(int damage){
        if((health-damage)>=0){
            health -= damage;
            return true;
        }
        return false;
    }
    public int[] getPos() {
        int[] res = new int[2];
        res[0] = this.x;
        res[1] = this.y;
        return res;
    }

    public boolean createPath(Graph tree, int castleX, int castleY) {
        //System.out.println(x + " " + y + " " + castleX + " " + castleY);
        ArrayList<Integer>[] currentPath = GraphUtils.getPath(tree, x, y, castleX, castleY);

        //assert currentPath != null;
        if(currentPath == null) return false;
        currentPathX = currentPath[0];
        currentPathY = currentPath[1];
        return true;
    }

    public void restoreEnergy() {
        this.energy = 10;
    }

    public void step(Field matrix[][]) {
        if(currentPathX.size() == 0) return;
        currentPathX.remove(0);
        currentPathY.remove(0);

        if(currentPathX.size() == 0) return;
        int toStepX = currentPathX.get(0);
        int toStepY = currentPathY.get(0);

        if(matrix[toStepX][toStepY].getHill()) {
            if(this.SoldierType == Type.PLAYER1_SOLDIER1 || this.SoldierType == Type.PLAYER2_SOLDIER1) {
                this.energy -= 9;
            }
        } else {
            this.energy--;
        }

        if(energy >= 0) {
            this.x = toStepX;
            this.y = toStepY;
        }

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
        return this.SoldierType;
    }

    public int getTax() {
        return this.tax;
    }


    public int getHealth() { return health; }

    public int getDamage() {
        return damage;
    }

    public int getPrice() {
        return price;
    }
}
