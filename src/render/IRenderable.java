package render;

import engine.IThreadable;
import org.newdawn.slick.Graphics;

public interface IRenderable extends IThreadable {

    void toRender(Graphics g);
}
