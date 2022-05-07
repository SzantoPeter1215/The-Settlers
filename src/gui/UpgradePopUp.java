package gui;

import javax.swing.*;

public class UpgradePopUp {
    public int pressed;
    public UpgradePopUp(String text){
        JFrame jFrame = new JFrame();
        Object[] options = {"Upgrade",
                "Demolish",
                "Cancel"};
        pressed = JOptionPane.showOptionDialog(jFrame,
                "Upgrade or demolish your tower!",
                text,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
    }
}
