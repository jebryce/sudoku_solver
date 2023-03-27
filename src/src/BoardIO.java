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

    public static char[] saveBoard( final int[][] tiles ) {
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

    public static int[][] boardToInt( final char[] board ) {
        final int total_num_tiles = Constants.NUM_TILES * Constants.NUM_TILES;
        int[][] tiles = new int[Constants.NUM_TILES][Constants.NUM_TILES];
        int xCord;
        int yCord;
        for ( int i = 0; i < total_num_tiles; i++ ) {
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
        setDuplicated( tiles );
        return tiles;
    }

    private static void setDuplicated( final Tile[][] tiles ) {
        // loop through board, if tile is set, check if other tiles in row/col/box are duplicated. if so, then set status
        for ( int y = 0; y < Constants.NUM_TILES; y++ ){
            for ( int x = 0; x < Constants.NUM_TILES; x++ ) {
                if ( tiles[x][y].getTileStatus() == TileStatus.SET ) {
                    for ( int col = x; col < Constants.NUM_TILES - 1; col++ ) {
                        if ( tiles[x][y].getValue() == tiles[col+1][y].getValue() ) {
                            tiles[x][y].setDuplicated();
                            tiles[col+1][y].setDuplicated();
                            break;
                        }
                    }
                    for ( int row = y + 1; row < Constants.NUM_TILES; row++ ) {
                        if ( tiles[x][y].getValue() == tiles[x][row].getValue() ) {
                            tiles[x][y].setDuplicated();
                            tiles[x][row].setDuplicated();
                            break;
                        }
                    }
                    for ( int boxX = x - x%3; boxX < x - x%3 + 3; boxX++ ) {
                        for ( int boxY = y - y%3; boxY < y - y%3 + 3; boxY++ ) {
                            if ( boxX == x || boxY == y ) {
                                continue;
                            }
                            if ( tiles[x][y].getValue() == tiles[boxX][boxY].getValue() ) {
                                tiles[x][y].setDuplicated();
                                tiles[boxX][boxY].setDuplicated();
                                break;
                            }
                        }
                    }
                }
            }
        }


    }
}
