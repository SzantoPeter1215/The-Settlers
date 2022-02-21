import gui.GameFrame;
import model.GameLogic;

import javax.swing.*;

public class Boot {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame(new GameLogic()).setVisible(true));
    }

}
