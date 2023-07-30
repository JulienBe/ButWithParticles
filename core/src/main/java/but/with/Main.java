package but.with;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private final Time time = new Time();
    private Grid grid; // lateinit cause libGDX
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        grid = new Grid();
        grid.addBlock();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        time.act(Gdx.graphics.getDeltaTime());
        grid.act(time);
        batch.begin();
        grid.display(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
