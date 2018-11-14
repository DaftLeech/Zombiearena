package weapons;

import entitys.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import resources.ResourceManager;
import world.Map;

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
        curAnim = animation_idle;
        frameCount = animation_idle_length();
        waitFactor = 1;



        cdl.countDown();
    }

    @Override
    public void loadAnimations() throws Exception{

        ArrayList<Image> temp;
        if(animation_idle_length() > 0) {
            temp = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/rifle/idle/survivor-idle_rifle_"
                            , ".png"
                            , animation_idle_length()));
            animation_idle = new Animation(temp.toArray(new org.newdawn.slick.Image[temp.size()]),50,false);
        }
        if(animation_meleeattack_length() > 0) {
            temp = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/rifle/meleeattack/survivor-meleeattack_rifle_"
                            , ".png"
                            , animation_meleeattack_length()));
            animation_meleeattack = new Animation(temp.toArray(new org.newdawn.slick.Image[temp.size()]),50,false);
        }
        if(animation_move_length() > 0) {
            temp = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/rifle/move/survivor-move_rifle_"
                            , ".png"
                            , animation_move_length()));
            animation_move = new Animation(temp.toArray(new org.newdawn.slick.Image[temp.size()]),50,false);
        }
        if(animation_reload_length() > 0) {
            temp = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/rifle/reload/survivor-reload_rifle_"
                            , ".png"
                            , animation_reload_length()));
            animation_reload = new Animation(temp.toArray(new org.newdawn.slick.Image[temp.size()]),50,false);
        }
        if(animation_shoot_length() > 0) {
            temp = new ArrayList<>(ResourceManager
                    .loadImageCollection("Top_Down_Survivor/rifle/shoot/survivor-shoot_rifle_"
                            , ".png"
                            , animation_shoot_length()));
            animation_shoot = new Animation(temp.toArray(new org.newdawn.slick.Image[temp.size()]),50,false);
        }
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
    public void toRender(Graphics g) {


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


        g.translate(-(Map.location.getX()), -(Map.location.getY()));
        g.rotate(parent.getLocation().getX(),parent.getLocation().getY(),parent.getYaw());


        synchronized (this) {
            if (curAnim.getFrameCount() > 0) {

                int meleeAnimOffX = 114;
                int offX = lastParentState == Player.MELEEATTACK ? meleeAnimOffX : AnimOffX;
                int meleeAnimOffY = 202;
                int offY = lastParentState == Player.MELEEATTACK ? meleeAnimOffY : AnimOffY;

                g.drawImage(curAnim.getCurrentFrame()
                        , (int) parent.getLocation().getX() - offX
                        , (int) parent.getLocation().getY() - offY
                        );
            }
        }


        g.setColor(Color.green);

        for(Shape hitbox : hitboxes)
            g.draw(hitbox);

        g.rotate(parent.getLocation().getX(),parent.getLocation().getY(),-parent.getYaw());
        g.translate(Map.location.getX(), Map.location.getY());
        g.flush();
    }

    @Override
    public int getTickRate() {
        return 0;
    }

    @Override
    public void toThread(GameContainer container, int delta) {

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
            lastFrame += delta;
            curAnim.update(delta);

            if(curAnim.getFrameCount()*curAnim.getDuration(0) < lastFrame)
                waitFactor += 1;
        }

        updateHitbox();

    }

    public void updateHitbox(){
        if(hitboxes.size() == 0) {
            hitboxes.add(new Circle(parent.getLocation().getX(), parent.getLocation().getY(), 80));
        } else {
            for(Circle hitbox : hitboxes){
                hitbox.setLocation(parent.getLocation().getX()-hitbox.radius, parent.getLocation().getY()-hitbox.radius);
            }
        }
    }

    @Override
    public void handleCurAnim(){


        int curState = parent.getCurState();

        if(curState != lastParentState) {

            curAnim = new Animation();
            lastParentState = curState;
            lastFrame = 0;

            if (curState == Player.IDLE) {
                curAnim = animation_idle;
                frameCount = animation_idle_length();
                frameAnim = 0;
                waitFactor = 0;
                breakAnimation = true;
            } else
            if (curState == Player.MOVE) {
                curAnim = animation_move;
                frameCount = animation_move_length();
                frameAnim = 0;
                waitFactor = 0;
                breakAnimation = true;
            } else
            if (curState == Player.RELOAD) {
                curAnim = animation_reload;
                frameCount = animation_reload_length();
                frameAnim = 0;
                waitFactor = 0;
                breakAnimation = false;
            } else
            if (curState == Player.MELEEATTACK) {
                curAnim = animation_meleeattack;
                frameCount = animation_meleeattack_length();
                frameAnim = 0;
                waitFactor = 0;
                breakAnimation = false;
            } else
            if (curState == Player.SHOOT) {
                curAnim = animation_shoot;
                frameCount = animation_shoot_length();
                frameAnim = 0;
                waitFactor = 0;
                breakAnimation = false;
            }
        }

    }
}
