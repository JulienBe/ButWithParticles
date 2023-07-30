package but.with;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    public static final int W = 10;
    public static final int H = 20;
    public static final Block NULL_BLOCK = new Block(5, 5);
    private final List<Block> blockGrid = new ArrayList<>();
    private final Array<Piece> pieces = new Array<>();

    public Grid() {
        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                blockGrid.add(NULL_BLOCK);
            }
        }
    }

    public void addPiece() {
        pieces.add(new Piece(blockGrid));
    }

    public void display(SpriteBatch batch) {
        blockGrid.stream()
            .filter(b -> b != NULL_BLOCK)
            .forEach(b -> b.display(batch));
    }

    public void act(Time time) {
        if (time.justTicked) {
            pieces.forEach(p -> p.act(time, blockGrid));
        }
    }
}
