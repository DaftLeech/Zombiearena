package render;

import engine.IThreadable;

import java.awt.*;

public interface IRenderable extends IThreadable {

    void toRender(Graphics2D g);
}
