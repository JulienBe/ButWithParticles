package but.with;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Block {

    public static final int SIZE = 8;
    private BlockPixel[] pixels = new BlockPixel[8 * 8];
    private MyColor color = new MyColor();
    GridPos gridPos; // Keeping it as it will probably make things easier, could be computed from the pixels

    public Block(int gridX, int gridY) {
        this.gridPos = new GridPos(gridX, gridY);
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = new BlockPixel(new GridPos(i % 8, i / 8), color);
        }
    }

    public void display(Batch batch) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i].display(batch);
        }
    }

    public void updateY(int newGridY) {
        gridPos.updateY(newGridY);
        for (int i = 0; i < pixels.length; i++) {
            pixels[i].updateY(newGridY);
        }
    }
}
