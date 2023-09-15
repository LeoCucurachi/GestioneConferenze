package UI;

import DTO.Luogo;
import com.github.lgooddatepicker.components.DatePicker;
import prova.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggiungiConferenza extends JFrame{
    Controller controller;
    private JLabel Data_ILabel;
    private JPanel MainPanel;
    private com.github.lgooddatepicker.components.DatePicker Data_IPicker;
    private JButton AddConferenza;
    private JLabel Data_FLabel;
    private DatePicker Data_FPicker;
    private JLabel LuogoLabel;
    private JComboBox<Luogo> LuogoComboBox;
    private JLabel DescrizioneLabel;
    private JTextField DescrizioneField;
    private JButton aggiungiLuogoButton;
    private JButton indietroButton;

    public AggiungiConferenza(Controller c){
        controller = c;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(MainPanel);
        MainPanel.setVisible(true);
        setSize(800, 350);
        controller.SetLuoghi(LuogoComboBox);

        AddConferenza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.CreateConference(Data_IPicker.getDate(), Data_FPicker.getDate(), ((Luogo) LuogoComboBox.getSelectedItem()), DescrizioneField.getText());
            }
        });
        aggiungiLuogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.AggiungiConferenzaToAggiungiLuogo();
            }
        });
    }

    public void RefreshComboBox(){
        controller.SetLuoghi(LuogoComboBox);
    }

}
