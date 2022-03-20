package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    public void loadImage(Graphics2D graphics2D,int x,int y,String path){
        BufferedImage image = null;
        try {
            image = resources.ImageLoader.readImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics2D.drawImage(image , x, y, GameUIConstants.GAME_AREA_RECTANGLE, GameUIConstants.GAME_AREA_RECTANGLE, null);
    }
}
