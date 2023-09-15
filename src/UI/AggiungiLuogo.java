package UI;

import prova.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AggiungiLuogo extends JFrame {

    private JPanel MainPanel;
    private JLabel IndirizzoLabel;
    private JTextField ViaField;
    private JLabel SedeLabel;
    private JTextField SedeField;
    private JButton AggiungiButton;
    private JButton IndietroButton;
    private JTextField CivicoField;
    private JTextField PaeseField;
    final private Controller controller;

    public AggiungiLuogo(Controller c){
        controller = c;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(MainPanel);
        MainPanel.setVisible(true);
        setSize(800, 350);

        IndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.AggiungiLuogoIndietro();
            }
        });
        AggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.CreateLuogo(ViaField.getText(), CivicoField.getText(), PaeseField.getText(), SedeField.getText());
            }
        });

        CivicoField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                CivicoField.setEditable((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.isActionKey() || e.getKeyCode() == KeyEvent.VK_BACK_SPACE);
            }
        });
    }
}
