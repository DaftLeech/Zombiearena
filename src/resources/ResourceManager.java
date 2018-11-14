package resources;

import org.newdawn.slick.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;

public class ResourceManager {

    private static final GraphicsConfiguration gsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

    public static BufferedImage loadIcon(String path) {
        return toCompatibleImage((BufferedImage) new ImageIcon((new File("src/resources/"+path)).getPath()).getImage(), gsConfig);
    }

    public static BufferedImage loadImage(String path) throws Exception{
        return toCompatibleImage(ImageIO.read(new File("src/resources/"+path)), gsConfig);
    }

    private static BufferedImage toCompatibleImage(BufferedImage image, GraphicsConfiguration gfx_config)
    {


        if (image.getColorModel().equals(gfx_config.getColorModel()))
            return image;

        // not optimized
        BufferedImage new_image = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());


        Graphics2D g2d = (Graphics2D) new_image.getGraphics();


        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return new_image;
    }


    public static BufferedImage makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // Alpha bits are set to opaque
            final int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ( ( rgb | 0xFF000000 ) == markerRGB ) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                }
                else {
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        BufferedImage dest = new BufferedImage(
                im.getWidth(), im.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(Toolkit.getDefaultToolkit().createImage(ip), 0, 0, null);
        g2.dispose();
        return dest;
    }

    public static ArrayList<BufferedImage> loadImageCollection(String path, String extension, int imageCount, boolean x) throws Exception{
        ArrayList<BufferedImage> ret = new ArrayList<>();

        for(int i = 0; i < imageCount; i++){

            ret.add(loadImage(path+String.valueOf(i)+extension));

        }

        return ret;
    }

    public static ArrayList<Image> loadImageCollection(String path, String extension, int imageCount) throws Exception{
        ArrayList<Image> ret = new ArrayList<>();

        for(int i = 0; i < imageCount; i++){

            ret.add(new Image("src/resources/"+path+String.valueOf(i)+extension));

        }

        return ret;
    }
}
