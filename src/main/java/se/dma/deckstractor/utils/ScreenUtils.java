package main.java.se.dma.deckstractor.utils;

import main.java.se.dma.deckstractor.Main;

import java.awt.*;

/**
 * Created by jquievreux on 30/04/15.
 */
public class ScreenUtils {
    public static void main(String[] args) {
        // Print the screen size
        log("Screen size : " + getScreenDimension());

    }

    /**
     *  Get the current screen size
     **/
    public static Dimension getScreenDimension(){
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    private static void log(String txt){
        System.out.println(txt);
    }

    public static Point getMousePosition(){
        try {
            return MouseInfo.getPointerInfo().getLocation();
        }catch (Exception e){
            return new Point(0,0);
        }
    }

    public static String getReadableMousePosition(){
        return String.format(Main.PROPERTIES.getProperty("notification.helper"),(int) ScreenUtils.getMousePosition().getX(),(int)ScreenUtils.getMousePosition().getY());
    }
}
