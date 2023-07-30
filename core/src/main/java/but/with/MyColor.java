package but.with;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.stream.IntStream;

public class MyColor {
    public static final int COLOR_MAX = 15;
    public static final int SHADE_MAX = 4;
    public static final Array<TextureRegion> textures = getTextureRegions();

    private static Array<TextureRegion> getTextureRegions() {
        Texture t = new Texture(Gdx.files.internal("palette_pico8.png"));
        return IntStream.rangeClosed(0, COLOR_MAX * SHADE_MAX)
            .mapToObj(i -> new TextureRegion(t, i % SHADE_MAX, i / COLOR_MAX, 1, 1))
            .collect(Array::new, Array::add, Array::addAll);
    }

    private int color = 0;
    private int shade = 0;

    public void draw(Batch batch, int x, int y, float w, float h) {
        batch.draw(textures.get(color * COLOR_MAX + shade), x, y, w, h);
    }
}

