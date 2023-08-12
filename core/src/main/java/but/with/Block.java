package but.with;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Block {

    public static final int SIZE = 8;
    List<BlockPixel> pixels = new ArrayList<>(SIZE * SIZE);
    private MyColor color = new MyColor();

    public Block(int gridX, int gridY, Grid grid) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            pixels.add(
                new BlockPixel(
                    new GridPos(gridX + (i % SIZE), gridY + (i / SIZE)),
                    color,
                    grid)
            );
        }
    }

    public void display(Batch batch, Grid grid) {
        pixels.forEach(p -> p.display(batch, grid));
    }

    public void setNull(Grid grid) {
        pixels.forEach(p -> grid.setNull(p.gridPos));
    }

    public void moveDown(Grid grid) {
        pixels.forEach(p -> {
            grid.setNullIfMe(p);
            p.gridPos.y--;
            grid.set(p);
        });
    }

    private BlockPixel lower(int x) {
        return pixels.stream()
            .filter(p -> p.gridPos.x == x)
            .min(Comparator.comparingInt(p -> p.gridPos.y))
            .get();
    }

    public List<Integer> getDiffFromHighestIn(Grid grid) {
        return pixels.stream()
            // I want only the lower Y for a given X
            .map(p -> lower(p.gridPos.x))
            .distinct()
            .map(p -> p.gridPos.y - grid.getHighest(p.gridPos.x, p.gridPos.y - 1))
            .collect(Collectors.toList());
    }
}
