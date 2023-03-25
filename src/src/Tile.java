import java.awt.Graphics2D;

public class Tile {
    private final int        xPos;
    private final int        yPos;
    private       int        value;
    private       TileStatus tileStatus = TileStatus.UNSET;

    public Tile( final int xCord, final int yCord ) {
        xPos = xCord * Constants.TILE_SIZE;
        yPos = yCord * Constants.TILE_SIZE;
    }

    public void setFinalValue( final int finalValue ) {
        if ( valueIsInvalid( finalValue ) ) {
            return;
        }
        value = finalValue;
        tileStatus = TileStatus.SET_FINAL;
    }

    public void setValue( final int newValue ) {
        if ( valueIsInvalid( newValue ) ) {
            return;
        }
        value = newValue;
        tileStatus = TileStatus.SET;
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
        if ( tileStatus != TileStatus.UNSET ) {
            graphics2D.setColor( Colors.CHARCOAL_GRAY );
            graphics2D.drawString(
                    String.valueOf(value),
                    xPos + Constants.TILE_TEXT_X_OFFSET,
                    yPos + Constants.TILE_TEXT_Y_OFFSET
            );
        }
    }

    private boolean valueIsInvalid( final int newValue ) {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return true;
        }
        if ( newValue < 0 ) {
            return true;
        }
        if ( newValue > 9 ) {
            return true;
        }
        return false;
    }
}
