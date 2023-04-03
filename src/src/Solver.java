
public class Solver {
    private final int         EMPTY_TILE = 0;
    private final int         NOT_FOUND  = -1;
    private final int[][]     tiles      = new int[Constants.NUM_TILES][Constants.NUM_TILES];
    private final boolean[][] finalTiles = new boolean[Constants.NUM_TILES][Constants.NUM_TILES];

    public Solver( final char[] board ) {
        loadBoard( board );
        loadFinal( board );
    }

    public char[] step() {
        // first find the first empty tile
        int tileNum = findFirstEmptyTile();

        // if there isn't an empty tile, the board is full, and (hopefully) it is solved!
        if ( tileNum == NOT_FOUND ) {
            System.out.println("Board is solved!");
            return saveBoard();
        }

        int value;
        do {
            value = findPlaceableValue( tileNum );

            // if no values are possible, erase the tile
            if ( value == NOT_FOUND ) {
                eraseTile( tileNum );
            }
            // else if there is a placeable value, place it!
            else {
                placeAValue( value, tileNum );
                return saveBoard();
            }

            // if the board isn't empty, but you can't place a new number,
            // find the last value that you can change
            tileNum = findLastChangeableTile();
        } while ( tileNum != NOT_FOUND );

        System.out.println("Unsolvable board!");
        return saveBoard();
    }

    private int findFirstEmptyTile() {
        int x, y;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            x = i % Constants.NUM_TILES;
            y = i / Constants.NUM_TILES;
            // brute force
            int EMPTY_TILE = 0;
            if ( tiles[x][y] == EMPTY_TILE ) {
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

    private char[] saveBoard() {
        int xCord;
        int yCord;
        char newBoardValue;
        char[] board = new char[Constants.TOTAL_TILES];
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            newBoardValue  = (char) tiles[xCord][yCord];
            if ( finalTiles[xCord][yCord] ) {
                newBoardValue += '0';
            }
            else {
                newBoardValue += 'A' - 1;
            }
            board[i]       = newBoardValue;
        }
        return board;
    }

    private void loadBoard( final char[] board ) {
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
    }

    private void loadFinal( final char[] board ) {
        int xCord;
        int yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            finalTiles[xCord][yCord] = board[i] >= '1' && board[i] <= '9';
        }
    }
}

