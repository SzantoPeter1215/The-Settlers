package gui;

import javax.swing.*;

public class PopUp {
    public PopUp(String text){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, text);
    }
}
