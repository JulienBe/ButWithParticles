package but.with;

public class Pos {
    public int x;
    public int y;

    public Pos(int gridX, int gridY) {
        this.x = gridX;
        this.y = gridY;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Pos{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}
