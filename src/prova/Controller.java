package prova;

import DAO.ConferenzaDAO;
import DAO.LuogoDAO;
import DTO.Conferenza;
import DTO.Luogo;
import PGDAO.ConferenzaPGDAO;
import PGDAO.LuogoPGDAO;
import UI.AggiungiConferenza;
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

    public static void main(String [] args){
        Controller controller = new Controller();
    }

    public Controller(){
        conferenzaDAO = new ConferenzaPGDAO();
        luogoDAO = new LuogoPGDAO();
        //prova = new Prova(this);
        aggiungiConferenza = new AggiungiConferenza(this);
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

    public boolean CreateConference(LocalDate data_i, LocalDate data_f, Luogo luogo, String descrizione){
        boolean esito = false;
        if(data_i != null && data_f != null && luogo != null) {
            Conferenza conferenza = new Conferenza(data_i, data_f, descrizione, luogo);
            esito = conferenzaDAO.insert(conferenza);
        }

        if(!esito){
            JOptionPane.showMessageDialog(aggiungiConferenza, "Attenzione, compila tutti i campi");
        }

        return esito;
    }

    public void SetLuoghi(JComboBox comboBox){
        DefaultComboBoxModel defaultComboBoxModel = (DefaultComboBoxModel) comboBox.getModel();
        defaultComboBoxModel.addAll(luogoDAO.getAll());

        comboBox.setModel(defaultComboBoxModel);
    }
}
