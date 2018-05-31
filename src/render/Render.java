package render;

import general.Zombiearena;
import objects.GameObject;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Render {

    public static int cur_fps;
    private BufferStrategy buffStrat;
    private Window win;
    private static ArrayList<GameObject> listDrawables;

    public Render(Window win){

        this.win = win;
        createBuffer();
        listDrawables = new ArrayList<>();

    }

    public void update(){

        Graphics g = null;
        do {

            do {
                try {
                    g = buffStrat.getDrawGraphics();
                    updateAll((Graphics2D) g);

                } finally {
                    g.dispose();
                }
            } while (buffStrat.contentsRestored());
            buffStrat.show();
        } while (buffStrat.contentsLost());

    }

    private void updateAll(Graphics2D g){

        clearScreen(g);
        updateDrawables(g);
        updateFPS(g);

    }


    private void createBuffer(){
        win.createBufferStrategy(2);
        this.buffStrat = win.getBufferStrategy();

    }


    private void clearScreen(Graphics2D g){

        g.clearRect(0,0,Window.WIDTH,Window.HEIGHT);

    }

    private void updateDrawables(Graphics2D g){

        synchronized (listDrawables){

            for(GameObject obj : listDrawables){

                obj.toRender(g);

            }


        }

    }


    private void updateFPS(Graphics2D g) {

        g.clearRect(0, 0, 100, 30);
        g.setPaint(Color.WHITE);
        g.fillRect(0, 0, 100, 30);

        g.setPaint(Color.BLACK);
        g.drawString("FPS: " + String.valueOf(cur_fps), 10, 20);


    }

    public static void addToDrawables(GameObject obj){
        listDrawables.add(obj);
    }
}
