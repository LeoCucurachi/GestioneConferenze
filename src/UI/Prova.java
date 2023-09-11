package UI;

import prova.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Prova extends JFrame{
    Controller controller;
    private JTextPane ConferenzeText;
    private JButton button1;
    private JPanel MainPanel;
    private JTable ConferenzeTable;

    public Prova(Controller c){
        controller = c;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(MainPanel);
        MainPanel.setVisible(true);
        setSize(800, 350);
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        ConferenzeTable.setModel(tableModel);
        controller.setConferenceList(ConferenzeTable);
    }


}
