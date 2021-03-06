package miniprojet.ui;

import miniprojet.Resources;
import miniprojet.model.Data;

import javax.swing.*;
import java.awt.*;

/**
 * @author Axel LE BOT
 */
public class MainView extends JFrame implements Runnable {
    private Data data;
    private LeaderboardView leaderboardView;
    private GameView gameView;

    public MainView() {
        setTitle(Resources.APP_FRAME_TITLE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(this.getClass().getResource("./mainicon.png")).getImage());

        this.data = Data.getInstance();

        initMainView();
        initMenuBar();
    }

    private void initMenuBar() {
        // Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        // Build Menu
        JMenu mainMenu = new JMenu(Resources.MAIN_FRAME_MENU_LABEL);
        menuBar.add(mainMenu);

        JMenuItem menuItem = new JMenuItem(Resources.MAIN_FRAME_MENU_ITEM_QUIT_LABEL);
        menuItem.addActionListener(e -> dispose());
        mainMenu.add(menuItem);

        //Build Display
        JMenu displayMenu = new JMenu(Resources.MAIN_FRAME_MENU_DISPLAY_LABEL);
        menuBar.add(displayMenu);

        JMenuItem toggleLeaderboard = new JCheckBoxMenuItem(Resources.MAIN_FRAME_MENU_ITEM_TOGGLE_LEADERBOARD);
        toggleLeaderboard.setSelected(leaderboardView.isVisible());
        toggleLeaderboard.addItemListener(e -> {
            leaderboardView.setVisible(!leaderboardView.isVisible());
        });

        displayMenu.add(toggleLeaderboard);

        setJMenuBar(menuBar);
    }

    private void initMainView() {
        setLayout(new GridLayout());  // the default GridLayout is like a grid with 1 column and 1 row,

        leaderboardView = new LeaderboardView(data);  // Create Leaderboard Panel
        data.addObserver(leaderboardView);
        gameView = new GameView(this, data); // Create Grid Panel

        add(leaderboardView, LEFT_ALIGNMENT); // Add Leaderboard Panel
        add(gameView, RIGHT_ALIGNMENT); // Add Game Panel

        leaderboardView.setVisible(true);
        gameView.setVisible(true);

        pack();
    }

    @Override
    public void run() {
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }
}
