package Main;

import Tiles.Tiles;

public class StateControl {
    private final int        maxHistory    = 100;
    private final Tiles[]    tilesHistory  = new Tiles[maxHistory];
    private       int        currentDepth  = 0;
    private       Tiles      linkedTiles;

    public void setLinkedTiles( final Tiles tiles ) {
        linkedTiles = tiles;
        tilesHistory[0] = linkedTiles;
    }

    public void saveState() {
        shiftHistoryRightOne();
        tilesHistory[0] = linkedTiles.clone();
    }

    public Tiles undo() {
        Tiles tiles = tilesHistory[0];
        if ( currentDepth > 1 ) {
            shiftHistoryLeftOne();
        }
        return tiles;
    }

    private void shiftHistoryLeftOne() {
        for ( int i = 1; i < maxHistory; i++ ) {
            if ( tilesHistory[i-1] == null ) {
                break;
            }
            tilesHistory[i-1] = tilesHistory[i];
        }
        currentDepth--;
    }

    private void shiftHistoryRightOne() {
        for ( int i = currentDepth; i > 0; i-- ) {
            tilesHistory[i] = tilesHistory[i-1];
        }
        if ( currentDepth < maxHistory - 1 ) {
            currentDepth++;
        }
    }
}
