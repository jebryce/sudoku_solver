

import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    private       Thread        gameThread;
    private final KeyHandler    keyHandler   = new KeyHandler();
    private final MouseHandler  mouseHandler = new MouseHandler();
    private final Tiles         tiles        = new Tiles( keyHandler, mouseHandler );

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

        tiles.repaintTilesBackground( graphics2D );

        tiles.repaintTilesText( graphics2D );

        tiles.drawGrid( graphics2D );

        graphics2D.dispose();
    }

    private void update() {
        tiles.update();
    }
}
