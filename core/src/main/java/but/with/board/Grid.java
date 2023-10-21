package but.with.board;

import but.with.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.*;

public class Grid implements InputHandler {
    public static final int W = 10 * Block.SIZE;
    public static final int H = 20 * Block.SIZE;
    public static final int DISPLAY_H = H + (4 * Block.SIZE);
    public int x = Block.SIZE * 2;
    public int y = Block.SIZE * 2;
    private final Array<Piece> pieces = new Array<>();
    private final Array<BlockPixel> pixels = new Array<>(DISPLAY_H * W);
    private MyColor color = new MyColor(8);
    boolean displayBags = false;
    private final SandGrid sandGrid = new SandGrid();

    public Grid() {
        for (int x = 0; x < W; x++) {
            for (int y = 0; y < DISPLAY_H; y++) {
                pixels.add(null);
            }
        }
    }

    public void addPiece() {
        pieces.add(new Piece(this));
    }

    public void display(SpriteBatch batch, int pixelSize) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F6))
            displayBags = !displayBags;
        color.draw(batch, x, y, W * pixelSize, DISPLAY_H * pixelSize);
        if (displayBags) {
            Set<Sandbag> sandbags = new HashSet<>();
            pixels.forEach(p -> {
                if (p != null && p.sandbag != null)
                    sandbags.add(p.sandbag);
            });
            int i = 0;
            for (Sandbag sandbag : sandbags) {
                sandbag.draw(batch, this, pixelSize, new MyColor(i % MyColor.COLORS));
                i++;
            }
        } else
            pixels.forEach(p -> {
                if (p != null) p.display(batch, this, pixelSize);
            });
    }

    public void act(Time time) {
        if (time.pieces.act)
            actPieces(time);
        if (time.sand.act)
            sandGrid.actSand(pixels, this);
    }

    private void actPieces(Time time) {
        pieces.forEach(p -> {
            if (!p.act(time, this)) {
                p.convertToSand();
                pieces.removeValue(p, true);
            }
        });
    }

    public void set(BlockPixel pixel, Pos pos) {
        checkPos(pos);
        pixels.set(pos.y * W + pos.x, pixel);
    }

    private void checkPos(Pos pos) {
        if (!isValid(pos))
            throw new RuntimeException("Invalid pos: " + pos.x + ", " + pos.y);
    }

    public void setNull(int x, int y) {
        pixels.set(y * W + x, null);
    }

    public void setNull(Pos pos) {
        checkPos(pos);
        pixels.set(pos.y * W + pos.x, null);
    }

    public BlockPixel get(int wannaX, int wannaY) {
        return pixels.get(wannaY * W + wannaX);
    }

    public void setNullIfMe(BlockPixel pixel, Pos pos) {
        if (get(pos.x, pos.y) == pixel)
            setNull(pos);
    }

    /**
     * @return the y coordinate of the first non-null pixel below the given one
     */
    public int castRayDown(int x, int startY, List<BlockPixel> ignoreList) {
        for (int y = startY; y >= 0; y--) {
            BlockPixel bp = get(x, y);
            if (bp != null && !ignoreList.contains(bp))
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

    public static boolean isValid(Pos pos) {
        return pos.x >= 0 && pos.x < W && pos.y >= 0 && pos.y < DISPLAY_H;
    }
}
