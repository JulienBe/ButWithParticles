package but.with;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Block {

    public static final float W = 16f;
    private MyColor color = new MyColor();
    int gridX = 0;
    int gridY = 0;
    float displayX = 0f;
    float displayY = 0f;

    public Block(int grixX, int grixY) {
        this.gridX = grixX;
        this.gridY = grixY;
        this.displayX = gridX * W;
        this.displayY = gridY * W;
    }

    public void display(Batch batch) {
        color.draw(batch, displayX, displayY, W, W);
    }

    public void updateY(int offsetY) {
        gridY += offsetY;
        displayY = gridY * W;
    }
}
