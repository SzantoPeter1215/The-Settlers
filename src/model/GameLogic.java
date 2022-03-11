package model;

import model.info.InfoBoard;

public final class GameLogic {
    private int row;
    private int column;
    private String player1_Name;
    private String player2_Name;
    private Type FillTowerType;
    private int playerTurn = 1;
    private int player1Gold = GameConstants.START_GOLD_PLAYER;
    private int player2Gold = GameConstants.START_GOLD_PLAYER;



    public int getPlayer1Gold() {
        return player1Gold;
    }

    public int getPlayer2Gold() {
        return player2Gold;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int player) {
        playerTurn = player;
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

    }

    public void newGame(int row, int column, String userName){
        this.row = row;
        this.column = column;

        grids = new Type[row][column];
        stepCounter = 0;
        initGrid();

        initInfoBoard();

        //TODO: set the grids to their default type. (according to the map we choose)

        infoBoard.reset(userName);
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
    public void removeMoney(int minusGold,int player){
        if(player==1){
            int expectedGold = this.player1Gold - minusGold;
            if(expectedGold<0){
                this.player1Gold = 0;
            }
            else{
                this.player1Gold -= minusGold;
            }
        }
        else if(player==2){
            int expectedGold = this.player2Gold - minusGold;
            if(expectedGold<0){
                this.player2Gold = 0;
            }
            else{
                this.player2Gold -= minusGold;
            }

        }
    }

    public void nextAttackPhase() {
        if(stepCounter >= 3) {
            stepCounter = 0;
            playerTurn = 1;
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
                        grids[i][j] = Type.EMPTY;
                        if(j >= column - 1) break; //out of index stb
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
