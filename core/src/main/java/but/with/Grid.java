package but.with;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.List;

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
        if (time.sand.act)
            actSand();
        if (time.pieces.act)
            actPieces(time);
    }

    private void actSand() {
        pixels.forEach(p -> {
            // make sand fall down if free or randomly to down left or down right if free
            if (p.sand && p.pos.y > 0 && Rnd.instance.nextBoolean()) {
                setNull(p.pos);
                if (get(p.pos.x, p.pos.y - 1) == NULL) {
                    p.pos.y--;
                } else {
                    if (Rnd.instance.nextBoolean() && p.pos.x > 0 && get(p.pos.x - 1, p.pos.y - 1) == NULL) {
                        p.pos.x--;
                        p.pos.y--;
                    } else if (p.pos.x < W - 1 && get(p.pos.x + 1, p.pos.y - 1) == NULL) {
                        p.pos.x++;
                        p.pos.y--;
                    }
                }
                set(p);
            }
        });
    }

    private void actPieces(Time time) {
        pieces.forEach(p -> {
            if (!p.act(time, this)) {
                p.convertToSand();
                pieces.removeValue(p, true);
            }
        });
    }

    public void set(BlockPixel pixel) {
        checkPos(pixel.pos);
        pixels.set(pixel.pos.y * W + pixel.pos.x, pixel);
    }

    private void checkPos(Pos pos) {
        if (!isValid(pos))
            throw new RuntimeException("Invalid pos: " + pos.x + ", " + pos.y);
    }

    public void setNull(Pos pos) {
        checkPos(pos);
        pixels.set(pos.y * W + pos.x, NULL);
    }

    public BlockPixel get(int wannaX, int wannaY) {
        return pixels.get(wannaY * W + wannaX);
    }

    public void setNullIfMe(BlockPixel pixel) {
        if (get(pixel.pos.x, pixel.pos.y) == pixel)
            setNull(pixel.pos);
    }

    /**
     * @return the y coordinate of the first non-null pixel below the given one
     */
    public int castRayDown(int x, int startY, List<BlockPixel> ignoreList) {
        for (int y = startY; y >= 0; y--) {
            BlockPixel bp = get(x, y);
            if (bp != NULL && !ignoreList.contains(bp))
                return y;
        }
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

    @Override
    public boolean onRotate() {
        pieces.forEach(piece -> piece.rotate(this));
        return true;
    }

    public boolean isValid(Pos pos) {
        return pos.x >= 0 && pos.x < W && pos.y >= 0 && pos.y < DISPLAY_H;
    }
}
