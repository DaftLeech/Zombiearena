package general;

import engine.ThreadManager;
import entitys.Player;
import render.Window;

import java.awt.*;

public class Zombiearena {

    public static ThreadManager threadManager;
    public static Window window;
    public static Player pLocal;


    public static void main(String[] args){

        window = new Window();
        threadManager = new ThreadManager();


        try {
            pLocal = new Player(new Point(100, 100));
        } catch (Exception e){
            e.printStackTrace();
        }



    }

}
