package miniprojet.ui;

import miniprojet.Resources;
import miniprojet.model.Data;
import miniprojet.model.Record;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Axel LE BOT
 */
public class EndGameDialog extends JDialog implements ActionListener {
    // UI
    private JButton btnSaveAndReplay;
    private JButton btnReplay;
    private JButton btnSaveAndQuit;
    private JButton btnQuit;
    private JTextField fieldNickname;

    // Behavior
    private boolean save = false;
    private int returnStatement;
    public static final int REPLAY = 1;
    public static final int QUIT = 2;

    private Data data;
    private Record record;

    /**
     * @param ownerFrame the {@code Frame} the window where the dialog will be displayed
     * @param data       the {@code Data} of the application
     * @param record     the {@code Record} of the game
     */
    EndGameDialog(JFrame ownerFrame, Data data, Record record) {
        super(ownerFrame, Resources.END_DIALOG_TITLE_END_GAME, true);
        this.data = data;
        this.record = record;

        initDialog();
    }

    private void initDialog() {
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        String msg = (this.record.getMaxValue() == Resources.GAME_GOAL) ? Resources.LABEL_END_GAME_SUCCEED : Resources.LABEL_END_GAME_FAILED;
        btnSaveAndReplay = new JButton(Resources.END_DIALOG_BUTTON_SAVE_REPLAY);
        btnSaveAndReplay.setBackground(Color.GREEN);
        btnSaveAndReplay.addActionListener(this);

        btnReplay = new JButton(Resources.END_DIALOG_BUTTON_REPLAY);
        btnReplay.addActionListener(this);

        btnQuit = new JButton(Resources.END_DIALOG_BUTTON_QUIT);
        btnQuit.setBackground(Color.RED);
        btnQuit.addActionListener(this);

        btnSaveAndQuit = new JButton(Resources.END_DIALOG_BUTTON_SAVE_QUIT);
        btnSaveAndQuit.setBackground(Color.red);
        btnSaveAndQuit.addActionListener(this);

        JLabel lblMessage = new JLabel(msg);
        lblMessage.setFont(new Font("Serif", Font.PLAIN, 36));

        JLabel lblNickname = new JLabel(Resources.FIELD_NICKNAME_LABEL);
        lblNickname.setFont(new Font("Serif", Font.PLAIN, 13));
        fieldNickname = new JTextField(20);

        JPanel panel = new JPanel(); // Create panel
        panel.setBackground(Color.WHITE); // Set background color
        panel.setLayout(new GridBagLayout()); // Set layout

        GridBagConstraints cont = new GridBagConstraints();

        // Label
        cont.fill = GridBagConstraints.CENTER;
        cont.weightx = 1;
        cont.weighty = 1;
        cont.ipadx = 0;
        cont.ipady = 0;
        cont.gridx = 0;
        cont.gridy = 0;
        cont.gridwidth = 4;
        panel.add(lblMessage, cont);

        // Form
        cont.fill = GridBagConstraints.CENTER;
        cont.gridwidth = 2;
        cont.gridx = 0;
        cont.gridy = 1;
        panel.add(lblNickname, cont);
        cont.gridx = 2;
        panel.add(fieldNickname, cont);

        // BUTTONS
        cont.fill = GridBagConstraints.CENTER;
        cont.gridwidth = 1;
        cont.gridx = 0;
        cont.gridy = 2;
        panel.add(btnSaveAndReplay, cont);
        cont.gridx = 1;
        panel.add(btnReplay, cont);
        cont.gridx = 2;
        panel.add(btnQuit, cont);
        cont.gridx = 3;
        panel.add(btnSaveAndQuit, cont);

        setContentPane(panel);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();

        if (source == btnSaveAndReplay || source == btnReplay || source == btnQuit || source == btnSaveAndQuit) {
            if (source == btnSaveAndReplay) {
                save = true;
                returnStatement = REPLAY;
            } else if (source == btnReplay) {
                save = false;
                returnStatement = REPLAY;
            } else if (source == btnQuit) {
                save = false;
                returnStatement = QUIT;
            } else if (source == btnSaveAndQuit) {
                save = true;
                returnStatement = QUIT;
            }
            record.setNickname(fieldNickname.getText());
            data.addRecord(record);
            data.notifyObservers();
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
