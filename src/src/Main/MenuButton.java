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
    private       Color backgroundColor;
    private       Color textColor;
    private final Font font = new Font( null, Font.PLAIN, Constants.MENU_TEXT_SIZE );

    public MenuButton( final int xPos, final int yPos, final int width, final int height, final String text ) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.text = text;
        int xOffset = 15;
        this.textXPos = xPos + xOffset;
        int textHeight = 15;
        this.textYPos = yPos + ( height + textHeight ) / 2 ;
    }

    public void setBackgroundColor( final Color newColor ) {
        backgroundColor = newColor;
    }

    public void setTextColor( final Color newColor ) {
        textColor = newColor;
    }

    public void repaint( final Graphics2D graphics2D ) {
        graphics2D.setColor( backgroundColor );
        graphics2D.fillRect( xPos, yPos, width, height );

        graphics2D.setColor( textColor );
        graphics2D.setFont( font );
        graphics2D.drawString( text, textXPos, textYPos );

    }
}
