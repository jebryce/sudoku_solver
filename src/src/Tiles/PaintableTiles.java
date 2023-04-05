package Tiles;

import Main.*;

import java.awt.*;

public class PaintableTiles extends Tiles {
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final StateControl stateControl;

    public PaintableTiles( final KeyHandler keyHandler, final MouseHandler mouseHandler, final StateControl stateControl, final char[] board ) {
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        this.stateControl = stateControl;
        stateControl.setLinkedTiles( this );
        loadBoard( board );
    }

    public void update() {
        int xCord = mouseHandler.xPos / Constants.TILE_SIZE;
        int yCord = mouseHandler.yPos / Constants.TILE_SIZE;
        Tile currentTile = getTiles()[xCord][yCord];
        for( int i = 0; i < Constants.NUM_VALUES; i++ ) {
            if ( keyHandler.numbersPressed[i] ) {
                if ( keyHandler.isNoteModePressed() ) {
                    stateControl.saveState();
                    currentTile.setNote(i);
                }
                else if ( i != getTiles()[xCord][yCord].getValue() ) {
                    stateControl.saveState();
                    currentTile.setValue(i);
                }
                keyHandler.numbersPressed[i] = false;
            }
        }
        if ( keyHandler.isClearTilePressed() && currentTile.isNotEmpty()) {
            stateControl.saveState();
            currentTile.clear();
        }
    }

    public void repaint( final Graphics2D graphics2D ) {
        repaintTilesBackground( graphics2D );
        repaintTilesText( graphics2D );
        repaintTilesNotes( graphics2D );
        drawGrid( graphics2D );
    }

    private void repaintTilesBackground( final Graphics2D graphics2D ) {
        final int xCord = mouseHandler.xPos / Constants.TILE_SIZE;
        final int yCord = mouseHandler.yPos / Constants.TILE_SIZE;
        final int value = getTiles()[xCord][yCord].getValue();
        int x,y;

        for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
            x = tileNum % Constants.NUM_TILES;
            y = tileNum / Constants.NUM_TILES;
            if ( x == xCord && y == yCord ) {
                graphics2D.setColor( Colors.BABY_BLUE );
            }
            else if ( value != 0 && getTiles()[x][y].getValue() == value ) {
                graphics2D.setColor( Colors.SKY_BABY_BLUE );
            }
            else if ( getTiles()[x][y].isValueInNotes( value ) ) {
                graphics2D.setColor( Colors.SKY_BABY_BLUE );
            }
            else if ( getTiles()[xCord][yCord].isTileVisible( getTiles()[x][y] ) ) {
                graphics2D.setColor( Colors.SKY_BLUE );
            }
            else {
                graphics2D.setColor( Colors.EGGSHELL );
            }
            getTiles()[x][y].repaintBackground( graphics2D );
        }
    }

    private void repaintTilesText( final Graphics2D graphics2D ) {
        graphics2D.setFont( new Font( null, Font.PLAIN, Constants.TEXT_SIZE ) );
        int x,y;
        for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
            x = tileNum % Constants.NUM_TILES;
            y = tileNum / Constants.NUM_TILES;
            getTiles()[x][y].repaintText( graphics2D );
        }
    }

    private void repaintTilesNotes( final Graphics2D graphics2D ) {
        graphics2D.setFont( new Font( null, Font.PLAIN, Constants.NOTES_TEXT_SIZE ) );
        int x,y;
        for ( int tileNum = 0; tileNum < Constants.TOTAL_TILES; tileNum++ ) {
            x = tileNum % Constants.NUM_TILES;
            y = tileNum / Constants.NUM_TILES;
            getTiles()[x][y].repaintNotes( graphics2D );
        }
    }

    private void drawGrid( final Graphics2D graphics2D ) {
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
}
