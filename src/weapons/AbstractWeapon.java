package weapons;

import engine.ThreadManager;
import entitys.Player;
import objects.Entity;
import objects.GameObject;
import render.Render;

import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractWeapon extends Entity {

    public Player parent;
    public ArrayList<Image> animation_idle;
    public ArrayList<Image> animation_meleeattack;
    public ArrayList<Image> animation_move;
    public ArrayList<Image> animation_reload;
    public ArrayList<Image> animation_shoot;
    public ArrayList<Image> curAnim = new ArrayList<>();
    public abstract int animation_idle_length();
    public abstract int animation_meleeattack_length();
    public abstract int animation_move_length();
    public abstract int animation_reload_length();
    public abstract int animation_shoot_length();
    public abstract void loadAnimations() throws Exception;
    public abstract void handleCurAnim();

    public int tickrateAnim = 10;
    public int frameAnim = 0;
    public int frameCount = 0;
    public int waitFactor = 0;

    //
    public int AnimOffX = 0;
    public int AnimOffY = 0;


}
