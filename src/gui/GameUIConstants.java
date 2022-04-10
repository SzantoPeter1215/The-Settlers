package gui;

import java.awt.*;

public final class GameUIConstants {
    public int TIMER = 400; //attack phase: 1s
    public int GAMEAREA_HEIGHT;
    public int GAMEAREA_WIDTH;
    public int GAMEAREA_HEIGHT_canBeDividedBy;
    public int GAMEAREA_WIDTH_canBeDividedBy;
    public final static String Army_image = "images/army.png";
    public final static String disabled_Tower1Image = "images/disabled_towerExample.png";
    public final static String disalbed_Tower2Image = "images/disabled_tower2_Example.png";
    public final static String player1Soldier1 = "images/player1_soldier_1.png";
    public final static String player1Soldier2 = "images/player1_soldier_2.png";
    public final static String player2Soldier1 = "images/player2_soldier_1.png";
    public final static String player2Soldier2 = "images/player2_soldier_2.png";
    public final static String disabled_Soldier1 = "images/disabled_soldier_1.png";
    public final static String disabled_Soldier2 = "images/disabled_soldier_2.png";
    public final static String Player1_Castle = "images/player1_castle.png";
    public final static String Player2_Castle = "images/player2_castle.png";
    public final static String Player1Tower1 = "images/player1_tower1.png";
    public final static String Player1Tower2 = "images/player1_tower2.png";
    public final static String Player2Tower1 = "images/player2_tower1.png";
    public final static String Player2Tower2 = "images/player2_tower2.png";
    public final static String Hill = "images/mountain_final.png";
    public final static String Water = "images/water.png";
    public Rectangle gameareaREACT;
    public Rectangle player1InfoBoard;
    public Rectangle player2InfoBoard;
    public Rectangle player1_placeOfTower1_OnInfoBoard;
    public Rectangle player2_placeOfTower1_OnInfoBoard;
    public Rectangle player1_placeOfTower2_OnInfoBoard;
    public Rectangle player2_placeOfTower2_OnInfoBoard;

    public Rectangle player1_placeOfSoldier1_OnInfoBoard;
    public Rectangle player1_placeOfSoldier2_OnInfoBoard;
    public Rectangle player2_placeOfSoldier1_OnInfoBoard;
    public Rectangle player2_placeOfSoldier2_OnInfoBoard;

    public Position player1_text_onInfoBoard;
    public Position player2_text_onInfoBoard;
    public Position player1_moneyAndNameText;
    public Position player2_moneyAndNameText;
    public static final int GAME_AREA_RECTANGLE = 50;
    public GameUIConstants(){//in this method we calculate the gamearea size. It's important that i can be divided by the small react(it will be one unit on the game where the user can put things)
        int WIDTH_hasToBeDividedByRectSize = GAME_WIDTH;
        while (WIDTH_hasToBeDividedByRectSize>0&&WIDTH_hasToBeDividedByRectSize%GAME_AREA_RECTANGLE!=0){
            WIDTH_hasToBeDividedByRectSize-=1;
        }
        int HEIGHT_hasToBeDividedByRectSize = GAME_HEIGHT-(int)(GAME_HEIGHT*0.3);
        while (HEIGHT_hasToBeDividedByRectSize>0&&HEIGHT_hasToBeDividedByRectSize%GAME_AREA_RECTANGLE!=0){
            HEIGHT_hasToBeDividedByRectSize-=1;
        }
        GAMEAREA_WIDTH = WIDTH_hasToBeDividedByRectSize;//WIDTH_hasToBeDividedByRectSize;
        GAMEAREA_HEIGHT = HEIGHT_hasToBeDividedByRectSize;
        GAMEAREA_HEIGHT_canBeDividedBy = 10; // (int)(GAMEAREA_HEIGHT/GAME_AREA_RECTANGLE);
        GAMEAREA_WIDTH_canBeDividedBy = 15;// (int)(GAMEAREA_WIDTH/GAME_AREA_RECTANGLE);

        gameareaREACT = new Rectangle(0,(GAME_HEIGHT-GAMEAREA_HEIGHT)/2,GAMEAREA_WIDTH,GAMEAREA_HEIGHT);
        player1InfoBoard = new Rectangle(0,0,GAMEAREA_WIDTH,(GAME_HEIGHT-GAMEAREA_HEIGHT)/2);
        player2InfoBoard = new Rectangle(0,GAME_HEIGHT-((GAME_HEIGHT-GAMEAREA_HEIGHT)/2),GAMEAREA_WIDTH,(GAME_HEIGHT-GAMEAREA_HEIGHT)/2);

        player1_placeOfTower1_OnInfoBoard = new Rectangle(0,GameUIConstants.SMALL_FONT.getSize()*2,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
        player2_placeOfTower1_OnInfoBoard =new Rectangle(0,this.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*2,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
        player1_placeOfTower2_OnInfoBoard = new Rectangle(GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.SMALL_FONT.getSize()*2,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
        player2_placeOfTower2_OnInfoBoard = new Rectangle(GameUIConstants.GAME_AREA_RECTANGLE,this.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*2,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);

        player1_placeOfSoldier1_OnInfoBoard = new Rectangle(0,player1_placeOfTower1_OnInfoBoard.y+GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
        player1_placeOfSoldier2_OnInfoBoard = new Rectangle(GameUIConstants.GAME_AREA_RECTANGLE,player1_placeOfSoldier1_OnInfoBoard.y,GameUIConstants.GAME_AREA_RECTANGLE,GameUIConstants.GAME_AREA_RECTANGLE);
        player2_placeOfSoldier1_OnInfoBoard = new Rectangle(0,player2_placeOfTower1_OnInfoBoard.y+GAME_AREA_RECTANGLE,GAME_AREA_RECTANGLE,GAME_AREA_RECTANGLE);
        player2_placeOfSoldier2_OnInfoBoard = new Rectangle(GAME_AREA_RECTANGLE,player2_placeOfSoldier1_OnInfoBoard.y,GAME_AREA_RECTANGLE,GAME_AREA_RECTANGLE);

        player2_text_onInfoBoard = new Position(GameUIConstants.GAME_AREA_RECTANGLE*4,this.player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize()*3);
        player1_text_onInfoBoard = new Position(GameUIConstants.GAME_AREA_RECTANGLE*4, GameUIConstants.SMALL_FONT.getSize()*3);
        player1_moneyAndNameText = new Position( 0, GameUIConstants.SMALL_FONT.getSize());
        player2_moneyAndNameText = new Position(0,player2InfoBoard.y+GameUIConstants.SMALL_FONT.getSize());
    }

    // width will store the width of the screen
    public static final int SCREENWIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

    // height will store the height of the screen
    public static final int SCREENHEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final int GAME_WIDTH = SCREENWIDTH-(int)(SCREENWIDTH*0.1);
    public static final int GAME_HEIGHT = SCREENHEIGHT-(int)(SCREENHEIGHT*0.1);


    public static final Color BACKGROUND_COLOR = new Color(0xDDEEFF);

    public static final int INFO_POS_X = GAME_WIDTH-(int)(GAME_WIDTH*0.9);
    public static final int INFO_POS_Y = GAME_HEIGHT-(int)(GAME_HEIGHT*0.9);
    public static final int TITLE_POS_X = 130;
    public static final int TITLE_POS_Y = 150;
    public static final int CLICK_POS_X = 120;
    public static final int CLICK_POS_Y = 400;


    public static final int BLOCK_SIZE = 30;

    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Font MAIN_FONT = new Font("Monospaced", Font.BOLD, 48);
    public static final Font SMALL_FONT = MAIN_FONT.deriveFont(Font.BOLD, 18);

    public static final Stroke LARGE_STROKE = new BasicStroke(5);
    public static final Stroke SMALL_STROKE = new BasicStroke(1);

    public static final Color TITLE_BG_COLOR = Color.WHITE;
    public static final Color GRID_COLOR = new Color(0xBECFEA);
    public static final Color GRID_BORDER_COLOR = new Color(0x7788AA);

    //public static final Rectangle GRID_RECTANGLE = new Rectangle(0, GAME_HEIGHT-(int)(GAME_HEIGHT*0.85), GAME_WIDTH,(int) (GAME_HEIGHT-(GAME_HEIGHT*0.35)));
    public static final Rectangle TITLE_RECTANGLE = new Rectangle(100, 85, 252, 100);
    public static final Rectangle CLICK_RECTANGLE = new Rectangle(50, 375, 252, 40);

    public static final int TOP_MARGIN = 50;
    public static final int LEFT_MARGIN = 20;
}
