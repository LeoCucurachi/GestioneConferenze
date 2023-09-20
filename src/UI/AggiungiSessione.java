package UI;

import java.awt.EventQueue;
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
import prova.Controller;

import com.github.lgooddatepicker.components.DateTimePicker;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AggiungiSessione extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Conferenza conferenza;
	private Controller controller;
	private JComboBox<Locazione> locazioneComboBox;

	/**
	 * Create the frame.
	 */
	public AggiungiSessione(Controller c, Conferenza conf) {
		controller = c;
		conferenza = conf;
		
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
		contentPane.add(dataSessionePicker);
		
		locazioneComboBox = new JComboBox<Locazione>();
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
		oraInizioPickerSettings.generatePotentialMenuTimes(TimeIncrement.FiveMinutes, null, null);
		oraInizioPicker.setBounds(6, 84, 127, 29);
		contentPane.add(oraInizioPicker);
		
		JLabel oraFineLabel = new JLabel("Ora Fine");
		oraFineLabel.setBounds(6, 135, 61, 16);
		contentPane.add(oraFineLabel);
		
		TimePickerSettings oraFinePickerSettings = new TimePickerSettings();
		TimePicker oraFinePicker = new TimePicker(oraFinePickerSettings);
		oraFinePickerSettings.generatePotentialMenuTimes(TimeIncrement.FiveMinutes, null, null);
		oraFinePicker.setBounds(6, 152, 127, 29);
		contentPane.add(oraFinePicker);
		
		oraInizioPicker.addTimeChangeListener(new TimeChangeListener() {
			
			@Override
			public void timeChanged(TimeChangeEvent arg0) {
				oraFinePickerSettings.generatePotentialMenuTimes(TimeIncrement.FiveMinutes, arg0.getNewTime().plusMinutes(5), null);
				
			}
		});
		oraFinePicker.addTimeChangeListener(new TimeChangeListener() {
			
			@Override
			public void timeChanged(TimeChangeEvent arg0) {
				oraInizioPickerSettings.generatePotentialMenuTimes(TimeIncrement.FiveMinutes, null, arg0.getNewTime().minusMinutes(5));
				
			}
		});
		
		JButton confermaButton = new JButton("Conferma");
		confermaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.insertSessione(dataSessionePicker.getDate(), oraInizioPicker.getTime(), oraFinePicker.getTime(), conferenza, (Locazione)locazioneComboBox.getSelectedItem(), new Partecipante(1));
			}
		});
		confermaButton.setBounds(427, 282, 117, 29);
		contentPane.add(confermaButton);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.AggiungiSessioneIndietro();
			}
		});
		indietroButton.setBounds(6, 282, 117, 29);
		contentPane.add(indietroButton);
		
		RefreshComboBox();
		
		setVisible(true);
	}
	
	public void RefreshComboBox() {
		controller.SetLocazioniComboBox(locazioneComboBox, conferenza.getLuogo());
	}
}
