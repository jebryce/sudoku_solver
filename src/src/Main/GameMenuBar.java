package Main;

import Solver.Solvers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenuBar extends JMenuBar implements ActionListener {
    private final GamePanel            gamePanel;
    private final JRadioButtonMenuItem bruteForceSolver = new JRadioButtonMenuItem( "Brute Force Solver" );
    private final JRadioButtonMenuItem sieveSolver      = new JRadioButtonMenuItem( "Sieve Solver" );

    public GameMenuBar( final GamePanel gamePanel ) {
        this.gamePanel = gamePanel;

        ButtonGroup solvers = new ButtonGroup();
        solvers.add( bruteForceSolver );
        solvers.add( sieveSolver );

        JMenu solverSelect = new JMenu("Solver Select");
        this.add(solverSelect);

        bruteForceSolver.addActionListener( this );
        solverSelect.add( bruteForceSolver );

        sieveSolver.addActionListener( this );
        solverSelect.add( sieveSolver );
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        JMenuItem actionSource = (JMenuItem) e.getSource();
        if ( actionSource == bruteForceSolver ) {
            gamePanel.setSolver( Solvers.BRUTE_FORCE_SOLVER );
        }
        if ( actionSource == sieveSolver ) {
            gamePanel.setSolver( Solvers.SIEVE_SOLVER );
        }
    }
}
