package miniprojet.ui;

import miniprojet.Resources;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Axel LE BOT
 */
public class EndDialog extends JDialog implements ActionListener {
    // UI
    private JButton btnRetry;
    private JButton btnQuit;

    private Frame ownerFrame;
    // Behavior
    private int returnStatement;
    public static final int REPLAY = 1;
    public static final int QUIT = 2;

    /**
     * @param ownerFrame the {@code Frame} the window where the dialog will be displayed
     * @param win        boolean true if player won false otherwise
     */
    EndDialog(JFrame ownerFrame, boolean win) {
        super(ownerFrame, Resources.END_DIALOG_TITLE_END_GAME, true);
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        initPanel(win);
    }

    private void initPanel(boolean win) {
        String msg = (win) ? Resources.LABEL_END_GAME_SUCCEED : Resources.LABEL_END_GAME_FAILED;

        btnRetry = new JButton(Resources.BUTTON_RESTART);
        btnRetry.setBackground(Color.GREEN);
        btnRetry.addActionListener(this);

        btnQuit = new JButton(Resources.BUTTON_QUIT);
        btnQuit.setBackground(Color.RED);
        btnQuit.addActionListener(this);

        JLabel lbl = new JLabel(msg);
        lbl.setFont(new Font("Serif", Font.PLAIN, 36));

        JPanel panel = new JPanel(); // Create panel
        panel.setBackground(Color.WHITE); // Set background color
        panel.setLayout(new GridBagLayout()); // Set layout

        GridBagConstraints cont = new GridBagConstraints();
        cont.fill = GridBagConstraints.CENTER;
        cont.weightx = 1;
        cont.weighty = 1;
        cont.ipadx = 0;
        cont.ipady = 0;
        cont.gridx = 0;
        cont.gridy = 0;
        cont.gridwidth = 2;
        panel.add(lbl, cont);

        cont.fill = GridBagConstraints.CENTER;
        cont.gridwidth = 1;
        cont.gridx = 0;
        cont.gridy = 1;
        panel.add(btnRetry, cont);
        cont.gridx = 1;
        panel.add(btnQuit, cont);

        setContentPane(panel);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == btnRetry) {
            this.returnStatement = REPLAY;
            this.setVisible(false);
        } else if (source == btnQuit) {
            this.returnStatement = QUIT;
            this.setVisible(false);
        }
    }

    /**
     * Show Dialog
     *
     * @return action statement
     */
    public int showDialog() {
        this.setVisible(true); // Set visible
        return returnStatement; // Return
    }

}
