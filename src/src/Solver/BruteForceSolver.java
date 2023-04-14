package Solver;

import Main.Constants;

public class BruteForceSolver extends Solver {
    private final int         EMPTY_TILE = 0;
    private final int         NOT_FOUND  = -1;

    public BruteForceSolver( final char[] board ) {
        super( board );
    }

    public void solve() {
        long time = System.nanoTime();
        do {
            step();
        } while ( findFirstEmptyTile() != NOT_FOUND ); // @TODO figure out a way to not get stuck on unsolvable boards
        time = System.nanoTime() - time;
        System.out.format( "This \"smart\" brute-force solver took: %d nanoseconds,\n", time );
        System.out.format( "                                   or %.4f milliseconds,\n",
                (double) time / Constants.NANO_SEC_PER_M_SEC );
        System.out.format( "                                   or %.4f seconds.\n",
                (double) time / Constants.NANO_SEC_PER_SEC );
    }

    public void step() {
        // first find the first empty tile
        int tileNum = findFirstEmptyTile();

        // if there isn't an empty tile, the board is full, and (hopefully) it is solved!
        if ( tileNum == NOT_FOUND ) {
            return;
        }

        int value;
        do {
            value = findPlaceableValue( tileNum );

            // if no values are possible, erase the tile then find the last changeable tile
            if ( value == NOT_FOUND ) {
                eraseTile( tileNum );
                tileNum = findLastChangeableTile();
            }
            // else if there is a placeable value, place it!
            else {
                placeAValue( value, tileNum );
                return;
            }
        } while ( tileNum != NOT_FOUND );
        // most of the time if you reach here the board is unsolvable.
        // there is an edge case, but I am deciding not to worry about it right now
        // try board: "12345678000000000I000000000000000000000000000000000000000000000000000000000000000" to showcase it
    }

    private int findFirstEmptyTile() {
        int xCord, yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            // brute force
            int EMPTY_TILE = 0;
            if ( tiles[xCord][yCord] == EMPTY_TILE ) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private int findPlaceableValue( final int tileNum  ) {
        int x = tileNum % Constants.NUM_TILES;
        int y = tileNum / Constants.NUM_TILES;

        // if this method is called on a blank tile, value = 0,
        // then the first iteration of the while loop checks for the value of 1
        //
        // if this method is called on a filled tile - that needs replacing,
        // then the first iteration of the while loop checks for the filled tile's value +1
        int value = tiles[x][y];
        while ( value < Constants.NUM_TILES ) {
            value++;
            if ( isValueTaken( value, tileNum ) ) {
                continue;
            }
            return value;
        }
        return NOT_FOUND;
    }

    private boolean isValueTaken( final int value, final int tileNum ) {
        final int xCord = tileNum % Constants.NUM_TILES;
        final int yCord = tileNum / Constants.NUM_TILES;

        // is the value in the row / col
        for ( int i = 0; i < Constants.NUM_TILES; i++ ) {
            if ( value == tiles[i][yCord] ) {
                return true;
            }
            if ( value == tiles[xCord][i] ) {
                return true;
            }
        }

        // is the value in the areas of the box that haven't been checked
        final int baseY = yCord - yCord % 3;
        final int baseX = xCord - xCord % 3;
        int testY, testX;
        for ( int i = 0; i < 4; i++ ) {
            testY = ( i / 2 + yCord + 1 ) % 3 + baseY;
            testX = ( i % 2 + xCord + 1 ) % 3 + baseX;
            if ( value == tiles[testX][testY] ) {
                return true;
            }
        }

        // if the value is in neither area
        return false;
    }

    private void placeAValue( final int value, final int tileNum ) {
        final int xCord = tileNum % Constants.NUM_TILES;
        final int yCord = tileNum / Constants.NUM_TILES;
        tiles[xCord][yCord] = value;
    }

    private int findLastChangeableTile() {
        int x, y;
        for ( int i = Constants.TOTAL_TILES - 1; i >= 0; i-- ) {
            x = i % Constants.NUM_TILES;
            y = i / Constants.NUM_TILES;
            if ( tiles[x][y] == EMPTY_TILE ) {
                continue;
            }
            if ( finalTiles[x][y] ) {
                continue;
            }
            // if the tile isn't empty or final
            return i;
        }
        return NOT_FOUND;
    }

    private void eraseTile( final int tileNum ) {
        final int xCord = tileNum % Constants.NUM_TILES;
        final int yCord = tileNum / Constants.NUM_TILES;
        if ( finalTiles[xCord][yCord] ) {
            return;
        }
        tiles[xCord][yCord] = EMPTY_TILE;
    }
}

