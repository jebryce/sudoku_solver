import java.awt.Graphics2D;

public class Tile {
    private final int        xPos;
    private final int        yPos;
    private       int        value        = 0;
    private       TileStatus tileStatus   = TileStatus.UNSET;
    private final boolean[]  notes        = new boolean[Constants.NUM_TILES];
    private final Tile[]     visibleTiles = new Tile[Constants.TOTAL_TILES];

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

    public void clear() {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return;
        }
        for ( int i = 0; i < Constants.NUM_TILES; i++ ) {
            notes[i] = false;
        }
        value = 0;
        tileStatus = TileStatus.UNSET;
    }

    public void setDuplicated() {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return;
        }
        tileStatus = TileStatus.DUPLICATE;
    }

    public void unsetDuplicated() {
        if ( tileStatus != TileStatus.DUPLICATE ) {
            return;
        }
        tileStatus = TileStatus.SET;
    }

    public void setNote( final int newNote ) {
        if ( tileStatus != TileStatus.UNSET ) {
            return;
        }
        notes[newNote - 1] = !notes[newNote - 1];
    }

    public int getValue() {
        return value;
    }

    public TileStatus getTileStatus() {
        return tileStatus;
    }

    public void repaintBackground( final Graphics2D graphics2D ) {
        graphics2D.fillRect( xPos, yPos, Constants.TILE_SIZE, Constants.TILE_SIZE );
    }

    public void repaintText( final Graphics2D graphics2D ) {
        if ( tileStatus == TileStatus.UNSET ) {
            return;
        }
        else if ( tileStatus == TileStatus.SET ) {
            graphics2D.setColor( Colors.CHARCOAL_GRAY );
        }
        else if ( tileStatus == TileStatus.SET_FINAL) {
            graphics2D.setColor( Colors.BLACK );
        }
        else if ( tileStatus == TileStatus.DUPLICATE ) {
            graphics2D.setColor( Colors.CORAL_PINK );
        }
        graphics2D.drawString(
                String.valueOf(value),
                xPos + Constants.TILE_TEXT_X_OFFSET,
                yPos + Constants.TILE_TEXT_Y_OFFSET
        );
    }

    public void repaintNotes( final Graphics2D graphics2D ) {
        if ( tileStatus != TileStatus.UNSET ) {
            return;
        }
        graphics2D.setColor( Colors.CHARCOAL_GRAY );
        float xOffset;
        int yOffset;
        int ct = 0;
        for ( int i = 0; i < Constants.NUM_TILES; i++ ) {
            if ( notes[i] ) {
                xOffset = Constants.NOTES_X_OFFSET + ( ct%3 ) * ( Constants.NOTES_TEXT_WIDTH + Constants.NOTES_X_OFFSET );
                yOffset = ( ct/3 + 1)  * ( Constants.NOTES_TEXT_HEIGHT + Constants.NOTES_Y_OFFSET );
                graphics2D.drawString(
                        String.valueOf(i+1),
                        xPos + xOffset,
                        yPos + yOffset
                );
                ct++;
            }
        }
    }

    public void setVisibleTile( final Tile newTile ) {
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            if ( visibleTiles[i] == null ) {
                visibleTiles[i] = newTile;
                return;
            }
        }
    }

    private boolean valueIsInvalid( final int newValue ) {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return true;
        }
        if ( newValue < 1 ) {
            return true;
        }
        if ( newValue > 9 ) {
            return true;
        }
        return false;
    }
}
