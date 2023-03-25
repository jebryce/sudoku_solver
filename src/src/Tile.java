import java.awt.Graphics2D;

public class Tile {
    private final int  xPos;
    private final int  yPos;
    private       int        value;
    private       TileStatus tileStatus = TileStatus.UNSET;

    public Tile( final int xCord, final int yCord ) {
        xPos = xCord * Constants.TILE_SIZE;
        yPos = yCord * Constants.TILE_SIZE;
    }

    public boolean setFinalValue( final int finalValue ) {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return false;
        }
        value = finalValue;
        tileStatus = TileStatus.SET_FINAL;
        return true;
    }

    public boolean setValue( final int newValue ) {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return false;
        }
        value = newValue;
        tileStatus = TileStatus.SET;
        return true;
    }

    public boolean clearValue() {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return false;
        }
        tileStatus = TileStatus.UNSET;
        return true;
    }

    public int getValue() {
        if ( tileStatus != TileStatus.UNSET ) {
            return value;
        }
        return -1;
    }

    public void repaint( final Graphics2D graphics2D ) {
        graphics2D.setColor( Colors.EGGSHELL );
        graphics2D.fillRect( xPos, yPos, Constants.TILE_SIZE, Constants.TILE_SIZE );
    }
}
