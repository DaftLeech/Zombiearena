package general;

import engine.ThreadManager;
import entitys.Player;
import entitys.Zombie;
import objects.GameObject;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import render.Render;
import render.Window;
import settings.Settings;
import weapons.pistole;
import weapons.rifle;
import world.Map;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class Zombiearena extends BasicGame{

    public static Player pLocal;
    public static Window window;

    public Zombiearena(){
        super("Zombiearena");
    }

    public static void main(String[] args){
        window = new Window();

        try {

            AppGameContainer app = new AppGameContainer(new Zombiearena());
            app.setDisplayMode(Window.WIDTH, Window.HEIGHT, true);
            app.start();



        } catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {


        ThreadManager threadManager = new ThreadManager();
        Settings settings = new Settings();

        Map map = new Map();
        int roomCount = map.getDngn().getFinalRooms().size();
        int roomID = (int)(roomCount *Math.random());
        Rectangle room =  map.getDngn().getFinalRooms().get(roomID);
        Map.location = new Point(room.getCenterX()- Window.WIDTH/2,room.getCenterY() - Window.HEIGHT/2);
        pLocal = new Player(new Point(room.getCenterX(), room.getCenterY()));
        for(int i = 0; i < 100; i++) {
            roomID = (int)(roomCount *Math.random());
            room =  map.getDngn().getFinalRooms().get(roomID);
            new Zombie(new Point(room.getCenterX(),room.getCenterY()));
        }
        weapons.rifle rifle = new rifle();
        weapons.pistole pistole = new pistole();
        pLocal.setRifle(rifle);
        pLocal.setPistole(pistole);
        pLocal.setCurWeapon(pistole);
        pistole.setParent(pLocal);
        rifle.setParent(pLocal);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        for(GameObject obj: ThreadManager.objects)
            obj.toThread(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

        for(GameObject obj : Render.listDrawables.stream().sorted((obj1,obj2) -> Integer.compare(obj1.LayerToInt(),obj2.LayerToInt())).collect(Collectors.toCollection(ArrayList::new))) {
            obj.toRender(graphics);

        }
    }
}
