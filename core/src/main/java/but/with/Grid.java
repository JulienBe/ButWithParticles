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
        if (time.justTicked) {
            pixels.forEach(p -> {
                // make sand fall down if free or randomly to down left or down right if free
                if (p.sand && p.gridPos.y > 0 && Rnd.instance.nextBoolean()) {
                    // can if fall down?
                    setNull(p.gridPos);
                    if (get(p.gridPos.x, p.gridPos.y - 1) == NULL) {
                        p.gridPos.y--;
                    } else {
                        if (Rnd.instance.nextBoolean() && p.gridPos.x > 0 && get(p.gridPos.x - 1, p.gridPos.y - 1) == NULL) {
                            p.gridPos.x--;
                            p.gridPos.y--;
                        } else if (p.gridPos.x < W && get(p.gridPos.x + 1, p.gridPos.y - 1) == NULL) {
                            p.gridPos.x++;
                            p.gridPos.y--;
                        }
                    }
                    set(p);
                }
            });
            pieces.forEach(p -> {
                if (!p.act(time, this)) {
                    p.convertToSand();
                    pieces.removeValue(p, true);
                }
            });
        }
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
