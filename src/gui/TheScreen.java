package gui;

import model.Field;
import model.GameLogic;
import model.PlayerTurn;

import java.util.List;

public class TheScreen {

    private final GameUIConstants gameConstants;
    public TheScreen() {
        gameConstants = new GameUIConstants();//it will calculate the area based on the current screen
    }
    //TODO: formazas: atlathatobb lenne, ha spacek lennenek
    // az osszehasonlito operandusok elott ES utan a>=b<c>d&&a||v vagy a >= b < c > d && a || v
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
    public boolean playerInfoBoardTrainingField1_clicked(int x, int y){
        if(x>=gameConstants.player1_placeOfTrainingField_OnInfoBoard.x&&x<=gameConstants.player1_placeOfTrainingField_OnInfoBoard.x+GameUIConstants.GAME_AREA_RECTANGLE){
            if(y>=gameConstants.player1_placeOfTrainingField_OnInfoBoard.y && y<=gameConstants.player1_placeOfTrainingField_OnInfoBoard.y+GameUIConstants.GAME_AREA_RECTANGLE){
                return true;
            }
        }
        return false;
    }
    public boolean playerInfoBoardSolder1_clicked(int x, int y){
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
    public boolean clickAbleField(List<Position> clickAbleField, Field[][] grids, int x, int y){
        for (Position clickableObject:
             clickAbleField) {
            if(clickableObject.x<=x&&x<=(clickableObject.x+GameUIConstants.GAME_AREA_RECTANGLE)&&
            clickableObject.y<=y&&y<=clickableObject.y+GameUIConstants.GAME_AREA_RECTANGLE){
                return true;
            }
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
    public boolean onPlayer1Unit1(int x, int y){
        int infoX = gameConstants.player1_placeOfSoldier1_OnInfoBoard.x;
        int infoY = gameConstants.player1_placeOfSoldier1_OnInfoBoard.y;
        if(infoX<x&&
                x<infoX+GameUIConstants.GAME_AREA_RECTANGLE
        &&infoY<y&&y<infoY+GameUIConstants.GAME_AREA_RECTANGLE){
            return true;
        }
        return false;
    }
    public boolean onPlayer1Unit2(int x, int y){
        int infoX = gameConstants.player1_placeOfSoldier2_OnInfoBoard.x;
        int infoY = gameConstants.player1_placeOfSoldier2_OnInfoBoard.y;
        if(infoX<x&&
                x<infoX+GameUIConstants.GAME_AREA_RECTANGLE
                &&infoY<y&&y<infoY+GameUIConstants.GAME_AREA_RECTANGLE){
            return true;
        }
        return false;
    }
    public boolean onPlayer2Unit1(int x, int y){
        int infoX = gameConstants.player2_placeOfSoldier1_OnInfoBoard.x;
        int infoY = gameConstants.player2_placeOfSoldier1_OnInfoBoard.y;
        if(infoX<x&&
                x<infoX+GameUIConstants.GAME_AREA_RECTANGLE
                &&infoY<y&&y<infoY+GameUIConstants.GAME_AREA_RECTANGLE){
            return true;
        }
        return false;
    }
    public boolean onPlayer2Unit2(int x, int y){
        int infoX = gameConstants.player2_placeOfSoldier2_OnInfoBoard.x;
        int infoY = gameConstants.player2_placeOfSoldier2_OnInfoBoard.y;
        if(infoX<x&&
                x<infoX+GameUIConstants.GAME_AREA_RECTANGLE
                &&infoY<y&&y<infoY+GameUIConstants.GAME_AREA_RECTANGLE){
            return true;
        }
        return false;
    }
}
