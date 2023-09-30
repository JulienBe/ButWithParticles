package but.with;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import static but.with.BlockPixel.NULL;

public class Grid implements InputHandler {
    public static final int W = 10 * Block.SIZE;
    public static final int H = 20 * Block.SIZE;
    public static final int DISPLAY_H = H + (4 * Block.SIZE);
    int x = Block.SIZE * 2;
    int y = Block.SIZE * 2;
    private final Array<Piece> pieces = new Array<>();
    private final Array<BlockPixel> pixels = new Array<>(DISPLAY_H * W);

    public Grid() {
        for (int x = 0; x < W; x++) {
            for (int y = 0; y < DISPLAY_H; y++) {
                pixels.add(NULL);
            }
        }
    }

    public void addPiece() {
        pieces.add(new Piece(this));
    }

    public void display(SpriteBatch batch) {
        pixels.forEach(p -> p.display(batch, this));
    }

    public void act(Time time) {
        if (time.justTicked)
            pieces.forEach(p -> {
                if (!p.act(time, this))
                    p.convertToSand(this);
            });
    }

    public void set(BlockPixel pixel) {
        pixels.set(pixel.gridPos.y * W + pixel.gridPos.x, pixel);
    }
    public void setNull(GridPos gridPos) {
        pixels.set(gridPos.y * W + gridPos.x, NULL);
    }

    public boolean isNull(int wannaX, int wannaY) {
        return pixels.get(wannaY * W + wannaX) == NULL;
    }

    public BlockPixel get(int wannaX, int wannaY) {
        return pixels.get(wannaY * W + wannaX);
    }

    public void safeSet(BlockPixel b) {
        if (b.gridPos.y >= 0 && b.gridPos.y < DISPLAY_H && b.gridPos.x >= 0 && b.gridPos.x < W)
            pixels.set(b.gridPos.y * W + b.gridPos.x, b);
    }

    public BlockPixel safeGet(int x, int y) {
        if (y >= 0 && y < DISPLAY_H && x >= 0 && x < W)
            return get(x, y);
        return NULL;
    }

    public int clampY(int y) {
        if (y < 0)
            return 0;
        return Math.min(y, DISPLAY_H - 1);
    }

    public int clampX(int x) {
        if (x < 0)
            return 0;
        return Math.min(x, W - 1);
    }

    public void setNullIfMe(BlockPixel pixel) {
        if (get(pixel.gridPos.x, pixel.gridPos.y) == pixel)
            setNull(pixel.gridPos);
    }

    public int getHighest(int x, int startY) {
        for (int y = startY; y >= 0; y--) {
            if (get(x, y) != NULL)
                return y;
        }
        return 0;
    }

    @Override
    public void onLeft() {
        pieces.forEach(piece -> piece.lateralMove(-2, this));
        InputHandler.super.onLeft();
    }

    @Override
    public void onRight() {
        pieces.forEach(piece -> piece.lateralMove(+2, this));
        InputHandler.super.onRight();
    }
}
