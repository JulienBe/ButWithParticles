package but.with;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.util.*;
import java.util.function.Consumer;

public class Gamepad extends InputAdapter {

    private static final Map<Integer, Consumer<InputHandler>> keyMap = initKeyMap();

    private static Map<Integer, Consumer<InputHandler>> initKeyMap() {
        Map<Integer, Consumer<InputHandler>> map = new HashMap<>();

        map.put(Input.Keys.NUMPAD_4, InputHandler::onLeft);
        map.put(Input.Keys.Q, InputHandler::onLeft);
        map.put(Input.Keys.A, InputHandler::onLeft);
        map.put(Input.Keys.LEFT, InputHandler::onLeft);

        map.put(Input.Keys.RIGHT, InputHandler::onRight);
        map.put(Input.Keys.NUMPAD_6, InputHandler::onRight);
        map.put(Input.Keys.D, InputHandler::onRight);

        map.put(Input.Keys.UP, InputHandler::onRotate);
        map.put(Input.Keys.NUMPAD_8, InputHandler::onRotate);
        map.put(Input.Keys.W, InputHandler::onRotate);
        map.put(Input.Keys.SPACE, InputHandler::onRotate);
        map.put(Input.Keys.NUMPAD_5, InputHandler::onRotate);
        return map;
    }

    private final List<InputHandler> handlers = new ArrayList<>();

    @Override
    public boolean keyDown(int keycode) {
        Consumer<InputHandler> fun = keyMap.getOrDefault(keycode, InputHandler::notMappedKey);
        handlers.forEach(fun);
        return super.keyDown(keycode);
    }

    public void addHandler(InputHandler handler) {
        handlers.add(handler);
    }
}
