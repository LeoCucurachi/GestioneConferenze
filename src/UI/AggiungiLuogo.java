package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import prova.Controller;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class AggiungiLuogo extends JFrame {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JPanel contentPane;
	private JTextField viaField;
	private JTextField civicoField;
	private JTextField paeseField;
	private JTextField sedeField;
	
	/**
	 * Create the frame.
	 */
	public AggiungiLuogo(Controller c) {
		controller = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel indirizzoLabel = new JLabel("Indirizzo");
		indirizzoLabel.setBounds(6, 6, 61, 16);
		
		JLabel viaLabel = new JLabel("Via:");
		viaLabel.setBounds(6, 45, 61, 16);
		
		viaField = new JTextField();
		viaField.setBounds(43, 40, 108, 26);
		viaField.setColumns(10);
		
		JLabel civicoLabel = new JLabel("Civico:");
		civicoLabel.setBounds(179, 45, 61, 16);
		
		civicoField = new JTextField();
		civicoField.setBounds(240, 40, 83, 26);
		civicoField.setColumns(10);
		
		JLabel paeseLabel = new JLabel("Paese:");
		paeseLabel.setBounds(341, 45, 61, 16);
		
		paeseField = new JTextField();
		paeseField.setBounds(389, 40, 108, 26);
		paeseField.setColumns(10);
		
		JLabel sedeLabel = new JLabel("Sede");
		sedeLabel.setBounds(6, 125, 61, 16);
		
		sedeField = new JTextField();
		sedeField.setBounds(6, 153, 491, 26);
		sedeField.setColumns(10);
		
		JButton confermaButton = new JButton("Conferma");
		confermaButton.setBounds(503, 237, 117, 29);
		confermaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.insertLuogo(viaField.getText(), civicoField.getText(), paeseField.getText(), sedeField.getText());
			}
		});
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.setBounds(6, 237, 117, 29);
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.AggiungiLuogoIndietro();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(indietroButton);
		contentPane.add(indirizzoLabel);
		contentPane.add(viaLabel);
		contentPane.add(viaField);
		contentPane.add(civicoLabel);
		contentPane.add(civicoField);
		contentPane.add(paeseField);
		contentPane.add(paeseLabel);
		contentPane.add(sedeLabel);
		contentPane.add(sedeField);
		contentPane.add(confermaButton);
		
		
		setVisible(true);
	}

}
