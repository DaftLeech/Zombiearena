package render;

import java.awt.*;

public class Camera {

    private final Rectangle glueArea;
    private static Camera instance;

    private Camera(){

        double screenPerc = 0.4;
        int x = (int)(Window.WIDTH * screenPerc);
        int y = (int)(Window.HEIGHT * screenPerc);

        glueArea = new Rectangle(x, y, Window.WIDTH - x * 2, Window.HEIGHT - y * 2);

    }

    public static Camera getInstance(){
        if(instance == null){
            instance = new Camera();

        }
        return instance;
    }

    public Rectangle getGlueArea(){
        return glueArea;
    }



}
