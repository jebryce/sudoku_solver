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
    private final Menu             mainMenu         = new Menu( keyHandler, mouseHandler, this );
    private final Menu             controlsMenu     = new Menu( keyHandler, mouseHandler, this );


    public GamePanel() {
        this.setPreferredSize( new Dimension( Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT ) );
        this.setBackground( Colors.BLACK );
        this.setDoubleBuffered( true );
        this.addKeyListener( keyHandler );
        this.setFocusTraversalKeysEnabled( false ); // can receive tab inputs
        this.addMouseMotionListener( mouseHandler );
        this.addMouseListener( mouseHandler );
        this.setFocusable( true );

        mainMenu.addOption( new MenuButton( 0,  0, 77, 40, "Play", GameState.SUDOKU_PLAY ) );
        mainMenu.addOption( new MenuButton( 0, 40, 129, 40, "Controls", GameState.CONTROLS_MENU ) );

        controlsMenu.addOption( new MenuButton(   0, 25, "Press the numbers '1' through '9' to enter a value into a tile" ) );
        controlsMenu.addOption( new MenuButton(  25, 25, "Hold 'shift' while pressing a number to add a note to a tile" ) );
        controlsMenu.addOption( new MenuButton(  50, 25, "Press either 'space' or 'backspace' to clear a tile" ) );
        controlsMenu.addOption( new MenuButton(  75, 25, "Press 'command + z' to undo the last action" ) );
        controlsMenu.addOption( new MenuButton( 100, 25, "Press 'enter' to step through the current solver" ) );
        controlsMenu.addOption( new MenuButton( 125, 25, "Press 'shift + enter' to use the current solver to solve the board" ) );
        controlsMenu.addOption( new MenuButton( 0, Constants.SCREEN_HEIGHT - 40, 273, 40, "Return to Main Menu", GameState.MAIN_MENU ) );
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
            case MAIN_MENU     -> mainMenu.repaint( graphics2D );
            case SUDOKU_PLAY   -> tiles.repaint( graphics2D );
            case CONTROLS_MENU -> controlsMenu.repaint( graphics2D );
        }
        graphics2D.dispose();
    }

    private void update() {
        switch ( gameState ) {
            case MAIN_MENU     -> mainMenu.update();
            case SUDOKU_PLAY   -> updateSUDOKU_PLAY();
            case CONTROLS_MENU -> controlsMenu.update();
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

    public void setGameState( final GameState newGameState ) {
        gameState = newGameState;
    }
}
