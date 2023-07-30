package but.with;

public class Time {
    private float time = 0f;
    private float tick = 0.3f;
    private int currentTick = 0;
    boolean justTicked = false;

    public void act(float deltaTime) {
        justTicked = false;
        time += deltaTime;
        if (time > tick * currentTick) {
            currentTick++;
            justTicked = true;
        }
    }
}
