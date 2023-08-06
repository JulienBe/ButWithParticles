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
        pieces.add(new Piece(this));
    }

    public void display(SpriteBatch batch) {
        blockGrid.stream()
            .filter(b -> b != NULL_BLOCK)
            .forEach(b -> b.display(batch));
    }

    public void act(Time time) {
        if (time.justTicked)
            pieces.forEach(p -> p.act(time, this));
    }

    public void set(Block b) {
        blockGrid.set(b.gridPos.y * W + b.gridPos.x, b);
    }
    public void setNull(GridPos gridPos) {
        blockGrid.set(gridPos.y * W + gridPos.x, NULL_BLOCK);
    }

    public boolean isNull(int wannaX, int wannaY) {
        return blockGrid.get(wannaY * W + wannaX) == NULL_BLOCK;
    }

    public Block get(int wannaX, int wannaY) {
        return blockGrid.get(wannaY * W + wannaX);
    }

    public void safeSet(Block b) {
        if (b.gridPos.y >= 0 && b.gridPos.y < H && b.gridPos.x >= 0 && b.gridPos.x < W)
            blockGrid.set(b.gridPos.y * W + b.gridPos.x, b);
    }

    public Block safeGet(int x, int y) {
        if (y >= 0 && y < H && x >= 0 && x < W)
            return get(x, y);
        return NULL_BLOCK;
    }

    public int clampY(int y) {
        if (y < 0)
            return 0;
        return Math.min(y, H - 1);
    }
}
