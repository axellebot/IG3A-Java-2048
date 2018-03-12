package miniprojet.ui;

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
    private final int caseSizePixel;
    private final int outlineWidth;
    private final int fontSize;

    // Defaults
    private static int DEFAULT_GRID_SIZE = 4;

    // Game Status
    private long gameStatStartTime;
    private int gameStatMoveCounter;

    // Game Listener
    private ArrayList<GameListener> gameListenerList;

    public GameComponent() {
        super();
        this.caseSizePixel = 100;
        this.outlineWidth = 15;
        this.fontSize = 40;
        this.gameListenerList = new ArrayList<>();
        initGridComponent();
    }

    public GameComponent(int caseSizePixel, int outlineWidth, int fontSize) {
        super();
        this.caseSizePixel = caseSizePixel;
        this.outlineWidth = outlineWidth;
        this.fontSize = fontSize;
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
            if (!grid.equalsGrid(g_tmp)) {
                gameStatMoveCounter++;
                this.grid.addRandom();
                repaint();
            }

            checkGameStatus();
        }
    }

    private void checkGameStatus() {
        if (this.grid.gridLost() || this.grid.gridWon()) {
            boolean win = grid.gridWon();
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

    /**
     * Grid
     */
    public class Grid {
        private final int FINAL_VALUE = 2048;
        private int size;
        private Tile grille[][];

        public Grid(int size) {
            this.size = size;
            this.grille = new Tile[this.size][this.size];

            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    grille[i][j] = new Tile(0);
                }
            }
        }

        public Grid(Grid g) {
            this.size = g.size;
            this.grille = new Tile[this.size][this.size];
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    this.grille[i][j] = new Tile(g.getGrille()[i][j]);
                }
            }
        }

        public int getSize() {
            return size;
        }

        public Tile[][] getGrille() {
            return grille;
        }

        @Override
        public String toString() {
            String _tmp = "";
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    _tmp += grille[i][j].toString() + "\t";
                }
                _tmp += "\n";
            }
            return _tmp;
        }

        public void addRandom() {
            ArrayList<Tile> emptyTile = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (grille[i][j].getValue() == 0) {
                        emptyTile.add(grille[i][j]);
                    }
                }
            }
            if (emptyTile.size() > 0) {
                int index = (int) (Math.random() * (emptyTile.size() - 1));

                double rand = Math.random();
                if (rand <= 0.5) {
                    emptyTile.get(index).setValue(2);

                } else {
                    if (rand > 0.5) {
                        emptyTile.get(index).setValue(4);

                    }
                }
            }
        }

        public int[] fetchColumn(int j) {
            int tab[] = new int[size];

            //initaliser les cases de tab à 0
            for (int i = 0; i < size; i++) {
                tab[i] = 0;
            }

            int k = 0;

            for (int i = 0; i < size; i++) {
                if (grille[i][j].getValue() > 0) {
                    tab[k] = grille[i][j].getValue();
                    k++;
                }
            }
            return tab;
        }

        public int[] fetchLine(int i) {
            int tab[] = new int[size];

            //initaliser les cases de tab à 0
            for (int j = 0; j < size; j++) {
                tab[j] = 0;
            }

            int k = 0;

            for (int j = 0; j < size; j++) {
                if (grille[i][j].getValue() > 0) {
                    tab[k] = grille[i][j].getValue();
                    k++;
                }
            }
            return tab;
        }

        public void pushUp() {
            for (int j = 0; j < size; j++) { // pour chaque colonne
                int tab[] = fetchColumn(j); //recupérer la colonne condensé
                int i = 0, k = 0;

                while (k < size) {  //chaque ligne de la colonne
                    if (k + 1 < size && tab[k] == tab[k + 1]) {
                        this.grille[i][j].setValue(tab[k] + tab[k + 1]);
                        k += 2;
                    } else {
                        this.grille[i][j].setValue(tab[k]);
                        k++;
                    }
                    i++;
                }
                for (; i < size; i++) {
                    this.grille[i][j].setValue(0);
                }
            }
        }

        public void pushDown() {
            for (int j = 0; j < size; j++) { // pour chaque colonne
                int tab[] = fetchColumn(j); //recupérer la colonne condensé
                int i = this.size - 1, k = this.size - 1;

                while (k >= 0) {  //chaque ligne de la colonne
                    if (tab[k] != 0) {
                        if (k - 1 >= 0 && tab[k] == tab[k - 1]) {
                            this.grille[i][j].setValue(tab[k] + tab[k - 1]);
                            k -= 2;
                        } else {
                            this.grille[i][j].setValue(tab[k]);
                            k--;
                        }
                        i--;
                    } else {
                        k--;
                    }
                }
                for (; i >= 0; i--) {
                    this.grille[i][j].setValue(0);
                }
            }
        }

        public void pushLeft() {
            for (int i = 0; i < size; i++) { // pour chaque ligne
                int tab[] = fetchLine(i); //recupérer la ligne condensé
                int j = 0, k = 0;

                while (k < size) {  //chaque colonne de la ligne
                    if (k + 1 < size && tab[k] == tab[k + 1]) {
                        this.grille[i][j].setValue(tab[k] + tab[k + 1]);
                        k += 2;
                    } else {
                        this.grille[i][j].setValue(tab[k]);
                        k++;
                    }
                    j++;
                }
                for (; j < size; j++) {
                    this.grille[i][j].setValue(0);
                }
            }
        }

        public void pushRight() {
            for (int i = 0; i < size; i++) { // pour chaque ligne
                int tab[] = fetchLine(i); //recupérer la ligne condensé
                int j = this.size - 1, k = this.size - 1;

                while (k >= 0) {  //chaque ligne de la colonne
                    if (tab[k] != 0) {
                        if (k - 1 >= 0 && tab[k] == tab[k - 1]) {
                            this.grille[i][j].setValue(tab[k] + tab[k - 1]);
                            k -= 2;
                        } else {
                            this.grille[i][j].setValue(tab[k]);
                            k--;
                        }
                        j--;
                    } else {
                        k--;
                    }
                }
                for (; j >= 0; j--) {
                    this.grille[i][j].setValue(0);
                }
            }

        }

        /**
         * Compare 2 grid
         *
         * @param grid the {Grid} to compare
         * @return true if same grid false otherwise
         */
        public boolean equalsGrid(Grid grid) {
            if (!(this.getSize() == grid.getSize())) { // not same size ?
                return false;
            } else {
                for (int i = 0; i < this.getSize(); i++) {
                    for (int j = 0; j < this.getSize(); j++) {
                        if (this.getGrille()[i][j].getValue() != grid.getGrille()[i][j].getValue()) { // same value ?
                            return false;
                        }
                    }
                }
                return true;
            }
        }

        public boolean gridLost() {
            int k = 0;

            Grid g_tmp = new Grid(this);

            boolean grilleRempli = false;
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    if (this.grille[i][j].getValue() != 0) {
                        k++;
                    }
                }
            }
            if (k == this.getSize() * this.getSize()) {
                grilleRempli = true;
            }
            if (grilleRempli) {
                g_tmp.pushDown();
                g_tmp.pushUp();
                g_tmp.pushLeft();
                g_tmp.pushRight();
                return this.equalsGrid(g_tmp);
            }
            return false;
        }

        public boolean gridWon() {
            return (getValueMax() == FINAL_VALUE);
        }

        public int getValueMax() {
            int max = 0;
            int currentValue = 0;
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    currentValue = this.grille[i][j].getValue();
                    max = Math.max(currentValue, max);
                    if (max == FINAL_VALUE) {
                        return currentValue;
                    }
                }
            }
            return max;
        }
    }

    /**
     * Tile
     */
    public class Tile {
        private int value;
        private int x, y;

        public Tile(int value) {
            this.value = value;
        }

        public Tile(Tile c) {
            this.value = c.getValue();
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }
    }
}
