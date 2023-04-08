package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseMotionListener, MouseListener {
    public int xPos;
    public int yPos;
    private boolean mouseClick;

    @Override
    public void mouseDragged( MouseEvent e ) {
    }

    @Override
    public void mouseMoved( MouseEvent e ) {
        xPos = e.getX();
        yPos = e.getY();
    }

    @Override
    public void mouseClicked( MouseEvent e ) { }

    @Override
    public void mousePressed( MouseEvent e ) {
        mouseClick = true;
    }

    @Override
    public void mouseReleased( MouseEvent e ) { }

    @Override
    public void mouseEntered( MouseEvent e ) {}

    @Override
    public void mouseExited( MouseEvent e ) { }

    public boolean isMouseClicked() {
        if ( mouseClick ) {
            mouseClick = false;
            return true;
        }
        return false;
    }
}
