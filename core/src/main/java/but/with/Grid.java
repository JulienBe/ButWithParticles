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
    private MyColor color = new MyColor(8);

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
        color.draw(batch, x, y, W * Main.PIXEL_SIZE, DISPLAY_H * Main.PIXEL_SIZE);
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

    public BlockPixel get(int wannaX, int wannaY) {
        return pixels.get(wannaY * W + wannaX);
    }

    public void setNullIfMe(BlockPixel pixel) {
        if (get(pixel.gridPos.x, pixel.gridPos.y) == pixel)
            setNull(pixel.gridPos);
    }

    /**
     * @return the y coordinate of the first non-null pixel below the given one
     */
    public int castRayDown(int x, int startY) {
        for (int y = startY; y >= 0; y--)
            if (get(x, y) != NULL)
                return y;
        return 0;
    }

    @Override
    public void onLeft() {
        pieces.forEach(piece -> piece.lateralMove(-Block.SIZE, this));
        InputHandler.super.onLeft();
    }

    @Override
    public void onRight() {
        pieces.forEach(piece -> piece.lateralMove(+Block.SIZE, this));
        InputHandler.super.onRight();
    }
}
