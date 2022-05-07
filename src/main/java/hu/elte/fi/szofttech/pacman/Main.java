package main.java.hu.elte.fi.szofttech.pacman;

import main.java.hu.elte.fi.szofttech.pacman.gui.GameFrame;
import main.java.hu.elte.fi.szofttech.pacman.model.GameLogic;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame(new GameLogic()).setVisible(true));
    }

}

//TODO: opentest4j