package UI;

import java.awt.EventQueue;
import java.time.YearMonth;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;

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
import principale.Controller;
import tableModels.PartecipanteTableModel;

import com.github.lgooddatepicker.components.DateTimePicker;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AggiungiSessione extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Conferenza conferenza;
	private Controller controller;
	private JComboBox<Locazione> locazioneComboBox;
	private JTable coordinatoreTable;
	private JTable keynoteTable;
	private JTextField cercaCoordinatore;
	private JTextField cercaKeynote;

	/**
	 * Create the frame.
	 */
	public AggiungiSessione(Controller c, Conferenza conf) {
		controller = c;
		conferenza = conf;
		setTitle("Aggiungi Sessione");

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 652, 632);
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
				System.out.print(coordinatoreTable.getSelectedRow());
					controller.insertSessione(dataSessionePicker.getDate(), oraInizioPicker.getTime(), oraFinePicker.getTime(), conferenza, (Locazione)locazioneComboBox.getSelectedItem(), coordinatoreTable.getSelectedRow(), coordinatoreTable, keynoteTable.getSelectedRow(), keynoteTable);
			}
		});
		confermaButton.setBounds(529, 569, 117, 29);
		contentPane.add(confermaButton);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.AggiungiSessioneIndietro();
			}
		});
		indietroButton.setBounds(6, 569, 117, 29);
		contentPane.add(indietroButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 296, 386, 116);
		contentPane.add(scrollPane);
		
		coordinatoreTable = new JTable();
		controller.SetPartecipantiTableModel(coordinatoreTable);
		controller.SetPartecipanti(coordinatoreTable);
		scrollPane.setViewportView(coordinatoreTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 441, 386, 116);
		contentPane.add(scrollPane_1);
		
		keynoteTable = new JTable();
		controller.SetPartecipantiTableModel(keynoteTable);
		controller.SetPartecipanti(keynoteTable);
		scrollPane_1.setViewportView(keynoteTable);
		
		JLabel coordinatoreLabel = new JLabel("Coordinatore");
		coordinatoreLabel.setBounds(6, 268, 95, 16);
		contentPane.add(coordinatoreLabel);
		
		JLabel keynoteLabel = new JLabel("Keynote Speaker");
		keynoteLabel.setBounds(6, 424, 117, 16);
		contentPane.add(keynoteLabel);
		
		cercaCoordinatore = new JTextField();
		cercaCoordinatore.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			    TableRowSorter<PartecipanteTableModel> sorter = new TableRowSorter<PartecipanteTableModel>(((PartecipanteTableModel)coordinatoreTable.getModel())); 
			    List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
			    try {
			        String text = cercaCoordinatore.getText();
			        String[] textArray = text.split(" ");

			        for (int i = 0; i < textArray.length; i++) {
			        	System.out.println(textArray[i]);
			            filters.add(RowFilter.regexFilter("(?i)" + textArray[i], 1, 2));
			        }

			    } catch (java.util.regex.PatternSyntaxException ex) {
			            return;
			    }
			    sorter.setRowFilter(RowFilter.andFilter(filters));
			    coordinatoreTable.setRowSorter(sorter);
			}
		});
		cercaCoordinatore.setBounds(469, 386, 130, 26);
		contentPane.add(cercaCoordinatore);
		cercaCoordinatore.setColumns(10);
		
		cercaKeynote = new JTextField();
		cercaKeynote.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			    TableRowSorter<PartecipanteTableModel> sorter = new TableRowSorter<PartecipanteTableModel>(((PartecipanteTableModel)keynoteTable.getModel())); 
			    List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
			    try {
			        String text = cercaKeynote.getText();
			        String[] textArray = text.split(" ");

			        for (int i = 0; i < textArray.length; i++) {
			        	System.out.println(textArray[i]);
			            filters.add(RowFilter.regexFilter("(?i)" + textArray[i], 1, 2));
			        }

			    } catch (java.util.regex.PatternSyntaxException ex) {
			            return;
			    }
			    sorter.setRowFilter(RowFilter.andFilter(filters));
			    keynoteTable.setRowSorter(sorter);
			}
		});
		cercaKeynote.setBounds(469, 531, 130, 26);
		contentPane.add(cercaKeynote);
		cercaKeynote.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Cera Coordinatore");
		lblNewLabel.setBounds(469, 368, 117, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Cerca Keynote Speaker");
		lblNewLabel_1.setBounds(469, 514, 154, 16);
		contentPane.add(lblNewLabel_1);
		
		RefreshComboBox();
		
		setVisible(true);
	}
	
	public void RefreshComboBox() {
		controller.SetLocazioniComboBox(locazioneComboBox, conferenza.getLuogo());
	}
}
