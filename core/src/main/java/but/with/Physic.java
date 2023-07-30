package but.with;

import java.util.List;

public class Physic {
    public static void moveDown(Block b, List<Block> blocks) {
        b.updateY(-1);
        if (b.gridY >= 0 && b.gridY < Grid.H) {
            blocks.set(b.gridY * Grid.W + b.gridX, b);
        }
    }

    public static boolean canGoDown(Block b, List<Block> blockGrid, List<Block> shouldMove) {
        int wannaX = b.gridX;
        int wannaY = b.gridY - 1;
        return (
                wannaY >= 0 && wannaY < Grid.H &&
                wannaX >= 0 && wannaX < Grid.W &&
                    (
                        blockGrid.get(wannaY * Grid.W + wannaX) == Grid.NULL_BLOCK ||
                        shouldMove.contains(blockGrid.get(wannaY * Grid.W + wannaX))
                    )
        ) ||
                wannaY >= Grid.H; // some blocks may be more than 1 block above the grid
    }
}
