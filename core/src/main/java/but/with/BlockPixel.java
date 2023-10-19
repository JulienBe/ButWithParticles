package but.with;

import but.with.board.Grid;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * In the model, a block is 6 inner pixels and 2 outer pixels.
 */
public class BlockPixel {
    private Pos pos;
    MyColor color;

    public Sandbag sandbag;
    public boolean sand = false;
    boolean rested = false;

    public BlockPixel(Pos pos, MyColor color, Grid grid) {
        this.pos = pos;
        this.color = color;
        grid.set(this, pos);
    }

    public void display(Batch batch, Grid grid, int pixelSize) {
        color.draw(batch, grid.x + pos.x * pixelSize, grid.y + pos.y * pixelSize, pixelSize, pixelSize);
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


    public int x() {
        return pos.x;
    }

    public int y() {
        return pos.y;
    }

    public void movePiece(Grid grid, int diffX, int diffY) {
        newPos(grid, pos.x + diffX, pos.y + diffY);
    }

    public void moveSand(int diffX, Grid grid) {
        newPos(grid, pos.x + diffX, pos.y - 1);
    }
    public void newPos(Grid grid, int x, int y) {
        grid.setNullIfMe(this, pos);
        pos.x = x;
        pos.y = y;
        grid.set(this, pos);
    }
}
