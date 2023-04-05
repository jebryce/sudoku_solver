package Main;

import java.awt.*;

public class Menu {
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    public Menu( final KeyHandler keyHandler, final MouseHandler mouseHandler ) {
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    }

    public void update() {

    }

    public void repaint( Graphics2D graphics2D ) {
        graphics2D.setColor( Colors.CACTUS_GREEN );
        graphics2D.fillRect( 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT );
    }
}
