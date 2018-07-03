package engine;

import entitys.Player;
import render.Render;
import render.Window;
import objects.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class ThreadManager {

    public static ArrayList<GameObject> objects;
    private Thread paintThread;
    private Thread tickThread;
    private final int maxTick = 1000;
    private int tick = 0;


    public ThreadManager(){

        objects = new ArrayList<GameObject>();


        startPaintThread();
        startTickThread();

    }



    public void startPaintThread(){

        if(paintThread == null || !paintThread.isAlive()){
            paintThread = new Thread(new Runnable(){
                @Override
                public void run() {
                    long lastUpdateTime = System.currentTimeMillis();
                    long targetFrameTime = 1000/144;
                    boolean updateFPS = true;
                    int frameCount = 1;
                    while(true){
                        long defaultTime = System.currentTimeMillis();
                        Window.renderer.update();
                        frameCount++;



                        if(defaultTime-lastUpdateTime > 1000){

                            lastUpdateTime = System.currentTimeMillis();
                            updateFPS = true;

                        }
                        long realframeTime = System.currentTimeMillis()-defaultTime;

                        if (targetFrameTime > realframeTime){

                            try {
                                Thread.sleep(targetFrameTime - realframeTime);
                            } catch (InterruptedException e){
                            }
                        }

                        if(updateFPS) {
                            Render.cur_fps = (int) (1000/(System.currentTimeMillis()-defaultTime));
                            Render.cur_fps = frameCount;
                            frameCount = 1;
                            updateFPS = false;
                        }

                    }
                }
            });

            paintThread.start();
        }
    }

    public static void addToThreadList(GameObject obj){
        objects.add(obj);
    }

    public void startTickThread() {

        if (tickThread == null || !tickThread.isAlive()) {
            tickThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    while(true) {
                        for (GameObject obj : objects) {

                            try {
                                obj.cdl.await();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (obj.getTickRate() != -2) {
                                obj.toThread(tick);

                            }


                        }

                        tick = tick + 1 < 1000 ? tick + 1 : 0;

                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


            tickThread.start();
        }
    }


}
