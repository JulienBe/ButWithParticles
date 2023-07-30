package but.with;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Block {
    public static final float W = 32f;
    private int x = 0;
    private int y = 0;
    private MyColor color = new MyColor();

    public void display(Batch batch) {
        color.draw(batch, x, y, W, W);
    }
}
