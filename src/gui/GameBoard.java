package gui;

import model.*;
import model.info.InfoBoard;
import resources.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;


public class GameBoard extends JPanel {

    private final GameLogic gameLogic;

    private String name;

    private GameUIConstants gameConstants;

    private Timer timer;

    private Position player1Castle;
    private Position player2Castle;

    private PlayerTurn playerTurn;

    public GameBoard(GameLogic gameLogic) {
        gameConstants = new GameUIConstants();//it will calculate the area
        this.gameLogic = gameLogic;
        this.playerTurn = gameLogic.getPlayerTurn();
        setPreferredSize(new Dimension(GameUIConstants.GAME_WIDTH, GameUIConstants.GAME_HEIGHT));
        setBackground(GameUIConstants.BACKGROUND_COLOR);
        setFocusable(true);

        startNewGame();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO: create disabled state for the WHOLE INFO BOARD!!
                if(gameLogic.getPlayerTurn() == null) return;
                int startOfThePlayer1InfoBoard = (gameConstants.player1InfoBoard.y+(GameUIConstants.SMALL_FONT.getSize()*2));
                int endOfThePlayer1InfoBoard = startOfThePlayer1InfoBoard+GameUIConstants.GAME_AREA_RECTANGLE;
                //TODO: REFACTOR!
                InfoBoard infoBoard = gameLogic.getInfoBoard();
                if (infoBoard.isGameOver()) {
                    startNewGame();

                }
                if(onGameArea(e.getX(),e.getY())) {
                    if(gameLogic.getFillTowerType()!=null) {
                        Type[][] localGrid = gameLogic.getGrids();
                        int x = convertYtoModelX_GameArea(e.getY());
                        int y = convertXtoModelY_GameArea(e.getX());
                        if(localGrid[x][y]==Type.EMPTY) {
                            localGrid[x][y] = gameLogic.getFillTowerType(); //setting the model after click somewhere on the game area
                            gameLogic.setGrids(localGrid);
                            gameLogic.removeMoney(GameConstants.TOWER1_PRICE,playerTurn);

                        }
                    }
                }
                else if(playerInfoBoardTower1_clicked(e.getX(),e.getY())) {
                    gameLogic.setFillTowerType(Type.TOWER1);

                }
                else if(playerInfoBoardTower2_clicked(e.getX(),e.getY())){
                    gameLogic.setFillTowerType(Type.TOWER2);

                }
                else if(playerInfoBoardEndTurn_clicked(e.getX(),e.getY())){
                    //TODO: create simulated part by setting variable to 0
                    if (playerTurn == PlayerTurn.PLAYER2){
                        gameLogic.setPlayerTurn(PlayerTurn.ATTACK);
                    }
                    if(playerTurn == PlayerTurn.PLAYER1){
                        gameLogic.setPlayerTurn(PlayerTurn.PLAYER2);
                    }
                    playerTurn = gameLogic.getPlayerTurn();

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
        int castleRandomX = ThreadLocalRandom.current().nextInt(0, (int) (gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4));
        int castleRandomY = ThreadLocalRandom.current().nextInt(0, (int) gameConstants.GAMEAREA_HEIGHT_canBeDividedBy);
        this.player1Castle = new Position(castleRandomX,castleRandomY);
        gameLogic.setTypeElement(castleRandomY,castleRandomX,Type.CASTLE);
    }

    private final Action oneGameCycleAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(playerTurn == PlayerTurn.ATTACK) {
                gameLogic.nextAttackPhase();
            }
            repaint();
        }
    };


    private boolean onGameArea(int x, int y){
        int startOfTheGameBoard_Y = gameConstants.gameareaREACT.height+gameConstants.gameareaREACT.y;
        int endOfTheGameBoard_Y = gameConstants.gameareaREACT.y;
        if(y<=startOfTheGameBoard_Y&&y>=endOfTheGameBoard_Y&&x<=gameConstants.gameareaREACT.width){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean playerInfoBoardTower1_clicked(int x, int y){
        if(playerTurn==PlayerTurn.PLAYER1){
            int startOfThePlayer1InfoBoard = (gameConstants.player1InfoBoard.y+(GameUIConstants.SMALL_FONT.getSize()*2));
            int endOfThePlayer1InfoBoard = startOfThePlayer1InfoBoard+GameUIConstants.GAME_AREA_RECTANGLE;
            if((y>=startOfThePlayer1InfoBoard&&y<=endOfThePlayer1InfoBoard)&&(x>=0&&x<=GameUIConstants.GAME_AREA_RECTANGLE)){
                return true;
            }
        }
        if(playerTurn==PlayerTurn.PLAYER2) {
            int startOfThePlayer2InfoBoard = (gameConstants.player2InfoBoard.y + (GameUIConstants.SMALL_FONT.getSize() * 2));
            int endOfThePlayer2InfoBoard = startOfThePlayer2InfoBoard + GameUIConstants.GAME_AREA_RECTANGLE;
            if ((y >= startOfThePlayer2InfoBoard && y <= endOfThePlayer2InfoBoard) && (x >= 0 && x <= GameUIConstants.GAME_AREA_RECTANGLE)) {
                return true;
            }
        }
        return false;
    }
    private boolean playerInfoBoardTower2_clicked(int x, int y){
        if(playerTurn==PlayerTurn.PLAYER1){
            int startOfThePlayer1InfoBoard = (gameConstants.player1InfoBoard.y+(GameUIConstants.SMALL_FONT.getSize()*2));
            int endOfThePlayer1InfoBoard = startOfThePlayer1InfoBoard+GameUIConstants.GAME_AREA_RECTANGLE;
            if((y>=startOfThePlayer1InfoBoard&&y<=endOfThePlayer1InfoBoard)&&(x>=GameUIConstants.GAME_AREA_RECTANGLE&&x<=(GameUIConstants.GAME_AREA_RECTANGLE*2))){
                return true;
            }
        }
        if(playerTurn==PlayerTurn.PLAYER2) {
            int startOfThePlayer2InfoBoard = (gameConstants.player2InfoBoard.y+(GameUIConstants.SMALL_FONT.getSize()*2));
            int endOfThePlayer2InfoBoard = startOfThePlayer2InfoBoard+GameUIConstants.GAME_AREA_RECTANGLE;
            if((y>=startOfThePlayer2InfoBoard&&y<=endOfThePlayer2InfoBoard)&&(x>=GameUIConstants.GAME_AREA_RECTANGLE&&x<=(GameUIConstants.GAME_AREA_RECTANGLE*2))){
                return true;
            }
        }
        return false;
    }
    private boolean playerInfoBoardEndTurn_clicked(int x, int y){
        //TODO:shorten these if statements
        if((y>=GameUIConstants.SMALL_FONT.getSize()*2&&y<=GameUIConstants.SMALL_FONT.getSize()*3)&&(x>=GameUIConstants.GAME_AREA_RECTANGLE*4&&x<=GameUIConstants.GAME_AREA_RECTANGLE*6)&&playerTurn==PlayerTurn.PLAYER1){
            return true;
        }
        if((y>=gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*2&&y<=gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*3)&&(x>=GameUIConstants.GAME_AREA_RECTANGLE*4&&x<=GameUIConstants.GAME_AREA_RECTANGLE*6)&&playerTurn==PlayerTurn.PLAYER2){
            return true;
        }
        return false;
    }
    private int convertYtoModelX_GameArea(int y){//it has to be on the gameArea
        int endOfTheGameBoard_Y = gameConstants.gameareaREACT.y;
        return ((y - endOfTheGameBoard_Y) / GameUIConstants.GAME_AREA_RECTANGLE);
    }
    private int convertXtoModelY_GameArea(int x){//it has to be on the gamearea
        return x / GameUIConstants.GAME_AREA_RECTANGLE;
    }


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


    private void drawGamePart(Graphics2D g, int r, int c) { //TODO: TYPE param needed for swich. Eg: the segment is a tower type, empty or soilder et
        int x = GameUIConstants.LEFT_MARGIN + c * GameUIConstants.BLOCK_SIZE;
        int y = GameUIConstants.TOP_MARGIN + r * GameUIConstants.BLOCK_SIZE;

        try {
            String path;

            path = "images/png.png" ;
            BufferedImage image = ImageLoader.readImage(path);
            g.drawImage(image , x, y, GameUIConstants.BLOCK_SIZE, GameUIConstants.BLOCK_SIZE, null);

        } catch (IOException ex) {
            System.err.println("Failed to set body image! __ " + ex.getMessage());
        }
    }
    private void loadTower1_OnTheField(Graphics2D graphics2D,int x,int y,boolean disabled){
        String path;

        if(!disabled) {
            path = "images/towerExample.png";
        }
        else{
            path = "images/disabled_towerExample.png";
        }
        BufferedImage image = null;
        try {
            image = ImageLoader.readImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics2D.drawImage(image , x, y, GameUIConstants.GAME_AREA_RECTANGLE, GameUIConstants.GAME_AREA_RECTANGLE, null);
    }
    private void loadTower2_OnTheField(Graphics2D graphics2D,int x,int y,boolean disabled){
        String path;

        if(!disabled){
            path = "images/tower2_Example.png" ;
        }
        else{
            path = "images/disabled_tower2_Example.png";
        }
        BufferedImage image = null;
        try {
            image = ImageLoader.readImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics2D.drawImage(image , x, y, GameUIConstants.GAME_AREA_RECTANGLE, GameUIConstants.GAME_AREA_RECTANGLE, null);
    }
    private void loadCastleOnTheField(Graphics2D graphics2D,int x,int y,boolean disabled){
        String path;
        if(!disabled){
            path = "images/castle.png" ;
        }
        else{
            path = "images/castle.png";
        }
        BufferedImage image = null;
        try {
            image = ImageLoader.readImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics2D.drawImage(image , x, y, GameUIConstants.GAME_AREA_RECTANGLE, GameUIConstants.GAME_AREA_RECTANGLE, null);
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
                graphics2D.draw(currentField);
                if(localGrid[y_col][x_row] == Type.TOWER1){
                    loadTower1_OnTheField(graphics2D,currentField.x,currentField.y,false);
                }
                else if(localGrid[y_col][x_row] == Type.TOWER2){
                    loadTower2_OnTheField(graphics2D,currentField.x,currentField.y,false);
                }
                else if(localGrid[y_col][x_row]== Type.CASTLE){
                    loadCastleOnTheField(graphics2D,currentField.x,currentField.y,false);
                }
            }
        }
        drawInfoBoard(graphics2D);
    }
    //TODO: refactor the player1/player2 draw out functions
    private void player1_drawOutTheTowerField(Type Tower,Graphics2D graphics2D,boolean disabled){
        if(Tower == Type.TOWER1){
            if(gameLogic.getFillTowerType()==Type.TOWER1 && gameLogic.getPlayerTurn()==PlayerTurn.PLAYER1){
                graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
            }
            else{
                graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
            }
            Rectangle Tower1_Field = new Rectangle(0,GameUIConstants.SMALL_FONT.getSize()*2,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
            loadTower1_OnTheField(graphics2D,0,GameUIConstants.SMALL_FONT.getSize()*2,disabled);
            graphics2D.draw(Tower1_Field);
        }
        else if(Tower == Type.TOWER2){
            if(gameLogic.getFillTowerType()==Type.TOWER2 && gameLogic.getPlayerTurn()==PlayerTurn.PLAYER1){
                graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
            }
            else{
                graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
            }
            Rectangle Tower2_Field = new Rectangle(GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.SMALL_FONT.getSize()*2,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
            loadTower2_OnTheField(graphics2D,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.SMALL_FONT.getSize()*2,disabled);
            graphics2D.draw(Tower2_Field);
        }
    }
    private void player1_drawOutEndButton(Graphics2D graphics2D){//todo: when someone clicks on this the tower image become disabled
        graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
        if(gameLogic.getPlayerTurn()==PlayerTurn.PLAYER2)
        {
            graphics2D.drawString("not my turn", GameUIConstants.GAME_AREA_RECTANGLE*4, GameUIConstants.SMALL_FONT.getSize()*3);
        }
        else{
            graphics2D.drawString("End turn", GameUIConstants.GAME_AREA_RECTANGLE*4, GameUIConstants.SMALL_FONT.getSize()*3);
        }
    }
    private void player2_drawOutTheTowerField(Type Tower,Graphics2D graphics2D,boolean disabled){
        if(Tower == Type.TOWER1){
            if(gameLogic.getFillTowerType()==Type.TOWER1 && gameLogic.getPlayerTurn()==PlayerTurn.PLAYER2){
                graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
            }
            else{
                graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
            }
            Rectangle Tower1_Field = new Rectangle(0,gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*2,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
            loadTower1_OnTheField(graphics2D,0,gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*2,disabled);
            graphics2D.draw(Tower1_Field);
        }
        else if(Tower == Type.TOWER2){
            if(gameLogic.getFillTowerType()==Type.TOWER2 && gameLogic.getPlayerTurn()==PlayerTurn.PLAYER2){
                graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
            }
            else{
                graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
            }
            Rectangle Tower2_Field = new Rectangle(GameUIConstants.GAME_AREA_RECTANGLE,gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*2,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
            loadTower2_OnTheField(graphics2D,GameUIConstants.GAME_AREA_RECTANGLE,gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*2,disabled);
            graphics2D.draw(Tower2_Field);
        }
    }
    private void player2_drawOutEndButton(Graphics2D graphics2D){//todo: when someone clicks on this the tower image become disabled
        graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
        if(gameLogic.getPlayerTurn()==PlayerTurn.PLAYER1){
            graphics2D.drawString("not my turn", GameUIConstants.GAME_AREA_RECTANGLE*4, gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*3);
        }
        else{
            graphics2D.drawString("End turn", GameUIConstants.GAME_AREA_RECTANGLE*4, gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*3);
        }
    }
    private void drawInfoBoard(Graphics2D graphics2D) {//drawing out the player 1 info board
        //TODO: rethink infoboard structure. Just example.


        graphics2D.setColor(GameUIConstants.GRID_BORDER_COLOR);
        graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
        graphics2D.draw(gameConstants.player1InfoBoard);

        graphics2D.setColor(GameUIConstants.TEXT_COLOR);
        graphics2D.setFont(GameUIConstants.SMALL_FONT);
        graphics2D.drawString("Player 1         Money: "+gameLogic.getPlayer1Gold(), 0, GameUIConstants.SMALL_FONT.getSize());

        if(gameLogic.getPlayerTurn()==PlayerTurn.PLAYER2){
            player1_drawOutTheTowerField(Type.TOWER1,graphics2D,true);
            player1_drawOutTheTowerField(Type.TOWER2,graphics2D,true);
        }
        else{
            player1_drawOutTheTowerField(Type.TOWER1,graphics2D,false);
            player1_drawOutTheTowerField(Type.TOWER2,graphics2D,false);
        }
        player1_drawOutEndButton(graphics2D);

        graphics2D.draw(gameConstants.player2InfoBoard);

        graphics2D.setColor(GameUIConstants.TEXT_COLOR);
        graphics2D.setFont(GameUIConstants.SMALL_FONT);
        graphics2D.drawString("Player 2         Money: "+gameLogic.getPlayer2Gold(), 0, gameConstants.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize());

        if(gameLogic.getPlayerTurn()==PlayerTurn.PLAYER1){
            player2_drawOutTheTowerField(Type.TOWER1,graphics2D,true);
            player2_drawOutTheTowerField(Type.TOWER2,graphics2D,true);
        }
        else{
            player2_drawOutTheTowerField(Type.TOWER1,graphics2D,false);
            player2_drawOutTheTowerField(Type.TOWER2,graphics2D,false);
        }

        player2_drawOutEndButton(graphics2D);


    }



    private void drawAll(Graphics2D graphics2D) {
        //TODO: use drawGamePart to draw elements.
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawGameArea(graphics2D);

        InfoBoard infoBoard = gameLogic.getInfoBoard();
        if (infoBoard.isGameOver()) {

                drawStartScreen(graphics2D);

        } else {
            drawAll(graphics2D);
        }
        Toolkit.getDefaultToolkit().sync();
    }
}
