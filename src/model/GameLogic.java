package model;

import gui.GameUIConstants;
import gui.Position;
import model.info.InfoBoard;
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

    private PathSolver path;


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

    public void setTypeElement(int x, int y,Type element){
        this.grids[x][y] = element;
    }

    public Type[][] getGrids() {
        return grids;
    }

    public void setGrids(Type[][] grids) {
        this.grids = grids;
    }

    private Type[][] grids;

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

        grids = new Type[row][column];
        stepCounter = 0;
        initGrid();

        initInfoBoard();
        path.initPatrix(column);

        //TODO: set the grids to their default type. (according to the map we choose)

        infoBoard.reset(userName);

        int Player1castleRandomX = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4);
        int Player2castleRandomX = ThreadLocalRandom.current().nextInt(gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4*3, gameConstants.GAMEAREA_WIDTH_canBeDividedBy-1);
        int castleRandomY = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_HEIGHT_canBeDividedBy);//It's the same for both castles
        this.player1Castle = new Position(Player1castleRandomX,castleRandomY);
        this.player2Castle = new Position(Player2castleRandomX,castleRandomY);
        setTypeElement(castleRandomY,Player1castleRandomX,Type.CASTLE);
        setTypeElement(castleRandomY,Player2castleRandomX,Type.CASTLE);
        setFillTowerType(Type.TOWER1);
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
                grids[i][j] = Type.EMPTY;
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

    private void updatePathMatrix() {
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                switch (grids[i][j]) {
                    case EMPTY: {
                        path.setMatrixField(i, j, 1);
                        break;
                    }
                    default: {
                        path.setMatrixField(i, j, 0);
                    }
                }
            }
        }
    }
    public void initAttackPhase() {
        GameLogic.playerTurn = PlayerTurn.ATTACK;
        System.out.println("ATTACK!");
        stepCounter = 0;
        updatePathMatrix();
    }

    public void nextAttackPhase() {
        if(stepCounter >= 3) {
            playerTurn = PlayerTurn.PLAYER1;
            return;
        }
        ++stepCounter;
        //TODO: create a method that accepts a function and executes it to all the grid elements.
        //TODO: HANDLE WHEN TWO THINGS ARE IN THE SAME PLACE!!!

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                Type current = grids[i][j];
                switch (current) {
                    case TOWER1: {
                        PathSolver.getPath(i, j, player2Castle.x, player2Castle.y);
                        grids[i][j] = Type.EMPTY;
                        if(j >= column - 1) break; //out of index stb
                        //TODO: where are the solders
                        grids[i][j + 1] = Type.TOWER1;
                        ++j;
                        break;
                    }
                    case TOWER2: {
                        grids[i][j] = Type.EMPTY;
                        if(j <= 1) break;
                        grids[i][j - 3] = Type.TOWER2;
                        break;
                    }
                    default: {
                    //TODO: add the other types once done
                    }
                }
            }
        }
    }


    public InfoBoard getInfoBoard() {
        return infoBoard;
    }

}
