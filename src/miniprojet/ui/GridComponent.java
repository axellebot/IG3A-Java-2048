package miniprojet.ui;

import miniprojet.model.Grid;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Axel LE BOT
 */
public class GridComponent extends JComponent implements KeyListener {
    private Grid grid;
    private JFrame owner;
    // Properties
    private int caseSizePixel;
    private int outlineWidth;
    private int fontSize;

    // Defaults
    private static int DEFAULT_GRID_SIZE = 4;

    public GridComponent(JFrame ownerFrame) {
        super();
        this.owner = ownerFrame;
        caseSizePixel = 100;
        outlineWidth = 15;
        fontSize = 40;
        generateGrid(DEFAULT_GRID_SIZE);
        setFocusable(true);
        addKeyListener(this);
    }

    private void generateGrid(int size) {
        this.grid = new Grid(size);
        this.grid.addRandom();
        this.grid.addRandom();
        this.repaint();
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
            generateGrid(DEFAULT_GRID_SIZE);
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
                this.grid.addRandom();
            }

            repaint();

            checkGameStatus(ke);
        }
    }

    private void checkGameStatus(KeyEvent ke) {
        EndDialog boite = null;
        if (this.grid.grillePerdu()) {        // Game lost ?
            boite = new EndDialog(this.owner, false);
            boite.setLocationRelativeTo(this);//position de la boite de dialogue par rapport à this
        } else if (this.grid.grilleGagne()) { // Game won ?
            boite = new EndDialog(this.owner, true);
            boite.setLocationRelativeTo(this);//position de la boite de dialogue par rapport à this
        }
        if (boite != null) {
            int result = boite.showDialog();
            switch (result) {
                default:
                case EndDialog.REPLAY:
                    generateGrid(DEFAULT_GRID_SIZE);
                    break;
                case EndDialog.QUIT:
                    this.owner.dispose();
                    break;
            }
        }
    }
}
