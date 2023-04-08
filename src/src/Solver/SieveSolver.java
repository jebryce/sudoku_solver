package Solver;

import Main.Constants;
import Tiles.Tiles;
import Tiles.TileStatus;
import Tiles.Tile;

public class SieveSolver {
    private final Tile[][]    tiles;
    private       SieveStatus sieveStatus = SieveStatus.NOTE_TAKE;

    public SieveSolver( final Tiles tiles ) {
        this.tiles = tiles.getTiles();
    }

    public void step() {
        //  first, check if any tile is empty with no notes
        //      if found, write down all possible values
        //          if no notes can be written, board is unsolvable ------------------> return
        //          else -------------------------------------------------------------> return
        //  then check if any tile is empty with no value ( but has notes )
        //      if that tile only has 1 possible value, place it ---------------------> return
        //      else continue
        //  finally, if you get here, either:
        //      all tiles are filled and board is solved -----------------------------> return
        //      or board needs more advanced logic to solve, so give up --------------> return
        switch ( sieveStatus ) {
            case UNSOLVABLE -> {}
            case NOTE_TAKE -> stepNoteTake();
            case SET_VALUE -> setValueStep();
        }
    }

    private void stepNoteTake() {
        int xCord, yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            if ( tiles[xCord][yCord].isNotEmpty() ) {
                continue;
            }
            tiles[xCord][yCord].setPossibleNotes();
            if ( tiles[xCord][yCord].isNotEmpty() ) {
                return;
            }
            sieveStatus = SieveStatus.UNSOLVABLE;
        }
        sieveStatus = SieveStatus.SET_VALUE;
    }

    private void setValueStep() {
        int xCord, yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            if ( tiles[xCord][yCord].getNumNotes() != 1 ) {
                continue;
            }
            tiles[xCord][yCord].setValueToFirstNote();
            return;
        }
        sieveStatus = SieveStatus.SOLVED;
    }
}
