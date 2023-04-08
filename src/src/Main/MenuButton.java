package Main;

import java.awt.*;

public class MenuButton {
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private final int textXPos;
    private final int textYPos;
    private final String text;
    private final Font font = new Font( null, Font.PLAIN, Constants.MENU_TEXT_SIZE );
    private final GameState returnState;

    public MenuButton( final int xPos, final int yPos, final int width, final int height, final String text, final GameState returnState ) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.text = text;
        int xOffset = 15;
        this.textXPos = xPos + xOffset;
        int textHeight = 15;
        this.textYPos = yPos + ( height + textHeight ) / 2 ;
        this.returnState = returnState;
    }

    public boolean isPointOnButton( final int xPos, final int yPos ) {
        if ( xPos < this.xPos ) {
            return false;
        }
        if ( xPos > this.xPos + this.width ) {
            return false;
        }
        if ( yPos < this.yPos ) {
            return false;
        }
        if ( yPos > this.yPos + this.height ) {
            return false;
        }
        return true;
    }

    public GameState getReturnState() {
        return returnState;
    }

    public void repaint( final Graphics2D graphics2D ) {
        graphics2D.setFont( font );
        graphics2D.drawString( text, textXPos, textYPos );
    }
}
