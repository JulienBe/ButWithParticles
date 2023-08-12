package but.with;

public class GridPos {
    int x;
    int y;

    public GridPos(int gridX, int gridY) {
        this.x = gridX;
        this.y = gridY;
    }


    public int downOneBlock() {
        return y - Block.SIZE;
    }

    @Override
    public String toString() {
        return "GridPos{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}
