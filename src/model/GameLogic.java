package model;

import gui.GameUIConstants;
import gui.Position;
import model.dijkstra.Graph;
import model.dijkstra.GraphUtils;
import model.info.InfoBoard;
import model.soldier.Soldier;
import model.soldier.SoldierType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class GameLogic {
    private final GameUIConstants gameConstants;

    private int row;
    private int column;
    private Type FillTowerType;
    public static PlayerTurn playerTurn = PlayerTurn.PLAYER1;
    private int player1Gold = GameConstants.START_GOLD_PLAYER;
    private int player2Gold = GameConstants.START_GOLD_PLAYER;
    public Position player1Castle;
    public Position player2Castle;

    private int Player1Unit1Number;
    private int Player1Unit2Number;
    private int Player2Unit1Number;
    private int Player2Unit2Number;

    //private PathSolver path;

    private ArrayList<Soldier> allSoldiers;

    Graph graphForRegular;
    Graph graphForCLimber;

    public Field[][] grids;

    private InfoBoard infoBoard;

    /*
        Durning attack phase we count how much rounds has been elpased
        and we swich back to the forst player once it's done.
    **/
    private int stepCounter;

    public int getPlayer1Unit1Number() {
        return Player1Unit1Number;
    }

    public void incPlayer1Unit1Number() {
        Player1Unit1Number += 1;
    }

    public int getPlayer1Unit2Number() {
        return Player1Unit2Number;
    }

    public void incPlayer1Unit2Number() {
        Player1Unit2Number += 1;
    }

    public int getPlayer2Unit1Number() {
        return Player2Unit1Number;
    }

    public void incPlayer2Unit1Number() {
        Player2Unit1Number += 1;
    }

    public int getPlayer2Unit2Number() {
        return Player2Unit2Number;
    }

    public void incPlayer2Unit2Number() {
        Player2Unit2Number += 1;
    }

    public int getPlayer1Gold() {
        return player1Gold;
    }

    public int getPlayer2Gold() {
        return player2Gold;
    }

    public Type getFillTowerType() {
        return FillTowerType;
    }

    public void setFillTowerType(Type fillTowerType) {
        FillTowerType = fillTowerType;
    }

    public int getRow() {
        return row;
    }
    public int getColumn(){
        return column;
    }

    /*public void setRow(int row) {
        //this.row = row;
    }*/

    /*public void setTypeElement(int x, int y,Type element){
        if(element==Type.EMPTY){
            grids[x][y].stuffOnTheField.clear();
        }
        grids[x][y].add(element);
    }*/
    public Field[][] getGrids() {
        /*Type[][] convertedGrid = new Type[row][column];
        for(int i = 0;i<column;i++){
            for (int j = 0; j < row; j++) {
                convertedGrid[i][j] = grids[i][j].getMainType();
            }
        }
        return convertedGrid;*/
        return grids;
    }



    public GameLogic() {
        gameConstants = new GameUIConstants();
    }

    public void newGame(int row, int column, String userName){
        this.row = gameConstants.GAMEAREA_HEIGHT_canBeDividedBy;
        this.column = gameConstants.GAMEAREA_WIDTH_canBeDividedBy;

        //path = new PathSolver();

        grids = new Field[row][column];
        stepCounter = 0;
        initGrid();

        //TODO: remove , just for testing hill and wa'er. Migh causes out of index error (decrease the sizes then)
        //0-17
        for(int k = 0; k < this.row; ++k) {
            if(k == this.row / 2) continue;

            for(int i = this.column / 2 - this.column / 5; i <this.column / 2 + this.column / 5; ++i) {

                grids[k][i].addHill();

                if(i%5 == 0 && k % 2 == 0) {
                    grids[k][i].isHill = false;
                    grids[k][i].addWater();
                } else if(i % 3 == 0 && k%5 == 0) {
                    grids[k][i].isHill = false;

                }

            }
        }

        for(int k = 0; k < 20; k+=3) {
            for(int i = 0; i < 7; i+= 2) {

            }
        }

        initInfoBoard();

        allSoldiers = new ArrayList<>();

        graphForRegular = new Graph();
        graphForCLimber = new Graph();

        infoBoard.reset(userName);

        int Player1castleRandomX = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4);
        int Player2castleRandomX = ThreadLocalRandom.current().nextInt(gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4*3, gameConstants.GAMEAREA_WIDTH_canBeDividedBy-1);
        int castleRandomY = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_HEIGHT_canBeDividedBy);//It's the same for both castles
        this.player1Castle = new Position(castleRandomY,Player1castleRandomX);
        this.player2Castle = new Position(castleRandomY,Player2castleRandomX);
        Castle castle_Player1 = new Castle(PlayerTurn.PLAYER1,100,Type.PLAYER1_CASTLE);
        grids[castleRandomY][Player1castleRandomX].addCaslte(castle_Player1);
        Castle castle_Player2 = new Castle(PlayerTurn.PLAYER2,100,Type.PLAYER2_CASTLE);
        grids[castleRandomY][Player2castleRandomX].addCaslte(castle_Player2);
        setFillTowerType(Type.TOWER1);
    }
    public void createPlayer1Soldier1(){
        //player1Soldiers.add(new Soldier(SoldierType.REGULAR, this.player1Castle.x,this.player1Castle.y));

    }

    private void initInfoBoard() {
        infoBoard = new InfoBoard();
    }


    private boolean isDead() {
        //TODO: check all the requirements
        return false;
    }

    /**
     * Az alap négyzetháló mátrix inicializálása
     */
    private void initGrid() {
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                //grids[i][j] = Type.EMPTY;
                grids[i][j] = new Field(j,i);
            }
        }
    }
    public Boolean removeMoney(int minusGold,PlayerTurn player){
        if(player==PlayerTurn.PLAYER1){
            int expectedGold = this.player1Gold - minusGold;
            if(expectedGold<0){
                return false;
            }
            else{
                this.player1Gold -= minusGold;
                return true;
            }
        }
        else if(player==PlayerTurn.PLAYER2){
            int expectedGold = this.player2Gold - minusGold;
            if(expectedGold<0){
                return false;
            }
            else{
                this.player2Gold -= minusGold;
                return true;
            }
        }
        return false;
    }

    private void addMoney(int amount, int player) {

        if(player == 1) {
            player1Gold += amount;
        } else {
            player2Gold += amount;

        }
    }
    private void clearGridSoldiers() {
        for (int i = 0; i < this.row; ++i) {
            for (int j = 0; j < this.column; ++j) {
                if(grids[i][j].soldiersOnTheField.size()>0) {
                    grids[i][j].removeSoldier();
                }
            }
        }
    }
    public void initAttackPhase() {
        GameLogic.playerTurn = PlayerTurn.ATTACK;
        System.out.println("ATTACK!");
        stepCounter = 0;

        //Time to create the two graphs for the path finder to solve
        Graph[] res = GraphUtils.constructGraph(grids);

        graphForRegular = res[0];
        graphForCLimber = res[1];

        addSoldiersToLists();


        for(Soldier s : allSoldiers) {
            s.restoreEnergy();

            if(PlayerTurn.PLAYER1==s.OwnerPlayer){
                boolean found = s.createPath(s.getType() == Type.PLAYER1_SOLDIER1?
                        graphForRegular :
                        graphForCLimber ,player2Castle.x, player2Castle.y);
                if(!found) {
                    s.createPath(graphForCLimber ,player2Castle.x, player2Castle.y);
                    //todo handle if this is null.
                }

                addMoney(s.getTax(), 1);
            }
            else{
                boolean found = s.createPath(s.getType() == Type.PLAYER2_SOLDIER1?
                        graphForRegular :
                        graphForCLimber ,player1Castle.x, player1Castle.y);
                if(!found) {
                    s.createPath(graphForCLimber ,player1Castle.x, player1Castle.y);
                }
                addMoney(s.getTax(), 2);
            }
        }
    }

    public void nextAttackPhase() {
        if(stepCounter >= GameConstants.ATTACK_ROUNDS) {
            playerTurn = PlayerTurn.PLAYER1;
            return;
        }
        ++stepCounter;

        clearGridSoldiers();

        for(Soldier s : allSoldiers) {
            s.step(grids);
            int[] cords = s.getPos();
            grids[cords[0]][cords[1]].addSoldier(s);

        }
        addSoldiersToLists();
        //damageSoldiers();
    }

    private void addSoldiersToLists() {
        allSoldiers.clear();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                List<Soldier> toMovedSoldier =grids[i][j].getSoliders();
                if(toMovedSoldier!=null){
                    allSoldiers.addAll(grids[i][j].getSoliders());
                }
            }
        }
    }

    public void damageSoldiers(){
        for(Soldier s : allSoldiers) {
            int[] cords = s.getPos();
            boolean takesDamage = false;
            for(int i = -1; i <= 1; i++){
                for(int j = -1; j <= 1; j++){
                    if(grids[cords[0]+i][cords[1]+i].isTowerOnTheField()) {
                        takesDamage = true;
                    }
                }
            }
            if(takesDamage){
                s.setHealth(s.getHealth()-30);
            }
        }
    }

    public InfoBoard getInfoBoard() {
        return infoBoard;
    }

    public boolean canBuild(int x, int y) {
        int[][] matrix = new int[grids.length][grids[0].length];

        for(int i = 0; i < grids.length; ++i) {
            for(int j = 0; j < grids[0].length; ++j) {
                Field current = grids[i][j];
                //TODO: maybe reverse
                if(i == x && j == y) {
                    matrix[i][j] = 0;
                } else if(!current.isTowerOnTheField() && !current.getWater()) {
                    matrix[i][j] = 1;
                }
                else {
                    matrix[i][j] = 0;
                }
            }
        }

        int[] pos1 = {player1Castle.x,player1Castle.y};
        int[] pos2 = {player2Castle.x,player2Castle.y};

        boolean res = CheckRouteExist.check(matrix, pos1, pos2);
        System.out.println(res);
        return res;
    }
}
