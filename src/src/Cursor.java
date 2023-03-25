import java.awt.Graphics2D;

public class Cursor {
    private final MouseHandler mouseHandler;

    public Cursor( MouseHandler mouseHandler ) {
        this.mouseHandler = mouseHandler;
    }

    public void repaint( Graphics2D graphics2D ) {
        int xCord = mouseHandler.xPos / Constants.TILE_SIZE;
        int yCord = mouseHandler.yPos / Constants.TILE_SIZE;
        xCord *= Constants.TILE_SIZE;
        yCord *= Constants.TILE_SIZE;

        graphics2D.setColor( Colors.BABY_BLUE );
        graphics2D.fillRect( xCord, yCord, Constants.TILE_SIZE, Constants.TILE_SIZE );
    }
}
