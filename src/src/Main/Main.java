package Main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable( false );
        window.setTitle( "Sudoku" );

        JMenuBar menuBar = new JMenuBar();

        window.setJMenuBar(menuBar);

        GamePanel gamePanel = new GamePanel();
        window.add( gamePanel );

        JMenu menu = new JMenu("A Menu");
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("A text-only menu item");
        menu.add(menuItem);

        window.pack();

        window.setLocationRelativeTo( null );
        window.setVisible( true );

        gamePanel.startGameThread();
    }
}
