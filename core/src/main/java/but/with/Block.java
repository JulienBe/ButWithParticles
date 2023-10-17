package but.with;

import but.with.board.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A simple tetris block, 4 make a piece
 * Stored as a list of pixels
 */
public class Block {

    public static final int SIZE = 8;
    List<BlockPixel> pixels = new ArrayList<>(SIZE * SIZE);
    private MyColor color;
    private int bottomY;
    private int leftX;

    public Block(int gridX, int gridY, Grid grid, MyColor color) {
        this.color = color;
        for (int i = 0; i < SIZE * SIZE; i++) {
            pixels.add(
                new BlockPixel(
                    new Pos(gridX + (i % SIZE), gridY + (i / SIZE)),
                    this.color,
                    grid)
            );
        }
        bottomY = gridY;
        leftX = gridX;
    }

    public void moveDown(Grid grid) {
        pixels.forEach(p -> {
            grid.setNullIfMe(p);
            p.pos.y--;
            grid.set(p);
        });
        bottomY--;
    }
    public void lateralMove(int i, Grid grid) {
        pixels.forEach(p -> {
            grid.setNullIfMe(p);
            p.pos.x += i;
            grid.set(p);
        });
        leftX += i;
    }

    public List<Integer> diffFromHighestPointIn(Grid grid, List<BlockPixel> ignoreList) {
        return IntStream.range(leftX, leftX + SIZE)
            .map(x -> bottomY - grid.castRayDown(x, bottomY - 1, ignoreList))
            .boxed()
            .collect(Collectors.toList());
    }

    public void rotate(Grid grid, Offset offset, int currentOffset) {
        Pos newBlockPos = getRotatePos(offset, currentOffset);
        for (int i = 0; i < pixels.size(); i++) {
            BlockPixel p = pixels.get(i);
            grid.setNullIfMe(p);
            p.pos.update(newBlockPos.x + i % SIZE, newBlockPos.y + i / SIZE);
            grid.set(p);
        }
        leftX = newBlockPos.x;
        bottomY = newBlockPos.y;
    }

    public boolean canRotate(Grid grid, Offset offset, List<BlockPixel> ignoreList, int currentOffset) {
        Pos newBlockPos = getRotatePos(offset, currentOffset);
        for (int i = 0; i < pixels.size(); i++) {
            Pos newPixelPos = new Pos(newBlockPos.x + i % SIZE, newBlockPos.y + i / SIZE);
            if (!grid.isValid(newPixelPos))
                return false;
            BlockPixel currentPixel = grid.get(newPixelPos.x, newPixelPos.y);
            if (currentPixel != null && !ignoreList.contains(currentPixel))
                return false;
        }

        return true;
    }

    private Pos getRotatePos(Offset offset, int currentOffset) {
        if (currentOffset % 2 == 0)
            return new Pos(
                (leftX - offset.x * Block.SIZE) + offset.y * Block.SIZE,
                (bottomY - offset.y * Block.SIZE) + offset.x * Block.SIZE
            );
        else
            return new Pos(
                (leftX - offset.y * Block.SIZE) + offset.x * Block.SIZE,
                (bottomY - offset.x * Block.SIZE) + offset.y * Block.SIZE
            );
    }
}
