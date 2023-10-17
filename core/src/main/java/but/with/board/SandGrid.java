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
        for (Sandbag bag : bags) {
            if (bag.connectLeftRight()) {
                bag.sand.forEach(p -> {
                    grid.setNull(p.pos);
                    p.sand = false;
                });
            }
        }
    }

    private static void move(Array<BlockPixel> pixels, Grid grid) {
        pixels.forEach(p -> {
            // make sand fall down if free or randomly to down left or down right if free
            if (p != null && p.sand && Rnd.instance.nextBoolean() && p.pos.y > 0) {
                if (grid.get(p.pos.x, p.pos.y - 1) == null)
                    grid.updatePos(p, 0);
                else
                    if (Rnd.instance.nextBoolean() && p.pos.x > 0 && grid.get(p.pos.x - 1, p.pos.y - 1) == null)
                        grid.updatePos(p, -1);
                    else if (p.pos.x < Grid.W - 1 && grid.get(p.pos.x + 1, p.pos.y - 1) == null)
                        grid.updatePos(p, 1);
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
            Pos neighborPos = new Pos(p.pos.x + offset.x, p.pos.y + offset.y);
            if (Grid.isValid(neighborPos)) {
                BlockPixel neighbor = grid.get(neighborPos.x, neighborPos.y);
                if (neighbor != null && neighbor.sand && neighbor.sandbag != null && neighbor.sandbag.colorMatch(p.sandbag))
                    matchingBags.add(neighbor.sandbag);
            }
        }
        return matchingBags;
    }
}
