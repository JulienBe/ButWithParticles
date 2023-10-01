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
    final List<Block> blocks = new ArrayList<>();
    private int blockedTicks = 0;

    public Piece(Grid grid) {
        //Offset[] template = TEMPLATES[Rnd.instance.nextInt(TEMPLATES.length)];
        Offset[] template = TEMPLATES[0];
        for (Offset offset : template) {
            Block b = new Block(Grid.W/2 + offset.x*Block.SIZE, Grid.H + offset.y*Block.SIZE, grid);
            blocks.add(b);
        }
    }

    public boolean act(Time time, Grid grid) {
        if (time.justTicked) {
            List<Integer> diffMap = blocks
                .stream()
                .map(block -> block.diffFromHighestPointIn(grid))
                .flatMap(List::stream)
                .collect(Collectors.toList());

            if (diffMap.stream().allMatch(diff -> diff > 1)) {
                blockedTicks = 0;
                blocks.forEach(b -> b.moveDown(grid));
            } else {
                blockedTicks++;
                return blockedTicks <= 2;
            }
        }
        return true;
    }

    public void convertToSand() {
        blocks.forEach(b -> b.pixels.forEach(p -> p.sand = true));
    }

    public void lateralMove(int i, Grid grid) {
        // There is probably a more elegant way than this left/right split
        if (i > 0) {
            int rightest = blocks.stream()              // iterate over all the block pixels to find the rightest
                .mapToInt(b ->
                    b.pixels.stream()
                        .mapToInt(p -> p.gridPos.x)
                        .max()
                        .getAsInt())
                .max()
                .getAsInt();
            i = Math.min(i, Grid.W - rightest - 1);     // Now set a new value of i if it's too big
        } else {
            int leftest = blocks.stream()               // iterate over all the block pixels to find the leftest
                .mapToInt(b ->
                    b.pixels.stream()
                        .mapToInt(p -> p.gridPos.x)
                        .min()
                        .getAsInt())
                .min()
                .getAsInt();
            i = Math.max(i, -leftest);                  // Now set a new value of i if it's too big
        }
        int finalI = i;
        blocks.forEach(b -> b.lateralMove(finalI, grid));
    }
}
