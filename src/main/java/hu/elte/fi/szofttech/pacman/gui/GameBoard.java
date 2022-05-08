package main.java.hu.elte.fi.szofttech.pacman.gui;

import main.java.hu.elte.fi.szofttech.pacman.model.*;
import main.java.hu.elte.fi.szofttech.pacman.model.info.InfoBoard;
import main.java.hu.elte.fi.szofttech.pacman.model.soldier.Soldier;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class GameBoard extends JPanel {

    private final GameLogic gameLogic;

    private String name;

    private final GameUIConstants gameConstants;

    private Timer timer;


    private TheScreen ScreenMethods;
    private ImageLoader imageLoader;
    private List<Position> clickableObject; //many things on one field


    public GameBoard(GameLogic gameLogic) {
        gameConstants = new GameUIConstants();//it will calculate the area
        ScreenMethods = new TheScreen();
        imageLoader = new ImageLoader();
        this.gameLogic = gameLogic;
        //setPreferredSize(new Dimension(GameUIConstants.GAME_WIDTH, GameUIConstants.GAME_HEIGHT));
        setPreferredSize(new Dimension(GameUIConstants.GAME_WIDTH, GameUIConstants.GAME_HEIGHT));
        setBackground(GameUIConstants.BACKGROUND_COLOR);
        setFocusable(true);
        clickableObject = new ArrayList<>();
        startNewGame();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO: create disabled state for the WHOLE INFO BOARD!!
                if(GameLogic.playerTurn == null) return;
                int startOfThePlayer1InfoBoard = (gameConstants.player1InfoBoard.y+(GameUIConstants.SMALL_FONT.getSize()*2));
                int endOfThePlayer1InfoBoard = startOfThePlayer1InfoBoard+GameUIConstants.GAME_AREA_RECTANGLE;
                int player1_castle_x = gameLogic.player1Castle.x;
                int player1_castle_y = gameLogic.player1Castle.y;
                int player2_castle_x = gameLogic.player2Castle.x;
                int player2_castle_y = gameLogic.player2Castle.y;
                //TODO: REFACTOR!
                InfoBoard infoBoard = gameLogic.getInfoBoard();
                if (infoBoard.isGameOver()) {
                    startNewGame();

                }
                if(ScreenMethods.clickAbleField(clickableObject,gameLogic.grids,e.getX(),e.getY())){
                    int y = ScreenMethods.convertXtoModelY_GameArea(e.getX());
                    int x = ScreenMethods.convertYtoModelX_GameArea(e.getY());
                    StringBuilder severalThingOnTheField = new StringBuilder();
                    for (Soldier soldier:
                         gameLogic.grids[x][y].soldiersOnTheField) {
                        severalThingOnTheField.append("Type: "+soldier.SoldierType.toString()+
                                "\nOwner: "+soldier.OwnerPlayer.toString()+
                                "\nHealth: "+soldier.getHealth()+"\n - \n");
                    }
                    PopUp pop = new PopUp(severalThingOnTheField.toString());
                    //gameLogic.grids[x][y].soldiersOnTheField.size();
                }
                else if(ScreenMethods.onGameArea(e.getX(),e.getY())) {//we want to place a tower on the gamearea
                    if(gameLogic.getFillTowerType()!=null) {
                        int x = ScreenMethods.convertYtoModelX_GameArea(e.getY());
                        int y = ScreenMethods.convertXtoModelY_GameArea(e.getX());
                        if(gameLogic.grids[x][y].isEmpty()) {
                            int minusMoney = gameLogic.getFillTowerType()==Type.TOWER1 ? GameConstants.TOWER1_PRICE : 0;
                            minusMoney = gameLogic.getFillTowerType()==Type.TOWER2 ? GameConstants.TOWER2_PRICE : minusMoney;
                            if(gameLogic.getFillTowerType()==Type.PLAYER1_TrainingField||gameLogic.getFillTowerType()==Type.PLAYER2_TrainingField){
                                minusMoney = GameConstants.TRAININGFIELD_PRICE;
                            }
                            if(!gameLogic.grids[x][y].getWater() && !gameLogic.grids[x][y].getHill() && gameLogic.canBuild(x,y)){//ha ures a mezo
                                if(gameLogic.isMyArea(y,GameLogic.playerTurn)&&gameLogic.removeMoney(minusMoney,GameLogic.playerTurn)) {//ha az en teruletem es van ra eleg penzem
                                    Tower newTowerOnThisField;
                                    if(gameLogic.getFillTowerType() == Type.TOWER1) {
                                        newTowerOnThisField = new Tower(GameLogic.playerTurn,
                                                100, gameLogic.getFillTowerType(),1,10);
                                        gameLogic.grids[x][y].addTower(newTowerOnThisField);
                                    } else if(gameLogic.getFillTowerType() == Type.TOWER2){
                                        newTowerOnThisField = new Tower(GameLogic.playerTurn,
                                                100, gameLogic.getFillTowerType(),2,10);
                                        gameLogic.grids[x][y].addTower(newTowerOnThisField);
                                    }
                                    else if(gameLogic.getFillTowerType()==Type.PLAYER1_TrainingField){
                                        gameLogic.placedTrainingFields.add(gameLogic.grids[x][y].
                                                addTrainingField(gameLogic.getFillTowerType()));//it returns with a traningfield

                                    }
                                    else if(gameLogic.getFillTowerType()==Type.PLAYER2_TrainingField){
                                        gameLogic.placedTrainingFields.add(gameLogic.grids[x][y].
                                                addTrainingField(gameLogic.getFillTowerType()));
                                    }
                                } else {
                                    if(!gameLogic.isMyArea(y,GameLogic.playerTurn)){
                                        PopUp popUp = new PopUp("Az ellenseg teruletere nem lehet tornyot rakni.");
                                    }
                                    else{
                                        PopUp popUp = new PopUp("Elfogyott a penz.");
                                    }
                                }
                            }
                            else{
                                PopUp popUp = new PopUp("Nem lehet ide epitkezni");
                            }
                        } else if(gameLogic.grids[x][y].isTowerOnTheField() && gameLogic.grids[x][y].getTowerOnTheField().OwnerPlayer == GameLogic.playerTurn){
                            if(gameLogic.grids[x][y].getTowerOnTheField().TowerType == Type.TOWER1){
                                UpgradePopUp upgradePopUp = new UpgradePopUp("Torony 1!");
                                if(upgradePopUp.pressed == 0){
                                    gameLogic.upgradeTower(x, y);
                                }else if(upgradePopUp.pressed == 1){
                                    gameLogic.demolishTower(x, y);
                                }
                            } else if(gameLogic.grids[x][y].getTowerOnTheField().TowerType == Type.TOWER2){
                                UpgradePopUp upgradePopUp = new UpgradePopUp("Torony 2!");
                                if(upgradePopUp.pressed == 0){
                                    gameLogic.upgradeTower(x, y);
                                }else if(upgradePopUp.pressed == 1){
                                    gameLogic.demolishTower(x, y);
                                }
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
                    int x = ScreenMethods.convertYtoModelX_GameArea(e.getY());
                    int y = ScreenMethods.convertXtoModelY_GameArea(e.getX());
                    if(GameLogic.playerTurn==PlayerTurn.PLAYER1){
                        Soldier soldier = new Soldier(PlayerTurn.PLAYER1,100,Type.PLAYER1_SOLDIER1,player1_castle_x,player1_castle_y);
                        if(gameLogic.removeMoney(soldier.getPrice(),gameLogic.playerTurn)){
                            gameLogic.incPlayer1Unit1Number();
                            gameLogic.grids[player1_castle_x][player1_castle_y].addSoldier(soldier,gameLogic); //caslte start
                           // gameLogic.a
                        }
                    }
                }
                else if(ScreenMethods.onPlayer1Unit2(e.getX(),e.getY())){
                    int x = ScreenMethods.convertYtoModelX_GameArea(e.getY());
                    int y = ScreenMethods.convertXtoModelY_GameArea(e.getX());
                    if(GameLogic.playerTurn==PlayerTurn.PLAYER1){
                        Soldier soldier = new Soldier(PlayerTurn.PLAYER1,100,Type.PLAYER1_SOLDIER2,player1_castle_x,player1_castle_y);
                            if(gameLogic.removeMoney(soldier.getPrice(),gameLogic.playerTurn)){
                                gameLogic.incPlayer1Unit2Number();
                                gameLogic.grids[player1_castle_x][player1_castle_y].addSoldier(soldier,gameLogic);

                            }
                    }
                }
                else if(ScreenMethods.onPlayer2Unit1(e.getX(),e.getY())){
                    int x = ScreenMethods.convertYtoModelX_GameArea(e.getY());
                    int y = ScreenMethods.convertXtoModelY_GameArea(e.getX());
                    if(GameLogic.playerTurn==PlayerTurn.PLAYER2){

                        Soldier soldier = new Soldier(PlayerTurn.PLAYER2,100,Type.PLAYER2_SOLDIER1,player2_castle_x,player2_castle_y);
                        if(gameLogic.removeMoney(soldier.getPrice(),gameLogic.playerTurn)){
                            gameLogic.incPlayer2Unit1Number();
                            gameLogic.grids[player2_castle_x][player2_castle_y].addSoldier(soldier,gameLogic);
                        }

                    }
                }
                else if(ScreenMethods.onPlayer2Unit2(e.getX(),e.getY())){
                    int x = ScreenMethods.convertYtoModelX_GameArea(e.getY());
                    int y = ScreenMethods.convertXtoModelY_GameArea(e.getX());
                    if(GameLogic.playerTurn==PlayerTurn.PLAYER2){
                        Soldier soldier = new Soldier(PlayerTurn.PLAYER2,100,Type.PLAYER2_SOLDIER2,player2_castle_x,player2_castle_y);
                        if(gameLogic.removeMoney(soldier.getPrice(),gameLogic.playerTurn)){
                            gameLogic.incPlayer2Unit1Number();
                            gameLogic.grids[player2_castle_x][player2_castle_y].addSoldier(soldier,gameLogic);
                        }
                    }
                }
                else if(ScreenMethods.playerInfoBoardTrainingField1_clicked(e.getX(),e.getY())){
                    gameLogic.setFillTowerType(Type.PLAYER1_TrainingField);
                }
                else if(ScreenMethods.playerInfoBoardTrainingField2_clicked(e.getX(),e.getY())){
                    gameLogic.setFillTowerType(Type.PLAYER2_TrainingField);
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
                gameLogic.fillUpTowerAndCastleList();
                repaint();
            }
        });
    }

    private Object getData(JTable table, int row_index, int col_index){
        return table.getModel().getValueAt(row_index, col_index);
    }
    private ArrayList readFile(){
        try {
            URL url = getClass().getResource("maps.txt");
            assert url != null;
            File myObj = new File(url.getPath());
            Scanner myReader = new Scanner(myObj);

            ArrayList<ArrayList<String[]>> maps = new ArrayList<>();

            //String data = myReader.nextLine();
            maps.add(new ArrayList<>());

            int current = 0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.equals("s")){
                    maps.add(new ArrayList<>());
                } else {
                    String[] segments = data.split(", ");
                    maps.get(maps.size() - 1).add(segments);
                }
            }
            myReader.close();
            return maps;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    private void fileWriteToFile(ArrayList<ArrayList<String[]>> maps) {

        try {
            URL url = getClass().getResource("maps.txt");
            assert url != null;
            File myObj = new File(url.getPath());
            FileWriter myWriter = new FileWriter(myObj, false);
            myWriter.write("");

            myWriter.close();

            FileWriter myWriterAppend = new FileWriter(myObj, true);

            for(int i = 0; i<maps.size(); ++i) {
                if(i!=0) {
                    myWriterAppend.write("\ns\n");

                }
                for(int j = 0; j < maps.get(i).size(); ++j) {
                    if(j > 0) {
                        myWriterAppend.write("\n");
                    }
                    for(int k = 0; k < maps.get(i).get(j).length; ++k) {
                        if(k>0) {
                            myWriterAppend.write(", ");
                        }
                        myWriterAppend.write(maps.get(i).get(j)[k]);

                    }
                }
            }
            myWriterAppend.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private void createInputDialog() {
        ArrayList<ArrayList<String[]>> maps = readFile();
        assert maps != null;
        String[] selectTables = new String[maps.size()];


        int k = 0;
        for(ArrayList<String[]> val : maps) {
            selectTables[k] = k+1 + ". map";
            ++k;
        }
        gameLogic.setRawMap(maps.get(0));

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("<html>_: EMPTY<br/>c1: CASTLE 1<br/>c2: CASTLE 2" +
                "<br/>w: WATER<br/>h: HILL</html>"));


        String[][] rec = {
                {"_","_","_",   "_","_","_",   "_","_","_",   "_","_","_",   "_","_","_" },
                {"_","_","_",   "_","_","_",   "_","_","_",   "_","_","_",   "_","_","_" },
                {"_","_","_",   "_","_","_",   "_","_","_",   "_","_","_",   "_","_","_" },
                {"_","_","_",   "_","_","_",   "_","_","_",   "_","_","_",   "_","_","_" },
                {"_","c1","_",   "_","w","_",   "_","_","_",   "_","_","_",   "_","_","_" },
                {"_","_","_",   "_","h","_",   "_","_","_",   "_","_","_",   "_","_","_" },
                {"_","_","_",   "_","_","_",   "_","_","_",   "_","_","_",   "_","_","_" },
                {"_","_","_",   "_","_","_",   "_","_","_",   "_","_","_",   "_","c2","_" },
                {"_","_","_",   "_","_","_",   "_","_","_",   "_","_","_",   "_","_","_" },
                {"_","_","_",   "_","h","_",   "_","_","_",   "_","_","_",   "_","_","_" },
        };
        String[] header = { "1", "2", "3", "4", "5", "6" ,"7", "8",
                "9" ,"10", "11", "12" ,"13", "14", "15"};
        JTable table = new JTable(rec, header);
        myPanel.add(new JScrollPane(table));

        JButton b=new JButton("Save and select currently edited");
        JButton b2=new JButton("Select the one from the dropdown current");

        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ArrayList<String[]> toAdd = new ArrayList<>();
                for(int i=0; i < 10; ++i) {
                    String[] newRec =
                            {  "_",  "_",  "_", "_",  "_",  "_", "_",  "_",  "_", "_",  "_",  "_", "_",  "_",  "_" }
                            ;
                    for(int j = 0; j < 15; ++j) {
                        newRec[j] = getData(table, i, j).toString();
                    }
                    toAdd.add(newRec);
                }
                maps.add(toAdd);
                gameLogic.setRawMap(toAdd);

                StringBuilder toShow = new StringBuilder();
                for (int i = 0; i < 10; ++i) {
                    toShow.append(Arrays.toString(toAdd.get(i)));
                    toShow.append("\n");
                }
                PopUp pop = new PopUp(toShow.toString());

                fileWriteToFile(maps);
            }
        });
        JComboBox<String> mapList = new JComboBox<>(selectTables);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                StringBuilder toShow = new StringBuilder();

                int idx = Integer.parseInt(((String) Objects.requireNonNull(mapList.getSelectedItem()))
                        .split("\\.")[0]);
                ArrayList<String[]> map = maps.get(idx-1);

                for (int i = 0; i < 10; ++i) {
                    toShow.append(Arrays.toString(map.get(i)));
                    toShow.append("\n");
                }
                gameLogic.setRawMap(map);
                PopUp pop = new PopUp(toShow.toString());
            }
        });



        myPanel.add(b);
        myPanel.add(new JLabel("or"));
        myPanel.add(b2);
        myPanel.add(mapList);


        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter your game settings:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

        } else {
            System.exit(-1);
        }
    }

    private void startNewGame() {
        int time = gameConstants.TIMER;
        this.timer = new Timer(time, this.oneGameCycleAction);
        createInputDialog();

        this.timer.start();
        GameLogic.playerTurn=PlayerTurn.PLAYER1;
        gameLogic.newGame(gameConstants.GAMEAREA_HEIGHT_canBeDividedBy,gameConstants.GAMEAREA_WIDTH_canBeDividedBy, name);
        repaint();
    }

    private final Action oneGameCycleAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(GameLogic.playerTurn == PlayerTurn.ATTACK) {
                gameLogic.nextAttackPhase();
            }
            if(gameLogic.isGameOver()){
                gameLogic.newGame(15,10,"");
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
        for (Field rangedObject : gameLogic.towerAndCastle) {
            for (int y_col = 0; y_col < gameLogic.getRow(); y_col++) {
                for (int x_row = 0; x_row < gameLogic.getColumn(); x_row++) {
                    if(rangedObject.isCastleOnTheField()){
                        if(gameLogic.inTheDistanceForTowerRange(rangedObject.x,rangedObject.y,x_row,y_col,rangedObject.getCastleOnTheField().range)){
                            drawTowerRange(graphics2D,x_row,y_col,rangedObject.getCastleOnTheField().OwnerPlayer);
                        }
                    }
                    else if(rangedObject.isTowerOnTheField()){
                        if(gameLogic.inTheDistanceForTowerRange(rangedObject.x,rangedObject.y,x_row,y_col,rangedObject.getTowerOnTheField().range)){
                            drawTowerRange(graphics2D,x_row,y_col, rangedObject.getTowerOnTheField().OwnerPlayer);
                        }
                    }
                }
            }
        }
        //drawModelToTheGamearea(graphics2D, localGrid);
    }
    private Rectangle getGameAreaField(int x,int y){
        return new Rectangle(gameConstants.gameareaREACT.x+x* GameUIConstants.GAME_AREA_RECTANGLE,
                gameConstants.gameareaREACT.y+y*GameUIConstants.GAME_AREA_RECTANGLE,
                GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
    }
    private Rectangle getHealthBar(int x, int y){
        return new  Rectangle(gameConstants.gameareaREACT.x+x* GameUIConstants.GAME_AREA_RECTANGLE + 5,gameConstants.gameareaREACT.y+y*GameUIConstants.GAME_AREA_RECTANGLE,40,9);
    }
    private Rectangle fillHealthBar(int x, int y, double percentage){
        int width = (int)(40*percentage);
        return new Rectangle(gameConstants.gameareaREACT.x+x* GameUIConstants.GAME_AREA_RECTANGLE + 5,gameConstants.gameareaREACT.y+y*GameUIConstants.GAME_AREA_RECTANGLE+2,width,5);
    }
    private void makeHealthBar(Graphics2D graphics2D,int x, int y,int healthFromSoldier){
        Rectangle healthbar = getHealthBar(x,y);
        graphics2D.setColor(Color.red);
        graphics2D.fill(healthbar);
        double percentage = (double) healthFromSoldier / 100;
        Rectangle health = fillHealthBar(x,y,percentage);
        graphics2D.setColor(Color.green);
        graphics2D.fill(health);
    }
    private void drawModelToTheGamearea(Graphics2D graphics2D, Field[][] localGrid) {
        clickableObject.clear();
        for (int y_col = 0; y_col < gameLogic.getRow(); y_col++) {
            for (int x_row = 0; x_row < gameLogic.getColumn(); x_row++) {
                Rectangle currentField = getGameAreaField(x_row,y_col);
                graphics2D.setColor(Color.BLACK);
                //todo it needs to beredone
                if(localGrid[y_col][x_row].isCastleOnTheField()){
                    Castle castleOnTheField = localGrid[y_col][x_row].getCastleOnTheField();

                    drawFixedRectAngleFieldWithImage(graphics2D,currentField.x,currentField.y,castleOnTheField.castleImage(),false);
                    makeHealthBar(graphics2D,x_row,y_col,castleOnTheField.health);
                }
                else if(localGrid[y_col][x_row].isTrainingFieldOnThisField()){
                    TrainingField trainingField = localGrid[y_col][x_row].getTrainingField();
                    String trainingFieldImage = trainingField.TowerType==Type.PLAYER1_TrainingField ? GameUIConstants.TraningFieldPlayer1 : GameUIConstants.TraningFieldPlayer2;
                    drawFixedRectAngleFieldWithImage(graphics2D,currentField.x,currentField.y,trainingFieldImage,false);
                }
                else if(localGrid[y_col][x_row].isTowerOnTheField()){
                    Tower towerOnThisField = localGrid[y_col][x_row].getTowerOnTheField();
                    drawFixedRectAngleFieldWithImage(graphics2D,currentField.x,currentField.y,Tower.towerImage(Tower.playerTowerType(towerOnThisField.OwnerPlayer,towerOnThisField.TowerType)),false);
                }
                else if(localGrid[y_col][x_row].CountOfTheSoldier()==1){
                    Soldier soldier = localGrid[y_col][x_row].getFirstSoldier();
                    imageLoader.loadImage(graphics2D,currentField.x,currentField.y,soldier.getSoliderImage());
                    makeHealthBar(graphics2D,x_row,y_col,soldier.getHealth());
                }
                else if(localGrid[y_col][x_row].CountOfTheSoldier()>1){
                      graphics2D.setFont(GameUIConstants.MAIN_FONT);
                    graphics2D.drawString("X", currentField.x,currentField.y+GameUIConstants.GAME_AREA_RECTANGLE);
                   // drawFixedRectAngleFieldWithImage(graphics2D,currentField.x,currentField.y,GameUIConstants.Army_image,false);
                    clickableObject.add(new Position(currentField.x,currentField.y));
                }
                else if(localGrid[y_col][x_row].getHill()){
                    drawFixedRectAngleFieldWithImage(graphics2D,currentField.x,currentField.y,GameUIConstants.Hill,false);
                }
                else if(localGrid[y_col][x_row].getWater()){
                    drawFixedRectAngleFieldWithImage(graphics2D,currentField.x,currentField.y,GameUIConstants.Water,false);
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
        //TODO: SWICH A SOK IF HELYETT! meg ez az image loaderes dolog is ismetles.
        drawModelToTheGamearea(graphics2D, localGrid);
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
    private void drawoutTrainingField(Graphics2D graphics2D, boolean isPlayer1){
        drawFixedRectAngleFieldWithImage(graphics2D,gameConstants.player1_placeOfTrainingField_OnInfoBoard.x,gameConstants.player1_placeOfTrainingField_OnInfoBoard.y,GameUIConstants.TraningFieldPlayer1,false);
        drawFixedRectAngleFieldWithImage(graphics2D,gameConstants.player2_placeOfTrainingField_OnInfoBoard.x,gameConstants.player2_placeOfTrainingField_OnInfoBoard.y,GameUIConstants.TraningFieldPlayer2,false);
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
    private void drawTowerRange(Graphics2D graphics2D,int x, int y, PlayerTurn playerTurn){
        Rectangle area = getGameAreaField(x,y);
        Color red = new Color(255,102,102);
        Color blue = new Color(0,0,255);
        graphics2D.setStroke(GameUIConstants.LARGE_STROKE); //clear things out
        if(playerTurn==PlayerTurn.PLAYER1){
            graphics2D.setColor(blue);
        }
        else{
            graphics2D.setColor(red);
        }
        graphics2D.draw(area);
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(GameUIConstants.SMALL_STROKE);
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
        graphics2D.drawString("Player 1         Gold: "+gameLogic.getPlayer1Gold()+"    Regular: "+gameLogic.getPlayer1Unit1Count()+"  Climber: "+gameLogic.getPlayer1Unit2Count(),
                gameConstants.player1_moneyAndNameText.x,gameConstants.player1_moneyAndNameText.y);
        graphics2D.drawString("Player 2         Gold: "+gameLogic.getPlayer2Gold()+"    Regular: "+gameLogic.getPlayer2Unit1Count()+"  Climber: "+gameLogic.getPlayer2Unit2Count(),
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
        drawoutTrainingField(graphics2D,false);
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
