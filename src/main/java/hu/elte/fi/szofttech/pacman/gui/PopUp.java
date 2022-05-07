package main.java.hu.elte.fi.szofttech.pacman.gui;

import javax.swing.*;

public class PopUp {
    public PopUp(String text){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, text);
    }
}
