package weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entitys.Player;
import general.Zombiearena;
import render.graphicmath;
import resources.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class pistole extends AbstractWeapon{


    public pistole(){


        this.parent = Zombiearena.pLocal;
        AnimOffX = 95;
        AnimOffY = 120;
        try {
            loadAnimations();
        } catch (Exception e){
            e.printStackTrace();
        }
        curAnim = new ArrayList<>(animation_idle);
        frameCount = animation_idle_length();



        cdl.countDown();

    }

    @Override
    public int animation_idle_length() {
        return 20;
    }

    @Override
    public int animation_meleeattack_length() {
        return 15;
    }

    @Override
    public int animation_move_length() {
        return 20;
    }

    @Override
    public int animation_reload_length() {
        return 15;
    }

    @Override
    public int animation_shoot_length() {
        return 3;
    }

    @Override
    public double spread() {
        return 0;
    }

    @Override
    public double speed() {
        return 0;
    }

    @Override
    public double reloadTime() {
        return 0;
    }

    @Override
    public void loadAnimations() throws Exception {

        if(animation_idle_length() > 0)
            animation_idle = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/handgun/idle/survivor-idle_handgun_"
                            ,".png"
                            ,animation_idle_length()));
        if(animation_meleeattack_length() > 0)
            animation_meleeattack = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/handgun/meleeattack/survivor-meleeattack_handgun_"
                            ,".png"
                            ,animation_meleeattack_length()));
        if(animation_move_length() > 0)
            animation_move = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/handgun/move/survivor-move_handgun_"
                            ,".png"
                            ,animation_move_length()));
        if(animation_reload_length() > 0)
            animation_reload = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/handgun/reload/survivor-reload_handgun_"
                            ,".png"
                            ,animation_reload_length()));
        if(animation_shoot_length() > 0)
            animation_shoot = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/handgun/shoot/survivor-shoot_handgun_"
                            ,".png"
                            ,animation_shoot_length()));

    }

    @Override
    public void handleCurAnim() {

        int curState = parent.getCurState();

        if(curState != lastParentState) {

            curAnim = new ArrayList<>();
            lastParentState = curState;

            if (curState == Player.IDLE) {
                curAnim = new ArrayList<>(animation_idle);
                frameCount = animation_idle_length();
                frameAnim = 0;
                waitFactor = 0;
            }
            if (curState == Player.MOVE) {
                curAnim = new ArrayList<>(animation_move);
                frameCount = animation_move_length();
                frameAnim = 0;
                waitFactor = 0;
            }
            if (curState == Player.RELOAD) {
                curAnim = new ArrayList<>(animation_reload);
                frameCount = animation_reload_length();
                frameAnim = 0;
                waitFactor = 1;
            }
            if (curState == Player.MELEEATTACK) {
                curAnim = new ArrayList<>(animation_meleeattack);
                frameCount = animation_meleeattack_length();
                frameAnim = 0;
                waitFactor = 1;
            }
            if (curState == Player.SHOOT) {
                curAnim = new ArrayList<>(animation_shoot);
                frameCount = animation_shoot_length();
                frameAnim = 0;
                waitFactor = 1;
            }
        }

    }

    @Override
    public void toRender(SpriteBatch batch) {

        if(parent == null) return;
        synchronized (parent) {
            try {
                parent.cdl.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!(parent.getCurWeapon() == this))
                return;
        }



        synchronized (this) {
            if (curAnim.size() > 0) {

                int meleeAnimOffX = 104;
                int offX = lastParentState == Player.MELEEATTACK ? meleeAnimOffX : AnimOffX;
                int meleeAnimOffY = 125;
                int offY = lastParentState == Player.MELEEATTACK ? meleeAnimOffY : AnimOffY;


                batch.draw(curAnim.get(frameAnim)
                        ,parent.getLocation().x - offX
                        ,parent.getLocation().y - offY
                        ,offX
                        ,offY
                        ,curAnim.get(frameAnim).getWidth()
                        ,curAnim.get(frameAnim).getHeight()
                        ,1f
                        ,1f
                        ,parent.getYaw()-90f
                        , 0
                        , 0
                        , curAnim.get(frameAnim).getWidth()
                        , curAnim.get(frameAnim).getHeight()
                        , false
                        , false);

            }
        }




    }

    @Override
    public int getTickRate() {
        return 0;
    }

    @Override
    public void toThread(int tick) {

        if(parent == null)return;
        synchronized (parent) {
            try {
                parent.cdl.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(!(parent.getCurWeapon() == this))
            return;

        synchronized (this) {
            handleCurAnim();


            if(tick % tickrateAnim == 0) {

                frameAnim = (frameAnim + 1) % frameCount;

            }
        }

    }
}
