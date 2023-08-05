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
    private int blockedTicks = 0;

    public Piece(Grid grid) {
        //Offset[] template = TEMPLATES[Rnd.instance.nextInt(TEMPLATES.length)];
        Offset[] template = TEMPLATES[1];
        for (Offset offset : template) {
            Block b = new Block(4 + offset.x, 19 + offset.y);
            blocks.add(b);
            if (b.gridPos.y < Grid.H)
                grid.set(b);
        }
    }

    public void act(Time time, Grid grid) {
        if (time.justTicked) {
            if (active) {
                boolean canGoDown = blocks.stream().allMatch(b -> Physic.canGoDown(b, grid, blocks));
                if (canGoDown) {
                    blocks.forEach(b -> Physic.moveDown(b, grid));
                    blockedTicks = 0;
                } else
                    blockedTicks++;
                if (blockedTicks > 2)
                    active = false;
            } else {
                blocks.forEach(b -> grid.setNull(b.gridPos));
            }
        }
    }
}
