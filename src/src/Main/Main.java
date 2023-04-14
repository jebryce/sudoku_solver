package Main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable( false );
        window.setTitle( "Sudoku" );

        GamePanel gamePanel = new GamePanel();
        window.add( gamePanel );

        GameMenuBar menuBar = new GameMenuBar( gamePanel );
        window.setJMenuBar(menuBar);

        window.pack();

        window.setLocationRelativeTo( null );
        window.setVisible( true );

        gamePanel.startGameThread();
    }
}
