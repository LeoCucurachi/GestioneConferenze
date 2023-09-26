package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import prova.ConferenzeTableModel;
import prova.Controller;
import prova.PartecipanteTableModel;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.TextField;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.github.lgooddatepicker.components.CalendarPanel;
import javax.swing.JLabel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import DTO.Conferenza;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.beans.PropertyChangeEvent;

public class VisualizzaConferenze extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable conferenzeTable;
	private Controller controller;
	private JTextField txtCerca;
	
	DatePicker fromDate;
	DatePicker toDate;
	private JLabel lblNewLabel;
	/**
	 * Create the frame.
	 */
	public VisualizzaConferenze(Controller c) {
		controller = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1053, 453);

		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemUpdate = new JMenuItem("Modifica");
		JMenuItem menuItemRemove = new JMenuItem("Elimina");
		JSeparator separator = new JSeparator();
		JMenuItem menuItemSessioni = new JMenuItem("Gestisci Sessioni");

		 
		popupMenu.add(menuItemUpdate);
		popupMenu.add(menuItemRemove);
		popupMenu.add(separator);
		popupMenu.add(menuItemSessioni);
		
		
		JScrollPane scrollPane = new JScrollPane();
		conferenzeTable = new JTable();
		conferenzeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
			        int r = conferenzeTable.rowAtPoint(e.getPoint());
			        if(r > 0 || r < conferenzeTable.getRowCount()) {
			        	conferenzeTable.setRowSelectionInterval(r, r);
			        } else {
			        	conferenzeTable.clearSelection();
			        }
			        
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
				}
			}
		);
		conferenzeTable.setSurrendersFocusOnKeystroke(false);
		conferenzeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		conferenzeTable.setShowVerticalLines(false);
		scrollPane.setViewportView(conferenzeTable);
		conferenzeTable.setAutoCreateRowSorter(true);
		controller.SetConferenceTableModel(conferenzeTable);

		
		menuItemUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaConferenzeToModificaConferenza((Conferenza)conferenzeTable.getModel().getValueAt(conferenzeTable.getRowSorter().convertRowIndexToModel(conferenzeTable.getSelectedRow()), 5));
			}
		});
		
		menuItemRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.removeConferenza(conferenzeTable, conferenzeTable.getRowSorter().convertRowIndexToModel(conferenzeTable.getSelectedRow()));
			}
		});
				
		menuItemSessioni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaConferenzeToVisualizzaSessioni((Conferenza)conferenzeTable.getModel().getValueAt(conferenzeTable.getRowSorter().convertRowIndexToModel(conferenzeTable.getSelectedRow()), 5));
				
			}
		});
		//conferenzeTable.setComponentPopupMenu(popupMenu);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaConferenzeIndietro();
			}
		});
		
		JPanel panel = new JPanel();
		
		JButton aggiungiConferenzaButton = new JButton("Aggiungi Conferenza");
		aggiungiConferenzaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaConferenzeToAggiungiConferenza();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(indietroButton)
							.addPreferredGap(ComponentPlacement.RELATED, 821, Short.MAX_VALUE)
							.addComponent(aggiungiConferenzaButton)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(indietroButton)
						.addComponent(aggiungiConferenzaButton)))
		);
		
		lblNewLabel = new JLabel("Cerca per Sede:");
		
		txtCerca = new JTextField();
		lblNewLabel.setLabelFor(txtCerca);
		txtCerca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TableRowSorter<ConferenzeTableModel> sorter = new TableRowSorter<ConferenzeTableModel>(((ConferenzeTableModel)conferenzeTable.getModel())); 
			    List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
			    try {
			        String text = txtCerca.getText();
			        filters.add(RowFilter.regexFilter("(?i)" + text, 4));

			    } catch (java.util.regex.PatternSyntaxException ex) {
			            return;
			    }
			    sorter.setRowFilter(RowFilter.andFilter(filters));
			    conferenzeTable.setRowSorter(sorter);
			}
		});
		txtCerca.setColumns(10);
		
		DatePickerSettings fromDatePickerSettings = new DatePickerSettings();
		fromDate = new DatePicker(fromDatePickerSettings);
		fromDatePickerSettings.setAllowKeyboardEditing(false);
		
		DatePickerSettings toDatePickerSettings = new DatePickerSettings();
		toDate = new DatePicker(toDatePickerSettings);
		toDatePickerSettings.setAllowKeyboardEditing(false);
		
		JLabel lblNewLabel_1 = new JLabel("Da:");
		lblNewLabel_1.setLabelFor(fromDate);
		
		JLabel lblNewLabel_2 = new JLabel("A:");
		lblNewLabel_2.setLabelFor(toDate);
		
		JButton istituzioniButton = new JButton("Statistiche Afferenze");
		istituzioniButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaConferenzeToVisualizzaIstituzioni();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(14)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtCerca, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
					.addGap(52)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(fromDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(toDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
					.addComponent(istituzioniButton)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(txtCerca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fromDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(toDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2)
						.addComponent(istituzioniButton)))
		);
		panel.setLayout(gl_panel);
		toDate.addDateChangeListener(new DateChangeListener() {
			
			@Override
			public void dateChanged(DateChangeEvent arg0) {
				RefreshConferenze(fromDate.getDate(), arg0.getNewDate());
				fromDatePickerSettings.setDateRangeLimits(null, toDate.getDate());
			}
		});
		
		fromDate.addDateChangeListener(new DateChangeListener() {
			
			@Override
			public void dateChanged(DateChangeEvent arg0) {
				RefreshConferenze(arg0.getNewDate(), toDate.getDate());
				toDatePickerSettings.setDateRangeLimits(fromDate.getDate(), null);
			}
		});
		contentPane.setLayout(gl_contentPane);
		
		RefreshConferenze();
		setVisible(true);
	}
	
	public void RefreshConferenze() {
		controller.SetTableToSearchLocationConference(conferenzeTable, txtCerca.getText(), fromDate.getDate(), toDate.getDate());
	}
	
	public void RefreshConferenze(LocalDate from, LocalDate to) {
		controller.SetTableToSearchLocationConference(conferenzeTable, txtCerca.getText(), from, to);
	}
}
