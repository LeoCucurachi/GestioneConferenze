package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import DTO.Sessione;
import principale.Controller;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.demo.zExtra_DataBindingDemo;

import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class VisualizzaProgramma extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller controller;
	private Sessione sessione;
	private JTable programmaTable;
	private JButton aggiungiElementoButton;
	/**
	 * Create the frame.
	 */
	public VisualizzaProgramma(Controller c, Sessione s) {
		controller = c;
		sessione = s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 481);
		setSize(800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Visualizza Programma");


		setContentPane(contentPane);
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemRemove = new JMenuItem("Elimina");
		 
		popupMenu.add(menuItemRemove);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaProgrammaIndietro();
			}
		});
		
		programmaTable = new JTable();
		programmaTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
			        int r = programmaTable.rowAtPoint(e.getPoint());
			        if(r > 0 || r < programmaTable.getRowCount()) {
			        	programmaTable.setRowSelectionInterval(r, r);
			        } else {
			        	programmaTable.clearSelection();
			        }
			        
			        if(programmaTable.isEnabled()) {
			        	popupMenu.show(e.getComponent(), e.getX(), e.getY());
			        }
				}
				}
			}
		);
		scrollPane.setViewportView(programmaTable);
		
		controller.SetProgrammaTableModel(programmaTable);
		controller.SetProgrammaOfSessione(programmaTable, sessione);
		
		aggiungiElementoButton = new JButton("Aggiungi");
		aggiungiElementoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaProgrammaToAggiungiElementoProgramma(sessione);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(indietroButton)
					.addPreferredGap(ComponentPlacement.RELATED, 596, Short.MAX_VALUE)
					.addComponent(aggiungiElementoButton))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(53)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(indietroButton)
						.addComponent(aggiungiElementoButton))
					.addContainerGap())
		);
		
		menuItemRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deleteElementoProgramma(programmaTable, programmaTable.getRowSorter().convertRowIndexToModel(programmaTable.getSelectedRow()));
			}
		});
		
		TimePickerSettings oraInizioTimePickerSettings = new TimePickerSettings();
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
	
	public void RefreshProgrammaTable() {
		controller.SetProgrammaOfSessione(programmaTable, sessione);
	}
}
