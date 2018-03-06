package miniprojet.ui;

import miniprojet.Observer;
import miniprojet.Resources;
import miniprojet.model.Data;

import javax.swing.*;
import java.awt.*;

public class LeaderboardView extends JInternalFrame implements Observer {
    private Data data;
    private JList listComponent;

    public LeaderboardView(Data data) {
        this.data = data;
        listComponent = new JList();
        initLeaderboardView();
    }

    private void initLeaderboardView() {
        setBackground(Color.white); // Set background color
        setTitle(Resources.LEADERBOARD_PANEL_TITLE);

        listComponent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(listComponent, BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 100);
    }

    @Override
    public void update() {
        System.out.println("updated");
        System.out.println(data.toString());
    }
}
