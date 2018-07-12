package render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import engine.IThreadable;

import java.awt.*;

public interface IRenderable extends IThreadable {

    void toRender(SpriteBatch batch);
}
