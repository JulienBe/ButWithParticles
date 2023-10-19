package but.with;

import but.with.board.Grid;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    public int pixelSize;
    private final Time time = new Time();
    private Background background;
    private Grid grid; // lateinit cause libGDX
    private SpriteBatch batch;
    private Gamepad gamepad;

    @Override
    public void create() {
        pixelSize = (int) (Gdx.graphics.getHeight() / 240.0f);
        batch = new SpriteBatch();
        grid = new Grid();
        grid.addPiece();
        background = new Background();
        gamepad = new Gamepad();
        gamepad.addHandler(grid);
        Gdx.input.setInputProcessor(gamepad);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5))
            grid.addPiece();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        time.act(Gdx.graphics.getDeltaTime());
        grid.act(time);
        batch.begin();
        background.display(batch);
        grid.display(batch, pixelSize);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
