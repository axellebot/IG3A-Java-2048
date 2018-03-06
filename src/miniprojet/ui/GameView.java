package miniprojet.ui;

import miniprojet.Resources;

import javax.swing.*;
import java.awt.*;

public class GameView extends JInternalFrame {
    private JFrame ownerFrame;
    private GridComponent gridComponent;

    public GameView(JFrame ownerFrame) {
        this.ownerFrame = ownerFrame;
        initLayout();
        initComponents();
    }

    private void initComponents() {
        gridComponent = new GridComponent(ownerFrame);
        add(gridComponent); // Add gridView to the panel with constraints layout
    }

    private void initLayout() {
        setFocusable(false);
        setBackground(Color.white); // Set background color
        setTitle(Resources.GAME_PANEL_TITLE);
    }
}
