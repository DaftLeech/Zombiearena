package weapons;

import entitys.Player;
import objects.Entity;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.GeomUtil;

public abstract class AbstractWeapon extends Entity {

    Player parent = null;
    Animation animation_idle;
    Animation animation_meleeattack;
    Animation animation_move;
    Animation animation_reload;
    Animation animation_shoot;
    Animation curAnim = new Animation();
    public abstract int animation_idle_length();
    public abstract int animation_meleeattack_length();
    public abstract int animation_move_length();
    public abstract int animation_reload_length();
    public abstract int animation_shoot_length();
    public abstract double spread();
    public abstract double speed();
    public abstract double reloadTime();
    public abstract void updateHitbox();

    public abstract void loadAnimations() throws Exception;
    public abstract void handleCurAnim();
    public void setParent(Player parent){
        this.parent = parent;
    }

    final int tickrateAnim = 10;
    public int frameAnim = 0;
    public int frameCount = 0;
    public float lastFrame;

    public int waitFactor = 1;
    public boolean breakAnimation = false;
    int lastParentState = 0;

    //
    int AnimOffX = 0;
    int AnimOffY = 0;


}
