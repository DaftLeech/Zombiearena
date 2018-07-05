package general;

import settings.Settings;
import engine.ThreadManager;
import entitys.Player;
import render.Window;
import weapons.pistole;
import weapons.rifle;
import world.Map;

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
            pLocal = new Player(new DPoint(100, 100));
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
