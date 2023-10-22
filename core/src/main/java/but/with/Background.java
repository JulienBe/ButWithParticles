package but.with;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private MyColor color = new MyColor(8).bright();

    public void display(SpriteBatch batch) {
        color.draw(batch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}

