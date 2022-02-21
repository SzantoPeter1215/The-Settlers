package gui;

import model.GameLogic;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    /**
     * Az alap ablak létrehozásáért felel.
     */
    public GameFrame(GameLogic gameLogic) {
        setTitle("Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().add(new GameBoard(gameLogic), BorderLayout.CENTER);
        pack();

        setLocationRelativeTo(null);
    }
}
