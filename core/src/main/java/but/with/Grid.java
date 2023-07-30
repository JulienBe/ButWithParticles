package but.with;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    public static final int W = 10;
    public static final int H = 20;
    public static final Block NULL_BLOCK = new Block(5, 5);
    private final List<Block> blocks = new ArrayList<>();

    public Grid() {
        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                blocks.add(NULL_BLOCK);
            }
        }
    }

    public void addBlock() {
        Block b = new Block(W / 2, H - 1);
        blocks.set(b.gridY * W + b.gridX, b);
    }

    public void display(SpriteBatch batch) {
        blocks.stream()
            .filter(b -> b != NULL_BLOCK)
            .forEach(b -> b.display(batch));
    }

    public void act(Time time) {
        if (time.justTicked) {
            blocks.stream()
                .filter(b -> b != NULL_BLOCK)
                .forEach(b -> Physic.moveDown(b, blocks));
        }
    }
}
