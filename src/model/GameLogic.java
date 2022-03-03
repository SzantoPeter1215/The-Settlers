package model;

import model.info.InfoBoard;

public final class GameLogic {
    private int row;
    private int column;
    private String player1_Name;
    private String player2_Name;
    private Type FillTowerType;

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

    public InfoBoard getInfoBoard() {
        return infoBoard;
    }
}
