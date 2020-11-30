package Control;

import Audio.SoundAccessor;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.util.HashSet;
import java.util.List;

public class KeyHandler implements NativeKeyListener {
    private final SoundAccessor accessor;
    private final HashSet<Integer> keyAlreadyPressed = new HashSet();
    private final List<Integer> keys;

    public KeyHandler(SoundAccessor accessor, List<Integer> keys) {
        this.accessor = accessor;
        this.keys = keys;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if(keyAlreadyPressed.contains(e.getKeyCode())) return;
        keyAlreadyPressed.add(e.getKeyCode());

        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        /*if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }
        if(e.getKeyCode()==NativeKeyEvent.VC_0){
            accessor.play(0);
        }
        if(e.getKeyCode()==NativeKeyEvent.VC_1){
            accessor.play(1);
        }*/
        if(keys.contains(e.getKeyCode()))
            accessor.play(keys.indexOf(e.getKeyCode()));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        keyAlreadyPressed.remove(e.getKeyCode());
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }
}
