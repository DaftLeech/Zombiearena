package entitys;

import engine.Listener;
import general.DPoint;
import render.Camera;
import settings.Settings;
import objects.Entity;
import render.Window;
import render.graphicmath;
import resources.ResourceManager;
import weapons.AbstractWeapon;
import weapons.pistole;
import weapons.rifle;
import world.Map;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity{


    private DPoint location = new DPoint(0,0);
    private double yaw;
    private int health;
    private static BufferedImage sprite;
    private ArrayList<Image> feetAnim_run;
    private ArrayList<Image> curAnim = new ArrayList<>();
    private final int feetAnim_run_imageCount = 20;
    private boolean isRunning = false;
    private int frameAnim = 0;
    private int frameCount = 0;
    private final float speed = 3F;

    public static final int IDLE = 0;
    public static final int MELEEATTACK = 1;
    public static final int MOVE = 2;
    public static final int RELOAD = 3;
    public static final int SHOOT = 4;
    private int curState = 0;


    private rifle rifleWeapon;
    private pistole pistoleWeapon;
    private AbstractWeapon curWeapon;





    public Player(DPoint location) throws Exception {
        super();





            this.location = location;
            this.yaw = 0.0;
            this.health = 3;

            if (sprite == null) {

                sprite = ResourceManager.loadImage("Top_Down_Survivor/rifle/idle/survivor-idle_rifle_0.png");
                feetAnim_run = new ArrayList<>(ResourceManager.loadImageCollection("Top_Down_Survivor/feet/run/survivor-run_", ".png", feetAnim_run_imageCount));
            }





            curWeapon = null;


            cdl.countDown();

    }

    @Override
    public void toRender(Graphics2D g) {



        AffineTransform at = graphicmath.createRotation(this.yaw,this.location);
        AffineTransform backup = g.getTransform();

        graphicmath.drawShadow(g,this.location);

        g.setTransform(at);

        int animOffY = 70;//
        int animOffX = 68;
        if(curAnim.size() > 0)
            g.drawImage(curAnim.get(frameAnim),(int)getLocation().x - animOffX,(int)getLocation().y - animOffY, null);



        g.setTransform(backup);

        g.setStroke(new BasicStroke(2));
        g.setPaint(Color.RED);

        g.drawLine((int)getLocation().x-5,(int)getLocation().y,(int)getLocation().x-5,(int)getLocation().y);
        g.setPaint(Color.GREEN);

        g.drawLine((int)getLocation().x,(int)getLocation().y,(int)getLocation().x,(int)getLocation().y);

        g.draw(Camera.getInstance().getGlueArea());

    }

    @Override
    public void toThread(int tick) {


        handleKeyInput();
        setYaw(Listener.getMouseCursor());
        handleCurAnim();

        int tickrateAnim = 10;
        if(tick % tickrateAnim == 0) {
            if (isRunning) {
                frameAnim = (frameAnim + 1) % frameCount;
            } else {
                frameAnim = 0;
            }
        }





    }

    @Override
    public int getTickRate() {
        return 0;
    }

    public DPoint getLocation(){
        return this.location;
    }

    private void setLocation(DPoint location){
        this.location = location;
    }

    private void setLocation(double x, double y) { this.location = new DPoint(x,y);}

    public void setHealth(int health){
        this.health = health;
    }

    public void removeHealth(int value){
        this.health -= value;
    }

    public void addHealth(int value){
        this.health += value;
    }

    public int getHealth(){
        return this.health;
    }

    public double getYaw(){
        return this.yaw;
    }

    public void setYaw(int value){
        this.yaw = value;
    }

    private void setYaw(Point pLookAt){
        this.yaw = Math.toDegrees(Math.atan2(pLookAt.x - this.location.x, pLookAt.y- this.location.y));
    }

    private void setIsShooting(Boolean value){
        Boolean isShooting = value;
    }

    private void handleKeyInput(){

        synchronized (Listener.getKeyCodes()){

            int moveMod = (int) ((Listener.getKeyCodes().size() == 1 ? 2 : Math.sqrt(4)) );

            synchronized (this) {

                if(curWeapon == null) return;
                synchronized (curWeapon) {
                    if (curWeapon.frameAnim + 1 >= curWeapon.frameCount * curWeapon.waitFactor) {
                        curState = IDLE;
                        this.isRunning = false;
                        if (Listener.getKeyCodes().stream().anyMatch(k -> Listener.isMovementKeyCode(k))) {
                            this.isRunning = true;
                            curState = MOVE;
                        }
                        if (Listener.getKeyCodes().stream().anyMatch(k -> k == KeyEvent.VK_R)) {

                            curState = RELOAD;
                        }
                        if (Listener.getKeyCodes().stream().anyMatch(k -> k == KeyEvent.VK_V)) {

                            curState = MELEEATTACK;
                        }
                        if (Listener.isMousePressed()) {
                            setIsShooting(true);
                            curState = SHOOT;
                        }
                    }


                    for (int KeyCode : Listener.getKeyCodes()) {

                        if (Settings.boolByName("MovementType")) {

                            if (KeyCode == KeyEvent.VK_W && getLocation().y > 0) {
                                setLocation(getLocation().x, getLocation().y - moveMod);
                            }

                            if (KeyCode == KeyEvent.VK_S && getLocation().y < Window.HEIGHT) {
                                setLocation(getLocation().x, getLocation().y + moveMod);
                            }

                            if (KeyCode == KeyEvent.VK_A && getLocation().x > 0) {
                                setLocation(getLocation().x - moveMod, getLocation().y);
                            }

                            if (KeyCode == KeyEvent.VK_D && getLocation().x < Window.WIDTH) {
                                setLocation(getLocation().x + moveMod, getLocation().y);
                            }
                        } else {

                            synchronized (Map.location) {
                                if (KeyCode == KeyEvent.VK_W) {
                                    DPoint dir = new DPoint(Math.sin(Math.toRadians(yaw)), Math.cos(Math.toRadians(yaw)));
                                    handleMoveDir(dir);
                                }
                                if (KeyCode == KeyEvent.VK_S) {
                                    DPoint dir = new DPoint(-Math.sin(Math.toRadians(yaw)), -Math.cos(Math.toRadians(yaw)));
                                    handleMoveDir(dir);
                                }
                                if (KeyCode == KeyEvent.VK_A) {
                                    DPoint dir = new DPoint(Math.sin(Math.toRadians(yaw + 90)), Math.cos(Math.toRadians(yaw + 90)));
                                    handleMoveDir(dir);
                                }
                                if (KeyCode == KeyEvent.VK_D) {
                                    DPoint dir = new DPoint(Math.sin(Math.toRadians(yaw - 90)), Math.cos(Math.toRadians(yaw - 90)));
                                    handleMoveDir(dir);
                                }
                            }
                        }

                        if (KeyCode == Settings.keyByName("RifleKey")) {
                            curWeapon = rifleWeapon;
                        }

                        if (KeyCode == Settings.keyByName("PistoleKey")) {
                            curWeapon = pistoleWeapon;
                        }

                    }
                }

            }
        }

    }

    private void handleCurAnim(){

        curAnim = new ArrayList<>();

        if(isRunning){
            curAnim = new ArrayList<>(feetAnim_run);
            frameCount = feetAnim_run_imageCount;
        }

    }


    public void handleMouseInput(){


        setYaw(Listener.getMouseCursor());


    }

    public int getCurState(){
        return curState;
    }

    public AbstractWeapon getCurWeapon(){
        return curWeapon;
    }

    public void setCurWeapon(AbstractWeapon weap){
        this.curWeapon = weap;
    }

    public void setRifle(rifle rifle){
        this.rifleWeapon = rifle;
    }

    public void setPistole(pistole pistole){
        this.pistoleWeapon = pistole;
    }

    public void handleMoveDir(DPoint dir){
        DPoint newLoc = new DPoint(location.x + dir.x * speed, location.y + dir.y * speed);
        if(Map.contains(newLoc)) {
            if (Camera.getInstance().getGlueArea().contains(newLoc.toPoint())) {
                setLocation(newLoc);
            } else {
                DPoint newMapLoc = new DPoint(Map.location.x + dir.x * speed, Map.location.y + dir.y * speed);
                Map.location = newMapLoc;
            }
        }
    }

}
