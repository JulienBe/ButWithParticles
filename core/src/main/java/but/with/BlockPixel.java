package but.with;

import but.with.board.Grid;
import com.badlogic.gdx.graphics.g2d.Batch;

import static but.with.Main.PIXEL_SIZE;

/**
 * In the model, a block is 6 inner pixels and 2 outer pixels.
 */
public class BlockPixel {
    public Pos pos;
    MyColor color;

    public Sandbag sandbag;
    public boolean sand = false;
    boolean rested = false;

    public BlockPixel(Pos pos, MyColor color, Grid grid) {
        this.pos = pos;
        this.color = color;
        grid.set(this);
    }

    public void display(Batch batch, Grid grid) {
        color.draw(batch, grid.x + pos.x * PIXEL_SIZE, grid.y + pos.y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
    }

    public void setBag(Sandbag sandbag) {
        this.sandbag = sandbag;
    }

    public Sandbag newBag() {
        sandbag = new Sandbag(this);
        return sandbag;
    }

    public void sandIt() {
        sand = true;
        rested = false;
    }
}
