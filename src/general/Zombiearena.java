package general;

import Settings.Settings;
import engine.ThreadManager;
import entitys.Player;
import render.Window;

import java.awt.*;

public class Zombiearena {

    public static ThreadManager threadManager;
    public static Window window;
    public static Player pLocal;
    public static Settings settings;

    public static void main(String[] args){

        window = new Window();
        threadManager = new ThreadManager();
        settings = new Settings();


        try {
            pLocal = new Player(new DPoint(100, 100));
        } catch (Exception e){
            e.printStackTrace();
        }



    }

}
