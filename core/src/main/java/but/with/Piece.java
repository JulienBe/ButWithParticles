package but.with;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum Templates {
    I(new Offset(0, 0), new Offset(1, 0), new Offset(2, 0), new Offset(3, 0)),
    O(new Offset(0, 0), new Offset(0, 1), new Offset(1, 0), new Offset(1, 1)),
    T(new Offset(0, 0), new Offset(1, 0), new Offset(2, 0), new Offset(1, 1)),
    S(new Offset(0, 0), new Offset(1, 0), new Offset(1, 1), new Offset(2, 1)),
    Z(new Offset(0, 1), new Offset(1, 1), new Offset(1, 0), new Offset(2, 0)),
    J(new Offset(0, 0), new Offset(1, 0), new Offset(2, 0), new Offset(0, 1)),
    L(new Offset(0, 0), new Offset(1, 0), new Offset(2, 0), new Offset(2, 1));

    final Offset[] horizontal;

    Templates(Offset one, Offset two, Offset tree, Offset four) {
        this.horizontal = new Offset[]{one, two, tree, four};
    }

    public static Templates rnd() {
        return values()[Rnd.instance.nextInt(values().length)];
    }
}

public class Piece {
    final List<Block> blocks = new ArrayList<>();
    private int blockedTicks = 0;
    private final Templates template;
    private int currentOffset = 0;

    public Piece(Grid grid) {
        template = Templates.rnd();
        for (Offset offset : template.horizontal) {
            Block b = new Block(Grid.W/2 + offset.x*Block.SIZE, Grid.H + offset.y*Block.SIZE, grid);
            blocks.add(b);
        }
        currentOffset = 0;
    }

    public boolean act(Time time, Grid grid) {
        if (time.pieces.act) {
            List<Integer> diffMap = blocks
                .stream()
                .map(block -> block.diffFromHighestPointIn(grid, getBlockPixels()))
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

    private List<BlockPixel> getBlockPixels() {
        return blocks.stream().flatMap(b -> b.pixels.stream()).collect(Collectors.toList());
    }

    public void convertToSand() {
        blocks.forEach(b -> b.pixels.forEach(p -> p.sand = true));
    }

    public boolean rotate(Grid grid) {
        // if any block cannot rotate
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            if (!b.canRotate(grid, template.horizontal[i], getBlockPixels(), currentOffset))
                return false;
        }
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).rotate(grid, template.horizontal[i], currentOffset);
        }
        currentOffset++;
        return true;
    }

    public void lateralMove(int i, Grid grid) {
        // There is probably a more elegant way than this left/right split
        if (i > 0) {
            int rightest = blocks.stream()              // iterate over all the block pixels to find the rightest
                .mapToInt(b ->
                    b.pixels.stream()
                        .mapToInt(p -> p.pos.x)
                        .max()
                        .getAsInt())
                .max()
                .getAsInt();
            i = Math.min(i, Grid.W - rightest - 1);     // Now set a new value of i if it's too big
        } else {
            int leftest = blocks.stream()               // iterate over all the block pixels to find the leftest
                .mapToInt(b ->
                    b.pixels.stream()
                        .mapToInt(p -> p.pos.x)
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
