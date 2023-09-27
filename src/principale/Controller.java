package principale;

import DAO.ConferenzaDAO;
import DAO.EventoDAO;
import DAO.IntervalloDAO;
import DAO.InterventoDAO;
import DAO.IstituzioneDAO;
import DAO.LocazioneDAO;
import DAO.LuogoDAO;
import DAO.PartecipanteDAO;
import DAO.SessioneDAO;
import DTO.Conferenza;
import DTO.ElementoProgramma;
import DTO.Evento;
import DTO.Intervallo;
import DTO.Intervento;
import DTO.Istituzione;
import DTO.Locazione;
import DTO.Luogo;
import DTO.Partecipante;
import DTO.Sessione;
import Exceptions.NullFieldException;
import PGDAO.ConferenzaPGDAO;
import PGDAO.EventoPGDAO;
import PGDAO.IntervalloPGDAO;
import PGDAO.InterventoPGDAO;
import PGDAO.IstituzionePGDAO;
import PGDAO.LocazionePGDAO;
import PGDAO.LuogoPGDAO;
import PGDAO.PartecipantePGDAO;
import PGDAO.SessionePGDAO;
import UI.AggiungiConferenza;
import UI.AggiungiElementoProgramma;
import UI.AggiungiLuogo;
import UI.AggiungiSessione;
import UI.MainFrame;
import UI.ModificaConferenza;
import UI.ModificaSessione;
import UI.VisualizzaConferenze;
import UI.VisualizzaIstituzioni;
import UI.VisualizzaProgramma;
import UI.VisualizzaSessioni;
import tableModels.ConferenzeTableModel;
import tableModels.DateTableRenderer;
import tableModels.IstituzioniTableModel;
import tableModels.PartecipanteTableModel;
import tableModels.ProgrammaTableModel;
import tableModels.SessioniTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.w3c.dom.html.HTMLTableCaptionElement;

import com.github.lgooddatepicker.demo.zExtra_DataBindingDemo;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Controller {
    ConferenzaDAO conferenzaDAO;
    LuogoDAO luogoDAO;
    SessioneDAO sessioneDAO;
    LocazioneDAO locazioneDAO;
    IntervalloDAO intervalloDAO;
    InterventoDAO interventoDAO;
    EventoDAO eventoDAO;
    PartecipanteDAO partecipanteDAO;
    IstituzioneDAO istituzioneDAO;
    
    AggiungiConferenza aggiungiConferenza;
    AggiungiLuogo  aggiungiLuogo;
    MainFrame mainFrame;
    VisualizzaConferenze visualizzaConferenze;
    ModificaConferenza modificaConferenza;
    VisualizzaSessioni visualizzaSessioni;
    AggiungiSessione aggiungiSessione;
    ModificaSessione modificaSessione;
    VisualizzaProgramma visualizzaProgramma;
    AggiungiElementoProgramma aggiungiElementoProgramma;
    VisualizzaIstituzioni visualizzaIstituzioni;
//    VisualizzaIstituzioni visualizzaIstituzioni;

    
    	
    // main
    
    public static void main(String [] args){
        Controller controller = new Controller();
    }

    // Constructor
    
    public Controller(){
        conferenzaDAO = new ConferenzaPGDAO();
        luogoDAO = new LuogoPGDAO();
        sessioneDAO = new SessionePGDAO();
        locazioneDAO = new LocazionePGDAO();
        interventoDAO = new InterventoPGDAO();
        intervalloDAO = new IntervalloPGDAO();
        eventoDAO = new EventoPGDAO();
        partecipanteDAO = new PartecipantePGDAO();
        istituzioneDAO = new IstituzionePGDAO();
        
        visualizzaConferenze = new VisualizzaConferenze(this);        
    }

    
    // VisualizzaConferenze
    
    public void SetConferenceTableModel(JTable table){
        DefaultTableModel tableModel = new ConferenzeTableModel();

        table.setModel(tableModel);
        
        DefaultTableColumnModel defaultTableColumnModel = (DefaultTableColumnModel)table.getColumnModel();
              
        defaultTableColumnModel.getColumn(2).setPreferredWidth(140);
        defaultTableColumnModel.getColumn(2).setMaxWidth(140);
        defaultTableColumnModel.getColumn(2).setCellRenderer(new DateTableRenderer());
                
        defaultTableColumnModel.getColumn(3).setPreferredWidth(140);
        defaultTableColumnModel.getColumn(3).setMaxWidth(140);
        defaultTableColumnModel.getColumn(3).setCellRenderer(new DateTableRenderer());

        
        
        defaultTableColumnModel.removeColumn(table.getColumn("ID"));
        defaultTableColumnModel.removeColumn(table.getColumn("Conferenza"));

        table.setAutoCreateRowSorter(true);   
    }

    public void SetTableToAllConferences(JTable table) {
    	DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
    	tableModel.setRowCount(0);
    	ArrayList<Conferenza> conferenze = conferenzaDAO.getAll();
    	for (Conferenza conferenza:conferenze) {
            tableModel.addRow(new Object[]{conferenza.getId(), conferenza.getDescrizione(), conferenza.getData_inizio(), conferenza.getData_fine(), conferenza.getLuogo(), conferenza});
        }
    }
    
    public void SetTableToSearchLocationConference(JTable table, String src, LocalDate from, LocalDate to) {
    	DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
    	tableModel.setRowCount(0);
    	System.out.println("qui");
    	ArrayList<Conferenza> conferenze = conferenzaDAO.getAll();
    	for (Conferenza conferenza:conferenze) {
    		boolean condition = (from == null && to == null) || (from != null && to == null && (conferenza.getData_inizio().isAfter(from) || conferenza.getData_inizio().isEqual(from))) || (from == null && to != null && (conferenza.getData_fine().isBefore(to) || conferenza.getData_fine().isEqual(to))) || (from != null && to != null && (conferenza.getData_fine().isBefore(to) || conferenza.getData_fine().isEqual(to)) && (conferenza.getData_inizio().isAfter(from) || conferenza.getData_inizio().isEqual(from))); 
    		if(condition) {
    			tableModel.addRow(new Object[]{conferenza.getId(), conferenza.getDescrizione(), conferenza.getData_inizio(), conferenza.getData_fine(), conferenza.getLuogo(), conferenza});
    		}
        }
    }
    
    public void removeConferenza(JTable table, int row) {
		int id = (int)table.getModel().getValueAt(row, 0);
		
		String msg = "Sei sicuro di voler eliminare la conferenza '";
		msg += (String)table.getModel().getValueAt(row, 1);
		msg += "' dal " + (LocalDate)table.getModel().getValueAt(row, 2) + " al " + (LocalDate)table.getModel().getValueAt(row, 3) +"?";
		int result = JOptionPane.showConfirmDialog(visualizzaConferenze, msg, "Eliminazione Conferenza", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(result == 0) {
			conferenzaDAO.delete(id);
			((DefaultTableModel)table.getModel()).removeRow(row);
			visualizzaConferenze.RefreshConferenze();
		}
    }
    
    public void VisualizzaConferenzeIndietro() {
    	System.exit(0);
    }

	public void VisualizzaConferenzeToModificaConferenza(Conferenza c) {
		visualizzaConferenze.setVisible(false);
		modificaConferenza = new ModificaConferenza(this, c);
	}
	
	public void VisualizzaConferenzeToVisualizzaSessioni(Conferenza c) {
		visualizzaConferenze.setVisible(false);
		visualizzaSessioni = new VisualizzaSessioni(this, c);
	}
	
    public void VisualizzaConferenzeToAggiungiConferenza() {
    	visualizzaConferenze.setVisible(false);
    	aggiungiConferenza = new AggiungiConferenza(this);
    }
    
    public void VisualizzaConferenzeToVisualizzaIstituzioni() {
    	visualizzaConferenze.setVisible(false);
    	visualizzaIstituzioni = new VisualizzaIstituzioni(this);
    }
    
    // AggiungiConferenza
    
    public void insertConferenza(LocalDate data_i, LocalDate data_f, Luogo luogo, String descrizione){
        try {
            if (data_i != null && data_f != null && luogo != null) {
                Conferenza conferenza = new Conferenza(data_i, data_f, descrizione, luogo);
                conferenzaDAO.insert(conferenza);
                JOptionPane.showMessageDialog(aggiungiConferenza, "Conferenza inserita correttamente");
                AggiungiConferenzaIndietro();
                
            }
            else {
                throw new NullFieldException();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aggiungiConferenza, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void AggiungiConferenzaIndietro() {
    	aggiungiConferenza.dispose();
    	visualizzaConferenze.RefreshConferenze();
    	visualizzaConferenze.setVisible(true);
    }

    public void SetLuoghi(JComboBox<Luogo> comboBox){
        DefaultComboBoxModel<Luogo> defaultComboBoxModel = new DefaultComboBoxModel<>();
        defaultComboBoxModel.addAll(luogoDAO.getAll());

        comboBox.setModel(defaultComboBoxModel);
    }
    
    public void AggiungiConferenzaToAggiungiLuogo(){
        aggiungiLuogo = new AggiungiLuogo(this);
        aggiungiLuogo.setVisible(true);
        aggiungiLuogo.setAlwaysOnTop(true);
    }
    

    // AggiungiLuogo
    public void insertLuogo(String via, String civico, String paese, String sede){
        try {
            if (!via.isBlank() && !civico.isBlank() && !paese.isBlank() && !sede.isBlank()) {
                Luogo luogo = new Luogo(via + ", " + civico + ", " + paese, sede);
                luogoDAO.insert(luogo);
                aggiungiConferenza.RefreshLuoghiComboBox();
                AggiungiLuogoIndietro();
            }
            else {
                throw new NullFieldException();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aggiungiLuogo, e.getMessage());
        }
    }
    
    public void AggiungiLuogoIndietro(){
        aggiungiLuogo.dispose();
        aggiungiConferenza.setVisible(true);
    }
    
    // MainFrame
    
    public void MainFrameToVisualizzaConferenze() {
    	mainFrame.setVisible(false);
    	visualizzaConferenze = new VisualizzaConferenze(this);
    }
    
	// ModificaConferenza
    
	public void updateConferenza(int id, LocalDate data_i, LocalDate data_f, Luogo luogo, String descrizione, Conferenza c) {
        try {
            if (data_i != null && data_f != null && luogo != null) {
                c = new Conferenza(data_i, data_f, descrizione, luogo);
                conferenzaDAO.update(id, c);
                JOptionPane.showMessageDialog(aggiungiConferenza, "Conferenza modificata correttamente");
                visualizzaConferenze.RefreshConferenze();
                ModificaConferenzaIndietro();
                
            }
            else {
                throw new NullFieldException();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aggiungiConferenza, e.getMessage());
        }
	}
	
	public void ModificaConferenzaIndietro() {
		modificaConferenza.dispose();
		visualizzaConferenze.setVisible(true);
	}
	
	// VisualizzaSessioni
	
	public void SetSessionTableModel(JTable table) {
	       DefaultTableModel tableModel = new SessioniTableModel();
	        table.setModel(tableModel);
	        
	        DefaultTableColumnModel defaultTableColumnModel = (DefaultTableColumnModel)table.getColumnModel();
	              
	        defaultTableColumnModel.getColumn(1).setPreferredWidth(140);
	        defaultTableColumnModel.getColumn(1).setMaxWidth(140);
	        defaultTableColumnModel.getColumn(1).setMinWidth(140);
	        defaultTableColumnModel.getColumn(1).setCellRenderer(new DateTableRenderer());


	                
	        defaultTableColumnModel.getColumn(2).setPreferredWidth(60);
	        defaultTableColumnModel.getColumn(2).setMaxWidth(60);
	        defaultTableColumnModel.getColumn(2).setMinWidth(60);

	        
	        defaultTableColumnModel.getColumn(3).setPreferredWidth(60);
	        defaultTableColumnModel.getColumn(3).setMaxWidth(60);
	        defaultTableColumnModel.getColumn(3).setMinWidth(60);

	        
	        defaultTableColumnModel.removeColumn(table.getColumn("ID")); 
	        defaultTableColumnModel.removeColumn(table.getColumn("Sessione"));  

	}
	
//	public void SetTableToAllSessions(JTable table, Conferenza conferenza) {
//    	DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
//    	tableModel.setRowCount(0);
//    	ArrayList<Sessione> sessioni = sessioneDAO.getSessioniOfConferenza(conferenza);
//    	for (Sessione sessione:sessioni) {
//    		System.out.println(sessione.getId());
//            tableModel.addRow(new Object[]{sessione.getId(), sessione.getData_sessione(), sessione.getOra_inizio(), sessione.getOra_fine(), sessione.getLocazione(), sessione, sessione.getCoordinatore(), sessione.getKeynote()});
//        }
//	}
	
	public void SetTableToSessionOfDate(JTable table, Conferenza conferenza, LocalDate date) {
    	DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
    	tableModel.setRowCount(0);
    	ArrayList<Sessione> sessioni = sessioneDAO.getSessioniOfConferenza(conferenza);
    	for (Sessione sessione:sessioni) {
    		if(date == null || (date != null && sessione.getData_sessione().isEqual(date)))
    			tableModel.addRow(new Object[]{sessione.getId(), sessione.getData_sessione(), sessione.getOra_inizio(), sessione.getOra_fine(), sessione.getLocazione(), sessione, sessione.getCoordinatore(), sessione.getKeynote()});
        }
	}
	
	public void VisualizzaSessioniIndietro() {
		visualizzaSessioni.dispose();
		visualizzaConferenze.setVisible(true);
	}
	
	public void VisualizzaSessioniToAggiungiSessione(Conferenza conferenza) {
		visualizzaSessioni.setVisible(false);
		aggiungiSessione = new AggiungiSessione(this, conferenza);
	}
	
	public void VisualizzaSessioniToVisualizzaProgramma(Sessione sessione) {
		visualizzaSessioni.setVisible(false);
		visualizzaProgramma = new VisualizzaProgramma(this, sessione);
	}
	
	public void VisualizzaSessioniToModificaSessione(JTable table, int row) {
		visualizzaSessioni.setVisible(false);
//		int id = (int)table.getModel().getValueAt(row, 0);
//		LocalDate date = (LocalDate)table.getModel().getValueAt(row, 1);
//		LocalTime ora_inizio = (LocalTime)table.getModel().getValueAt(row, 2);
//		LocalTime ora_fine = (LocalTime)table.getModel().getValueAt(row, 3);
//		Locazione locazione = (Locazione)table.getModel().getValueAt(row, 4);
		Sessione s = (Sessione)table.getModel().getValueAt(row, 5);
		
		modificaSessione = new ModificaSessione(this, s);
	}
	
	
	// AggiungiSessione
	public void SetLocazioniComboBox(JComboBox<Locazione> locazioni, Luogo luogo) {
		DefaultComboBoxModel<Locazione> defaultComboBoxModel = new DefaultComboBoxModel<Locazione>();
		defaultComboBoxModel.addAll(locazioneDAO.getFromLuogo(luogo));
		locazioni.setModel(defaultComboBoxModel);
	}
	
	public void AggiungiSessioneIndietro() {
		aggiungiSessione.dispose();
		visualizzaSessioni.RefreshSessioniTable();
		visualizzaSessioni.setVisible(true);
	}
	
	public void insertSessione(LocalDate dataSessione, LocalTime oraInizio, LocalTime oraFine, Conferenza conferenza, Locazione locazione, int coordinatoreRow, JTable coordinatoreTable, int keynoteRow, JTable keynoteTable) {
		try {
			if(dataSessione != null && oraInizio != null && oraFine != null && conferenza != null && locazione != null && coordinatoreRow >= 0) {
				Sessione sessione;
				if(keynoteRow >= 0) {
					 sessione = new Sessione(dataSessione, oraInizio, oraFine, locazione, conferenza, (Partecipante)coordinatoreTable.getModel().getValueAt(coordinatoreTable.convertRowIndexToModel(coordinatoreRow), 5), (Partecipante)keynoteTable.getModel().getValueAt(keynoteTable.convertRowIndexToModel(keynoteRow), 5));
				}
				else {
					 sessione = new Sessione(dataSessione, oraInizio, oraFine, locazione, conferenza, (Partecipante)coordinatoreTable.getModel().getValueAt(coordinatoreTable.convertRowIndexToModel(coordinatoreRow), 5));

				}
				sessioneDAO.insert(sessione);
				aggiungiSessione.RefreshComboBox();
				AggiungiSessioneIndietro();
			}
			else {
				throw new NullFieldException();
			}
		} catch (Exception e) {
            JOptionPane.showMessageDialog(aggiungiSessione, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void removeSessione(JTable table, int row) {
		int id = (int)table.getModel().getValueAt(row, 0);
		
		String msg = "Sei sicuro di voler eliminare la sessione del ";
		msg += (LocalDate)table.getModel().getValueAt(row, 1);
		msg += " dalle " + (LocalTime)table.getModel().getValueAt(row, 2) + " alle " + (LocalTime)table.getModel().getValueAt(row, 3) +"?";
		int result = JOptionPane.showConfirmDialog(visualizzaConferenze, msg, "Eliminazione Sessione", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(result == 0) {
			sessioneDAO.delete(id);
			visualizzaSessioni.RefreshSessioniTable();
		}
	}
	
	// ModificaSessione
	public void updateSessione(int id, LocalDate data, LocalTime ora_inizio, LocalTime ora_fine, Conferenza conferenza, Locazione locazione, Partecipante organizzatore) {
		try {
			if(data != null && ora_inizio != null && ora_fine != null && locazione != null && organizzatore != null) {
				Sessione sessione = new Sessione(id, data, ora_inizio, ora_fine, locazione, conferenza, organizzatore);
				sessioneDAO.update(id, sessione);
                visualizzaSessioni.RefreshSessioniTable();
				ModificaSessioneIndietro();
			}
			else {
				throw new NullFieldException();
			}
		} catch (Exception e) {
            JOptionPane.showMessageDialog(modificaSessione, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void ModificaSessioneIndietro() {
		modificaSessione.dispose();
		visualizzaSessioni.setVisible(true);
	}
	
	public void SetSessioniTableOfDateAndLocation(JTable table, LocalDate data, Locazione locazione) {
		SessioniTableModel sessioniTableModel = (SessioniTableModel)table.getModel();
		sessioniTableModel.setRowCount(0);
		sessioniTableModel.addColumn("Conferenza");
    	ArrayList<Sessione> sessioni = sessioneDAO.getSessioniFromDateAndLocation(data, locazione);
    	for (Sessione sessione:sessioni) {
    			sessioniTableModel.addRow(new Object[]{sessione.getId(), sessione.getData_sessione(), sessione.getOra_inizio(), sessione.getOra_fine(), sessione.getLocazione(), sessione.getConferenza().getDescrizione()});	
    	}
	}
	
	// Visualizza Programma
	public void SetProgrammaTableModel(JTable table) {
		table.setModel(new ProgrammaTableModel());
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(table.getColumnModel().getColumnIndex("Ora Inizio"));
		
        DefaultTableColumnModel defaultTableColumnModel = (DefaultTableColumnModel)table.getColumnModel();

        defaultTableColumnModel.getColumn(1).setPreferredWidth(200);
        defaultTableColumnModel.getColumn(1).setMaxWidth(200);
        defaultTableColumnModel.getColumn(1).setMinWidth(200);
        
        defaultTableColumnModel.getColumn(2).setPreferredWidth(60);
        defaultTableColumnModel.getColumn(2).setMaxWidth(60);
        defaultTableColumnModel.getColumn(2).setMinWidth(60);
        
        defaultTableColumnModel.getColumn(3).setPreferredWidth(60);
        defaultTableColumnModel.getColumn(3).setMaxWidth(60);
        defaultTableColumnModel.getColumn(3).setMinWidth(60);
        
        defaultTableColumnModel.getColumn(5).setPreferredWidth(300);
        defaultTableColumnModel.getColumn(5).setMinWidth(300);
        
        defaultTableColumnModel.removeColumn(table.getColumn("ID")); 
        defaultTableColumnModel.removeColumn(table.getColumn("Elemento Programma"));  
	}
	
	public void SetProgrammaOfSessione(JTable table, Sessione sessione) {
    	DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
    	tableModel.setRowCount(0);
    	ArrayList<Intervento> interventi = interventoDAO.getInterventi(sessione);
    	for (Intervento intervento:interventi) {
    		tableModel.addRow(new Object[]{intervento.getId(), intervento.getTipoNom(), intervento.getOraInizio(), intervento.getOraFine(), intervento.getAbstract(), intervento.getSpeaker(), intervento});
    	}
    	ArrayList<Intervallo> intervalli = intervalloDAO.getIntervalli(sessione);
    	for (Intervallo intervallo:intervalli) {
    		tableModel.addRow(new Object[]{intervallo.getId(), intervallo.getTipoNom(), intervallo.getOraInizio(), intervallo.getOraFine(), "", "", intervallo});
    	}
    	ArrayList<Evento> eventi = eventoDAO.getEventi(sessione);
    	for (Evento evento:eventi) {
    		tableModel.addRow(new Object[]{evento.getId(), evento.getTipoNom(), evento.getOraInizio(), evento.getOraFine(), "", "", evento});
    	}
	
	}
	
	public void insertIntervallo(LocalTime ora_inizio, LocalTime ora_fine, String tipo, Sessione sessione) {
		try {
			if(ora_inizio != null && ora_fine != null && tipo != null && sessione != null) {
				Intervallo intervallo = new Intervallo(0, ora_inizio, ora_fine, tipo, sessione);
				intervalloDAO.insert(intervallo);
				visualizzaProgramma.RefreshProgrammaTable();
				AggiungiElementoProgrammaIndietro();
			}
			else {
				throw new NullFieldException();
			}
		} catch (Exception e) {
            JOptionPane.showMessageDialog(aggiungiSessione, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void insertEvento(LocalTime ora_inizio, LocalTime ora_fine, String tipo, Sessione sessione) {
		try {
			if(ora_inizio != null && ora_fine != null && tipo != null && sessione != null) {
				Evento evento = new Evento(0, ora_inizio, ora_fine, tipo, sessione);
				eventoDAO.insert(evento);
				visualizzaProgramma.RefreshProgrammaTable();
				AggiungiElementoProgrammaIndietro();
			}
			else {
				throw new NullFieldException();
			}
		} catch (Exception e) {
            JOptionPane.showMessageDialog(aggiungiSessione, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void insertIntervento(LocalTime ora_inizio, LocalTime ora_fine, String abstr, int row, JTable partecipantiTable, Sessione sessione) {
		try {
			if(ora_inizio != null && ora_fine != null && row >= 0 && sessione != null) {
				Intervento intervento = new Intervento(0, ora_inizio, ora_fine, abstr, sessione, (Partecipante)partecipantiTable.getModel().getValueAt(row, 5));
				interventoDAO.insert(intervento);
				visualizzaProgramma.RefreshProgrammaTable();
				AggiungiElementoProgrammaIndietro();
			}
			else {
				throw new NullFieldException();
			}
		} catch (Exception e) {
            JOptionPane.showMessageDialog(aggiungiSessione, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void VisualizzaProgrammaIndietro() {
		visualizzaProgramma.dispose();
		visualizzaSessioni.setVisible(true);
	}
	
	// AggiungiElementoProgramma
	
	public void VisualizzaProgrammaToAggiungiElementoProgramma(Sessione s) {
		visualizzaProgramma.setVisible(false);
		aggiungiElementoProgramma = new AggiungiElementoProgramma(this, s);
	}
	public void SetIntervalloTipiComboBox(JComboBox<String> comboBox) {
		DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>();
		defaultComboBoxModel.addAll(intervalloDAO.getEnumTypes());
		comboBox.setModel(defaultComboBoxModel);
	}
	
	public void AggiungiElementoProgrammaIndietro() {
		aggiungiElementoProgramma.dispose();
		visualizzaProgramma.setVisible(true);
	}
	
	public void SetEventoTipiComboBox(JComboBox<String> comboBox) {
		DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>();
		defaultComboBoxModel.addAll(eventoDAO.getEnumTypes());
		comboBox.setModel(defaultComboBoxModel);
	}
	
	public void SetSpeakerComboBox(JComboBox<Partecipante> comboBox) {
		DefaultComboBoxModel<Partecipante> defaultComboBoxModel = new DefaultComboBoxModel<Partecipante>();
		defaultComboBoxModel.addAll(partecipanteDAO.getAll());
		comboBox.setModel(defaultComboBoxModel);
	}
	
	public void SetPartecipantiTableModel(JTable table) {
		table.setModel(new PartecipanteTableModel());
		table.setAutoCreateRowSorter(true);
		
        DefaultTableColumnModel defaultTableColumnModel = (DefaultTableColumnModel)table.getColumnModel();

        defaultTableColumnModel.getColumn(1).setPreferredWidth(200);
        defaultTableColumnModel.getColumn(1).setMaxWidth(200);
        defaultTableColumnModel.getColumn(1).setMinWidth(200);
        
        defaultTableColumnModel.getColumn(2).setPreferredWidth(60);
        defaultTableColumnModel.getColumn(2).setMaxWidth(60);
        defaultTableColumnModel.getColumn(2).setMinWidth(60);
        
        defaultTableColumnModel.getColumn(3).setPreferredWidth(60);
        defaultTableColumnModel.getColumn(3).setMaxWidth(60);
        defaultTableColumnModel.getColumn(3).setMinWidth(60);
        
        defaultTableColumnModel.getColumn(5).setPreferredWidth(300);
        defaultTableColumnModel.getColumn(5).setMinWidth(300);
        
        defaultTableColumnModel.removeColumn(table.getColumn("ID")); 
        defaultTableColumnModel.removeColumn(table.getColumn("Partecipante"));
	
	}
	
	public void SetPartecipanti(JTable table) {
    	DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
    	tableModel.setRowCount(0);
    	ArrayList<Partecipante> partecipanti = partecipanteDAO.getAll();
    	for (Partecipante partecipante:partecipanti) {
    		tableModel.addRow(new Object[]{partecipante.getId(), partecipante.getNome(), partecipante.getCognome(), partecipante.getEmail(), partecipante.getIstituzione(), partecipante});
    	}
	}
	
	public void deleteElementoProgramma(JTable table, int row) {
		int id = (int)table.getModel().getValueAt(row, 0);	
		ElementoProgramma elementoProgramma = (ElementoProgramma)table.getModel().getValueAt(row, 6);
		if(elementoProgramma instanceof Intervallo) {
			String msg = "Sei sicuro di voler eliminare l'intervallo delle " + (LocalTime)table.getModel().getValueAt(row, 2);
			int result = JOptionPane.showConfirmDialog(visualizzaConferenze, msg, "Eliminazione Sessione", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == 0) {
				intervalloDAO.delete(elementoProgramma.getId());
				table.removeRowSelectionInterval(row, row);

			}
		}
		else if(elementoProgramma instanceof Intervento) {
			String msg = "Sei sicuro di voler eliminare l'intervento delle " + (LocalTime)table.getModel().getValueAt(row, 2);
			int result = JOptionPane.showConfirmDialog(visualizzaConferenze, msg, "Eliminazione Sessione", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == 0) {
				interventoDAO.delete(elementoProgramma.getId());
				table.removeRowSelectionInterval(row, row);

			}
		}
		else if(elementoProgramma instanceof Evento) {
			String msg = "Sei sicuro di voler eliminare l'evento delle " + (LocalTime)table.getModel().getValueAt(row, 2);
			int result = JOptionPane.showConfirmDialog(visualizzaConferenze, msg, "Eliminazione Sessione", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == 0) {
				eventoDAO.delete(elementoProgramma.getId());
				table.removeRowSelectionInterval(row, row);

			}
		}
		else {
			return;
		}
	}
	
	// VisualizzaIstituzioni
	
	public void SetIstituzioniTableModel(JTable table) {
		table.setModel(new IstituzioniTableModel());
		table.setAutoCreateRowSorter(true);
		
//        DefaultTableColumnModel defaultTableColumnModel = (DefaultTableColumnModel)table.getColumnModel();

//        defaultTableColumnModel.getColumn(1).setPreferredWidth(200);
//        defaultTableColumnModel.getColumn(1).setMaxWidth(200);
//        defaultTableColumnModel.getColumn(1).setMinWidth(200);
        
	}
	
	public void SetIstituzioni(JTable table, Month month, Year year) {
    	DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
    	tableModel.setRowCount(0);
    	DecimalFormat df = new DecimalFormat();
    	df.setMaximumFractionDigits(2);
    	ArrayList<Integer> conta = new ArrayList<Integer>();
    	ArrayList<Istituzione> istituzioni;
    	if(month == null) {
    		istituzioni = istituzioneDAO.getCountOfYear(year, conta);
    	}
    	else {
    		istituzioni = istituzioneDAO.getCountOfYearAndMonth(month, year, conta);
    	}
    	int totale = 0;
    	for (Integer n : conta) {
			totale += n;
		}
    	int i = 0;
    	for (Istituzione istituzione:istituzioni) {
    		tableModel.addRow(new Object[]{istituzione.getNome(), df.format((conta.get(i++) * 100) / (float)totale) + "%"});
    	}
	}
	
	public void SetAnniComboBox(JComboBox<Year> comboBox) {
		DefaultComboBoxModel<Year> defaultComboBoxModel = (DefaultComboBoxModel<Year>)comboBox.getModel();
		defaultComboBoxModel.addAll(conferenzaDAO.getAnni());
		comboBox.setModel(defaultComboBoxModel);
	}
	
	public void VisualizzaIstituzioniIndietro() {
		visualizzaIstituzioni.dispose();
		visualizzaConferenze.setVisible(true);
	}
}