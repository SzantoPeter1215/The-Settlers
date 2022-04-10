import gui.GameFrame;
import model.GameLogic;

import javax.swing.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame(new GameLogic()).setVisible(true));
    }

}
