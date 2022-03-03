package model.info;

public class InfoBoard {

    private int info;
    private String user;
    private int[] topInfo;
    private String[] topPlayer;
    private boolean gameOver = true;

    public void reset(String user) {
        setTopInfo();
        info = 0;
        this.user = user;
        gameOver = false;
    }

    public void setGameOver() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setTopInfo() {
        //TODO: add all the info to be displayed
    }

    public void addInfo(int info) {
        this.info += info;
    }

    public int getInfo() {
        return info;
    }


}
