import java.awt.*;

public class Tiles {
    private final Tile[][]     tiles = new Tile[Constants.TOTAL_TILES][Constants.TOTAL_TILES];
    private final KeyHandler   keyHandler;
    private final MouseHandler mouseHandler;

    public Tiles( final KeyHandler keyHandler, final MouseHandler mouseHandler, final char[] board ) {
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        loadBoard( board );
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

    public void update() {
        int xCord = mouseHandler.xPos / Constants.TILE_SIZE;
        int yCord = mouseHandler.yPos / Constants.TILE_SIZE;
        for( int i = 0; i < Constants.NUM_VALUES; i++ ) {
            if ( keyHandler.numbersPressed[i] ) {
                if ( keyHandler.isNoteModePressed() ) {
                    tiles[xCord][yCord].setNote(i);
                }
                else {
                    tiles[xCord][yCord].setValue(i);
                }
                keyHandler.numbersPressed[i] = false;
            }
        }
        if ( keyHandler.isClearTilePressed() ) {
            tiles[xCord][yCord].clear();
        }
    }

    public void repaintTilesBackground( final Graphics2D graphics2D ) {
        final int xCord = mouseHandler.xPos / Constants.TILE_SIZE;
        final int yCord = mouseHandler.yPos / Constants.TILE_SIZE;
        final int value = tiles[xCord][yCord].getValue();
        int x,y;

        for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
            x = tileNum % Constants.NUM_TILES;
            y = tileNum / Constants.NUM_TILES;
            if ( x == xCord && y == yCord ) {
                graphics2D.setColor( Colors.BABY_BLUE );
            }
            else if ( value != 0 && tiles[x][y].getValue() == value ) {
                graphics2D.setColor( Colors.SKY_BABY_BLUE );
            }
            else if ( tiles[x][y].isValueInNotes( value ) ) {
                graphics2D.setColor( Colors.SKY_BABY_BLUE );
            }
            else if ( tiles[xCord][yCord].isTileVisible( tiles[x][y] ) ) {
                graphics2D.setColor( Colors.SKY_BLUE );
            }
            else {
                graphics2D.setColor( Colors.EGGSHELL );
            }
            tiles[x][y].repaintBackground( graphics2D );
        }
    }

    public void repaintTilesText( final Graphics2D graphics2D ) {
        graphics2D.setFont( new Font( null, Font.PLAIN, Constants.TEXT_SIZE ) );
        int x,y;
        for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
            x = tileNum % Constants.NUM_TILES;
            y = tileNum / Constants.NUM_TILES;
            tiles[x][y].repaintText( graphics2D );
        }
    }

    public void repaintTilesNotes( final Graphics2D graphics2D ) {
        graphics2D.setFont( new Font( null, Font.PLAIN, Constants.NOTES_TEXT_SIZE ) );
        int x,y;
        for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
            x = tileNum % Constants.NUM_TILES;
            y = tileNum / Constants.NUM_TILES;
            tiles[x][y].repaintNotes( graphics2D );
        }
    }

    public void drawGrid( final Graphics2D graphics2D ) {
        graphics2D.setColor( Colors.CHARCOAL );
        int offset = Constants.TILE_SIZE;
        for ( int i = 1; i < Constants.NUM_TILES; i++ ){
            if ( i % 3 == 0 ) {
                graphics2D.setStroke( new BasicStroke( Constants.BOX_BORDER_SIZE  ) );
            } else {
                graphics2D.setStroke( new BasicStroke( Constants.CELL_BORDER_SIZE ) );
            }
            graphics2D.drawLine( offset, 0, offset, Constants.SCREEN_HEIGHT );
            graphics2D.drawLine( 0, offset, Constants.SCREEN_WIDTH, offset );
            offset += Constants.TILE_SIZE;
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

    private void setDuplicated() {
        int xCord;
        int yCord;
        for ( int i = 0; i < Constants.TOTAL_TILES; i++ ) {
            xCord = i % Constants.NUM_TILES;
            yCord = i / Constants.NUM_TILES;
            tiles[xCord][yCord].setVisibleDuplicates();
        }
    }
}
