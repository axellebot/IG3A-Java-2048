package miniprojet.ui;

import miniprojet.Resources;
import miniprojet.model.Person;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LeaderboardView extends JInternalFrame  {
    private List<Person> personList;
    private JList listComponent;

    public LeaderboardView() {
        listComponent = new JList();
        initLayout();
    }

    private void initLayout() {
        setBackground(Color.white); // Set background color
        setTitle(Resources.LEADERBOARD_PANEL_TITLE);

        listComponent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(listComponent,BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300,100);
    }
}
