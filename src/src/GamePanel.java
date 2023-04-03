

import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    private       Thread        gameThread;
    private final KeyHandler    keyHandler   = new KeyHandler();
    private final MouseHandler  mouseHandler = new MouseHandler();
    private       Tiles         tiles        = new Tiles( keyHandler, mouseHandler );

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
        final long updateFPSInterval  = Constants.NANO_SEC_PER_SEC / 10;
        final long updateGameInterval = Constants.NANO_SEC_PER_SEC / Constants.UPDATES_PER_SECOND;
        final long repaintInterval    = Constants.NANO_SEC_PER_SEC / Constants.MAX_FPS;
        long       sleepTime;
        long       lastRepaintTime    = 0;
        long       lastUpdateTime     = 0;
        long       currentTime;
        long       lastFPSTime        = 0;
        long       numFrames          = 0;
        double     FPS;

//        char[] board = "000260701680070090190004500820100040004602900050003028009300074040050036703018000".toCharArray();
//        char[] board = "001000000000000000000000000000000000000050000000000000000000000000000000000000900".toCharArray();
        char[] board = "000000000000000000000000000000000000000000000000000000000000000000000000000000000".toCharArray();
        Tile[][] loadedTiles = BoardIO.loadBoard( board );
        tiles = new Tiles( keyHandler, mouseHandler, loadedTiles );

        // main game loop
        while ( gameThread != null) {
            currentTime = System.nanoTime();
            if ( currentTime - lastUpdateTime > updateGameInterval ) {
                update();
                lastUpdateTime = currentTime;
            }
            if ( currentTime - lastFPSTime > updateFPSInterval ) {
                FPS = (double) ( numFrames * Constants.NANO_SEC_PER_SEC ) / ( currentTime - lastFPSTime );
                System.out.format("FPS: [%.1f]\r", FPS);
                numFrames = 0;
                lastFPSTime = currentTime;
            }
            if ( currentTime - lastRepaintTime > repaintInterval ) {
                repaint();
                numFrames++;
                lastRepaintTime = currentTime;
                sleepTime = repaintInterval - ( System.nanoTime() - currentTime) ;
                if ( sleepTime > 0 ) {
                    try {
                        Thread.sleep(sleepTime / Constants.NANO_SEC_PER_M_SEC);
                    } catch ( InterruptedException e ) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }

    protected void startGameThread() {
        gameThread = new Thread( this );
        gameThread.start();
    }

    protected void paintComponent ( final Graphics graphics ) {
        super.paintComponent( graphics );
        Graphics2D graphics2D = (Graphics2D) graphics;

        tiles.repaintTilesBackground( graphics2D );

        tiles.repaintTilesText( graphics2D );

        tiles.repaintTilesNotes( graphics2D );

        tiles.drawGrid( graphics2D );

        graphics2D.dispose();
    }

    private void update() {
        tiles.update();
    }
}
