package Tiles;

import Main.Colors;
import Main.Constants;

import java.awt.Graphics2D;

public class PaintableTile extends Tile {
    private final int        xPos;
    private final int        yPos;

    public PaintableTile( final int xCord, final int yCord ) {
        xPos = xCord * Constants.TILE_SIZE;
        yPos = yCord * Constants.TILE_SIZE;
    }

    public void repaintBackground( final Graphics2D graphics2D ) {
        graphics2D.fillRect( xPos, yPos, Constants.TILE_SIZE, Constants.TILE_SIZE );
    }

    public void repaintText( final Graphics2D graphics2D ) {
        if ( tileStatus == TileStatus.UNSET ) {
            return;
        }
        else if ( tileStatus == TileStatus.SET ) {
            graphics2D.setColor( Colors.CHARCOAL_GRAY );
        }
        else if ( tileStatus == TileStatus.SET_FINAL) {
            graphics2D.setColor( Colors.BLACK );
        }
        else if ( tileStatus == TileStatus.DUPLICATE ) {
            graphics2D.setColor( Colors.CORAL_PINK );
        }
        graphics2D.drawString(
                String.valueOf(value),
                xPos + Constants.TILE_TEXT_X_OFFSET,
                yPos + Constants.TILE_TEXT_Y_OFFSET
        );
    }

    public void repaintNotes( final Graphics2D graphics2D ) {
        if ( tileStatus != TileStatus.UNSET ) {
            return;
        }
        graphics2D.setColor( Colors.CHARCOAL_GRAY );
        float xOffset;
        int yOffset;
        int ct = 0;
        for ( int i = 0; i < Constants.NUM_TILES; i++ ) {
            if ( notes[i] ) {
                xOffset = Constants.NOTES_X_OFFSET + ( ct%3 ) * ( Constants.NOTES_TEXT_WIDTH + Constants.NOTES_X_OFFSET );
                yOffset = ( ct/3 + 1)  * ( Constants.NOTES_TEXT_HEIGHT + Constants.NOTES_Y_OFFSET );
                graphics2D.drawString(
                        String.valueOf(i+1),
                        xPos + xOffset,
                        yPos + yOffset
                );
                ct++;
            }
        }
    }
}
