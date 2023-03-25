
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseMotionListener {
    public int xPos;
    public int yPos;

    @Override
    public void mouseDragged( MouseEvent e ) {
    }

    @Override
    public void mouseMoved( MouseEvent e ) {
        xPos = e.getX();
        yPos = e.getY();
    }
}
