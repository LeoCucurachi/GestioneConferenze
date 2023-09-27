package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

import DTO.Partecipante;
import DTO.Sessione;
import principale.Controller;
import tableModels.PartecipanteTableModel;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;

import java.awt.CardLayout;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AggiungiElementoProgramma extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Controller controller;
	private Sessione sessione;
	private JTable programmaTable;
	private JTable partecipantiTable;
	private JTextField cercaField;

	/**
	 * Create the frame.
	 */
	public AggiungiElementoProgramma(Controller c, Sessione s) {
		controller = c;
		sessione = s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 669, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		
		JRadioButton eventoRadioButton = new JRadioButton("Evento");
		buttonGroup.add(eventoRadioButton);
		
		JRadioButton intervalloRadioButton = new JRadioButton("Intervallo");
		buttonGroup.add(intervalloRadioButton);
		
		JRadioButton interventoRadioButton = new JRadioButton("Intervento");
		buttonGroup.add(interventoRadioButton);
		
		
		JPanel addPane = new JPanel();
		
		TimePickerSettings oraInizioPickerSettings = new TimePickerSettings();
		TimePicker oraInizioPicker = new TimePicker(oraInizioPickerSettings);
		oraInizioPickerSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, sessione.getOra_inizio(), sessione.getOra_fine());
		
		TimePickerSettings oraFinePickerSettings = new TimePickerSettings();
		TimePicker oraFinePicker = new TimePicker(oraFinePickerSettings);
		oraFinePickerSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, sessione.getOra_inizio(), sessione.getOra_fine());
		
		JPanel specificPane = new JPanel();
		
		JPanel interventoPane = new JPanel();
		
		JTextArea abstractTextArea = new JTextArea();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		cercaField = new JTextField();
		cercaField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			    TableRowSorter<PartecipanteTableModel> sorter = new TableRowSorter<PartecipanteTableModel>(((PartecipanteTableModel)partecipantiTable.getModel())); 
			    List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
			    try {
			        String text = cercaField.getText();
			        String[] textArray = text.split(" ");

			        for (int i = 0; i < textArray.length; i++) {
			        	System.out.println(textArray[i]);
			            filters.add(RowFilter.regexFilter("(?i)" + textArray[i], 1, 2));
			        }

			    } catch (java.util.regex.PatternSyntaxException ex) {
			            return;
			    }
			    sorter.setRowFilter(RowFilter.andFilter(filters));
			    partecipantiTable.setRowSorter(sorter);
			}
		});
		
		
		cercaField.setColumns(10);
		
		JLabel cercaSpeakerLabel = new JLabel("Cerca Speaker");
		
		JLabel abstractLabel = new JLabel("Abstract");
		
		JLabel speakerLabel = new JLabel("Speaker");
		GroupLayout gl_interventoPane = new GroupLayout(interventoPane);
		gl_interventoPane.setHorizontalGroup(
			gl_interventoPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_interventoPane.createSequentialGroup()
					.addGap(6)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_interventoPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(cercaField)
						.addComponent(cercaSpeakerLabel))
					.addGap(12))
				.addGroup(gl_interventoPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(abstractLabel)
					.addContainerGap(574, Short.MAX_VALUE))
				.addGroup(gl_interventoPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(abstractTextArea, GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
					.addGap(48))
				.addGroup(gl_interventoPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(speakerLabel)
					.addContainerGap(566, Short.MAX_VALUE))
		);
		gl_interventoPane.setVerticalGroup(
			gl_interventoPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_interventoPane.createSequentialGroup()
					.addComponent(abstractLabel)
					.addGap(2)
					.addComponent(abstractTextArea, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(speakerLabel)
					.addGap(5)
					.addGroup(gl_interventoPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
						.addGroup(gl_interventoPane.createSequentialGroup()
							.addComponent(cercaSpeakerLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cercaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		
		partecipantiTable = new JTable();
		controller.SetPartecipantiTableModel(partecipantiTable);
		controller.SetPartecipanti(partecipantiTable);
		scrollPane_1.setViewportView(partecipantiTable);
		interventoPane.setLayout(gl_interventoPane);
		interventoPane.setVisible(false);
		
		JPanel intervalloPane = new JPanel();
		
		JComboBox<String> intervalloTipoComboBox = new JComboBox<String>();
		controller.SetIntervalloTipiComboBox(intervalloTipoComboBox);
		
		JLabel lblNewLabel = new JLabel("Tipo Intervento");
		GroupLayout gl_intervalloPane = new GroupLayout(intervalloPane);
		gl_intervalloPane.setHorizontalGroup(
			gl_intervalloPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_intervalloPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_intervalloPane.createParallelGroup(Alignment.LEADING)
						.addComponent(intervalloTipoComboBox, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addContainerGap(388, Short.MAX_VALUE))
		);
		gl_intervalloPane.setVerticalGroup(
			gl_intervalloPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_intervalloPane.createSequentialGroup()
					.addGap(12)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(intervalloTipoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(116, Short.MAX_VALUE))
		);
		intervalloPane.setLayout(gl_intervalloPane);
		intervalloPane.setVisible(false);
		
		JPanel eventoPane = new JPanel();
		eventoPane.setLayout(null);
		
		JComboBox<String> eventoTipoComboBox = new JComboBox<String>();
		eventoTipoComboBox.setBounds(6, 38, 211, 27);
		controller.SetEventoTipiComboBox(eventoTipoComboBox);
		eventoPane.add(eventoTipoComboBox);
		
		
		addPane.setVisible(false);
		
		JScrollPane scrollPane = new JScrollPane();
		
		programmaTable = new JTable();
		programmaTable.setRowSelectionAllowed(false);
		controller.SetProgrammaTableModel(programmaTable);
		controller.SetProgrammaOfSessione(programmaTable, sessione);
		scrollPane.setViewportView(programmaTable);
		
		JLabel oraInizioLabel = new JLabel("Ora Inizio");
		
		JLabel oraFineLabel = new JLabel("Ora Fine");
		
		JLabel programmaLabel = new JLabel("Orari Occupati");
		GroupLayout gl_addPane = new GroupLayout(addPane);
		gl_addPane.setHorizontalGroup(
			gl_addPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_addPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_addPane.createParallelGroup(Alignment.LEADING)
						.addComponent(oraFinePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(oraInizioPicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(oraInizioLabel)
						.addComponent(oraFineLabel))
					.addGap(26)
					.addGroup(gl_addPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_addPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
							.addGap(12))
						.addGroup(gl_addPane.createSequentialGroup()
							.addComponent(programmaLabel)
							.addContainerGap())))
				.addGroup(gl_addPane.createSequentialGroup()
					.addComponent(specificPane, GroupLayout.PREFERRED_SIZE, 633, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_addPane.setVerticalGroup(
			gl_addPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_addPane.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_addPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_addPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(programmaLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_addPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_addPane.createSequentialGroup()
									.addGap(11)
									.addComponent(oraInizioPicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(oraFineLabel)
									.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
									.addComponent(oraFinePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(14))
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(specificPane, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
						.addComponent(oraInizioLabel))
					.addContainerGap())
		);
		specificPane.setLayout(new CardLayout(0, 0));
		specificPane.add(interventoPane, "1");
		specificPane.add(intervalloPane, "2");
		specificPane.add(eventoPane, "3");
		
		JLabel tipoEventoLabel = new JLabel("Tipo Evento");
		tipoEventoLabel.setBounds(16, 10, 97, 16);
		eventoPane.add(tipoEventoLabel);
		addPane.setLayout(gl_addPane);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.AggiungiElementoProgrammaIndietro();
			}
		});
		
		JButton confermaButton = new JButton("Conferma");
		confermaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(intervalloRadioButton.isSelected()) {
					controller.insertIntervallo(oraInizioPicker.getTime(), oraFinePicker.getTime(), (String)intervalloTipoComboBox.getSelectedItem(), sessione);
				} else if (eventoRadioButton.isSelected()) {
					controller.insertEvento(oraInizioPicker.getTime(), oraFinePicker.getTime(), (String)eventoTipoComboBox.getSelectedItem(), sessione);
				} else if (interventoRadioButton.isSelected()) {
					System.out.println(partecipantiTable.getRowSorter().convertRowIndexToModel(partecipantiTable.getSelectedRow()));
					controller.insertIntervento(oraInizioPicker.getTime(), oraFinePicker.getTime(), abstractTextArea.getText(), partecipantiTable.getRowSorter().convertRowIndexToModel(partecipantiTable.getSelectedRow()), partecipantiTable, sessione);
				}
			}
		});
		confermaButton.setEnabled(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(indietroButton, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(interventoRadioButton, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(92)
							.addComponent(eventoRadioButton)
							.addGap(113)
							.addComponent(intervalloRadioButton)
							.addContainerGap(172, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(confermaButton, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(addPane, GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(interventoRadioButton)
							.addComponent(eventoRadioButton))
						.addComponent(intervalloRadioButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(addPane, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(indietroButton)
						.addComponent(confermaButton))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		eventoRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addPane.setVisible(true);
				confermaButton.setEnabled(true);
				specificPane.setVisible(true);
//				intervalloPane.setVisible(false);
//				interventoPane.setVisible(false);
//				eventoPane.setVisible(true);
				((CardLayout)specificPane.getLayout()).show(specificPane, "3");

			}
		});
		
		intervalloRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addPane.setVisible(true);
				specificPane.setVisible(true);
				confermaButton.setEnabled(true);
//				intervalloPane.setVisible(true);
//				interventoPane.setVisible(false);
//				eventoPane.setVisible(false);
				((CardLayout)specificPane.getLayout()).show(specificPane, "2");
				
			}
		});
		
		interventoRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addPane.setVisible(true);
				confermaButton.setEnabled(true);
				specificPane.setVisible(true);
//				intervalloPane.setVisible(false);
//				interventoPane.setVisible(true);
//				eventoPane.setVisible(false);
				((CardLayout)specificPane.getLayout()).show(specificPane, "1");

				
			}
		});
		
		eventoPane.setVisible(false);
		specificPane.setVisible(false);
		
		setVisible(true);
	}
}
