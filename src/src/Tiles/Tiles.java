package Tiles;

import Main.Constants;

public class Tiles implements Cloneable {
    private Tile[][] tiles = new Tile[Constants.NUM_TILES][Constants.NUM_TILES];

    public Tile[][] getTiles() {
        return tiles;
    }

    public void loadTiles( final Tiles tiles ) {
        this.tiles = tiles.getTiles();
        setVisibleTiles();
    }

    private void setVisibleTiles() {
        int xCord, yCord;
        for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
            xCord = tileNum % Constants.NUM_TILES;
            yCord = tileNum / Constants.NUM_TILES;

            // tiles in the row / col
            for ( int i = 0; i < Constants.NUM_TILES; i++ ) {
                // tiles in the same column
                if ( i != xCord ) {
                    tiles[xCord][yCord].setVisibleTile( tiles[i][yCord] );
                }
                // tiles in the same row
                if ( i != yCord ) {
                    tiles[xCord][yCord].setVisibleTile( tiles[xCord][i] );
                }

            }

            // tiles in the rest of the box
            final int baseY = yCord - yCord % 3;
            final int baseX = xCord - xCord % 3;
            int testY, testX;
            for ( int i = 0; i < 4; i++ ) {
                testY = ( i / 2 + yCord + 1 ) % 3 + baseY;
                testX = ( i % 2 + xCord + 1 ) % 3 + baseX;
                tiles[xCord][yCord].setVisibleTile( tiles[testX][testY] );
            }

        }
    }

    private void setDuplicated() {
        int xCord;
        int yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            tiles[xCord][yCord].setVisibleDuplicates();
        }
    }

    public void loadBoard( final char[] board ) {
        int xCord;
        int yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
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
        setVisibleTiles();
        setDuplicated();
    }

    public char[] saveBoard() {
        int xCord;
        int yCord;
        char newBoardValue;
        char[] board = new char[Constants.TOTAL_TILES];
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            newBoardValue  = (char) tiles[xCord][yCord].getValue();
            if ( tiles[xCord][yCord].getTileStatus() == TileStatus.SET_FINAL || newBoardValue == 0 ) {
                newBoardValue += '0';
            }
            else {
                newBoardValue += 'A' - 1;
            }
            board[i] = newBoardValue;
        }
        return board;
    }

    @Override
    public Tiles clone() {
        try {
            Tiles clone = (Tiles) super.clone();
            Tile[][] newTiles = new Tile[Constants.NUM_TILES][Constants.NUM_TILES];
            int xCord, yCord;
            for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
                xCord = tileNum % Constants.NUM_TILES;
                yCord = tileNum / Constants.NUM_TILES;
                newTiles[xCord][yCord] = tiles[xCord][yCord].clone();
            }
            clone.tiles = newTiles;
            return clone;
        } catch ( CloneNotSupportedException e ) {
            throw new AssertionError();
        }
    }
}