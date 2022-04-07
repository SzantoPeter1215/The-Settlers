package gui;

import model.*;
import model.info.InfoBoard;
import model.soldier.Soldier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;



public class GameBoard extends JPanel {

    private final GameLogic gameLogic;

    private String name;

    private final GameUIConstants gameConstants;

    private Timer timer;


    private TheScreen ScreenMethods;
    private ImageLoader imageLoader;


    public GameBoard(GameLogic gameLogic) {
        gameConstants = new GameUIConstants();//it will calculate the area
        ScreenMethods = new TheScreen();
        imageLoader = new ImageLoader();
        this.gameLogic = gameLogic;
        //setPreferredSize(new Dimension(GameUIConstants.GAME_WIDTH, GameUIConstants.GAME_HEIGHT));
        setPreferredSize(new Dimension(GameUIConstants.GAME_HEIGHT, GameUIConstants.GAME_HEIGHT));
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
                if(ScreenMethods.onGameArea(e.getX(),e.getY())) {//we want to place a tower on the gamearea
                    if(gameLogic.getFillTowerType()!=null) {
                        int x = ScreenMethods.convertYtoModelX_GameArea(e.getY());
                        int y = ScreenMethods.convertXtoModelY_GameArea(e.getX());
                        if(gameLogic.grids[x][y].isEmpty()) {
                            int minusMoney = gameLogic.getFillTowerType()==Type.TOWER1 ? GameConstants.TOWER1_PRICE : 0;
                            minusMoney = gameLogic.getFillTowerType()==Type.TOWER2 ? GameConstants.TOWER2_PRICE : minusMoney;
                            if(gameLogic.removeMoney(minusMoney,GameLogic.playerTurn)){
                                Tower newTowerOnThisField = new Tower(GameLogic.playerTurn,100,gameLogic.getFillTowerType());
                                gameLogic.grids[x][y].addTower(newTowerOnThisField);

                            }
                            else{
                                PopUp popUp = new PopUp("No money");
                            }
                        }
                    }
                }
                else if(ScreenMethods.playerInfoBoardTower1_clicked(e.getX(),e.getY())) {
                    gameLogic.setFillTowerType(Type.TOWER1);

                }
                else if(ScreenMethods.playerInfoBoardTower2_clicked(e.getX(),e.getY())){
                    gameLogic.setFillTowerType(Type.TOWER2);

                }
                else if(ScreenMethods.onPlayer1Unit1(e.getX(),e.getY())){
                    if(GameLogic.playerTurn==PlayerTurn.PLAYER1){
                        gameLogic.incPlayer1Unit1Number();
                        gameLogic.createPlayer1Soldier1();
                    }
                }
                else if(ScreenMethods.onPlayer1Unit2(e.getX(),e.getY())){
                    if(GameLogic.playerTurn==PlayerTurn.PLAYER1){
                        gameLogic.incPlayer1Unit2Number();
                    }
                }
                else if(ScreenMethods.onPlayer2Unit1(e.getX(),e.getY())){
                    if(GameLogic.playerTurn==PlayerTurn.PLAYER2){
                        gameLogic.incPlayer2Unit1Number();
                    }
                }
                else if(ScreenMethods.onPlayer2Unit2(e.getX(),e.getY())){
                    if(GameLogic.playerTurn==PlayerTurn.PLAYER2){
                        gameLogic.incPlayer2Unit2Number();
                    }
                }
                else if(ScreenMethods.playerInfoBoardEndTurn_clicked(e.getX(),e.getY())){
                    if (GameLogic.playerTurn == PlayerTurn.PLAYER2){
                        //after P2 ends their turn the attack phase begins
                        gameLogic.initAttackPhase();
                    }
                    else if(GameLogic.playerTurn == PlayerTurn.PLAYER1){
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
        //making random castle for player 1 in his field area
        //TODO: MI A FASZ KÖZE VAN A RANDOM CASTLE GENERÁLÁSNAK A FRAMEHEZ?
        // MAJD A GAME LOGIC MEGKÉRDEZI: HALLOD HOL VAN A TORONY?
        /*
        int Player1castleRandomX = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4);
        int Player2castleRandomX = ThreadLocalRandom.current().nextInt(gameConstants.GAMEAREA_WIDTH_canBeDividedBy/4*3, gameConstants.GAMEAREA_WIDTH_canBeDividedBy-1);
        int castleRandomY = ThreadLocalRandom.current().nextInt(0, gameConstants.GAMEAREA_HEIGHT_canBeDividedBy);//It's the same for both castles
        this.player1Castle = new Position(Player1castleRandomX,castleRandomY);
        this.player2Castle = new Position(Player2castleRandomX,castleRandomY);
        gameLogic.setTypeElement(castleRandomY,Player1castleRandomX,Type.CASTLE);
        gameLogic.setTypeElement(castleRandomY,Player2castleRandomX,Type.CASTLE);
        gameLogic.setFillTowerType(Type.TOWER1);
        */
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
    //TODO:refactor!
    private void drawTowerRange(Graphics2D graphics2D){
        Field[][] localGrid = gameLogic.getGrids();
        for (int y_col = 0; y_col < gameConstants.GAMEAREA_HEIGHT_canBeDividedBy; y_col++) {
            for (int x_row = 0; x_row < gameConstants.GAMEAREA_WIDTH_canBeDividedBy; x_row++) {
                if(localGrid[y_col][x_row].hasRange()){
                    int width = 3;
                    int height =3;
                    int x_adjust = 1;
                    int y_adjust = 1;
                    if(y_col == 0) {
                        height = 2;
                        y_adjust = 0;
                    }else if(y_col == gameConstants.GAMEAREA_HEIGHT_canBeDividedBy - 1) {
                        height = 2;
                    }
                    if(x_row == 0){
                        width = 2;
                        x_adjust = 0;
                    }else if(x_row == gameConstants.GAMEAREA_WIDTH_canBeDividedBy - 1){
                        width = 2;
                    }
                    Rectangle area = new Rectangle(gameConstants.gameareaREACT.x + x_row * GameUIConstants.GAME_AREA_RECTANGLE - x_adjust * GameUIConstants.GAME_AREA_RECTANGLE, gameConstants.gameareaREACT.y + y_col * GameUIConstants.GAME_AREA_RECTANGLE - y_adjust * GameUIConstants.GAME_AREA_RECTANGLE, GameUIConstants.GAME_AREA_RECTANGLE * width, GameUIConstants.GAME_AREA_RECTANGLE * height);
                    Rectangle currentField = new Rectangle(gameConstants.gameareaREACT.x+x_row*GameUIConstants.GAME_AREA_RECTANGLE,gameConstants.gameareaREACT.y+y_col*GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
                    Color c1 = new Color(255,102,102);
                    graphics2D.setColor(c1);
                    graphics2D.fill(area);
                    //todo omg it needs to be redone
                    if(localGrid[y_col][x_row].isCastleOnTheField()){
                        imageLoader.loadImage(graphics2D,currentField.x,currentField.y,Castle.castleImage(Castle.getCastlePlayer(GameLogic.playerTurn)));
                    }
                    else if(localGrid[y_col][x_row].isTowerOnTheField()){
                        Tower towerOnThisField = localGrid[y_col][x_row].getTowerOnTheField();
                        imageLoader.loadImage(graphics2D,currentField.x,currentField.y,Tower.towerImage(Tower.playerTowerType(towerOnThisField.OwnerPlayer,towerOnThisField.TowerType)));
                    }
                }
            }
        }
        towerRangeHelper(graphics2D, localGrid);
    }

    private void towerRangeHelper(Graphics2D graphics2D, Field[][] localGrid) {
        for (int y_col = 0; y_col < gameConstants.GAMEAREA_HEIGHT_canBeDividedBy; y_col++) {
            for (int x_row = 0; x_row < gameConstants.GAMEAREA_WIDTH_canBeDividedBy; x_row++) {
                Rectangle currentField = new Rectangle(gameConstants.gameareaREACT.x+x_row* GameUIConstants.GAME_AREA_RECTANGLE,gameConstants.gameareaREACT.y+y_col*GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
                graphics2D.setColor(Color.BLACK);
                //todo it needs to beredone
                if(localGrid[y_col][x_row].isCastleOnTheField()){
                    imageLoader.loadImage(graphics2D,currentField.x,currentField.y,Castle.castleImage(Castle.getCastlePlayer(GameLogic.playerTurn)));
                }
                else if(localGrid[y_col][x_row].isTowerOnTheField()){
                    Tower towerOnThisField = localGrid[y_col][x_row].getTowerOnTheField();
                    imageLoader.loadImage(graphics2D,currentField.x,currentField.y,Tower.towerImage(Tower.playerTowerType(towerOnThisField.OwnerPlayer,towerOnThisField.TowerType)));
                }
                else if(localGrid[y_col][x_row].CountOfTheSoldier()>0){
                    Soldier soldier = localGrid[y_col][x_row].getFirstSoldier();
                    imageLoader.loadImage(graphics2D,currentField.x,currentField.y,soldier.getSoliderImage());
                }
                else{
                    graphics2D.draw(currentField);
                }
            }
        }
    }

    private void drawGameArea(Graphics2D graphics2D) {
        graphics2D.setColor(GameUIConstants.GRID_COLOR);
        graphics2D.fill(gameConstants.gameareaREACT);
        graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
        graphics2D.setColor(GameUIConstants.GRID_BORDER_COLOR);
        graphics2D.draw(gameConstants.gameareaREACT);
        graphics2D.setStroke(GameUIConstants.SMALL_STROKE);

        Field[][] localGrid = gameLogic.getGrids();
        //TODO: SWICH A SOK IF HELYETT! meg ez az image loaderes dolog is ismétlés.
        towerRangeHelper(graphics2D, localGrid);
    }
    private void drawOutTowerUnitsInfoboard(Graphics2D graphics2D,boolean player1Tower1,boolean player1Tower2,boolean player2Tower1,boolean player2Tower2, boolean isPlayer1Turn,boolean isPlayer2Turn){
        drawFixedRectAngleFieldWithImage(graphics2D,gameConstants.player1_placeOfTower1_OnInfoBoard.x,gameConstants.player1_placeOfTower1_OnInfoBoard.y,
                isPlayer1Turn ? GameUIConstants.Player1Tower1 : GameUIConstants.disabled_Tower1Image,player1Tower1);
        drawFixedRectAngleFieldWithImage(graphics2D,gameConstants.player1_placeOfTower2_OnInfoBoard.x,gameConstants.player1_placeOfTower2_OnInfoBoard.y,
                isPlayer1Turn ? GameUIConstants.Player1Tower2 : GameUIConstants.disalbed_Tower2Image,player1Tower2);
        drawFixedRectAngleFieldWithImage(graphics2D,gameConstants.player2_placeOfTower1_OnInfoBoard.x,gameConstants.player2_placeOfTower1_OnInfoBoard.y,
                isPlayer2Turn ?GameUIConstants.Player2Tower1 : GameUIConstants.disabled_Tower1Image,player2Tower1);
        drawFixedRectAngleFieldWithImage(graphics2D,gameConstants.player2_placeOfTower2_OnInfoBoard.x,gameConstants.player2_placeOfTower2_OnInfoBoard.y,
                isPlayer2Turn ? GameUIConstants.Player2Tower2 : GameUIConstants.disalbed_Tower2Image,player2Tower2);
    }
    private void drawOutTheTowerField(Graphics2D graphics2D,boolean isThePlayer_Player2){//drawing out the tower fields on the infobroads in the following order:
            //default state:
            drawOutTowerUnitsInfoboard(graphics2D,false,false,false,false,false,false);
            if(gameLogic.getFillTowerType()==Type.TOWER1){//we clicked on a tower 1 button on the inforboard and it's not the player 2
                if(!isThePlayer_Player2){
                    drawOutTowerUnitsInfoboard(graphics2D,true,false,false,false,true,false);
                }
                else{
                    drawOutTowerUnitsInfoboard(graphics2D,false,false,true,false,false,true);
                }

            }
            if(gameLogic.getFillTowerType()==Type.TOWER2){
                if(!isThePlayer_Player2){
                    drawOutTowerUnitsInfoboard(graphics2D,false,true,false,false,true,false);
                }
                else{
                    drawOutTowerUnitsInfoboard(graphics2D,false,false,false,true,false,true);
                }
            }
    }
    private void drawFixedRectAngleFieldWithImage(Graphics2D graphics2D,int x,int y,String imageUrl,boolean isLargeStroke){
        if(isLargeStroke){
            graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
        }
        else{
            graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
        }
        Rectangle field = new Rectangle(x,y,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
        imageLoader.loadImage(graphics2D,x,y,imageUrl);
        graphics2D.draw(field);
        graphics2D.setStroke(GameUIConstants.SMALL_STROKE); //clear things out
    }
    private void SoldierStates(Graphics2D graphics2D,boolean player1_Soldier1_Active,boolean player1_Soldier2_Active,boolean player2_Soldier1_Active,boolean player2_Soldier2_active){
        imageLoader.loadImage(graphics2D,gameConstants.player1_placeOfSoldier1_OnInfoBoard.x,gameConstants.player1_placeOfSoldier1_OnInfoBoard.y,
                player1_Soldier1_Active ? GameUIConstants.player1Soldier1: GameUIConstants.disabled_Soldier1);
        graphics2D.draw(gameConstants.player1_placeOfSoldier1_OnInfoBoard);
        imageLoader.loadImage(graphics2D,gameConstants.player1_placeOfSoldier2_OnInfoBoard.x,gameConstants.player1_placeOfSoldier2_OnInfoBoard.y,
                player1_Soldier2_Active ? GameUIConstants.player1Soldier2: GameUIConstants.disabled_Soldier2);
        graphics2D.draw(gameConstants.player1_placeOfSoldier2_OnInfoBoard);
        imageLoader.loadImage(graphics2D,gameConstants.player2_placeOfSoldier1_OnInfoBoard.x,gameConstants.player2_placeOfSoldier1_OnInfoBoard.y,
                player2_Soldier1_Active ? GameUIConstants.player2Soldier1 : GameUIConstants.disabled_Soldier1);
        graphics2D.draw(gameConstants.player2_placeOfSoldier1_OnInfoBoard);
        imageLoader.loadImage(graphics2D,gameConstants.player2_placeOfSoldier2_OnInfoBoard.x,gameConstants.player2_placeOfSoldier2_OnInfoBoard.y,
                player2_Soldier2_active ? GameUIConstants.player2Soldier2 : GameUIConstants.disabled_Soldier2);
        graphics2D.draw(gameConstants.player2_placeOfSoldier2_OnInfoBoard);
    }
    private void DrawingOutSoldier(Graphics2D graphics2D){

        if(GameLogic.playerTurn == PlayerTurn.PLAYER1){
            SoldierStates(graphics2D,true,true,false,false);
        }
        else if(GameLogic.playerTurn == PlayerTurn.PLAYER2){
            SoldierStates(graphics2D,false,false,true,true);
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
        graphics2D.drawString("Player 1         Gold: "+gameLogic.getPlayer1Gold()+"    Unit 1: "+gameLogic.getPlayer1Unit1Number()+" Unit 2:"+gameLogic.getPlayer1Unit2Number(),
                gameConstants.player1_moneyAndNameText.x,gameConstants.player1_moneyAndNameText.y);
        graphics2D.drawString("Player 2         Gold: "+gameLogic.getPlayer2Gold()+"    Unit 1: "+gameLogic.getPlayer2Unit1Number()+" Unit 2:"+gameLogic.getPlayer2Unit2Number(),
                gameConstants.player2_moneyAndNameText.x,gameConstants.player2_moneyAndNameText.y);

        if(GameLogic.playerTurn==PlayerTurn.PLAYER1){
            drawOutTheTowerField(graphics2D,false);
            drawOutTheTowerField(graphics2D,false);
        }
        else{
            drawOutTheTowerField(graphics2D,true);
            drawOutTheTowerField(graphics2D,true);
        }
        DrawingOutSoldier(graphics2D);
        drawOutEndButton(graphics2D);
    }
    private void drawAll(Graphics2D graphics2D) {
        drawGameArea(graphics2D);
        drawInfoBoard(graphics2D);
        if(GameLogic.playerTurn == PlayerTurn.ATTACK){
            drawTowerRange(graphics2D);
        }
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
