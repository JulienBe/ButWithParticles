package but.with;

import java.util.List;

public class Physic {
    public static boolean moveDown(Block b, List<Block> blocks) {
        if (b.gridY > 0 && blocks.get((b.gridY - 1) * Grid.W + b.gridX) == Grid.NULL_BLOCK) {
            b.updateY(-1);
            return true;
        }
        return false;
    }
}
