package render;

import engine.Listener;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public static Render renderer;
    public static int WIDTH;
    public static int HEIGHT;
    private static DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[] {
            new DisplayMode(640, 480, 32, 0),
            new DisplayMode(640, 480, 16, 0),
            new DisplayMode(640, 480, 8, 0)
    };

    public Window(){

        Toolkit defToolkit = Toolkit.getDefaultToolkit();
        WIDTH = defToolkit.getScreenSize().width;
        HEIGHT = defToolkit.getScreenSize().height;





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
        for (int x = 0; x < BEST_DISPLAY_MODES.length; x++) {
            DisplayMode[] modes = device.getDisplayModes();
            for (int i = 0; i < modes.length; i++) {
                if (modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth()
                        && modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight()
                        && modes[i].getBitDepth() == BEST_DISPLAY_MODES[x].getBitDepth()
                        ) {
                    return BEST_DISPLAY_MODES[x];
                }
            }
        }
        return null;
    }

    public static void chooseBestDisplayMode(GraphicsDevice device) {
        DisplayMode best = getBestDisplayMode(device);
        if (best != null) {
            device.setDisplayMode(best);
        }
    }

}
