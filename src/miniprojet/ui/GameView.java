package miniprojet.ui;

import miniprojet.Resources;
import miniprojet.model.Data;
import miniprojet.model.Record;
import miniprojet.ui.GameComponent.GameListener;
import miniprojet.ui.GameComponent.GameStats;

import javax.swing.*;
import java.awt.*;

public class GameView extends JInternalFrame implements GameListener {
    private JFrame ownerFrame;
    private GameComponent gameComponent;
    private Data data;

    public GameView(JFrame ownerFrame, Data data) {
        this.ownerFrame = ownerFrame;
        this.data = data;
        init();
    }


    private void init() {
        setFocusable(false);
        setBackground(Color.white); // Set background color
        setTitle(Resources.GAME_PANEL_TITLE);

        gameComponent = new GameComponent(100,15,40);
        gameComponent.addGameListener(this);
        add(gameComponent); // Add gridView to the panel with constraints layout
    }

    @Override
    public void gameEnded(GameStats gameStats) {
        Record record = new Record("", gameStats.getMoveCounter(), gameStats.getTimeCounter(), gameStats.getMaxValue());

        EndGameDialog boite = new EndGameDialog(ownerFrame, data, record);
        boite.setLocationRelativeTo(this);//position de la boite de dialogue par rapport à this

        int result = boite.showDialog();
        switch (result) {
            default:
            case EndGameDialog.REPLAY:
                this.gameComponent.generateGrid();
                break;
            case EndGameDialog.QUIT:
                this.dispose();
                break;
        }
    }
}
