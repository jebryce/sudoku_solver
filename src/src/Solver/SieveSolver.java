package Solver;

import Main.Constants;

public class SieveSolver extends Solver{
    private       SieveStatus   sieveStatus = SieveStatus.NOTE_TAKE;
    private final boolean[][][] notes       = new boolean[Constants.NUM_TILES][Constants.NUM_TILES][Constants.NUM_TILES];

    public SieveSolver( final char[] board, final char[][] notes ) {
        super(board);
        loadNotes(notes);
    }

    public void updateNotes( final char[][] notes ) {
        loadNotes( notes );
    }
    public void solve() {
//        long time = System.nanoTime();
//        do {
//            step();
//        } while ( sieveStatus != SieveStatus.SOLVED  );
//        time = System.nanoTime() - time;
//        System.out.format( "This basic sieve solver took: %d nanoseconds,\n", time );
//        System.out.format( "                           or %.4f milliseconds,\n",
//                (double) time / Constants.NANO_SEC_PER_M_SEC );
//        System.out.format( "                           or %.4f seconds.\n",
//                (double) time / Constants.NANO_SEC_PER_SEC );
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
            notes[5][xCord][yCord] = true;
//            if ( tiles[xCord][yCord].isNotEmpty() ) {
//                continue;
//            }
//            tiles[xCord][yCord].setPossibleNotes();
        }
        sieveStatus = SieveStatus.SET_VALUE;
    }

    private void setValueStep() {
//        boolean didNothing = true;
//        int xCord, yCord;
//        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
//            xCord = i % Constants.NUM_TILES;
//            yCord = i / Constants.NUM_TILES;
//            if ( tiles[xCord][yCord].getNumNotes() != 1 ) {
//                continue;
//            }
//            tiles[xCord][yCord].setValueToFirstNote();
//            didNothing = false;
//        }
//        if ( didNothing ) {
//            sieveStatus = SieveStatus.SOLVED;
//        }
    }

    private void loadNotes( final char[][] notes ) {
        int xCord;
        int yCord;
        for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
            xCord = tileNum % Constants.NUM_TILES;
            yCord = tileNum / Constants.NUM_TILES;
            for ( int i = 0; i < Constants.NUM_TILES; i++ ) {
                this.notes[i][xCord][yCord] = notes[i][tileNum] == 1;
            }
        }
    }

    public char[][] getNotes() {
        int xCord;
        int yCord;
        char[][] notes = new char[Constants.NUM_TILES][Constants.TOTAL_TILES];
        for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
            xCord = tileNum % Constants.NUM_TILES;
            yCord = tileNum / Constants.NUM_TILES;
            for ( int i = 0; i < Constants.NUM_TILES; i++ ) {
                if ( this.notes[i][xCord][yCord] ) {
                    notes[i][tileNum] = 1;
                }
                else {
                    notes[i][tileNum] = 0;
                }
            }
        }
        return notes;
    }
}
