import java.awt.*;

public class Tiles {
    private final Tile[][]     tiles;
    private final Box[]        boxes        = new Box[Constants.NUM_TILES];
    private final KeyHandler   keyHandler;
    private final MouseHandler mouseHandler;

    public Tiles( final KeyHandler keyHandler, final MouseHandler mouseHandler ) {
        this.keyHandler   = keyHandler;
        this.mouseHandler = mouseHandler;
        tiles             = new Tile[Constants.NUM_TILES][Constants.NUM_TILES];
        for ( int y = 0; y < Constants.NUM_TILES; y++ ){
            for ( int x = 0; x < Constants.NUM_TILES; x++ ) {
                tiles[x][y] = new Tile( x, y );
            }
        }
        genBoxes();
    }

    public Tiles( final KeyHandler keyHandler, final MouseHandler mouseHandler, final Tile[][] tiles ) {
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        this.tiles = tiles;
        genBoxes();
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
        final int xCord = mouseHandler.xPos / Constants.TILE_SIZE;
        final int yCord = mouseHandler.yPos / Constants.TILE_SIZE;
        final int box   = xCord / 3 + yCord - (yCord % 3);
        final int value = tiles[xCord][yCord].getValue();
        for ( int y = 0; y < Constants.NUM_TILES; y++ ){
            for ( int x = 0; x < Constants.NUM_TILES; x++ ) {
                if ( x == xCord && y == yCord ) {
                    graphics2D.setColor( Colors.BABY_BLUE );
                }
                else if ( value != 0 && value == tiles[x][y].getValue() ) {
                    graphics2D.setColor( Colors.SKY_BABY_BLUE );
                }
                else if ( x == xCord || y == yCord ) {
                    graphics2D.setColor( Colors.SKY_BLUE );
                }
                else if ( boxes[box].isCordInBox( x, y ) ) {
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

    private void genBoxes() {
        for ( int box = 0; box < Constants.NUM_TILES; box++ ) {
            boxes[box] = new Box( box % 3, box / 3 );
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
