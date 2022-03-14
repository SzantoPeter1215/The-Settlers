package gui;

import model.GameLogic;
import model.PlayerTurn;

public class TheScreen {

    private final GameUIConstants gameConstants;
    public TheScreen() {
        gameConstants = new GameUIConstants();//it will calculate the area based on the current screen
    }
    public boolean playerInfoBoardTower1_clicked(int x, int y){
        if(GameLogic.playerTurn== PlayerTurn.PLAYER1){
            int startOfThePlayer1InfoBoard = (gameConstants.player1InfoBoard.y+(GameUIConstants.SMALL_FONT.getSize()*2));
            int endOfThePlayer1InfoBoard = startOfThePlayer1InfoBoard+GameUIConstants.GAME_AREA_RECTANGLE;
            if((y>=startOfThePlayer1InfoBoard&&y<=endOfThePlayer1InfoBoard)&&(x>=0&&x<=GameUIConstants.GAME_AREA_RECTANGLE)){
                return true;
            }
        }
        if(GameLogic.playerTurn==PlayerTurn.PLAYER2) {
            int startOfThePlayer2InfoBoard = (gameConstants.player2InfoBoard.y + (GameUIConstants.SMALL_FONT.getSize() * 2));
            int endOfThePlayer2InfoBoard = startOfThePlayer2InfoBoard + GameUIConstants.GAME_AREA_RECTANGLE;
            return (y >= startOfThePlayer2InfoBoard && y <= endOfThePlayer2InfoBoard) && (x >= 0 && x <= GameUIConstants.GAME_AREA_RECTANGLE);
        }
        return false;
    }
    public boolean playerInfoBoardTower2_clicked(int x, int y){
        if(GameLogic.playerTurn==PlayerTurn.PLAYER1){
            int startOfThePlayer1InfoBoard = (gameConstants.player1InfoBoard.y+(GameUIConstants.SMALL_FONT.getSize()*2));
            int endOfThePlayer1InfoBoard = startOfThePlayer1InfoBoard+GameUIConstants.GAME_AREA_RECTANGLE;
            if((y>=startOfThePlayer1InfoBoard&&y<=endOfThePlayer1InfoBoard)&&(x>=GameUIConstants.GAME_AREA_RECTANGLE&&x<=(GameUIConstants.GAME_AREA_RECTANGLE*2))){
                return true;
            }
        }
        if(GameLogic.playerTurn==PlayerTurn.PLAYER2) {
            int startOfThePlayer2InfoBoard = (gameConstants.player2InfoBoard.y+(GameUIConstants.SMALL_FONT.getSize()*2));
            int endOfThePlayer2InfoBoard = startOfThePlayer2InfoBoard+GameUIConstants.GAME_AREA_RECTANGLE;
            return (y >= startOfThePlayer2InfoBoard && y <= endOfThePlayer2InfoBoard) && (x >= GameUIConstants.GAME_AREA_RECTANGLE && x <= (GameUIConstants.GAME_AREA_RECTANGLE * 2));
        }
        return false;
    }
    public boolean playerInfoBoardEndTurn_clicked(int x, int y){
        int player1EndTurnY1 = GameUIConstants.SMALL_FONT.getSize()*2;
        int player1EndTurnY2 = GameUIConstants.SMALL_FONT.getSize()*3;
        int EndTurnX1 = GameUIConstants.GAME_AREA_RECTANGLE*4;
        int EndTurnX2 = GameUIConstants.GAME_AREA_RECTANGLE*6;
        if((y>=player1EndTurnY1&&y<=player1EndTurnY2)&&(x>=EndTurnX1&&x<=EndTurnX2)&&GameLogic.playerTurn==PlayerTurn.PLAYER1){
            return true;
        }
        int player2EndTurnY1 = gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*2;
        int player2EndTurnY2 = gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*3;
        if((y>=player2EndTurnY1&&y<=player2EndTurnY2)&&(x>=EndTurnX1&&x<=EndTurnX2)&&GameLogic.playerTurn==PlayerTurn.PLAYER2){
            return true;
        }
        return false;
    }
    public int convertYtoModelX_GameArea(int y){//it has to be on the gameArea
        int endOfTheGameBoard_Y = gameConstants.gameareaREACT.y;
        return ((y - endOfTheGameBoard_Y) / GameUIConstants.GAME_AREA_RECTANGLE);
    }
    public int convertXtoModelY_GameArea(int x){//it has to be on the gamearea
        return x / GameUIConstants.GAME_AREA_RECTANGLE;
    }
    public boolean onGameArea(int x, int y){
        int startOfTheGameBoard_Y = gameConstants.gameareaREACT.height+gameConstants.gameareaREACT.y;
        int endOfTheGameBoard_Y = gameConstants.gameareaREACT.y;
        return y <= startOfTheGameBoard_Y && y >= endOfTheGameBoard_Y && x <= gameConstants.gameareaREACT.width;
    }
}
