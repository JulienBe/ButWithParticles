package but.with;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;
import java.util.stream.IntStream;

public class MyColor {
    public static final int COLORS = 9;
    public static final int SHADES = 6;
    public static final int MAX_SHADE = SHADES - 1;
    public static final Array<TextureRegion> textures = getTextureRegions();
    private static final int[] PIECE_COLORS = {1, 4, 5, 6};
//    private static final int[] PIECE_COLORS = {3};
    private int color = 0;
    private int shade;
    private int targetShade;
    private Tick tick;

    public MyColor(int i) {
        setColor(i);
        shade = MAX_SHADE;
        phaseIn();
        tick = new Tick(0.10f);
        tick.rndOffset();
    }

    public static int pieceColor() {
        return PIECE_COLORS[Rnd.instance.nextInt(PIECE_COLORS.length)];
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean draw(Batch batch, float x, float y, float w, float h) {
        batch.draw(textures.get(color * SHADES + shade), x, y, w, h);
        if (tick.act(Gdx.graphics.getDeltaTime())) {
            // shade += (targetShade - shade) >> 31;
            if (shade < targetShade)
                shade++;
            else if (shade > targetShade)
                shade--;
        }
        return shade == targetShade;
    }

    private static Array<TextureRegion> getTextureRegions() {
        Texture t = new Texture(Gdx.files.internal("9colors.png"));
        return IntStream.rangeClosed(0, (COLORS * SHADES) - 1)
            .mapToObj(i -> new TextureRegion(t, i % SHADES, i / SHADES, 1, 1))
            .collect(Array::new, Array::add, Array::addAll);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyColor myColor = (MyColor) o;
        return color == myColor.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    public void phaseOut() {
        targetShade = MAX_SHADE;
    }

    public void phaseIn() {
        targetShade = 1;
    }

    public MyColor bright() {
        shade = 1;
        return this;
    }
}

