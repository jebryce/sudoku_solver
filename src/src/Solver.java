public class Solver {
    // brute force
    private final int       maxDepth     = Constants.NUM_TILES * Constants.NUM_TILES;
    private final int[][][] boards       = new int[maxDepth][Constants.NUM_TILES][Constants.NUM_TILES];
    private       int       currentDepth = 0;

    public Solver( final char[] board ) {
        this.boards[0] = BoardIO.boardToInt( board );
        solve();
    }

    private void solve() {
        // first find the first empty tile
        int tileNum = findFirstEmptyTile();
        if ( tileNum == -1 ) { // if all tiles are filled, then board is solved!
            return;
        }
        // find a the first value that is placeable on the tile
        findPlaceableValue( tileNum );
    }

    private int findFirstEmptyTile() {
        int[][] tiles = boards[currentDepth];
        int x, y;
        for ( int i = 0; i < Constants.NUM_TILES * Constants.NUM_TILES; i++ ) {
            x = i % Constants.NUM_TILES;
            y = i / Constants.NUM_TILES;
            if ( tiles[x][y] == 0 ) {
                return i;
            }
        }
        return -1;
    }

    private void findPlaceableValue( final int tileNum ) {
        boolean valuePlaced = false;
        for ( int value = 1; value <= Constants.NUM_TILES; value++ ) {
            if ( isValueTaken( value, tileNum ) ) {
                continue;
            }
            valuePlaced = true;
            createACopy();
            placeAValue( value, tileNum );
            solve();
        }
        if ( valuePlaced ) {
            currentDepth--;
        }
    }

    private void placeAValue( final int value, final int tileNum ) {
        final int xCord = tileNum % Constants.NUM_TILES;
        final int yCord = tileNum / Constants.NUM_TILES;
        boards[currentDepth][xCord][yCord] = value;
    }

    private void createACopy() {
        int xCord, yCord;
        for ( int i = 0; i < Constants.NUM_TILES * Constants.NUM_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            boards[currentDepth + 1][xCord][yCord] = boards[currentDepth][xCord][yCord];
        }
        currentDepth++;
    }

    private boolean isValueTaken( final int value, final int tileNum ) {
        final int xCord = tileNum % Constants.NUM_TILES;
        final int yCord = tileNum / Constants.NUM_TILES;
        int[][] tiles = boards[currentDepth];

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

}

