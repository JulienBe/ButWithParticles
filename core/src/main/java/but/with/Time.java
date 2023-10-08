package but.with;

public class Time {
    public Tick pieces = new Tick(0.050f);
    public Tick sand = new Tick(0.020f);

    public void act(float deltaTime) {
        pieces.act(deltaTime);
        sand.act(deltaTime);
    }

    class Tick {
        private float tick;
        private float time = 0f;
        private int currentTick = 0;
        boolean act = false;

        public Tick(float tick) {
            this.tick = tick;
        }

        public void act(float deltaTime) {
            act = false;
            time += deltaTime;
            if (time > tick * currentTick) {
                currentTick++;
                act = true;
            }
        }
    }
}
