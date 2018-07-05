package general;

import settings.Settings;
import engine.ThreadManager;
import entitys.Player;
import render.Window;
import weapons.pistole;
import weapons.rifle;
import world.Map;

import java.awt.*;

public class Zombiearena {

    private static ThreadManager threadManager;
    private static Window window;
    public static Player pLocal;
    private static Settings settings;
    private static weapons.rifle rifle;
    private static weapons.pistole pistole;
    private static Map map;

    public static void main(String[] args){

        window = new Window();
        threadManager = new ThreadManager();
        settings = new Settings();


        try {
            map = new Map();
            int roomCount = map.getDngn().getFinalRooms().size();
            int roomID = (int)(roomCount *Math.random());
            Rectangle room =  map.getDngn().getFinalRooms().get(roomID);
            Map.location = new DPoint(room.getCenterX()- Window.WIDTH/2,room.getCenterY() - Window.HEIGHT/2);
            pLocal = new Player(new DPoint(Window.WIDTH/2, Window.HEIGHT/2));
            rifle = new rifle();
            pistole = new pistole();
            pLocal.setRifle(rifle);
            pLocal.setPistole(pistole);
            pLocal.setCurWeapon(rifle);
            pistole.setParent(pLocal);
            rifle.setParent(pLocal);

        } catch (Exception e){
            e.printStackTrace();
        }



    }

}
