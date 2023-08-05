package but.with;

import java.util.List;

public class Physic {
    public static void moveDown(Block b, Grid grid) {
        if (isWithinGrid(b.gridPos))
            grid.setNull(b.gridPos);
        b.updateY(b.gridPos.downOneBlock());
        grid.safeSet(b);
    }

    public static boolean canGoDown(Block b, Grid grid, List<Block> ignoreBlocks) {
        int wannaX = b.gridPos.x;
        int wannaY = grid.clampY(b.gridPos.downOneBlock());
        if (!((grid.isNull(wannaX, wannaY) || ignoreBlocks.contains(grid.safeGet(wannaX, wannaY)))
            &&
            (isWithinGrid(wannaX, wannaY) || wannaY >= Grid.H))) {
            System.out.println("Can go do for " + wannaX + ", " + wannaY);
            System.out.println(grid.isNull(wannaX, wannaY) + " || " + ignoreBlocks.contains(grid.safeGet(wannaX, wannaY)));
            System.out.println(isWithinGrid(wannaX, wannaY) || wannaY >= Grid.H);
        }
        return
            (grid.isNull(wannaX, wannaY) || ignoreBlocks.contains(grid.safeGet(wannaX, wannaY)))
            &&
            (isWithinGrid(wannaX, wannaY) || wannaY >= Grid.H); // some blocks may be more than 1 block above the grid
    }

    private static boolean isWithinGrid(GridPos gridPos) {
        return isWithinGrid(gridPos.x, gridPos.y);
    }

    private static boolean isWithinGrid(int x, int y) {
        return x >= 0 && x < Grid.W && y >= 0 && y < Grid.H;
    }
}
