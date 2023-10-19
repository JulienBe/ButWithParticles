package but.with.board;

import but.with.*;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Set;

public class SandGrid {

    private static final Offset[] neighborsOffset = {
        new Offset(0, 1),
        new Offset(0, -1),
        new Offset(1, 0),
        new Offset(-1, 0),
    };

    static void actSand(Array<BlockPixel> pixels, Grid grid) {
        move(pixels, grid);
        checkConnections(pixels, grid);
    }

    private static void checkConnections(Array<BlockPixel> pixels, Grid grid) {
        Set<Sandbag> bags = new HashSet<>();
        pixels.forEach(p -> {
            if (p != null && p.sandbag != null)
                bags.add(p.sandbag);
        });
        for (Sandbag bag : bags)
            if (bag.connectLeftRight())
                bag.sand.forEach(p -> grid.setNull(p.x(), p.y()));
    }

    private static void move(Array<BlockPixel> pixels, Grid grid) {
        pixels.forEach(p -> {
            // make sand fall down if free or randomly to down left or down right if free
            if (p != null && p.sand && Rnd.instance.nextBoolean() && p.y() > 0) {
                if (grid.get(p.x(), p.y() - 1) == null)
                    p.moveSand(0, grid);
                else
                    if (Rnd.instance.nextBoolean() && p.x() > 0 && grid.get(p.x() - 1, p.y() - 1) == null)
                        p.moveSand(-1, grid);
                    else if (p.x() < Grid.W - 1 && grid.get(p.x() + 1, p.y() - 1) == null)
                        p.moveSand(1, grid);
                p.sandbag = null;
                findBags(p, grid);
            }
        });
    }

    private static void findBags(BlockPixel p, Grid grid) {
        Sandbag letsAnnoyTheGCNewBag = p.newBag();
        Set<Sandbag> matchingBags = getMatchingBags(p, grid);
        for (Sandbag bag : matchingBags) {
            letsAnnoyTheGCNewBag.merge(bag);
        }
    }

    private static Set<Sandbag> getMatchingBags(BlockPixel p, Grid grid) {
        Set<Sandbag> matchingBags = new HashSet<>();
        for (Offset offset : neighborsOffset) {
            Pos neighborPos = new Pos(p.x() + offset.x, p.y() + offset.y);
            if (Grid.isValid(neighborPos)) {
                BlockPixel neighbor = grid.get(neighborPos.x, neighborPos.y);
                if (neighbor != null && neighbor.sand && neighbor.sandbag != null && neighbor.sandbag.colorMatch(p.sandbag))
                    matchingBags.add(neighbor.sandbag);
            }
        }
        return matchingBags;
    }
}
