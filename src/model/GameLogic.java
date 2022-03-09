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


    public GameLogic() {

    }

    public void newGame(int row, int column, String userName){
        this.row = row;
        this.column = column;

        grids = new Type[row][column];
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

    public InfoBoard getInfoBoard() {
        return infoBoard;
    }
}
