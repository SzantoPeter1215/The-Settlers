package main.java.hu.elte.fi.szofttech.pacman.model;

import main.java.hu.elte.fi.szofttech.pacman.gui.*;
import main.java.hu.elte.fi.szofttech.pacman.model.dijkstra.Graph;
import main.java.hu.elte.fi.szofttech.pacman.model.dijkstra.GraphUtils;
import main.java.hu.elte.fi.szofttech.pacman.model.info.InfoBoard;
import main.java.hu.elte.fi.szofttech.pacman.model.soldier.Soldier;

import java.util.ArrayList;
import java.util.List;

public final class GameLogic {
    private final GameUIConstants gameConstants;
    public List<Field> towerAndCastle;

    private int row;
    private int column;
    private Type FillTowerType;
    public static PlayerTurn playerTurn = PlayerTurn.PLAYER1;
    private int player1Gold = GameConstants.START_GOLD_PLAYER;
    private int player2Gold = GameConstants.START_GOLD_PLAYER;
    private int player1Unit1Count;

    public int getPlayer1Unit1Count() {
        return player1Unit1Count;
    }
    public void incPlayer1Unit1Count() {
        player1Unit1Count++;
    }
    public int getPlayer1Unit2Count() {
        return player1Unit2Count;
    }
    public void incPlayer1Unit2Count() {
        player1Unit2Count++;
    }

    public int getPlayer2Unit1Count() {
        return player2Unit1Count;
    }
    public void incPlayer2Unit1Count() {
        player2Unit1Count++;
    }

    public int getPlayer2Unit2Count() {
        return player2Unit2Count;
    }
    public void incPlayer2Unit2Count() {
        player2Unit2Count++;
    }

    private int player1Unit2Count;
    private int player2Unit1Count;
    private int player2Unit2Count;
    //TODO: a castle helyet am nem a logicban kene tartani?
    // Vagy jo a megjelenitesi retegben mert nem fugg tole a tobbi esemeny.
    public Position player1Castle;
    public Position player2Castle;

    private int Player1Unit1Number;
    private int Player1Unit2Number;
    private int Player2Unit1Number;
    private int Player2Unit2Number;
    public ArrayList<TrainingField> placedTrainingFields;

    private ArrayList<String[]> rawMap;

    public void setRawMap(ArrayList<String[]> map) {
        rawMap = map;
    }

    //private PathSolver path;

    private ArrayList<Soldier> allSoldiers;

    Graph graphForRegular;
    Graph graphForCLimber;

    public Field[][] grids;

    private InfoBoard infoBoard;

    Castle castle_Player1;
    Castle castle_Player2;

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
    public void descPlayer1Unit1Number() {
        if(Player1Unit1Number-1>=0){
            Player1Unit1Number -= 1;
        }
    }
    public void descPlayer2Unit1Number() {
        if(Player2Unit1Number-1>=0){
            Player2Unit1Number -= 1;
        }
    }
    public void descPlayer1Unit2Number() {
        if(Player1Unit2Number-1>=0){
            Player1Unit2Number -= 1;
        }
    }
    public void descPlayer2Unit2Number() {
        if(Player2Unit2Number-1>=0){
            Player2Unit2Number -= 1;
        }
    }
    public void descUnitsAfterDeath(Type soldierType, PlayerTurn owner){
        if(soldierType==Type.PLAYER1_SOLDIER1&&owner==PlayerTurn.PLAYER1){
            this.descPlayer1Unit1Number();
        }
        else if(soldierType==Type.PLAYER1_SOLDIER2&&owner==PlayerTurn.PLAYER1){
            this.descPlayer1Unit2Number();
        }
        else if(soldierType==Type.PLAYER2_SOLDIER1&&owner==PlayerTurn.PLAYER2){
            this.descPlayer2Unit1Number();
        }
        else if(soldierType==Type.PLAYER2_SOLDIER2&&owner==PlayerTurn.PLAYER2){
            this.descPlayer2Unit1Number();
        }
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

    public Field[][] getGrids() {
        return grids;
    }
    public boolean inTheDistance(int origin_x, int origin_y,int x , int y, int distance) {
        if ((row > x && 0 <= x) && y < column && y >= 0) {
            return Math.abs(x - origin_x) <= distance && Math.abs(y - origin_y) <= distance;
        }
        return false;
    }
    public boolean isMyArea(int y,PlayerTurn playerTurn){
        if(y<(this.column/2)&&playerTurn==PlayerTurn.PLAYER1){
            return true;
        }
        else if((this.column/2)<y&&playerTurn==PlayerTurn.PLAYER2){
            return true;
        }
        return false;
    }
    public boolean inTheDistanceForTowerRange(int origin_x, int origin_y,int x , int y, int distance){
        if ((column > x && 0 <= x) && y < row && y >= 0) {
            return Math.abs(x - origin_x) <= distance && Math.abs(y - origin_y) <= distance;
        }
        return false;
    }


    public GameLogic() {
        gameConstants = new GameUIConstants();
    }

    public void newGame(int row, int column, String userName){
        this.row = gameConstants.GAMEAREA_HEIGHT_canBeDividedBy;
        this.column = gameConstants.GAMEAREA_WIDTH_canBeDividedBy;

        placedTrainingFields = new ArrayList<>();
        grids = new Field[row][column];
        towerAndCastle = new ArrayList<>();
        stepCounter = 0;
        initGrid();

        this.player1Gold = 1000;
        this.player2Gold = 1000;


        //TODO: remove , just for testing hill and wa'er. Migh causes out of index error (decrease the sizes then)

        //int Player1castleRandomX = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4);
        //int Player2castleRandomX = ThreadLocalRandom.current().nextInt(gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4*3, gameConstants.GAMEAREA_WIDTH_canBeDividedBy-1);


        int Player1castleRandomX = 0,Player2castleRandomX= 0, Player1castleRandomY = 1, Player2castleRandomY = 1;
        for(int k = 0; k < 10; ++k) {
            String[] line = rawMap.get(k)[0].split(",");

            int length = rawMap.get(k).length <=1 ? line.length : rawMap.get(k).length;
            for(int i =0; i <length; ++i) {
                String toCalc = rawMap.get(k).length <= 1 ? line[i] : rawMap.get(k)[i];
                switch (toCalc.trim()) {
                    case "w": {
                        grids[k][i].addWater();
                        break;
                    }
                    case "h": {
                        grids[k][i].addHill();
                        break;
                    }
                    case "c1": {
                        Player1castleRandomX = i;
                        Player1castleRandomY = k;
                        break;
                    }
                    case "c2": {
                        Player2castleRandomX = i;
                        Player2castleRandomY = k;
                        break;
                    }
                }
            }
        }


        initInfoBoard();
        //path.initMatrix(column);

        allSoldiers = new ArrayList<>();

        graphForRegular = new Graph();
        graphForCLimber = new Graph();

        //TODO: set the grids to their default type. (according to the map we choose)

        infoBoard.reset(userName);


        //int castleRandomY = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_HEIGHT_canBeDividedBy);//It's the same for both castles
        this.player1Castle = new Position(Player1castleRandomY,Player1castleRandomX);
        this.player2Castle = new Position(Player2castleRandomY,Player2castleRandomX);
        castle_Player1 = new Castle(PlayerTurn.PLAYER1,100,Type.PLAYER1_CASTLE,2,10);
        grids[Player1castleRandomY][Player1castleRandomX].addCaslte(castle_Player1);
        castle_Player2 = new Castle(PlayerTurn.PLAYER2,100,Type.PLAYER2_CASTLE,2,10);
        grids[Player2castleRandomY][Player2castleRandomX].addCaslte(castle_Player2);
        setFillTowerType(Type.TOWER1);
        fillUpTowerAndCastleList();
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
     * Az alap negyzethalo matrix inicializalasa
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

    public void addMoney(int amount, int player) {

        if(player == 1) {
            player1Gold += amount;
        } else {
            player2Gold += amount;

        }
    }

    /*
    private void updatePathMatrix() {
        for (int i = 0; i < this.row; ++i) {
            for (int j = 0; j < this.column; ++j) {
                if(!grids[i][j].isCastleOnTheField()&&!grids[i][j].isTowerOnTheField()){
                    path.setMatrixField(i, j, 1);
                }
                else{
                    path.setMatrixField(i, j, 0);
                }
            }
        }
    }
     */
    private void clearGridSoldiers() {
        for (int i = 0; i < this.row; ++i) {
            for (int j = 0; j < this.column; ++j) {
                if(grids[i][j].soldiersOnTheField.size()>0) {
                    grids[i][j].removeSoldier();
                }
            }
        }
    }
    public void putUnitsIntoTheTrainingFields(){
        for (TrainingField trainingField : placedTrainingFields) {
            Soldier soldier;
            if (trainingField.OwnerPlayer == PlayerTurn.PLAYER1) {
                soldier = new Soldier(PlayerTurn.PLAYER1, 100, Type.PLAYER1_SOLDIER1, trainingField.y, trainingField.x);
                incPlayer1Unit1Number();
            } else {
                soldier = new Soldier(PlayerTurn.PLAYER2, 100, Type.PLAYER2_SOLDIER1, trainingField.y, trainingField.x);
                incPlayer2Unit1Number();

            }
            System.out.println(trainingField.OwnerPlayer);
            grids[trainingField.y][trainingField.x].addSoldier(soldier,this);
        }
    }
    public void initAttackPhase() {
        //TODO: itt kene osszeszamolni a katonakat es penzt adni a megfelelo jatekosnak

        putUnitsIntoTheTrainingFields();
        //put soldiers in front of the traning fields

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

        damageSoldiers();
        for(Soldier s : allSoldiers) {
            s.step(grids);
            int[] cords = s.getPos();
            grids[cords[0]][cords[1]].addSoldier(s,this);

/*            if(s.OwnerPlayer == PlayerTurn.PLAYER1) {
                if(player2Castle.x == s.x && player2Castle.y == s.y) {
                    System.out.println("got in: " + s.getDamage());
                    castle_Player1.looseHealth(s.getDamage());
                    grids[s.x][s.y].removeSoldier();
                }
            } else {
                if(player1Castle.x == s.x && player1Castle.y == s.y) {
                    System.out.println("got in: " + s.getDamage());
                    castle_Player2.looseHealth(s.getDamage());
                    grids[s.x][s.y].removeSoldier();
                }
            }*/

        }
        addSoldiersToLists();
        //damageSoldiers();
    }

    private void addSoldiersToLists() {//it can calucalute the number
        player1Unit1Count = 0;
        player1Unit2Count = 0;
        player2Unit1Count = 0;
        player2Unit2Count = 0;
        allSoldiers.clear();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                List<Soldier> toMovedSoldier =grids[i][j].getSoliders();
                if(toMovedSoldier!=null){
                    allSoldiers.addAll(grids[i][j].getSoliders());
                }
            }
        }
        for (Soldier soldier : allSoldiers) {
            if(soldier.SoldierType == Type.PLAYER1_SOLDIER1){
                player1Unit1Count++;
            }
            else if(soldier.SoldierType == Type.PLAYER1_SOLDIER2){
                player1Unit2Count++;
            }
            else if(soldier.SoldierType == Type.PLAYER2_SOLDIER1){
                player2Unit1Count++;
            }
            else if(soldier.SoldierType == Type.PLAYER2_SOLDIER2){
                player2Unit2Count++;
            }
        }
    }

    public void damageSoldiers() {
        List<Soldier> deathSoldier = new ArrayList<>();
        for (Field towerOrCastle :
                towerAndCastle) {
            int damage = 0;
            int range = 0;
            PlayerTurn attackerOwner = null;
            if (towerOrCastle.isCastleOnTheField()) {
                Castle castle = towerOrCastle.getCastleOnTheField();
                damage = castle.damage;
                range = castle.range;
                attackerOwner = castle.OwnerPlayer;
            } else if (towerOrCastle.isTowerOnTheField()) {
                Tower tower = towerOrCastle.getTowerOnTheField();
                damage = tower.damage;
                range = tower.range;
                attackerOwner = tower.OwnerPlayer;
            }
            for (Soldier s : allSoldiers) {
                if (s.OwnerPlayer != attackerOwner && inTheDistance(towerOrCastle.y, towerOrCastle.x, s.x, s.y, range)) {//problem with the x and y solved this way
                    if (!s.minusHealth(damage)) {
                        if (!deathSoldier.contains(s)) {
                            deathSoldier.add(s);
                        }
                    }
                }
                if (s.OwnerPlayer == PlayerTurn.PLAYER1) {
                    if (player2Castle.x == s.x && player2Castle.y == s.y) {
                        System.out.println("got in: " + s.getDamage());
                        castle_Player2.looseHealth(s.getDamage());
                        if (!deathSoldier.contains(s)) {
                            deathSoldier.add(s);
                        }
                    }
                } else {
                    if (player1Castle.x == s.x && player1Castle.y == s.y) {
                        System.out.println("got in: " + s.getDamage());
                        castle_Player1.looseHealth(s.getDamage());
                        deathSoldier.add(s);
                        if(!deathSoldier.contains(s)){
                            deathSoldier.add(s);
                        }
                    }
                }
            }
            for (Soldier death :
                    deathSoldier) {
                descUnitsAfterDeath(death.SoldierType, death.OwnerPlayer);
                allSoldiers.remove(death);
            }
        }
       if(castle_Player1.health<=0){
            PopUp popUp = new PopUp("Gratulalok, Player 2.");
            infoBoard.setGameOver();
        }
        else if(castle_Player2.health<=0){
            PopUp popUp = new PopUp("Gratulalok, Player 1.");
            infoBoard.setGameOver();
            //new GameFrame(this);
        }
    }
    public boolean isGameOver(){
        if(castle_Player1.health<=0){
            return  true;
        }
        else if(castle_Player2.health<=0){
            return true;
        }
        return false;
    }
    public void fillUpTowerAndCastleList(){
        towerAndCastle.clear();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if(grids[i][j].isTowerOnTheField()||grids[i][j].isCastleOnTheField()){
                    towerAndCastle.add(grids[i][j]);
                }
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
        return res;
    }

    public void upgradeTower(int x, int y){
        grids[x][y].getTowerOnTheField().damage += 10;
        if(grids[x][y].getTowerOnTheField().TowerType == Type.TOWER1) {
            removeMoney(30, grids[x][y].getTowerOnTheField().OwnerPlayer);
        }else if(grids[x][y].getTowerOnTheField().TowerType == Type.TOWER2){
            removeMoney(60, grids[x][y].getTowerOnTheField().OwnerPlayer);
        }
    }

    public void demolishTower(int x, int y){
        Type towerType = grids[x][y].getTowerOnTheField().TowerType;
        grids[x][y].towersOnTheField.remove(grids[x][y].getTowerOnTheField());
        if(towerType == Type.TOWER1){
            if(playerTurn == PlayerTurn.PLAYER1){
                addMoney(20,1);
            }else if(playerTurn == PlayerTurn.PLAYER2){
                addMoney(20,2);
            }
        }else if(towerType == Type.TOWER2) {
            if (playerTurn == PlayerTurn.PLAYER1) {
                addMoney(40, 1);
            } else if (playerTurn == PlayerTurn.PLAYER2) {
                addMoney(40, 2);
            }
        }
    }
}
