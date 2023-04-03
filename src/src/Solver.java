
public class Solver {
    // brute force
    private final int     NOT_FOUND = -1;
    private final int[][] tiles;

    public Solver( final char[] board ) {
        this.tiles = loadBoard( board );
    }

    public char[] step() {
        // first find the first empty tile
        final int tileNum = findFirstEmptyTile();

        // then, if there is an empty tile, try to find a placeable value
        if ( tileNum != NOT_FOUND ) {
            final int value = findPlaceableValue( tileNum );
            // if there is a placeable value, place it!
            if ( value != NOT_FOUND ) {
                placeAValue( value, tileNum );
            }
        }

        return saveBoard();
    }

    private int findFirstEmptyTile() {
        final int EMPTY_TILE = 0;
        int x, y;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            x = i % Constants.NUM_TILES;
            y = i / Constants.NUM_TILES;
            if ( tiles[x][y] == EMPTY_TILE ) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private int findPlaceableValue( final int tileNum ) {
        for ( int value = 1; value <= Constants.NUM_TILES; value++ ) {
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

    private char[] saveBoard() {
        char newBoardValue;
        char[] board = new char[Constants.TOTAL_TILES];
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            newBoardValue  = (char) tiles[i%Constants.NUM_TILES][i/Constants.NUM_TILES];
            newBoardValue += '0';
            board[i]       = newBoardValue;
        }
        return board;
    }

    private int[][] loadBoard( final char[] board ) {
        int[][] tiles = new int[Constants.NUM_TILES][Constants.NUM_TILES];
        int xCord;
        int yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            if ( board[i] >= '1' && board[i] <= '9' ) {
                tiles[xCord][yCord] = board[i] - '0';
            }
            else if ( board[i] >= 'A' && board[i] <= 'I' ) {
                tiles[xCord][yCord] = board[i] - 'A' + 1;
            }
            else {
                tiles[xCord][yCord] = 0;
            }
        }
        return tiles;
    }
}

