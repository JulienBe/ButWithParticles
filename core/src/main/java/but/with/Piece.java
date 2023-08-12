package but.with;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Offset[] template = TEMPLATES[0];
        for (Offset offset : template) {
            Block b = new Block(Grid.W/2 + offset.x*Block.SIZE, Grid.H + offset.y*Block.SIZE, grid);
            blocks.add(b);
        }
    }

    public void act(Time time, Grid grid) {
        if (time.justTicked) {
            if (active) {
                List<Integer> diffMap = blocks
                    .stream()
                    .map(block -> block.getDiffFromHighestIn(grid))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

                if (diffMap.stream().allMatch(diff -> diff >= Block.SIZE)) {
                    blockedTicks = 0;
                    blocks.forEach(b -> b.moveDown(grid));
                } else {
                    blockedTicks++;
                    if (blockedTicks > 2) {
                        active = false;
                    }
                }
            } else {
                blocks.forEach(b -> b.setNull(grid));
            }
        }
    }
}
