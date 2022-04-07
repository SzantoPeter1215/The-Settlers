package model;

import gui.GameUIConstants;
import gui.Position;
import model.info.InfoBoard;
import model.soldier.Soldier;
import model.soldier.SoldierType;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public final class GameLogic {
    private final GameUIConstants gameConstants;

    private int row;
    private int column;
    private Type FillTowerType;
    public static PlayerTurn playerTurn = PlayerTurn.PLAYER1;
    private int player1Gold = GameConstants.START_GOLD_PLAYER;
    private int player2Gold = GameConstants.START_GOLD_PLAYER;
    //TODO: a castle helyét am nem a logicban kéne tartani?
    // Vagy jó a megjelenítési rétegben mert nem függ tőle a többi esemény.
    private Position player1Castle;
    private Position player2Castle;

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

    private int Player1Unit1Number;
    private int Player1Unit2Number;
    private int Player2Unit1Number;
    private int Player2Unit2Number;

    private PathSolver path;

    private ArrayList<Soldier> player1Soldiers;
    private ArrayList<Soldier> player2Soldiers;



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

    public void setRow(int row) {
        this.row = row;
    }

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

    public Field[][] grids;

    private InfoBoard infoBoard;

    /*
        Durning attack phase we count how much rounds has been elpased
        and we swich back to the forst player once it's done.
    **/
    private int stepCounter;

    public GameLogic() {
        gameConstants = new GameUIConstants();
    }

    public void newGame(int row, int column, String userName){
        this.row = row;
        this.column = column;

        path = new PathSolver();

        grids = new Field[row][column];
        stepCounter = 0;
        initGrid();

        initInfoBoard();
        path.initMatrix(column);

        player1Soldiers = new ArrayList<Soldier>();
        player2Soldiers = new ArrayList<Soldier>();

        //TODO: remove the inital solder makes after yopu can create your own!


/*        grids[1][1] = Type.SOLDER1;
        player1Soldiers.add(new Soldier(SoldierType.REGULAR, 1,1));

        grids[10][1] = Type.SOLDER1;
        player1Soldiers.add(new Soldier(SoldierType.CLIMBER, 10,1));

        grids[1][10] = Type.SOLDER2;
        player2Soldiers.add(new Soldier(SoldierType.REGULAR, 1,10));

        grids[10][10] = Type.SOLDER2;
        player2Soldiers.add(new Soldier(SoldierType.CLIMBER, 10,10));*/





        //TODO: set the grids to their default type. (according to the map we choose)

        infoBoard.reset(userName);

        int Player1castleRandomX = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4);
        int Player2castleRandomX = ThreadLocalRandom.current().nextInt(gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4*3, gameConstants.GAMEAREA_WIDTH_canBeDividedBy-1);
        int castleRandomY = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_HEIGHT_canBeDividedBy);//It's the same for both castles
        this.player1Castle = new Position(Player1castleRandomX,castleRandomY);
        this.player2Castle = new Position(Player2castleRandomX,castleRandomY);
        Castle castle_Player1 = new Castle(playerTurn,100,Type.PLAYER1_CASTLE);
        grids[castleRandomY][Player1castleRandomX].addCaslte(castle_Player1);
        Castle castle_Player2 = new Castle(playerTurn,100,Type.PLAYER2_CASTLE);
        grids[castleRandomY][Player2castleRandomX].addCaslte(castle_Player2);
        setFillTowerType(Type.TOWER1);
    }
    public void createPlayer1Soldier1(){
        player1Soldiers.add(new Soldier(SoldierType.REGULAR, this.player1Castle.x,this.player1Castle.y));
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
                grids[i][j] = new Field();
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

    private void updatePathMatrix() {
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < row; ++j) {
                if(!grids[i][j].isCastleOnTheField()&&!grids[i][j].isTowerOnTheField()){
                    path.setMatrixField(i, j, 1);
                }
                else{
                    path.setMatrixField(i, j, 0);
                }
            }
        }
    }
    private void clearGridSoldiers() {
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < row; ++j) {
                if(grids[i][j].soldiersOnTheField.size()>0) {
                    grids[i][j].removeSoldier();
                }
            }
        }
    }
    public void initAttackPhase() {
        //TODO: itt kéne összeszámolni a katonákat és pénzt adni a megfelelő játékosnak
        GameLogic.playerTurn = PlayerTurn.ATTACK;
        System.out.println("ATTACK!");
        stepCounter = 0;
        updatePathMatrix();

        for(Soldier s : player1Soldiers) {
            s.createPath(path, player2Castle.x, player2Castle.y);
            addMoney(s.getTax(), 1);
        }
        for(Soldier s : player2Soldiers) {
            s.createPath(path, player1Castle.y, player1Castle.x);
            addMoney(s.getTax(), 2);
        }
    }

    public void nextAttackPhase() {
        if(stepCounter >= 3) {
            playerTurn = PlayerTurn.PLAYER1;
            return;
        }
        ++stepCounter;
        //TODO: create a method that accepts a function and executes it to all the grid elements.
        //TODO: HANDLE WHEN TWO THINGS ARE IN THE SAME PLACE!!!

        clearGridSoldiers();

        for(Soldier s : player1Soldiers) {
            s.step();
            int[] cords = s.getPos();
            grids[cords[0]][cords[1]].addSoldier(s);

        }

        for(Soldier s : player2Soldiers) {
            s.step();
            int[] cords = s.getPos();
            grids[cords[0]][cords[1]].addSoldier(s);

        }

        soldiersHealth();
        damageSoldiers();
        soldiersHealth();
    }

    public void damageSoldiers(){
        for(Soldier s : player1Soldiers) {
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

        for(Soldier s : player2Soldiers) {
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

    public void soldiersHealth(){
        for(Soldier s : player1Soldiers) {
            System.out.println(s.getHealth());
        }

        for(Soldier s : player2Soldiers) {
            System.out.println(s.getHealth());
        }
    }

    public InfoBoard getInfoBoard() {
        return infoBoard;
    }
}
