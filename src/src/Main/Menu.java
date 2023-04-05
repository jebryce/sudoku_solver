package Main;

import java.awt.*;

public class Menu {
    private final KeyHandler   keyHandler;
    private final MouseHandler mouseHandler;
    private final GamePanel    gamePanel;

    public Menu( final KeyHandler keyHandler, final MouseHandler mouseHandler, final GamePanel gamePanel ) {
        this.keyHandler   = keyHandler;
        this.mouseHandler = mouseHandler;
        this.gamePanel    = gamePanel;
    }

    public void update() {
        if ( mouseHandler.isMouseClicked() ) {
            gamePanel.setGameState( GameState.SUDOKU_PLAY );
        }
    }

    public void repaint( final Graphics2D graphics2D ) {
        graphics2D.setColor( Colors.CACTUS_GREEN );
        graphics2D.fillRect( 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT );

        repaintPlayText( graphics2D );

        repaintText( graphics2D );
    }

    private void repaintPlayText( final Graphics2D graphics2D ) {
        int textX = 15;
        int textY = 34;
        graphics2D.setColor( Colors.SAP_GREEN );
        graphics2D.setFont( new Font( null, Font.BOLD, Constants.MENU_TEXT_SIZE ) );
        graphics2D.drawString( "Play", textX, textY );
    }

    private void repaintText( final Graphics2D graphics2D ) {
        repaintTitle( graphics2D );
        repaintAuthorName( graphics2D );
    }

    private void repaintTitle( final Graphics2D graphics2D ) {
        int x = 323;
        int y = 531;
        graphics2D.setColor( Colors.SAP_GREEN );
        graphics2D.setFont( new Font( null, Font.BOLD, Constants.TEXT_SIZE ) );
        graphics2D.drawString( "Sudoku Solver", x, y );
    }

    private void repaintAuthorName ( final Graphics2D graphics2D ) {
        int x = 463;
        int y = 556;
        graphics2D.setColor( Colors.SAP_GREEN );
        graphics2D.setFont( new Font( null, Font.BOLD, Constants.NOTES_TEXT_SIZE ) );
        graphics2D.drawString( "John Bryce", x, y );
    }
}
