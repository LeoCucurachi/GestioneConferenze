package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.github.lgooddatepicker.components.DatePicker;

import DTO.Conferenza;
import DTO.Luogo;
import Exceptions.DataFineDopoDataInizioException;
import principale.Controller;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ModificaConferenza extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField descrizioneField;
	private Controller controller;
	private JComboBox<Luogo> luogoComboBox;
	/**
	 * Create the frame.
	 */
	public ModificaConferenza(Controller c, Conferenza conf) {
		controller = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1425, 900);
		setTitle("Modifica Conferenza");

		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel dataInizioLabel = new JLabel("Data Inizio");
		dataInizioLabel.setBounds(6, 6, 84, 16);
		contentPane.add(dataInizioLabel);
		
		DatePicker dataInizioPicker = new DatePicker();
		dataInizioPicker.setBounds(6, 25, 212, 29);
		dataInizioPicker.setDate(conf.getData_inizio());
		contentPane.add(dataInizioPicker);
		
		JLabel dataFineLabel = new JLabel("Data Fine");
		dataFineLabel.setBounds(6, 76, 69, 16);
		contentPane.add(dataFineLabel);
		
		DatePicker dataFinePicker = new DatePicker();
		dataFinePicker.setBounds(6, 98, 212, 29);
		dataFinePicker.setDate(conf.getData_fine());
		contentPane.add(dataFinePicker);
		
		JLabel luogoLabel = new JLabel("Luogo");
		luogoLabel.setBounds(6, 159, 61, 16);
		contentPane.add(luogoLabel);
		
		luogoComboBox = new JComboBox<Luogo>();
		luogoComboBox.setBounds(0, 187, 373, 27);
		controller.SetLuoghi(luogoComboBox);
		luogoComboBox.setSelectedItem(conf.getLuogo());
		contentPane.add(luogoComboBox);
		
		descrizioneField = new JTextField();
		descrizioneField.setBounds(6, 266, 517, 69);
		descrizioneField.setText(conf.getDescrizione());
		contentPane.add(descrizioneField);
		descrizioneField.setColumns(10);
		
		JLabel descrizioneLabel = new JLabel("Descrizione");
		descrizioneLabel.setBounds(6, 249, 84, 16);
		contentPane.add(descrizioneLabel);
		
		JButton confermaButton = new JButton("Conferma");
		confermaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.updateConferenza(conf.getId(), dataInizioPicker.getDate(), dataFinePicker.getDate(), (Luogo)luogoComboBox.getSelectedItem(), descrizioneField.getText(), conf);
			}
		});
		confermaButton.setBounds(493, 364, 117, 29);
		contentPane.add(confermaButton);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.ModificaConferenzaIndietro();
			}
		});
		indietroButton.setBounds(6, 364, 117, 29);
		contentPane.add(indietroButton);
		
		setVisible(true);
	}
	
	public void RefreshLuoghiComboBox() {
		controller.SetLuoghi(luogoComboBox);
	}
}
