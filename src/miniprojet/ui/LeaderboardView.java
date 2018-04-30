package miniprojet.ui;

import miniprojet.Observer;
import miniprojet.Resources;
import miniprojet.model.Data;
import miniprojet.model.Record;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LeaderboardView extends JInternalFrame implements Observer, ActionListener {

    private Data data;

    // UI
    private JTable table;
    private ModeleRecord recordListModel;
    private JButton btnDeleteAll;
    private JButton btnDelete;

    public LeaderboardView(Data data) {
        this.data = data;
        initLeaderboardView();
    }

    private void initLeaderboardView() {
        setBackground(Color.white); // Set background color
        setTitle(Resources.LEADERBOARD_PANEL_TITLE);

        recordListModel = new ModeleRecord();
        table = new JTable(recordListModel);
        JScrollPane scrollPane = new JScrollPane(table);

        btnDelete = new JButton();
        btnDelete.setText(Resources.LEADERBOARD_BUTTON_DELETE);
        btnDelete.addActionListener(this);
        btnDeleteAll = new JButton();
        btnDeleteAll.setText(Resources.LEADERBOARD_BUTTON_DELETE_ALL);
        btnDeleteAll.addActionListener(this);


        JPanel panel = new JPanel();
        FlowLayout experimentLayout = new FlowLayout();
        panel.add(btnDelete);
        panel.add(btnDeleteAll);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    @Override
    public Dimension getPreferredSize() {
        return table.getPreferredSize();
    }

    @Override
    public void update() {
        recordListModel.removeAllRecord();
        for (Record record : data.getRecordList()) {
            recordListModel.addRecord(record);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnDelete) {
            int[] selectedRows = table.getSelectedRows();
            for (int selectedRow : selectedRows) {
                data.removeRecord(recordListModel.getElement(selectedRow));
            }
            data.notifyObservers();
        } else if (source == btnDeleteAll) {
            data.removeAllRecords();
            data.notifyObservers();
        }
    }


    public class ModeleRecord extends AbstractTableModel {
        private final ArrayList<Record> records = new ArrayList<Record>();

        String[] columnNames = {"Nickname",
                "Time",
                "Move Count",
                "Value"};

        public ModeleRecord() {
            super();
        }

        public int getRowCount() {
            return records.size();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return records.get(rowIndex).getNickname();
                case 1:
                    return records.get(rowIndex).getTimeCounter();
                case 2:
                    return records.get(rowIndex).getMoveCounter();
                case 3:
                    return records.get(rowIndex).getMaxValue();
                default:
                    return null;
            }
        }

        public void addRecord(Record record) {
            records.add(record);
            fireTableRowsInserted(records.size() - 1, records.size() - 1);
        }

        public void removeRecord(int rowIndex) {
            records.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public void removeAllRecord() {
            records.clear();
            fireTableDataChanged();
        }

        public Record getElement(int index) {
            return this.records.get(index);
        }
    }
}
