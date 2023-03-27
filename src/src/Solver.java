public class Solver {
    // brute force
    private final int[][] tiles;

    public Solver( final char[] board ) {
        this.tiles = BoardIO.boardToInt( board );
    }


    private boolean isValueInRowCol( final int value, final int xCord, final int yCord ) {
        for ( int i = 0; i < Constants.NUM_TILES; i++ ) {
            if ( value == tiles[i][yCord] ) {
                return true;
            }
            if ( value == tiles[xCord][i] ) {
                return true;
            }
        }
        return false;
    }

    private boolean isValueInBox( final int value, final int xCord, final int yCord ) {
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
        return false;
    }

}

