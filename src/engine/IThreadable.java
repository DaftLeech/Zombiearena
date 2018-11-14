package engine;

import org.newdawn.slick.GameContainer;

public interface IThreadable {

    int getTickRate();

    void toThread(GameContainer container, int delta);

}
