package miniprojet.ui;

import miniprojet.model.Grid;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author Axel LE BOT
 */
public class GameComponent extends JComponent implements KeyListener {
    private Grid grid;

    // UI Properties
    private final int caseSizePixel = 100;
    private final int outlineWidth = 15;
    private final int fontSize = 40;

    // Defaults
    private static int DEFAULT_GRID_SIZE = 4;

    // Game Status
    private long gameStatStartTime;
    private int gameStatMoveCounter;

    // Game Listener
    private ArrayList<GameListener> gameListenerList;

    public GameComponent() {
        super();
        this.gameListenerList = new ArrayList<>();
        initGridComponent();
    }

    private void initGridComponent() {
        generateGrid();
        setFocusable(true);
        addKeyListener(this);
    }

    public void generateGrid() {
        this.grid = new Grid(DEFAULT_GRID_SIZE);
        this.grid.addRandom();
        this.grid.addRandom();
        this.repaint();
        resetGameStats();
    }

    private void resetGameStats() {
        this.gameStatMoveCounter = 0;
        this.gameStatStartTime = System.currentTimeMillis();
    }

    private Color getColorOfNumber(int number) {
        Color c = new Color(0);
        switch (number) {
            case 0:
                c = new Color(205, 192, 180);
                break;
            case 2:
                c = new Color(238, 228, 218);
                break;
            case 4:
                c = new Color(237, 224, 200);
                break;
            case 8:
                c = new Color(245, 177, 121);
                break;
            case 16:
                c = new Color(245, 149, 99);
                break;
            case 32:
                c = new Color(246, 124, 95);
                break;
            case 64:
                c = new Color(246, 94, 59);
                break;
            case 128:
                c = new Color(237, 204, 114);
                break;
            case 256:
                c = new Color(233, 209, 87);
                break;
            case 512:
                c = new Color(237, 200, 80);
                break;
            case 1024:
                c = new Color(239, 197, 63);
                break;
            case 2048:
                c = new Color(236, 196, 0);
                break;
        }
        return c;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Draw case
        for (int i = 0; i < this.grid.getSize(); i++) {
            for (int j = 0; j < this.grid.getSize(); j++) {
                int value = grid.getGrille()[i][j].getValue();
                g2.setColor(getColorOfNumber(value));
                int x = j * (caseSizePixel + outlineWidth) + outlineWidth,
                        y = i * (caseSizePixel + outlineWidth) + outlineWidth,
                        size = caseSizePixel;
                g2.fillRect(x, y, size, size);
                if (value > 0) {
                    g2.setColor(Color.black);
                    g2.setFont(new Font("Calibri", Font.BOLD, fontSize));
                    g2.drawString(Integer.toString(value), x + caseSizePixel / 2, y + caseSizePixel / 2);
                }
            }
        }

        // Draw lines
        for (int j = 0; j <= this.grid.getSize(); j++) {
            g2.setColor(new Color(187, 173, 160));
            g2.fillRect(
                    j * (caseSizePixel + outlineWidth),
                    0,
                    outlineWidth,
                    grid.getSize() * (caseSizePixel + outlineWidth) + outlineWidth);
            g2.fillRect(
                    0,
                    j * (caseSizePixel + outlineWidth),
                    grid.getSize() * (caseSizePixel + outlineWidth) + outlineWidth,
                    outlineWidth);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int size = grid.getSize() * (caseSizePixel + outlineWidth) + outlineWidth;
        return new Dimension(size, size);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyChar() == '0' || ke.getKeyCode() == KeyEvent.VK_F5 || ke.getKeyCode() == KeyEvent.VK_R) {
            generateGrid();
        }
        if (ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            Grid g_tmp = new Grid(this.grid);

            switch (ke.getKeyCode()) {
                case KeyEvent.VK_UP:
                    this.grid.pushUp();
                    break;
                case KeyEvent.VK_DOWN:
                    this.grid.pushDown();
                    break;
                case KeyEvent.VK_LEFT:
                    this.grid.pushLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    this.grid.pushRight();
                    break;
            }

            //Grid changed ?
            if (!Grid.equalsGrille(grid, g_tmp)) {
                gameStatMoveCounter++;
                this.grid.addRandom();
                repaint();
            }

            checkGameStatus();
        }
    }

    private void checkGameStatus() {
        if (this.grid.grillePerdu() || this.grid.grilleGagne()) {
            boolean win = grid.grilleGagne();
            GameStats gameStats = new GameStats();
            gameStats.setTimeCounter(System.currentTimeMillis() - gameStatStartTime);
            gameStats.setMoveCounter(gameStatMoveCounter);
            gameStats.setMaxValue(this.grid.getValueMax());
            gameStats.setWin(win);
            for (GameListener gameListener : gameListenerList) {
                gameListener.gameEnded(gameStats);
            }
        }
    }

    public void addGameListener(GameListener gameListener) {
        this.gameListenerList.add(gameListener);
    }

    public boolean removeGameListener(GameListener gameListener) {
        return this.gameListenerList.remove(gameListener);
    }

    public interface GameListener {
        void gameEnded(GameStats gameStats);
    }

    public class GameStats {
        private boolean win;
        private int moveCounter;
        private long timeCounter;
        private int maxValue;

        public GameStats() {
            this.win = false;
            this.moveCounter = 0;
            this.timeCounter = 0;
            this.maxValue = 0;
        }

        public GameStats(boolean win, int moveCounter, int timeCounter, int maxValue) {
            this.win = win;
            this.moveCounter = moveCounter;
            this.timeCounter = timeCounter;
            this.maxValue = maxValue;
        }

        public boolean isWin() {
            return win;
        }

        public void setWin(boolean win) {
            this.win = win;
        }

        public int getMoveCounter() {
            return moveCounter;
        }

        public void setMoveCounter(int moveCounter) {
            this.moveCounter = moveCounter;
        }

        public long getTimeCounter() {
            return timeCounter;
        }

        public void setTimeCounter(long timeCounter) {
            this.timeCounter = timeCounter;
        }

        public int getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(int maxValue) {
            this.maxValue = maxValue;
        }
    }

}
