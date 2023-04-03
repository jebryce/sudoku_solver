
public class Solver {
    // brute force
    private final int       NOT_FOUND = -1;
    private final int       maxDepth       = Constants.NUM_TILES * (Constants.NUM_TILES + 1);
    private final int[][][] boards         = new int[maxDepth][Constants.NUM_TILES][Constants.NUM_TILES];
    private       int       currentDepth   = 0;
    private       int[][]   solvedBoard;

    public Solver( final char[] board ) {
        this.boards[0] = BoardIO.boardToInt( board );
    }

    public char[] step() {
        int[][] currentBoard = boards[currentDepth];
        // first find the first empty tile
        final int tileNum = findFirstEmptyTile( currentBoard );

        if ( tileNum != NOT_FOUND ) {
            final int value = findPlaceableValue( currentBoard, tileNum );
            if ( value != NOT_FOUND ) {
                currentBoard = duplicateTiles();
                placeAValue( currentBoard, value, tileNum );
            }
        }
        return saveBoard( boards[currentDepth] );
    }

    private int findFirstEmptyTile( final int[][] tiles) {
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

    private int findPlaceableValue( final int[][] tiles, final int tileNum ) {
        for ( int value = 1; value <= Constants.NUM_TILES; value++ ) {
            if ( isValueTaken( tiles, value, tileNum ) ) {
                continue;
            }
            return value;
        }
        return NOT_FOUND;
    }

    private boolean isValueTaken( final int[][] tiles, final int value, final int tileNum ) {
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

    private void placeAValue( final int[][] tiles, final int value, final int tileNum ) {
        final int xCord = tileNum % Constants.NUM_TILES;
        final int yCord = tileNum / Constants.NUM_TILES;
        tiles[xCord][yCord] = value;
    }

    private int[][] duplicateTiles() {
        int xCord, yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            boards[currentDepth + 1][xCord][yCord] = boards[currentDepth][xCord][yCord];
        }
        currentDepth++;
        return boards[currentDepth];
    }

    private char[] saveBoard( final int[][] tiles ) {
        final int total_num_tiles = Constants.NUM_TILES * Constants.NUM_TILES;
        char newBoardValue;
        char[] board = new char[total_num_tiles];
        for ( int i = 0; i < total_num_tiles; i++ ) {
            newBoardValue  = (char) tiles[i%Constants.NUM_TILES][i/Constants.NUM_TILES];
            newBoardValue += '0';
            board[i]       = newBoardValue;
        }
        return board;
    }
}

