package but.with;

import com.badlogic.gdx.graphics.g2d.Batch;

import static but.with.Main.PIXEL_SIZE;

/**
 * In the model, a block is 6 inner pixels and 2 outer pixels.
 */
public class BlockPixel {
    public static final BlockPixel NULL = new BlockPixel(new GridPos(0, 0), new MyColor(), new Grid());
    GridPos gridPos;
    private MyColor color;
    boolean sand = false;

    public BlockPixel(GridPos gridPos, MyColor color, Grid grid) {
        this.gridPos = gridPos;
        this.color = color;
        grid.set(this);
    }

    public void display(Batch batch, Grid grid) {
        color.draw(batch, grid.x + gridPos.x * PIXEL_SIZE, grid.y + gridPos.y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
    }
}
