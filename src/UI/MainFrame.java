package UI;

import prova.Controller;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
	
	private Controller controller;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainFrame(Controller c) {
		setResizable(false);
		controller = c;
		setTitle("Gestione Conferenze");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 651, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 639, 59);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 66, 639, 226);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton AggiungiConferenzaButton = new JButton("Aggiungi una nuova conferenza");
		AggiungiConferenzaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.VisualizzaConferenzeToAggiungiConferenza();
			}
		});
		AggiungiConferenzaButton.setBounds(6, 16, 270, 29);
		panel_1.add(AggiungiConferenzaButton);
		
		JButton VisualizzaConferenzeButton = new JButton("Visualizza le conferenze");
		VisualizzaConferenzeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.MainFrameToVisualizzaConferenze();
			}
		});
		VisualizzaConferenzeButton.setBounds(6, 57, 270, 29);
		panel_1.add(VisualizzaConferenzeButton);
		
		setVisible(true);
	}
}
