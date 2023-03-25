

import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    private       Thread        gameThread;
    private final Tile[][]      tiles        = new Tile[Constants.NUM_TILES][Constants.NUM_TILES];
    private final KeyHandler    keyHandler   = new KeyHandler();
    private final MouseHandler  mouseHandler = new MouseHandler();
    private final Cursor        cursor       = new Cursor( mouseHandler );

    public GamePanel() {
        this.setPreferredSize( new Dimension( Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT ) );
        this.setBackground( Colors.BLACK );
        this.setDoubleBuffered( true );
        this.addKeyListener( keyHandler );
        this.setFocusTraversalKeysEnabled( false ); // can receive tab inputs
        this.addMouseMotionListener( mouseHandler );
        this.setFocusable( true );
    }

    @Override
    public void run() {
        final long updateFPSInterval  = Constants.NANO_SEC_PER_SEC / 5;
        final long updateGameInterval = Constants.NANO_SEC_PER_SEC / Constants.UPDATES_PER_SECOND;
        long       lastUpdateTime     = 0;
        long       currentTime;
        long       lastFPSTime        = 0;
        long       numFrames          = 0;
        double     FPS;

        genTiles();

        // main game loop
        while ( gameThread != null) {
            currentTime = System.nanoTime();
            repaint();
            if ( currentTime - lastUpdateTime > updateGameInterval ) {
                update();
                lastUpdateTime = currentTime;
            }
            if ( currentTime - lastFPSTime > updateFPSInterval ) {
                FPS = (double) ( numFrames * Constants.NANO_SEC_PER_SEC ) / ( currentTime - lastFPSTime );
                System.out.format("FPS: [%.1f]\r", FPS);
                numFrames   = 0;
                lastFPSTime = currentTime;
            }
            numFrames++;
        }

    }

    protected void startGameThread() {
        gameThread = new Thread( this );
        gameThread.start();
    }

    protected void paintComponent ( final Graphics graphics ) {
        super.paintComponent( graphics );
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setFont( new Font( null, Font.PLAIN, Constants.TEXT_SIZE ) );

        repaintTiles( graphics2D );
        cursor.repaint( graphics2D );

        repaintTilesText( graphics2D );

        drawGrid( graphics2D );

        graphics2D.dispose();
    }

    private void repaintTiles( final Graphics2D graphics2D ) {
        for (int i = 0; i < Constants.NUM_TILES; i++ ){
            for (int j = 0; j < Constants.NUM_TILES; j++ ) {
                tiles[i][j].repaint( graphics2D );
            }
        }
    }

    private void repaintTilesText( final Graphics2D graphics2D ) {
        for (int i = 0; i < Constants.NUM_TILES; i++ ){
            for (int j = 0; j < Constants.NUM_TILES; j++ ) {
                tiles[i][j].repaintText( graphics2D );
            }
        }
    }

    private void drawGrid( final Graphics2D graphics2D ) {
        graphics2D.setColor( Colors.CHARCOAL_GRAY);
        int offset = Constants.TILE_SIZE;
        for (int i = 1; i < Constants.NUM_TILES; i++ ){
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

    private void update() {
        int xCord = mouseHandler.xPos / Constants.TILE_SIZE;
        int yCord = mouseHandler.yPos / Constants.TILE_SIZE;
        for( int i = 0; i < Constants.NUM_VALUES; i++ ) {
            if ( keyHandler.numbersPressed[i] ) {
                tiles[xCord][yCord].setValue( i );
                keyHandler.numbersPressed[i] = false;
            }
        }
        if ( keyHandler.spacePressed ) {
            tiles[xCord][yCord].clearValue();
            keyHandler.spacePressed = false;
        }
    }

    private void genTiles() {
        for (int i = 0; i < Constants.NUM_TILES; i++ ){
            for (int j = 0; j < Constants.NUM_TILES; j++ ) {
                tiles[i][j] = new Tile( i, j );
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
}
