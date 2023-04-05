package Main;

import Solver.BruteForceSolver;
import Tiles.PaintableTiles;

import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    // typical sudoku board
//    private char[] board = "000260001680070090190004500820100040004602900050003028009300074040050036703018000".toCharArray();
    // hard sudoku board
    private char[] board = "000009806000001020700300000080000100600050402004000030000000000003407080200103005".toCharArray();
    // blank sudoku board
//    private char[] board = "000000000000000000000000000000000000000000000000000000000000000000000000000000000".toCharArray();
    // funky edge case sudoku board - not solvable at start, but need to erase a tile to solve
//    private char[] board = "12345678000000000I000000000000000000000000000000000000000000000000000000000000000".toCharArray();
    // unsolvable sudoku board
//    private char[] board = "123456780000000009000000000000000000000000000000000000000000000000000000000000000".toCharArray();

    private       Thread           gameThread;
    private final KeyHandler       keyHandler       = new KeyHandler();
    private final MouseHandler     mouseHandler     = new MouseHandler();
    private final StateControl     stateControl     = new StateControl();
    private final PaintableTiles   tiles            = new PaintableTiles( keyHandler, mouseHandler, stateControl, board );
    private final BruteForceSolver bruteForceSolver = new BruteForceSolver( board );
    private       GameState        gameState        = GameState.MAIN_MENU;


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
        switch ( gameState ) {
            case MAIN_MENU -> {

            }
            case SUDOKU_PLAY -> {
                tiles.repaintTilesBackground( graphics2D );
                tiles.repaintTilesText( graphics2D );
                tiles.repaintTilesNotes( graphics2D );
                tiles.drawGrid( graphics2D );
            }
        }
        graphics2D.dispose();
    }

    private void update() {
        switch ( gameState ) {
            case MAIN_MENU   -> updateMAIN_MENU();
            case SUDOKU_PLAY -> updateSUDOKU_PLAY();
        }
    }

    private void updateMAIN_MENU() {
        if ( keyHandler.isStepSolverPressed() ) {
            gameState = GameState.SUDOKU_PLAY;
        }
    }

    private void updateSUDOKU_PLAY() {
        tiles.update();
        if ( keyHandler.isSolveBoardPressed() ) {
            stateControl.saveState();
            bruteForceSolver.updateBoard( tiles.saveBoard() );
            board = bruteForceSolver.solve();
            tiles.loadBoard( board );
        }
        else if ( keyHandler.isStepSolverPressed() ) {
            stateControl.saveState();
            bruteForceSolver.updateBoard( tiles.saveBoard() );
            board = bruteForceSolver.step();
            tiles.loadBoard( board );
        }
        if ( keyHandler.isUndoPressed() ) {
            tiles.loadTiles( stateControl.undo() ) ;
        }
    }
}
