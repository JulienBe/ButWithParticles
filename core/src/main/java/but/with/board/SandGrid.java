package but.with.board;

import but.with.*;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SandGrid {

    private static final Offset[] neighborsOffset = {
        new Offset(0, 1),
        new Offset(0, -1),
        new Offset(1, 0),
        new Offset(-1, 0),
    };
    private DisappearingAnim disappearing = null;

    /**
     * Make sand fall
     * @param pixels
     * @param grid
     * @return false if a bag of sand is still busy disappearing
     */
    boolean actSand(Array<BlockPixel> pixels, Grid grid) {
        if (disappearing != null) {
            if (!disappearing.act(grid)) {
                disappearing.end();
                disappearing = null;
            }
            return true;
        }
        int moved = move(pixels, grid);
        if (moved < 10)
            checkConnections(pixels);
        return false;
    }

    private boolean checkConnections(Array<BlockPixel> pixels) {
        Sandbag bag = checkBagToDisappear(pixels);
        if (bag != null)
            disappearing = new DisappearingAnim(bag, bag.size() / 20, bag.sand.iterator());

        return disappearing == null;
    }

    private Sandbag checkBagToDisappear(Array<BlockPixel> pixels) {
        Set<Sandbag> bags = new HashSet<>();
        pixels.forEach(p -> {
            if (p != null && p.sandbag != null)
                bags.add(p.sandbag);
        });
        for (Sandbag bag : bags)
            if (bag.connectLeftRight())
                return bag;
        return null;
    }

    private int move(Array<BlockPixel> pixels, Grid grid) {
        int moved = 0;
        for (BlockPixel p : pixels) {
            // make sand fall down if free or randomly to down left or down right if free
            if (p != null && p.sand && Rnd.instance.nextBoolean()) {
                if (p.y() > 0 && grid.get(p.x(), p.y() - 1) == null)
                    moved += p.moveSand(0, grid);
                else
                    if (p.y() > 0 && Rnd.instance.nextBoolean() && p.x() > 0 && grid.get(p.x() - 1, p.y() - 1) == null)
                        moved += p.moveSand(-1, grid);
                    else if (p.y() > 0 && p.x() < Grid.W - 1 && grid.get(p.x() + 1, p.y() - 1) == null)
                        moved += p.moveSand(1, grid);
                findBags(p, grid);
            }
        }
        return moved;
    }

    private void findBags(BlockPixel p, Grid grid) {
        Set<Sandbag> matchingBags = getMatchingBags(p, grid);

        Iterator<Sandbag> it = matchingBags.iterator();
        Sandbag rootBag;
        if (it.hasNext())
            rootBag = it.next();
        else
            rootBag = p.newBag();
        while (it.hasNext())
            rootBag.merge(it.next());
    }

    private Set<Sandbag> getMatchingBags(BlockPixel p, Grid grid) {
        Set<Sandbag> matchingBags = new HashSet<>();
        if (p.sandbag != null)
            matchingBags.add(p.sandbag);
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

class DisappearingAnim {
    private final Sandbag disappearingBag;
    private final Iterator<BlockPixel> iterator;
    private final int stepSize;

    public DisappearingAnim(Sandbag disappearingBag, int stepSize, Iterator<BlockPixel> iterator) {
        this.disappearingBag = disappearingBag;
        this.stepSize = stepSize;
        this.iterator = iterator;
    }

    public boolean act(Grid grid) {
        int step = stepSize;
        while (iterator.hasNext() && step > 0) {
            BlockPixel p = iterator.next();

            if (p != null) {
                grid.setNull(p.x(), p.y());
                iterator.remove();
                step--;
            }
        }
        return iterator.hasNext();
    }

    public void end() {
        disappearingBag.dispose();
    }
}
