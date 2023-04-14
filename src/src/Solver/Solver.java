package Solver;

import Main.Constants;

public class Solver {
    protected final int[][]     tiles      = new int[Constants.NUM_TILES][Constants.NUM_TILES];
    protected final boolean[][] finalTiles = new boolean[Constants.NUM_TILES][Constants.NUM_TILES];

    public Solver( final char[] board ) {
        loadBoard( board );
        loadFinal( board );
    }

    public void updateBoard( final char[] board ) {
        loadBoard( board );
        loadFinal( board );
    }

    public char[] getBoard() {
        int xCord;
        int yCord;
        char newBoardValue;
        char[] board = new char[Constants.TOTAL_TILES];
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            newBoardValue  = (char) tiles[xCord][yCord];
            if ( finalTiles[xCord][yCord] || newBoardValue == 0 ) {
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
