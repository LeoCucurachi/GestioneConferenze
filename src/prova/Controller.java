package prova;

import DAO.ConferenzaDAO;
import DAO.LuogoDAO;
import DTO.Conferenza;
import DTO.Luogo;
import Exceptions.NullFieldException;
import PGDAO.ConferenzaPGDAO;
import PGDAO.LuogoPGDAO;
import UI.AggiungiConferenza;
import UI.AggiungiLuogo;
import UI.MainFrame;
import UI.Prova;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Controller {
    ConferenzaDAO conferenzaDAO;
    LuogoDAO luogoDAO;
    Prova prova;
    AggiungiConferenza aggiungiConferenza;
    AggiungiLuogo  aggiungiLuogo;
    MainFrame mainFrame;

    public static void main(String [] args){
        Controller controller = new Controller();
    }

    public Controller(){
        conferenzaDAO = new ConferenzaPGDAO();
        luogoDAO = new LuogoPGDAO();
        mainFrame = new MainFrame(this);
        
    }

    public void setConferenceList(JTable table){
        ArrayList<Conferenza> conferenze = conferenzaDAO.getAll();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Descrizione");
        tableModel.addColumn("Data Inizio");
        tableModel.addColumn("Data Fine");
        tableModel.addColumn("Sede");

        for (Conferenza conferenza:conferenze) {
            tableModel.addRow(new Object[]{conferenza.getId(), conferenza.getDescrizione(), conferenza.getData_inizio().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)), conferenza.getData_fine().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)), conferenza.getLuogo().getSede()});
        }

        table.setModel(tableModel);
    }

    public void insertConferenza(LocalDate data_i, LocalDate data_f, Luogo luogo, String descrizione){
        try {
            if (data_i != null && data_f != null && luogo != null) {
                Conferenza conferenza = new Conferenza(data_i, data_f, descrizione, luogo);
                conferenzaDAO.insert(conferenza);
            }
            else {
                throw new NullFieldException();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aggiungiConferenza, e.getMessage());
        }
    }

    public void SetLuoghi(JComboBox<Luogo> comboBox){
        DefaultComboBoxModel<Luogo> defaultComboBoxModel = new DefaultComboBoxModel<>();
        defaultComboBoxModel.addAll(luogoDAO.getAll());

        comboBox.setModel(defaultComboBoxModel);
    }

    public void CreateLuogo(String via, String civico, String paese, String sede){
        try {
            if (!via.isBlank() && !civico.isBlank() && !paese.isBlank() && !sede.isBlank()) {
                Luogo luogo = new Luogo(via + ", " + civico + ", " + paese, sede);
                luogoDAO.insert(luogo);
                AggiungiLuogoIndietro();
            }
            else {
                throw new NullFieldException();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aggiungiLuogo, e.getMessage());
        }
    }
    public void AggiungiConferenzaToAggiungiLuogo(){
        aggiungiLuogo = new AggiungiLuogo(this);
        aggiungiLuogo.setVisible(true);
        aggiungiLuogo.setAlwaysOnTop(true);
    }
    public void AggiungiLuogoIndietro(){
        aggiungiLuogo.dispose();
        aggiungiConferenza.setVisible(true);
    }
}