package render;

import objects.GameObject;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import static java.awt.RenderingHints.*;

public class Render {

    public static int cur_fps;
    private BufferStrategy buffStrat;
    private final Window win;
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

        setRenderHints(g);
        clearScreen(g);
        updateDrawables(g);
        updateFPS(g);

    }


    private void createBuffer(){
        win.createBufferStrategy(2);
        this.buffStrat = win.getBufferStrategy();

    }

    private void setRenderHints(Graphics2D g){

        g.setRenderingHint(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g.setRenderingHint(KEY_COLOR_RENDERING, VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(KEY_DITHERING, VALUE_DITHER_ENABLE);
        g.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY);
        g.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_PURE);

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
        synchronized (listDrawables){
            listDrawables.add(obj);
        }
    }
}
