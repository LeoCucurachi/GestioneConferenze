package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import DTO.Conferenza;
import DTO.Sessione;
import prova.Controller;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

public class VisualizzaSessioni extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller controller;
	private JTable sessioniTable;
	private Conferenza conferenza;
	private DatePicker datePicker;
	
	/**
	 * Create the frame.
	 */
	public VisualizzaSessioni(Controller c, Conferenza conf) {
		controller = c;
		conferenza = conf;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1306, 462);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemUpdate = new JMenuItem("Modifica");
		JMenuItem menuItemRemove = new JMenuItem("Elimina");
		JSeparator separator = new JSeparator();
		JMenuItem menuItemProgramma = new JMenuItem("Visualizza Programma");
		 
		popupMenu.add(menuItemUpdate);
		popupMenu.add(menuItemRemove);
		popupMenu.add(separator);
		popupMenu.add(menuItemProgramma);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaSessioniIndietro();
			}
		});
		
		String confString = conferenza.getDescrizione() + " dal " + conferenza.getData_inizio().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " al " + conferenza.getData_fine().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " in " + conf.getLuogo(); 
		JLabel conferenzaLabel = new JLabel(confString);
		
		JButton aggiungiSessioneButton = new JButton("Aggiungi Sessione");
		aggiungiSessioneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaSessioniToAggiungiSessione(conferenza);
			}
		});
		sessioniTable = new JTable();
		controller.SetSessionTableModel(sessioniTable);
		
		JPanel panel = new JPanel();
		
		JLabel dataFilterLabel = new JLabel("Filtra per Data:");
		DatePickerSettings datePickerSettings = new DatePickerSettings();
		datePicker = new DatePicker(datePickerSettings);
		datePickerSettings.setAllowKeyboardEditing(false);
		datePickerSettings.setDateRangeLimits(conferenza.getData_inizio(), conferenza.getData_fine());
		datePickerSettings.setDefaultYearMonth(YearMonth.of(conferenza.getData_inizio().getYear(), conferenza.getData_inizio().getMonth()));
		datePickerSettings.setVisibleTodayButton(false);
		datePickerSettings.setVisibleNextYearButton(false);
		datePickerSettings.setVisiblePreviousYearButton(false);
		datePicker.addDateChangeListener(new DateChangeListener() {
			
			@Override
			public void dateChanged(DateChangeEvent arg0) {
				RefreshSessioniTable(arg0.getNewDate());
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(conferenzaLabel, GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE))
								.addComponent(indietroButton))
							.addGap(106)
							.addComponent(aggiungiSessioneButton))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1290, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 1174, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(conferenzaLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(indietroButton)
						.addComponent(aggiungiSessioneButton)))
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(14)
					.addComponent(dataFilterLabel)
					.addGap(18)
					.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(837))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(dataFilterLabel)
						.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		panel.setLayout(gl_panel);
		
		sessioniTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
			        int r = sessioniTable.rowAtPoint(e.getPoint());
			        if(r > 0 || r < sessioniTable.getRowCount()) {
			        	sessioniTable.setRowSelectionInterval(r, r);
			        } else {
			        	sessioniTable.clearSelection();
			        }
			        
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
				}
			}
		);
		scrollPane.setViewportView(sessioniTable);
		contentPane.setLayout(gl_contentPane);
		
		menuItemUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaSessioniToModificaSessione(sessioniTable, sessioniTable.getSelectedRow(), conferenza);
			}
		});
		
		menuItemRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.removeSessione(sessioniTable, sessioniTable.getSelectedRow());
			}
		});
		
		menuItemProgramma.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaSessioniToVisualizzaProgramma((Sessione)sessioniTable.getModel().getValueAt(sessioniTable.getSelectedRow(), 5));
			}
		});
		
		RefreshSessioniTable(datePicker.getDate());
		setVisible(true);
	}
	
	public Conferenza getConferenza() {
		return conferenza;
	}
	
	public void RefreshSessioniTable() {
		RefreshSessioniTable(datePicker.getDate());
	}
	
	public void RefreshSessioniTable(LocalDate data) {
		controller.SetTableToSessionOfDate(sessioniTable, conferenza, data);
	}
}
