package render;

import engine.Listener;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public static Render renderer;
    public static int WIDTH;
    public static int HEIGHT;
    private static final DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[] {
            new DisplayMode(640, 480, 32, 0),
            new DisplayMode(640, 480, 16, 0),
            new DisplayMode(640, 480, 8, 0)
    };

    public Window(){

        Toolkit defToolkit = Toolkit.getDefaultToolkit();
        WIDTH = defToolkit.getScreenSize().width;
        HEIGHT = defToolkit.getScreenSize().height;
        setBackground(Color.BLACK);





        //setSize(WIDTH,HEIGHT);

        Listener listener = new Listener();
        addMouseListener(listener);
        addMouseMotionListener(listener);

        addKeyListener(listener);

        setUndecorated(true);
        setIgnoreRepaint(true);
        validate();
        setVisible(true);

        renderer = new Render(this);

        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("sun.java2d.accthreshold", "0");

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        if (gs.isDisplayChangeSupported()) {
            System.out.println("DisplayChange is supported");
            chooseBestDisplayMode(gs);
        }


        if(gs.isFullScreenSupported()) {
            System.out.println("full-screen is supported");
            try{

                gs.setFullScreenWindow(this);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private static DisplayMode getBestDisplayMode(GraphicsDevice device) {
        for (DisplayMode BEST_DISPLAY_MODE : BEST_DISPLAY_MODES) {
            DisplayMode[] modes = device.getDisplayModes();
            for (DisplayMode mode : modes) {
                if (mode.getWidth() == BEST_DISPLAY_MODE.getWidth()
                        && mode.getHeight() == BEST_DISPLAY_MODE.getHeight()
                        && mode.getBitDepth() == BEST_DISPLAY_MODE.getBitDepth()
                        ) {
                    return BEST_DISPLAY_MODE;
                }
            }
        }
        return null;
    }

    private static void chooseBestDisplayMode(GraphicsDevice device) {
        DisplayMode best = getBestDisplayMode(device);
        if (best != null) {
            device.setDisplayMode(best);
        }
    }

}
