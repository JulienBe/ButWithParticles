package but.with;

import but.with.board.Grid;

import java.util.HashSet;
import java.util.Set;

/**
 * A bag of sand of the same color that is touching each other
 */
public class Sandbag {
    public Set<BlockPixel> sand = new HashSet<>();

    Sandbag(BlockPixel p) {
        sand.add(p);
    }

    public void add(BlockPixel p) {
        sand.add(p);
    }

    public void remove(BlockPixel p) {
        sand.remove(p);
    }

    public MyColor getColor() {
        return sand.isEmpty() ? null : sand.iterator().next().color;
    }

    public void merge(Sandbag other) {
        sand.addAll(other.sand);
        other.sand.forEach(p -> p.setBag(this));
    }

    public boolean connectLeftRight() {
        return sand.stream().anyMatch(p -> p.x() == 0) && sand.stream().anyMatch(p -> p.x() == Grid.W - 1);
    }

    public boolean colorMatch(Sandbag sandbag) {
        if (sandbag == null)
            return false;
        return sandbag.getColor().equals(getColor());
    }
}
