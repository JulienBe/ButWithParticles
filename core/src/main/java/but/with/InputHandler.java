package but.with;

public interface InputHandler {
    default void onLeft() {}
    default void onRight() {}
    default void notMappedKey() {}
}
