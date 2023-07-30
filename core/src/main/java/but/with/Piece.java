package but.with;

import java.util.ArrayList;
import java.util.List;

public class Piece {
    private static final Offset[][] TEMPLATES = {
        {new Offset(0, 0), new Offset(1, 0), new Offset(2, 0), new Offset(3, 0)}, // I
        {new Offset(0, 0), new Offset(0, 1), new Offset(1, 0), new Offset(1, 1)}, // O
        {new Offset(0, 0), new Offset(1, 0), new Offset(2, 0), new Offset(1, 1)}, // T
        {new Offset(0, 0), new Offset(1, 0), new Offset(1, 1), new Offset(2, 1)}, // S
        {new Offset(0, 1), new Offset(1, 1), new Offset(1, 0), new Offset(2, 0)}, // Z
        {new Offset(0, 0), new Offset(1, 0), new Offset(2, 0), new Offset(0, 1)}, // J
        {new Offset(0, 0), new Offset(1, 0), new Offset(2, 0), new Offset(2, 1)}  // L
    };
    private final List<Block> blocks = new ArrayList<>();
    private boolean active = true;

    public Piece(List<Block> blockGrid) {
        Offset[] template = TEMPLATES[Rnd.instance.nextInt(TEMPLATES.length)];
        for (Offset offset : template) {
            Block b = new Block(4 + offset.x, 19 + offset.y);
            blocks.add(b);
            if (b.gridY < Grid.H) {
                blockGrid.set(b.gridY * Grid.W + b.gridX, b);
            }
        }
    }

    public void act(Time time, List<Block> blockGrid) {
        if (time.justTicked) {
            boolean canGoDown = blocks.stream().allMatch(b -> Physic.canGoDown(b, blockGrid, blocks));
            if (canGoDown)
                blocks.forEach(b -> Physic.moveDown(b, blockGrid));
        }
    }
}
