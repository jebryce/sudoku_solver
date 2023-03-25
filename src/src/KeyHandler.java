import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean[] numbersPressed   = new boolean[Constants.NUM_VALUES];
    public boolean   backspacePressed;

    @Override
    public void keyTyped( KeyEvent event ) {}

    @Override
    public void keyPressed( KeyEvent event ) {
        int code = event.getKeyCode();
        if ( code >= KeyEvent.VK_1 && code <= KeyEvent.VK_9 ) {
            numbersPressed[code - KeyEvent.VK_0] = true;
        }
        if ( code == KeyEvent.VK_BACK_SPACE ) {
            backspacePressed = true;
        }
    }

    @Override
    public void keyReleased( KeyEvent event ) {}
}
