package but.with;

import com.badlogic.gdx.graphics.g2d.Batch;

import static but.with.Main.PIXEL_SIZE;

/**
 * In the model, a block is 6 inner pixels and 2 outer pixels.
 */
public class BlockPixel {
    private GridPos gridPos;
    private MyColor color;

    public BlockPixel(GridPos gridPos, MyColor color) {
        this.gridPos = gridPos;
        this.color = color;
    }

    public void display(Batch batch) {
        color.draw(batch, gridPos.x * PIXEL_SIZE, gridPos.y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
    }

    public void updateY(int y) {
        gridPos.y = y;
    }
}
