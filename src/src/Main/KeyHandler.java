package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public  boolean[]    numbersPressed = new boolean[Constants.NUM_VALUES];
    private boolean      spacePressed, backspacePressed, shiftPressed, enterPressed, commandPressed, zPressed;
    private boolean      clearTilePressed, noteModePressed, stepSolverPressed, solveBoardPressed, undoPressed;

    private StateControl stateControl;

    public void setStateControl( final StateControl stateControl ) {
        this.stateControl = stateControl;
    }

    @Override
    public void keyTyped( KeyEvent event ) {}

    @Override
    public void keyPressed( KeyEvent event ) {
        int code = event.getKeyCode();
        if ( code >= KeyEvent.VK_1 && code <= KeyEvent.VK_9 ) {
            numbersPressed[code - KeyEvent.VK_0] = true;
        }
        if ( code == KeyEvent.VK_SPACE ) {
            spacePressed = true;
        }
        if ( code == KeyEvent.VK_BACK_SPACE ) {
            backspacePressed = true;
        }
        if ( code == KeyEvent.VK_SHIFT ) {
            shiftPressed = true;
        }
        if ( code == KeyEvent.VK_ENTER ) {
            enterPressed = true;
        }
        if ( code == KeyEvent.VK_META ) { // command key on MAC
            commandPressed = true;
        }
        if ( code == KeyEvent.VK_Z ) {
            zPressed = true;
        }

        checkKeyBinds();
    }

    @Override
    public void keyReleased( KeyEvent event ) {
        int code = event.getKeyCode();
        if ( code == KeyEvent.VK_SPACE ) {
            spacePressed = false;
        }
        if ( code == KeyEvent.VK_BACK_SPACE ) {
            backspacePressed = false;
        }
        if ( code == KeyEvent.VK_SHIFT ) {
            shiftPressed = false;
        }
        if ( code == KeyEvent.VK_ENTER ) {
            enterPressed = false;
        }
        if ( code == KeyEvent.VK_META ) { // command key on MAC
            commandPressed = false;
        }
        if ( code == KeyEvent.VK_Z ) {
            zPressed = false;
        }

        checkKeyBinds();
    }

    private void checkKeyBinds() {
        clearTilePressed  = spacePressed || backspacePressed;
        noteModePressed   = shiftPressed;
        stepSolverPressed = enterPressed;
        solveBoardPressed = shiftPressed && enterPressed;
        undoPressed       = zPressed     && commandPressed;
    }

    public boolean isClearTilePressed() {
        if ( clearTilePressed ) {
            stateControl.saveState();
            clearTilePressed = false;
            return true;
        }
        return false;
    }

    public boolean isNoteModePressed() {
        return noteModePressed;
    }

    public boolean isStepSolverPressed() {
        if ( stepSolverPressed ) {
            stateControl.saveState();
            stepSolverPressed = false;
            return true;
        }
        return false;
    }

    public boolean isSolveBoardPressed() {
        if ( solveBoardPressed ) {
            stateControl.saveState();
            solveBoardPressed = false;
            return true;
        }
        return false;
    }

    public boolean isUndoPressed() {
        if ( undoPressed ) {
            undoPressed = false;
            return true;
        }
        return false;
    }
}
