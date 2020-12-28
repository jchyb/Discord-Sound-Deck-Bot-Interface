package Control;

import Audio.SoundAccessor;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.util.HashSet;
import java.util.List;

public class KeyHandler implements NativeKeyListener {
    private final SoundAccessor accessor;
    private final HashSet<Integer> keyAlreadyPressed = new HashSet<Integer>();
    private final List<Integer> keys;

    public KeyHandler(SoundAccessor accessor, List<Integer> keys) {
        this.accessor = accessor;
        this.keys = keys;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if(keyAlreadyPressed.contains(e.getKeyCode())) return;
        keyAlreadyPressed.add(e.getKeyCode());

        if(keys.contains(e.getKeyCode())) {
            System.out.println("Key pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
            accessor.play(keys.indexOf(e.getKeyCode()));
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        keyAlreadyPressed.remove(e.getKeyCode());
        System.out.println("Key released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key code typed: " + e.getKeyText(e.getKeyCode()));
    }
}
