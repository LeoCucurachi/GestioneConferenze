package UI;

import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAmount;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;

import DTO.Conferenza;
import DTO.Locazione;
import DTO.Partecipante;
import DTO.Sessione;
import principale.Controller;
import tableModels.SessioniTableModel;

import com.github.lgooddatepicker.components.DateTimePicker;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ModificaSessione extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Conferenza conferenza;
	private Controller controller;
	private JComboBox<Locazione> locazioneComboBox;

	/**
	 * Create the frame.
	 */
	public ModificaSessione(Controller c, Sessione sessione) {
		controller = c;
		conferenza = sessione.getConferenza();
		setTitle("Modifica Sessione");

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel dataLabel = new JLabel("Data Sessione");
		dataLabel.setBounds(6, 6, 154, 16);
		contentPane.add(dataLabel);
		
		DatePickerSettings datePickerSettings = new DatePickerSettings();
		DatePicker dataSessionePicker = new DatePicker(datePickerSettings);
		datePickerSettings.setAllowKeyboardEditing(false);
		datePickerSettings.setDateRangeLimits(conferenza.getData_inizio(), conferenza.getData_fine());
		datePickerSettings.setDefaultYearMonth(YearMonth.of(conferenza.getData_inizio().getYear(), conferenza.getData_inizio().getMonth()));
		datePickerSettings.setVisibleTodayButton(false);
		datePickerSettings.setVisibleNextYearButton(false);
		datePickerSettings.setVisiblePreviousYearButton(false);
		dataSessionePicker.setBounds(6, 25, 212, 29);
		dataSessionePicker.setDate(sessione.getData_sessione());
//		dataSessionePicker.addDateChangeListener(new DateChangeListener() {
//			
//			@Override
//			public void dateChanged(DateChangeEvent arg0) {
//				controller.SetSessioniTableOfDateAndLocation(sessioniTable, arg0.getNewDate(), (Locazione)locazioneComboBox.getSelectedItem());		
//			}
//		});
		contentPane.add(dataSessionePicker);
		
		locazioneComboBox = new JComboBox<Locazione>();
//		locazioneComboBox.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				controller.SetSessioniTableOfDateAndLocation(sessioniTable, dataSessionePicker.getDate(), (Locazione)locazioneComboBox.getSelectedItem());
//			}
//		});
		locazioneComboBox.setBounds(6, 223, 248, 27);
		contentPane.add(locazioneComboBox);
		
		JLabel locazioneLabel = new JLabel("Locazione");
		locazioneLabel.setBounds(6, 205, 95, 16);
		contentPane.add(locazioneLabel);
		
		JLabel oraInizioLabel = new JLabel("Ora Inizio");
		oraInizioLabel.setBounds(6, 66, 61, 16);
		contentPane.add(oraInizioLabel);
		
		TimePickerSettings oraInizioPickerSettings = new TimePickerSettings();
		TimePicker oraInizioPicker = new TimePicker(oraInizioPickerSettings);
		oraInizioPickerSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
		oraInizioPicker.setTime(sessione.getOra_inizio());
		oraInizioPicker.setBounds(6, 84, 127, 29);
		contentPane.add(oraInizioPicker);
		
		JLabel oraFineLabel = new JLabel("Ora Fine");
		oraFineLabel.setBounds(6, 135, 61, 16);
		contentPane.add(oraFineLabel);
		
		TimePickerSettings oraFinePickerSettings = new TimePickerSettings();
		TimePicker oraFinePicker = new TimePicker(oraFinePickerSettings);
		oraFinePickerSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
		oraFinePicker.setBounds(6, 152, 127, 29);
		oraFinePicker.setTime(sessione.getOra_fine());
		contentPane.add(oraFinePicker);
		
		oraInizioPicker.addTimeChangeListener(new TimeChangeListener() {
			
			@Override
			public void timeChanged(TimeChangeEvent arg0) {
				oraFinePickerSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, arg0.getNewTime().plusMinutes(5), null);
				
			}
		});
		oraFinePicker.addTimeChangeListener(new TimeChangeListener() {
			
			@Override
			public void timeChanged(TimeChangeEvent arg0) {
				oraInizioPickerSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, arg0.getNewTime().minusMinutes(5));
				
			}
		});
		
		JButton confermaButton = new JButton("Conferma");
		confermaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.updateSessione(sessione.getId(), dataSessionePicker.getDate(), oraInizioPicker.getTime(), oraFinePicker.getTime(), conferenza, (Locazione)locazioneComboBox.getSelectedItem(), sessione.getCoordinatore());
			}
		});
		confermaButton.setBounds(427, 282, 117, 29);
		contentPane.add(confermaButton);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.ModificaSessioneIndietro();
			}
		});
		indietroButton.setBounds(6, 282, 117, 29);
		contentPane.add(indietroButton);
				
		RefreshComboBox();
		locazioneComboBox.setSelectedItem(sessione.getLocazione());
		
		setVisible(true);
	}
	
	public void RefreshComboBox() {
		controller.SetLocazioniComboBox(locazioneComboBox, conferenza.getLuogo());
	}
}
