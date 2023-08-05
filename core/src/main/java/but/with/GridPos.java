package but.with;

import static but.with.Main.PIXEL_SIZE;

public class GridPos {
    public static final float CELL_SIZE = PIXEL_SIZE;
    int x;
    int y;

    public GridPos(int gridX, int gridY) {
        this.x = gridX;
        this.y = gridY;
    }

    public void updateY(int newGridY) {
        y = newGridY;
    }

    public int downOneBlock() {
        return y - Block.SIZE;
    }
}
