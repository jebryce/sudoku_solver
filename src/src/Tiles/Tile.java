package Tiles;

import Main.Constants;

import java.util.Arrays;

public class Tile implements Cloneable {
    protected int        value        = 0;
    protected TileStatus tileStatus   = TileStatus.UNSET;
    protected boolean[]  notes        = new boolean[Constants.NUM_TILES];
    private   int        numNotes     = 0;
    private   Tile[]     visibleTiles = new Tile[Constants.TOTAL_TILES];

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

    public boolean[] getNotes() {
        return notes;
    }

    public int getValue() {
        return value;
    }

    public TileStatus getTileStatus() {
        return tileStatus;
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
