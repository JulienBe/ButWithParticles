package but.with;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private final Array<Block> blocks = new Array<>();
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        blocks.add(new Block());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        blocks.forEach(b -> b.display(batch));
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
