package gui;

import model.*;
import model.info.InfoBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class GameBoard extends JPanel {

    private final GameLogic gameLogic;

    private String name;

    private final GameUIConstants gameConstants;

    private Timer timer;

    private Position player1Castle;
    private Position player2Castle;
    private TheScreen ScreenMethods;
    private ImageLoader imageLoader;


    public GameBoard(GameLogic gameLogic) {
        gameConstants = new GameUIConstants();//it will calculate the area
        ScreenMethods = new TheScreen();
        imageLoader = new ImageLoader();
        this.gameLogic = gameLogic;
        setPreferredSize(new Dimension(GameUIConstants.GAME_WIDTH, GameUIConstants.GAME_HEIGHT));
        setBackground(GameUIConstants.BACKGROUND_COLOR);
        setFocusable(true);

        startNewGame();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO: create disabled state for the WHOLE INFO BOARD!!
                if(GameLogic.playerTurn == null) return;
                int startOfThePlayer1InfoBoard = (gameConstants.player1InfoBoard.y+(GameUIConstants.SMALL_FONT.getSize()*2));
                int endOfThePlayer1InfoBoard = startOfThePlayer1InfoBoard+GameUIConstants.GAME_AREA_RECTANGLE;
                //TODO: REFACTOR!
                InfoBoard infoBoard = gameLogic.getInfoBoard();
                if (infoBoard.isGameOver()) {
                    startNewGame();

                }
                if(ScreenMethods.onGameArea(e.getX(),e.getY())) {
                    if(gameLogic.getFillTowerType()!=null) {
                        Type[][] localGrid = gameLogic.getGrids();
                        int x = ScreenMethods.convertYtoModelX_GameArea(e.getY());
                        int y = ScreenMethods.convertXtoModelY_GameArea(e.getX());
                        if(localGrid[x][y]==Type.EMPTY) {
                            localGrid[x][y] = gameLogic.getFillTowerType(); //setting the model after click somewhere on the game area
                            gameLogic.setGrids(localGrid);
                            gameLogic.removeMoney(GameConstants.TOWER1_PRICE,GameLogic.playerTurn);

                        }
                    }
                }
                else if(ScreenMethods.playerInfoBoardTower1_clicked(e.getX(),e.getY())) {
                    gameLogic.setFillTowerType(Type.TOWER1);

                }
                else if(ScreenMethods.playerInfoBoardTower2_clicked(e.getX(),e.getY())){
                    gameLogic.setFillTowerType(Type.TOWER2);

                }
                else if(ScreenMethods.playerInfoBoardEndTurn_clicked(e.getX(),e.getY())){
                    //TODO: create simulated part by setting variable to 0
                    if (GameLogic.playerTurn == PlayerTurn.PLAYER2){
                        GameLogic.playerTurn = PlayerTurn.ATTACK;
                    }
                    if(GameLogic.playerTurn == PlayerTurn.PLAYER1){
                        GameLogic.playerTurn = PlayerTurn.PLAYER2;
                    }
                }
                repaint();
            }
        });
    }

    private void startNewGame() {
        int time = gameConstants.TIMER;
        this.timer = new Timer(time, this.oneGameCycleAction);
        this.timer.start();
        gameLogic.newGame(gameConstants.GAMEAREA_HEIGHT_canBeDividedBy,gameConstants.GAMEAREA_WIDTH_canBeDividedBy, name);
        int castleRandomX = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4);
        int castleRandomY = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_HEIGHT_canBeDividedBy);
        this.player1Castle = new Position(castleRandomX,castleRandomY);
        gameLogic.setTypeElement(castleRandomY,castleRandomX,Type.CASTLE);
    }

    private final Action oneGameCycleAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(GameLogic.playerTurn == PlayerTurn.ATTACK) {
                gameLogic.nextAttackPhase();
            }
            repaint();
        }
    };
    private void drawStartScreen(Graphics2D graphics2D) {

        //TODO: remove all this. just example
        graphics2D.setFont(GameUIConstants.MAIN_FONT);

        graphics2D.setColor(Color.black);
        graphics2D.fill(GameUIConstants.TITLE_RECTANGLE);
        graphics2D.fill(GameUIConstants.CLICK_RECTANGLE);

        graphics2D.setColor(GameUIConstants.TEXT_COLOR);
        graphics2D.drawString("GAME OVER", GameUIConstants.TITLE_POS_X, GameUIConstants.TITLE_POS_Y);

        graphics2D.setFont(GameUIConstants.SMALL_FONT);
        graphics2D.drawString("Click to start", GameUIConstants.CLICK_POS_X, GameUIConstants.CLICK_POS_Y);


    }
    private void drawGameArea(Graphics2D graphics2D) {
        graphics2D.setColor(GameUIConstants.GRID_COLOR);
        graphics2D.fill(gameConstants.gameareaREACT);
        graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
        graphics2D.setColor(GameUIConstants.GRID_BORDER_COLOR);
        graphics2D.draw(gameConstants.gameareaREACT);
        graphics2D.setStroke(GameUIConstants.SMALL_STROKE);

        Type[][] localGrid = gameLogic.getGrids();
        for (int y_col = 0; y_col < gameConstants.GAMEAREA_HEIGHT_canBeDividedBy; y_col++) {
            for (int x_row = 0; x_row < gameConstants.GAMEAREA_WIDTH_canBeDividedBy; x_row++) {
                Rectangle currentField = new Rectangle(gameConstants.gameareaREACT.x+x_row*GameUIConstants.GAME_AREA_RECTANGLE,gameConstants.gameareaREACT.y+y_col*GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
                graphics2D.setColor(Color.BLACK);
                if(localGrid[y_col][x_row] == Type.TOWER1){
                    imageLoader.loadTower1_OnTheField(graphics2D,currentField.x,currentField.y,false);
                }
                else if(localGrid[y_col][x_row] == Type.TOWER2){
                    imageLoader.loadTower2_OnTheField(graphics2D,currentField.x,currentField.y,false);
                }
                else if(localGrid[y_col][x_row]== Type.CASTLE){
                    imageLoader.loadCastleOnTheField(graphics2D,currentField.x,currentField.y,false);
                }
                else{
                    graphics2D.draw(currentField);
                }
            }
        }
    }
    private void drawOutTheTowerField(Type Tower,Graphics2D graphics2D,boolean disabled){//drawing out the tower fields on the infobroads
        //player 1 tower 1 tower 2 and player 2 tower 1 tower 2
        if(Tower == Type.TOWER1){
            if(gameLogic.getFillTowerType()==Type.TOWER1 && !disabled){
                graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
            }
            else{
                graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
            }
            Rectangle player1Tower1_Field = gameConstants.player1_placeOfTower1_OnInfoBoard;
            imageLoader.loadTower1_OnTheField(graphics2D,gameConstants.player1_placeOfTower1_OnInfoBoard.x,
                    gameConstants.player1_placeOfTower1_OnInfoBoard.y,disabled);
            graphics2D.draw(player1Tower1_Field);
            if(gameLogic.getFillTowerType()==Type.TOWER1 && disabled){
                graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
            }
            else{
                graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
            }
            Rectangle player2Tower1_Field = gameConstants.player2_placeOfTower1_OnInfoBoard;
            imageLoader.loadTower1_OnTheField(graphics2D,gameConstants.player2_placeOfTower1_OnInfoBoard.x,
                    gameConstants.player2_placeOfTower1_OnInfoBoard.y,!disabled);
            graphics2D.draw(player2Tower1_Field);
        }
        else if(Tower == Type.TOWER2){
            if(gameLogic.getFillTowerType()==Type.TOWER2 && !disabled){
                graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
            }
            else{
                graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
            }
            Rectangle player1Tower2_Field = gameConstants.player1_placeOfTower2_OnInfoBoard;
            imageLoader.loadTower2_OnTheField(graphics2D,gameConstants.player1_placeOfTower2_OnInfoBoard.x,
                    gameConstants.player1_placeOfTower2_OnInfoBoard.y,disabled);
            graphics2D.draw(player1Tower2_Field);
            if(gameLogic.getFillTowerType()==Type.TOWER2 && disabled){
                graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
            }
            else{
                graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
            }
            Rectangle player2Tower2_Field = gameConstants.player2_placeOfTower2_OnInfoBoard;
            imageLoader.loadTower2_OnTheField(graphics2D,gameConstants.player2_placeOfTower2_OnInfoBoard.x,
                    gameConstants.player2_placeOfTower2_OnInfoBoard.y,!disabled);
            graphics2D.draw(player2Tower2_Field);
        }
    }
    private void drawOutEndButton(Graphics2D graphics2D){//todo: when someone clicks on this the tower image become disabled
        graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
        graphics2D.drawString(GameLogic.playerTurn==PlayerTurn.PLAYER1 ? "not my turn" : "end turn",
                gameConstants.player2_text_onInfoBoard.x,gameConstants.player2_text_onInfoBoard.y);
        graphics2D.drawString(GameLogic.playerTurn==PlayerTurn.PLAYER2 ? "not my turn" : "end turn",
                gameConstants.player1_text_onInfoBoard.x,gameConstants.player1_text_onInfoBoard.y);
    }
    private void drawInfoBoard(Graphics2D graphics2D) {//drawing out the player 1 info board
        graphics2D.setColor(GameUIConstants.GRID_BORDER_COLOR);
        graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
        graphics2D.draw(gameConstants.player1InfoBoard);
        graphics2D.draw(gameConstants.player2InfoBoard);

        graphics2D.setColor(GameUIConstants.TEXT_COLOR);
        graphics2D.setFont(GameUIConstants.SMALL_FONT);
        graphics2D.drawString("Player 1         Money: "+gameLogic.getPlayer1Gold(),
                gameConstants.player1_moneyAndNameText.x,gameConstants.player1_moneyAndNameText.y);
        graphics2D.drawString("Player 2         Money: "+gameLogic.getPlayer2Gold(),
                gameConstants.player2_moneyAndNameText.x,gameConstants.player2_moneyAndNameText.y);

        if(GameLogic.playerTurn==PlayerTurn.PLAYER1){
            drawOutTheTowerField(Type.TOWER1,graphics2D,false);
            drawOutTheTowerField(Type.TOWER2,graphics2D,false);
        }
        else{
            drawOutTheTowerField(Type.TOWER1,graphics2D,true);
            drawOutTheTowerField(Type.TOWER2,graphics2D,true);
        }
        drawOutEndButton(graphics2D);
    }
    private void drawAll(Graphics2D graphics2D) {
        drawGameArea(graphics2D);
        drawInfoBoard(graphics2D);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        InfoBoard infoBoard = gameLogic.getInfoBoard();
        if (infoBoard.isGameOver()) {
                drawStartScreen(graphics2D);
        } else {
            drawAll(graphics2D);
        }
        Toolkit.getDefaultToolkit().sync();
    }
}
