package but.with;

import com.badlogic.gdx.graphics.g2d.Batch;

import static but.with.Main.PIXEL_SIZE;

/**
 * In the model, a block is 6 inner pixels and 2 outer pixels.
 */
public class BlockPixel {
    Pos pos;
    private MyColor color;
    boolean sand = false;

    public BlockPixel(Pos pos, MyColor color, Grid grid) {
        this.pos = pos;
        this.color = color;
        grid.set(this);
    }

    public void display(Batch batch, Grid grid) {
        color.draw(batch, grid.x + pos.x * PIXEL_SIZE, grid.y + pos.y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
    }
}
