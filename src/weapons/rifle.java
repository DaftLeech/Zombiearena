package weapons;

import entitys.Player;
import render.graphicmath;
import resources.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class rifle extends AbstractWeapon {


    public rifle(){
        super();


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
    public void loadAnimations() throws Exception{

        if(animation_idle_length() > 0)
            animation_idle = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/rifle/idle/survivor-idle_rifle_"
                            ,".png"
                            ,animation_idle_length()));
        if(animation_meleeattack_length() > 0)
            animation_meleeattack = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/rifle/meleeattack/survivor-meleeattack_rifle_"
                            ,".png"
                            ,animation_meleeattack_length()));
        if(animation_move_length() > 0)
            animation_move = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/rifle/move/survivor-move_rifle_"
                            ,".png"
                            ,animation_move_length()));
        if(animation_reload_length() > 0)
            animation_reload = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/rifle/reload/survivor-reload_rifle_"
                            ,".png"
                            ,animation_reload_length()));
        if(animation_shoot_length() > 0)
            animation_shoot = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/rifle/shoot/survivor-shoot_rifle_"
                            ,".png"
                            ,animation_shoot_length()));
    }

    @Override
    public int animation_idle_length() {
        return 20;
    }

    @Override
    public int animation_meleeattack_length() {
        return 14;
    }

    @Override
    public int animation_move_length() {
        return 20;
    }

    @Override
    public int animation_reload_length() {
        return 20;
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
    public void toRender(Graphics2D g) {

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

        AffineTransform at = graphicmath.createRotation(parent.getYaw(),parent.getLocation());
        AffineTransform backup = g.getTransform();
        g.setTransform(at);

        synchronized (this) {
            if (curAnim.size() > 0) {

                int meleeAnimOffX = 114;
                int offX = lastParentState == Player.MELEEATTACK ? meleeAnimOffX : AnimOffX;
                int meleeAnimOffY = 202;
                int offY = lastParentState == Player.MELEEATTACK ? meleeAnimOffY : AnimOffY;

                g.drawImage(curAnim.get(frameAnim)
                        , (int) parent.getLocation().x - offX
                        , (int) parent.getLocation().y - offY
                        , null);
            }
        }



        g.setTransform(backup);
    }

    @Override
    public int getTickRate() {
        return 0;
    }

    @Override
    public void toThread(int tick) {

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
            handleCurAnim();


        if(tick % tickrateAnim == 0) {

            frameAnim = (frameAnim + 1) % frameCount;

        }
        }

    }

    @Override
    public void handleCurAnim(){


        int curState = parent.getCurState();

        if(curState != lastParentState) {

            curAnim = new ArrayList<>();
            lastParentState = curState;

            if (curState == Player.IDLE) {
                curAnim = new ArrayList<>(animation_idle);
                frameCount = animation_idle_length();
                frameAnim = 0;
                waitFactor = 0;
            } else
            if (curState == Player.MOVE) {
                curAnim = new ArrayList<>(animation_move);
                frameCount = animation_move_length();
                frameAnim = 0;
                waitFactor = 0;
            } else
            if (curState == Player.RELOAD) {
                curAnim = new ArrayList<>(animation_reload);
                frameCount = animation_reload_length();
                frameAnim = 0;
                waitFactor = 1;
            } else
            if (curState == Player.MELEEATTACK) {
                curAnim = new ArrayList<>(animation_meleeattack);
                frameCount = animation_meleeattack_length();
                frameAnim = 0;
                waitFactor = 1;
            } else
            if (curState == Player.SHOOT) {
                curAnim = new ArrayList<>(animation_shoot);
                frameCount = animation_shoot_length();
                frameAnim = 0;
                waitFactor = 1;
            }
        }

    }
}
