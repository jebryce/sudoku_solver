package Solver;

import Main.Constants;
import Tiles.Tiles;
import Tiles.Tile;

public class SieveSolver {
    private final Tile[][]    tiles;
    private       SieveStatus sieveStatus = SieveStatus.NOTE_TAKE;

    public SieveSolver( final Tiles tiles ) {
        this.tiles = tiles.getTiles();
    }

    public void solve() {
        long time = System.nanoTime();
        do {
            step();
        } while ( sieveStatus != SieveStatus.SOLVED  );
        time = System.nanoTime() - time;
        System.out.format( "This basic sieve solver took: %d nanoseconds,\n", time );
        System.out.format( "                           or %.4f milliseconds,\n",
                (double) time / Constants.NANO_SEC_PER_M_SEC );
        System.out.format( "                           or %.4f seconds.\n",
                (double) time / Constants.NANO_SEC_PER_SEC );
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
        }
        sieveStatus = SieveStatus.SET_VALUE;
    }

    private void setValueStep() {
        boolean didNothing = true;
        int xCord, yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            if ( tiles[xCord][yCord].getNumNotes() != 1 ) {
                continue;
            }
            tiles[xCord][yCord].setValueToFirstNote();
            didNothing = false;
        }
        if ( didNothing ) {
            sieveStatus = SieveStatus.SOLVED;
        }
    }
}
