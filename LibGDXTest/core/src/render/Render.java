package render;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import general.Zombiearena;
import objects.GameObject;
import world.Map;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.awt.RenderingHints.*;

public class Render  extends ApplicationAdapter {

    public static int cur_fps;
    private BufferStrategy buffStrat;
    private Window win;
    private static ArrayList<GameObject> listDrawables = new ArrayList<>();;
    private SpriteBatch batch;

    public Render(){


        //listDrawables = new ArrayList<>();


    }

    @Override
    public void create() {
        super.create();
        Zombiearena.load();
        batch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        updateAll(batch);
        batch.end();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        batch.dispose();super.dispose();
    }

    public void update(){

        updateAll(batch);
        Graphics g = null;
        do {

            do {
                try {
                    g = buffStrat.getDrawGraphics();
                    //updateAll(batch);

                } finally {
                    g.dispose();
                }
            } while (buffStrat.contentsRestored());
            buffStrat.show();
        } while (buffStrat.contentsLost());

    }

    private void updateAll(SpriteBatch batch){



        updateDrawables(batch);
        updateFPS(batch);

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

    private void updateDrawables(SpriteBatch batch){

        synchronized (listDrawables){

            for(GameObject obj : listDrawables.stream().filter(g -> g instanceof Map
            ).collect(Collectors.toCollection(ArrayList::new))){


                obj.toRender(batch);
            }

            for(GameObject obj : listDrawables.stream().filter(g -> !(g instanceof Map)
            ).collect(Collectors.toCollection(ArrayList::new))){


                obj.toRender(batch);

            }


        }

    }


    private void updateFPS(SpriteBatch batch) {


        BitmapFont font = new BitmapFont();


        font.draw(batch,"FPS: " + String.valueOf(cur_fps), 10, 20);


    }

    public static void addToDrawables(GameObject obj){
        synchronized (listDrawables){
            listDrawables.add(obj);
        }
    }
}
