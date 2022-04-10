import gui.GameFrame;
import model.GameLogic;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame(new GameLogic()).setVisible(true));
    }

}
