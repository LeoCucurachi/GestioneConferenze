package UI;

import prova.Controller;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.github.lgooddatepicker.components.DatePicker;

import DTO.Luogo;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AggiungiConferenza extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField descrizioneField;
	private Controller controller;
	/**
	 * Create the frame.
	 */
	public AggiungiConferenza(Controller c) {
		controller = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel dataInizioLabel = new JLabel("Data Inizio");
		dataInizioLabel.setBounds(6, 6, 84, 16);
		contentPane.add(dataInizioLabel);
		
		DatePicker dataInizioPicker = new DatePicker();
		dataInizioPicker.setBounds(6, 25, 212, 29);
		contentPane.add(dataInizioPicker);
		
		JLabel dataFineLabel = new JLabel("Data Fine");
		dataFineLabel.setBounds(6, 76, 69, 16);
		contentPane.add(dataFineLabel);
		
		DatePicker dataFinePicker = new DatePicker();
		dataFinePicker.setBounds(6, 98, 212, 29);
		contentPane.add(dataFinePicker);
		
		JLabel luogoLabel = new JLabel("Luogo");
		luogoLabel.setBounds(6, 159, 61, 16);
		contentPane.add(luogoLabel);
		
		JComboBox<Luogo> luogoComboBox = new JComboBox<Luogo>();
		luogoComboBox.setBounds(0, 187, 233, 27);
		contentPane.add(luogoComboBox);
		
		JButton aggiungiLuogoButton = new JButton("Aggiungi Luogo");
		aggiungiLuogoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.AggiungiConferenzaToAggiungiLuogo();
			}
		});
		aggiungiLuogoButton.setBounds(245, 186, 138, 29);
		contentPane.add(aggiungiLuogoButton);
		
		descrizioneField = new JTextField();
		descrizioneField.setBounds(6, 266, 299, 69);
		contentPane.add(descrizioneField);
		descrizioneField.setColumns(10);
		
		JLabel descrizioneLabel = new JLabel("Descrizione");
		descrizioneLabel.setBounds(6, 249, 84, 16);
		contentPane.add(descrizioneLabel);
		
		JButton confermaButton = new JButton("Conferma");
		confermaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.insertConferenza(dataInizioPicker.getDate(), dataFinePicker.getDate(), (Luogo)luogoComboBox.getSelectedItem(), descrizioneField.getText());
			}
		});
		confermaButton.setBounds(493, 364, 117, 29);
		contentPane.add(confermaButton);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		indietroButton.setBounds(6, 364, 117, 29);
		contentPane.add(indietroButton);
	}
}
