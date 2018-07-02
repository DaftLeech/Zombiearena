package entitys;

import engine.Listener;
import general.DPoint;
import Settings.Settings;
import objects.Entity;
import render.Window;
import render.graphicmath;
import resources.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    private DPoint location;
    private double yaw;
    private int health;
    private static BufferedImage sprite;
    private static Boolean isShooting = false;
    private float speed = 2;



    public Player(DPoint location) throws Exception {
        super();
        this.location = location;
        this.yaw = 0.0;
        this.health = 3;

        if(sprite == null){

            sprite = ResourceManager.loadImage("survivor.png");

        }
    }

    @Override
    public void toRender(Graphics2D g) {

        AffineTransform at = graphicmath.createRotation(this.yaw,this.location);
        AffineTransform backup = g.getTransform();

        graphicmath.drawShadow(g,this.location);

        g.setTransform(at);
        g.drawImage(sprite,(int)getLocation().x-15,(int)getLocation().y-15,30,30, null);


        g.setTransform(backup);

    }

    @Override
    public void toThread() {


        handleKeyInput();
        setYaw(Listener.getMouseCursor());


    }

    @Override
    public int getTickRate() {
        return 0;
    }

    public DPoint getLocation(){
        return this.location;
    }

    public void setLocation(DPoint location){
        this.location = location;
    }

    public void setLocation(double x, double y) { this.location = new DPoint(x,y);}

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

    public void setYaw(Point pLookAt){
        this.yaw = Math.toDegrees(Math.atan2(pLookAt.x - this.location.x, pLookAt.y- this.location.y));
    }

    public void setIsShooting(Boolean value){
        isShooting = value;
    }

    public void handleKeyInput(){

        synchronized (Listener.getKeyCodes()){

            int moveMod = (int) ((Listener.getKeyCodes().size() == 1 ? 2 : 1) );



            for(int KeyCode : Listener.getKeyCodes()){

                if(Settings.boolByName("MovementType")) {

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
                }else {
                    if (KeyCode == KeyEvent.VK_W) {
                        DPoint dir = new DPoint(Math.sin(Math.toRadians(yaw)), Math.cos(Math.toRadians(yaw)));
                        DPoint newLoc = new DPoint(location.x + dir.x * speed, location.y + dir.y * speed);
                        setLocation(newLoc);
                    }
                    if (KeyCode == KeyEvent.VK_S) {
                        DPoint dir = new DPoint(-Math.sin(Math.toRadians(yaw)), -Math.cos(Math.toRadians(yaw)));
                        DPoint newLoc = new DPoint(location.x + dir.x * speed, location.y + dir.y * speed);
                        setLocation(newLoc);
                    }
                    if (KeyCode == KeyEvent.VK_A) {
                        DPoint dir = new DPoint(Math.sin(Math.toRadians(yaw + 90)), Math.cos(Math.toRadians(yaw + 90)));
                        DPoint newLoc = new DPoint(location.x + dir.x * speed, location.y + dir.y * speed);
                        setLocation(newLoc);
                    }
                    if (KeyCode == KeyEvent.VK_D) {
                        DPoint dir = new DPoint(Math.sin(Math.toRadians(yaw - 90)), Math.cos(Math.toRadians(yaw - 90)));
                        DPoint newLoc = new DPoint(location.x + dir.x * speed, location.y + dir.y * speed);
                        setLocation(newLoc);
                    }
                }


            }
        }

    }


    public void handleMouseInput(){


        setYaw(Listener.getMouseCursor());


    }


}
