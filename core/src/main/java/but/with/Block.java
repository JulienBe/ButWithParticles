package but.with;

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
    private MyColor color = new MyColor();
    private int bottomY;
    private int leftX;

    public Block(int gridX, int gridY, Grid grid) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            pixels.add(
                new BlockPixel(
                    new GridPos(gridX + (i % SIZE), gridY + (i / SIZE)),
                    color,
                    grid)
            );
        }
        bottomY = gridY;
        leftX = gridX;
    }

    public void moveDown(Grid grid) {
        pixels.forEach(p -> {
            grid.setNullIfMe(p);
            p.gridPos.y--;
            grid.set(p);
        });
        bottomY--;
    }
    public void lateralMove(int i, Grid grid) {
        pixels.forEach(p -> {
            grid.setNullIfMe(p);
            p.gridPos.x += i;
            grid.set(p);
        });
        leftX += i;
    }

    public List<Integer> diffFromHighestPointIn(Grid grid) {
        return IntStream.range(leftX, leftX + SIZE)
            .map(x -> bottomY - grid.castRayDown(x, bottomY - 1))
            .boxed()
            .collect(Collectors.toList());
    }

}
