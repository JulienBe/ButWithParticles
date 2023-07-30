package but.with;

import java.util.Random;

public class Rnd extends Random {
    public static final Rnd instance = new Rnd();

    public Rnd() {
    }

    public Rnd(long seed) {
        super(seed);
    }

}
