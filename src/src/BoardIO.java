public class BoardIO {
    public static char[] saveBoard( final Tile[][] tiles ) {
        final int total_num_tiles = Constants.NUM_TILES * Constants.NUM_TILES;
        char newBoardValue;
        Tile currentTile;
        char[] board = new char[total_num_tiles];
        for ( int i = 0; i < total_num_tiles; i++ ) {
            currentTile = tiles[i%Constants.NUM_TILES][i/Constants.NUM_TILES];
            newBoardValue = (char) currentTile.getValue();
            if ( currentTile.getTileStatus() == TileStatus.SET_FINAL ) {
                newBoardValue += '0';
            }
            else if ( newBoardValue == 0 ) {
                newBoardValue += '0';
            }
            else {
                newBoardValue += 'A' - 1; // subtract one so that 1 corresponds to 'A' instead of 'B'
            }
            board[i] = newBoardValue;
        }
        return board;
    }

    // 000260701680070090190004500820100040004602900050003028009300074040050036703018000
    public static Tile[][] loadBoard( final char[] board ) {
        final int total_num_tiles = Constants.NUM_TILES * Constants.NUM_TILES;
        Tile[][] tiles = new Tile[Constants.NUM_TILES][Constants.NUM_TILES];
        int xCord;
        int yCord;
        for ( int i = 0; i < total_num_tiles; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            tiles[xCord][yCord] = new Tile( xCord, yCord );
            if ( board[i] >= '1' && board[i] <= '9' ) {
                tiles[xCord][yCord].setFinalValue( board[i] - '0' );
            }
            else if ( board[i] >= 'A' && board[i] <= 'I' ) {
                tiles[xCord][yCord].setValue( board[i] - 'A' + 1 );
            }
            else {
                tiles[xCord][yCord].setValue( 0 );
            }
        }
        return tiles;
    }
}
