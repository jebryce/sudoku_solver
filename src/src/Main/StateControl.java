package Main;

import Tiles.Tile;
import Tiles.Tiles;
import Tiles.TileStatus;

public class StateControl {
    private final int        maxHistory    = 100;
    private final Tile[][][] tilesHistory  = new Tile[maxHistory][Constants.NUM_TILES][Constants.NUM_TILES];
    private       int        currentDepth  = 0;
    private final Tiles      originalTiles;

    public StateControl( final Tiles tiles ) {
        originalTiles = tiles;

    }

    public void saveState() {
        Tile[][] tiles = originalTiles.getTiles();
        Tile[][] newTiles = new Tile[Constants.NUM_TILES][Constants.NUM_TILES];
        int xCord, yCord;
        for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
            xCord = tileNum % Constants.NUM_TILES;
            yCord = tileNum / Constants.NUM_TILES;
            newTiles[xCord][yCord] = tiles[xCord][yCord].clone();
        }
        shiftHistoryRightOne();
        tilesHistory[0] = newTiles;
    }

    public Tile[][] undo() {
        Tile[][] tiles = tilesHistory[0];
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
        for ( int i = currentDepth++; i > 0; i-- ) {
            tilesHistory[i] = tilesHistory[i-1];
        }
    }
}
