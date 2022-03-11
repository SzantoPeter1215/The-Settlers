package gui;

import java.awt.*;

public final class GameUIConstants {
    public int TIMER = 1000; //attack phase: 1s
    public int GAMEAREA_HEIGHT;
    public int GAMEAREA_WIDTH;
    public int GAMEAREA_HEIGHT_canBeDividedBy;
    public int GAMEAREA_WIDTH_canBeDividedBy;
    public Rectangle gameareaREACT;
    public Rectangle player1InfoBoard;
    public Rectangle player2InfoBoard;
    public GameUIConstants(){//in this method we calculate the gamearea size. It's important that i can be divided by the small react(it will be one unit on the game where the user can put things)
        int WIDTH_hasToBeDividedByRectSize = GAME_WIDTH;
        while (WIDTH_hasToBeDividedByRectSize>0&&WIDTH_hasToBeDividedByRectSize%GAME_AREA_RECTANGLE!=0){
            WIDTH_hasToBeDividedByRectSize-=1;
        }
        int HEIGHT_hasToBeDividedByRectSize = GAME_HEIGHT-(int)(GAME_HEIGHT*0.3);
        while (HEIGHT_hasToBeDividedByRectSize>0&&HEIGHT_hasToBeDividedByRectSize%GAME_AREA_RECTANGLE!=0){
            HEIGHT_hasToBeDividedByRectSize-=1;
        }
        GAMEAREA_WIDTH = WIDTH_hasToBeDividedByRectSize;
        GAMEAREA_HEIGHT = HEIGHT_hasToBeDividedByRectSize;
        GAMEAREA_HEIGHT_canBeDividedBy = (int)(GAMEAREA_HEIGHT/GAME_AREA_RECTANGLE);
        GAMEAREA_WIDTH_canBeDividedBy = (int)(GAMEAREA_WIDTH/GAME_AREA_RECTANGLE);
        gameareaREACT = new Rectangle(0,(GAME_HEIGHT-GAMEAREA_HEIGHT)/2,GAMEAREA_WIDTH,GAMEAREA_HEIGHT);
        player1InfoBoard = new Rectangle(0,0,GAMEAREA_WIDTH,(GAME_HEIGHT-GAMEAREA_HEIGHT)/2);
        player2InfoBoard = new Rectangle(0,GAME_HEIGHT-((GAME_HEIGHT-GAMEAREA_HEIGHT)/2),GAMEAREA_WIDTH,(GAME_HEIGHT-GAMEAREA_HEIGHT)/2);
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
    public static final int GAME_AREA_RECTANGLE = 50;

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
