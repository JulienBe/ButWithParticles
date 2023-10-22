package but.with;

public class Time {
    public Tick pieces = new Tick(0.050f);
    public Tick sand = new Tick(0.020f);

    public void act(float deltaTime) {
        pieces.act(deltaTime);
        sand.act(deltaTime);
    }
}
