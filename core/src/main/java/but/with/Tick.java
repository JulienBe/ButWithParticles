package but.with;

public class Tick {
    private final float tick;
    private float time = 0f;
    private int currentTick = 0;
    public boolean act = false;

    public Tick(float tick) {
        this.tick = tick;
    }

    public void rndOffset() {
        time += Rnd.instance.nextFloat(tick);
    }

    public boolean act(float deltaTime) {
        act = false;
        time += deltaTime;
        if (time > tick * currentTick) {
            currentTick++;
            act = true;
        }
        return act;
    }
}
