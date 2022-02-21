package gui;

import model.*;
import model.info.InfoBoard;
import resources.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameBoard extends JPanel {

    private final GameLogic gameLogic;

    private String name;


    public GameBoard(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        setPreferredSize(new Dimension(GameUIConstants.GAME_WIDTH, GameUIConstants.GAME_HEIGHT));
        setBackground(GameUIConstants.BACKGROUND_COLOR);
        setFocusable(true);

        startNewGame();



        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                InfoBoard infoBoard = gameLogic.getInfoBoard();
                if (infoBoard.isGameOver()) {
                    startNewGame();
                    repaint();
                }
            }
        });

        //TODO: these listeners can help start the game. If not useful delete.
        KeyListener gameKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                final InfoBoard infoBoard = gameLogic.getInfoBoard();
                if (infoBoard.isGameOver()) {
                    return;
                }

                switch (e.getKeyCode()) {
                }
                repaint();
            }
        };
        addKeyListener(gameKeyListener);
    }


    private void createInputDialog() {
        JTextField nameField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Name: "));

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter your game settings:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            name = nameField.getText().trim();
        }
    }

    private void startNewGame() {
        createInputDialog();

        gameLogic.newGame(GameConstants.NORMAL_ROW_COUNT, GameConstants.NORMAL_COL_COUNT, name);
    }



    private void drawStartScreen(Graphics2D graphics2D) {

        //TODO: remove all this. just example
        graphics2D.setFont(GameUIConstants.MAIN_FONT);

        graphics2D.setColor(GameUIConstants.TITLE_BG_COLOR);
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

    private void drawUI(Graphics2D graphics2D) {
        //TODO: rethink, just example
        graphics2D.setColor(GameUIConstants.GRID_COLOR);
        graphics2D.fill(GameUIConstants.GRID_RECTANGLE);

        graphics2D.setStroke(GameUIConstants.LARGE_STROKE);
        graphics2D.setColor(GameUIConstants.GRID_BORDER_COLOR);
        graphics2D.draw(GameUIConstants.GRID_RECTANGLE);

        drawInfoBoard(graphics2D);
    }


    private void drawInfoBoard(Graphics2D graphics2D) {
        //TODO: rethink infoboard structure. Just example.

        InfoBoard infoBoard = gameLogic.getInfoBoard();
        int x = GameUIConstants.INFO_POS_X;
        int y = GameUIConstants.INFO_POS_Y;
        graphics2D.setColor(GameUIConstants.TEXT_COLOR);
        graphics2D.setFont(GameUIConstants.SMALL_FONT);


        graphics2D.drawString("INFO BOARD", x, y);
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

        drawUI(graphics2D);

        InfoBoard infoBoard = gameLogic.getInfoBoard();
        if (infoBoard.isGameOver()) {

                drawStartScreen(graphics2D);

        } else {
            drawAll(graphics2D);
        }
        Toolkit.getDefaultToolkit().sync();
    }


    private final Action oneGameCycleAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InfoBoard infoBoard = gameLogic.getInfoBoard();
            if (!infoBoard.isGameOver()) {
                //TODO: use gamelogic to get the current state.
            }
            repaint();

        }
    };

}
