package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    public void loadTower1_OnTheField(Graphics2D graphics2D, int x, int y, boolean disabled){
        String path;

        if(!disabled) {
            path = "images/towerExample.png";
        }
        else{
            path = "images/disabled_towerExample.png";
        }
        BufferedImage image = null;
        try {
            image = resources.ImageLoader.readImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics2D.drawImage(image , x, y, GameUIConstants.GAME_AREA_RECTANGLE, GameUIConstants.GAME_AREA_RECTANGLE, null);
    }
    public void loadTower2_OnTheField(Graphics2D graphics2D,int x,int y,boolean disabled){
        String path;

        if(!disabled){
            path = "images/tower2_Example.png" ;
        }
        else{
            path = "images/disabled_tower2_Example.png";
        }
        BufferedImage image = null;
        try {
            image = resources.ImageLoader.readImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics2D.drawImage(image , x, y, GameUIConstants.GAME_AREA_RECTANGLE, GameUIConstants.GAME_AREA_RECTANGLE, null);
    }
    public void loadCastleOnTheField(Graphics2D graphics2D,int x,int y,boolean disabled){
        String path;
        if(!disabled){
            path = "images/castle.png" ;
        }
        else{
            path = "images/castle.png";
        }
        BufferedImage image = null;
        try {
            image = resources.ImageLoader.readImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics2D.drawImage(image , x, y, GameUIConstants.GAME_AREA_RECTANGLE, GameUIConstants.GAME_AREA_RECTANGLE, null);
    }
}
