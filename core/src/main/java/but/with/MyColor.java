package but.with;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.stream.IntStream;

public class MyColor {
    public static final int COLORS = 16;
    public static final int SHADES = 6;
    public static final Array<TextureRegion> textures = getTextureRegions();
    private int color = 0;
    private int shade = 0;

    public MyColor() {}

    public MyColor(int i) {
        setColor(i);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void draw(Batch batch, float x, float y, float w, float h) {
        batch.draw(textures.get(color * SHADES + shade), x, y, w, h);
    }

    private static Array<TextureRegion> getTextureRegions() {
        Texture t = new Texture(Gdx.files.internal("palette_pico8.png"));
        return IntStream.rangeClosed(0, (COLORS * SHADES) - 1)
            .mapToObj(i -> new TextureRegion(t, i % SHADES, i / SHADES, 1, 1))
            .collect(Array::new, Array::add, Array::addAll);
    }
}

