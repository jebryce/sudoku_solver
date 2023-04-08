package Solver;

import Main.Constants;
import Tiles.Tiles;
import Tiles.Tile;

public class SieveSolver {
    private final Tile[][] tiles;

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
        int xCord;
        int yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            if ( tiles[xCord][yCord].isNotEmpty() ) {
                continue; // eventually replace with Enum for state of solver
            }
            tiles[xCord][yCord].setPossibleNotes();
            return;
        }
    }
}
