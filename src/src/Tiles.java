import java.awt.*;

public class Tiles {
    private final Tile[][]     tiles        = new Tile[Constants.NUM_TILES][Constants.NUM_TILES];
    private final Box[]        boxes        = new Box[Constants.NUM_TILES];
    private final KeyHandler   keyHandler;
    private final MouseHandler mouseHandler;

    public Tiles( final KeyHandler keyHandler, final MouseHandler mouseHandler ) {
        this.keyHandler   = keyHandler;
        this.mouseHandler = mouseHandler;
        int currentBox = 0;
        for ( int y = 0; y < Constants.NUM_TILES; y++ ){
            for ( int x = 0; x < Constants.NUM_TILES; x++ ) {
                if ( x % 3 == 0 && y % 3 == 0 ) {
                    boxes[currentBox++] = new Box( x, y );
                }
                tiles[x][y] = new Tile( x, y );
            }
        }

        tiles[0][1].setFinalValue( 6 );
        tiles[0][2].setFinalValue( 1 );
        tiles[0][3].setFinalValue( 8 );
        tiles[0][8].setFinalValue( 7 );
        tiles[1][1].setFinalValue( 8 );
        tiles[1][2].setFinalValue( 9 );
        tiles[1][3].setFinalValue( 2 );
        tiles[1][5].setFinalValue( 5 );
        tiles[1][7].setFinalValue( 4 );
        tiles[2][4].setFinalValue( 4 );
        tiles[2][6].setFinalValue( 9 );
        tiles[2][8].setFinalValue( 3 );
        tiles[3][0].setFinalValue( 2 );
        tiles[3][3].setFinalValue( 1 );
        tiles[3][4].setFinalValue( 6 );
        tiles[3][6].setFinalValue( 3 );
        tiles[4][0].setFinalValue( 6 );
        tiles[4][1].setFinalValue( 7 );
        tiles[4][7].setFinalValue( 5 );
        tiles[4][8].setFinalValue( 1 );
        tiles[5][2].setFinalValue( 4 );
        tiles[5][4].setFinalValue( 2 );
        tiles[5][5].setFinalValue( 3 );
        tiles[5][8].setFinalValue( 8 );
        tiles[6][0].setFinalValue( 7 );
        tiles[6][2].setFinalValue( 5 );
        tiles[6][4].setFinalValue( 9 );
        tiles[7][1].setFinalValue( 9 );
        tiles[7][3].setFinalValue( 4 );
        tiles[7][5].setFinalValue( 2 );
        tiles[7][6].setFinalValue( 7 );
        tiles[7][7].setFinalValue( 3 );
        tiles[8][0].setFinalValue( 1 );
        tiles[8][5].setFinalValue( 8 );
        tiles[8][6].setFinalValue( 4 );
        tiles[8][7].setFinalValue( 6 );
    }

    public void update() {
        int xCord = mouseHandler.xPos / Constants.TILE_SIZE;
        int yCord = mouseHandler.yPos / Constants.TILE_SIZE;
        for( int i = 0; i < Constants.NUM_VALUES; i++ ) {
            if ( keyHandler.numbersPressed[i] ) {
                tiles[xCord][yCord].setValue( i );
                if ( isValueDuplicated( xCord, yCord ) ) {
                    setDuplicated( xCord, yCord );
                }
                keyHandler.numbersPressed[i] = false;
            }
        }
        if ( keyHandler.spacePressed || keyHandler.backspacePressed ) {
            clearAndUnsetDuplicated( xCord, yCord );
            keyHandler.spacePressed = false;
            keyHandler.backspacePressed = false;
        }
    }

    public void repaintTilesBackground( final Graphics2D graphics2D ) {
        int xCord      = mouseHandler.xPos / Constants.TILE_SIZE;
        int yCord      = mouseHandler.yPos / Constants.TILE_SIZE;
        int currentBox = xCord / 3 + yCord - (yCord % 3);
        for ( int y = 0; y < Constants.NUM_TILES; y++ ){
            for ( int x = 0; x < Constants.NUM_TILES; x++ ) {
                if ( x == xCord && y == yCord ) {
                    graphics2D.setColor( Colors.BABY_BLUE );
                }
                else if ( x == xCord || y == yCord ) {
                    graphics2D.setColor( Colors.SKY_BLUE );
                }
                else if ( boxes[currentBox].isCordInBox( x, y ) ) {
                    graphics2D.setColor( Colors.SKY_BLUE );
                }
                else {
                    graphics2D.setColor ( Colors.EGGSHELL );
                }
                tiles[x][y].repaintBackground( graphics2D );
            }
        }
    }

    public void repaintTilesText( final Graphics2D graphics2D ) {
        for ( int y = 0; y < Constants.NUM_TILES; y++ ){
            for ( int x = 0; x < Constants.NUM_TILES; x++ ) {
                tiles[x][y].repaintText( graphics2D );
            }
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

    private boolean isValueDuplicated( final int xCord, final int yCord ) {
        final int value = tiles[xCord][yCord].getValue();
        int currentBox = xCord / 3 + yCord - (yCord % 3);
        for ( int y = 0; y < Constants.NUM_TILES; y++ ) {
            for ( int x = 0; x < Constants.NUM_TILES; x++ ) {
                if ( x == xCord && y == yCord ) {
                    continue;
                }
                if ( x == xCord || y == yCord || boxes[currentBox].isCordInBox( x, y ) ) {
                    if ( value == tiles[x][y].getValue() ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void setDuplicated( final int xCord, final int yCord ) {
        final int value = tiles[xCord][yCord].getValue();
        int currentBox = xCord / 3 + yCord - (yCord % 3);
        for ( int y = 0; y < Constants.NUM_TILES; y++ ) {
            for ( int x = 0; x < Constants.NUM_TILES; x++ ) {
                if ( x == xCord || y == yCord || boxes[currentBox].isCordInBox( x, y ) ) {
                    if ( value == tiles[x][y].getValue() ) {
                        tiles[x][y].setDuplicated();
                    }
                }
            }
        }
    }

    private void clearAndUnsetDuplicated( final int xCord, final int yCord ) {
        final int value = tiles[xCord][yCord].getValue();
        tiles[xCord][yCord].clearValue();
        int currentBox = xCord / 3 + yCord - (yCord % 3);
        for ( int y = 0; y < Constants.NUM_TILES; y++ ) {
            for ( int x = 0; x < Constants.NUM_TILES; x++ ) {
                if ( x == xCord || y == yCord || boxes[currentBox].isCordInBox( x, y ) ) {
                    if ( value == tiles[x][y].getValue() ) {
                        if ( isValueDuplicated( x, y ) ) {
                            continue;
                        }
                        tiles[x][y].unsetDuplicated();
                    }
                }
            }
        }
    }
}
