package Tiles;

import Main.Colors;
import Main.Constants;

import java.awt.Graphics2D;
import java.util.Arrays;

public class Tile implements Cloneable {
    private final int        xPos;
    private final int        yPos;
    private       int        value        = 0;
    private       TileStatus tileStatus   = TileStatus.UNSET;
    private       boolean[]  notes        = new boolean[Constants.NUM_TILES];
    private       int        numNotes     = 0;
    private       Tile[]     visibleTiles = new Tile[Constants.TOTAL_TILES];

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
        setVisibleDuplicates();
    }

    public void setValue( final int newValue ) {
        if ( valueIsInvalid( newValue ) ) {
            return;
        }
        clear();
        value = newValue;
        tileStatus = TileStatus.SET;
        setVisibleDuplicates();
        unsetVisibleNotes( value );
    }

    public void clear() {
        if ( tileStatus == TileStatus.SET_FINAL ) {
            return;
        }
        Arrays.fill( notes, false );
        numNotes = 0;
        value = 0;
        tileStatus = TileStatus.UNSET;
        clearVisibleDuplicates();
    }

    public void setNote( final int newNote ) {
        if ( tileStatus != TileStatus.UNSET ) {
            return;
        }
        notes[newNote - 1] = !notes[newNote - 1];
        if ( notes[newNote - 1] ) {
            numNotes++;
        }
        else {
            numNotes--;
        }
    }

    public boolean[] getNotes() {
        return notes;
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

    public boolean isTileVisible( final Tile tile ) {
        for ( Tile visibleTile : visibleTiles ) {
            if ( visibleTile == null ) {
                return false;
            }
            if ( tile == visibleTile ) {
                return true;
            }
        }
        return false;
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

    public boolean isValueInNotes( final int value ) {
        if ( value == 0 ) {
            return false;
        }
        if ( tileStatus == TileStatus.UNSET ) {
            return notes[value-1];
        }
        return false;
    }

    public boolean isNotEmpty() {
        if ( tileStatus != TileStatus.UNSET ) {
            return true;
        }
        if ( numNotes != 0 ) {
            return true;
        }
        return false;
    }

    protected void setVisibleDuplicates() {
        if ( tileStatus == TileStatus.UNSET ) {
            return;
        }
        unsetDuplicated();
        for ( Tile visibleTile : visibleTiles ) {
            if ( visibleTile == null ) {
                return;
            }
            if ( visibleTile.getValue() == value ) {
                visibleTile.setDuplicated();
                setDuplicated();
            }
        }
    }

    private void clearVisibleDuplicates() {
        for ( Tile visibleTile : visibleTiles ) {
            if ( visibleTile == null ) {
                return;
            }
            if ( visibleTile.getTileStatus() == TileStatus.DUPLICATE ) {
                visibleTile.setVisibleDuplicates();
            }
        }
    }

    private void unsetVisibleNotes( final int noteNumber ) {
        for ( Tile visibleTile : visibleTiles ) {
            if ( visibleTile == null ) {
                return;
            }
            if ( visibleTile.getTileStatus() == TileStatus.UNSET ) {
                visibleTile.unsetNote( noteNumber );
            }
        }
    }

    private void unsetNote( final int noteNumber ) {
        if ( tileStatus != TileStatus.UNSET ) {
            return;
        }
        if ( noteNumber == 0 ) {
            return;
        }
        if ( notes[noteNumber - 1] ) {
            setNote( noteNumber ); // setNote keeps numNotes correct, so best to call instead of duplicating code
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

    @Override
    public Tile clone() {
        try {
            Tile tile         = (Tile) super.clone();
            tile.notes        = notes.clone();
            tile.visibleTiles = new Tile[Constants.TOTAL_TILES];
            return tile;
        } catch ( CloneNotSupportedException e ) {
            throw new AssertionError();
        }
    }
}
