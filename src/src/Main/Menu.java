package Main;

import java.awt.*;

public class Menu {
    private final KeyHandler   keyHandler;
    private final MouseHandler mouseHandler;
    private final GamePanel    gamePanel;
    private final int          maxOptions = 10;
    private final MenuButton[] menuButtons = new MenuButton[maxOptions];
    private final GameState    escapeGameState;

    public Menu( final KeyHandler keyHandler, final MouseHandler mouseHandler, final GamePanel gamePanel, final GameState escapeGameState ) {
        this.keyHandler   = keyHandler;
        this.mouseHandler = mouseHandler;
        this.gamePanel    = gamePanel;
        this.escapeGameState = escapeGameState;
    }

    public void addOption( MenuButton newMenuButton) {
        int index = 0;
        while ( menuButtons[index] != null ) {
            index++;
        }
        menuButtons[index] = newMenuButton;
    }

    public void update() {
        if ( keyHandler.isEscapeMenuPressed() ) {
            gamePanel.setGameState( escapeGameState );
        }
        int xPos = mouseHandler.xPos;
        int yPos = mouseHandler.yPos;
        if ( mouseHandler.isMouseClicked() ) {
            for ( MenuButton menuButton : menuButtons ) {
                if ( menuButton == null ) {
                    break;
                }
                if ( menuButton.isPointOnButton( xPos, yPos ) ) {
                    gamePanel.setGameState( menuButton.getReturnState() );
                    break;
                }
            }
        }
    }

    public void repaint( final Graphics2D graphics2D ) {
        graphics2D.setColor( Colors.CACTUS_GREEN );
        graphics2D.fillRect( 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT );
        int xPos = mouseHandler.xPos;
        int yPos = mouseHandler.yPos;

        for ( MenuButton menuButton : menuButtons ) {
            if ( menuButton == null ) {
                break;
            }
            if ( menuButton.isPointOnButton( xPos, yPos ) ) {
                graphics2D.setColor( Colors.DARK_FOREST_GREEN );
            }
            else {
                graphics2D.setColor( Colors.SAP_GREEN );
            }
            menuButton.repaint( graphics2D );
        }

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
