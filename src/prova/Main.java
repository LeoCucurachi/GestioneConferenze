package prova;

import DTO.Conferenza;
import DTO.Intervento;
import DTO.Partecipante;
import PGDAO.ConferenzaPGDAO;
import PGDAO.InterventoPGDAO;
import PGDAO.PartecipantePGDAO;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Prova");
        PartecipantePGDAO parDAO = new PartecipantePGDAO();
        Partecipante par = new Partecipante();

        InterventoPGDAO interDAO = new InterventoPGDAO();
        ArrayList<Intervento> in;
        par = parDAO.getPartecipante(1);
        if(par != null){
            System.out.println(par.toString());
        }

        ConferenzaPGDAO conferenzaPGDAO = new ConferenzaPGDAO();
        Conferenza c = conferenzaPGDAO.get(4);

        System.out.println(conferenzaPGDAO.getWithSessions(1).getSessioni().toString());
        //  SessionePGDAO sessionePGDAO = new SessionePGDAO();
        //  Sessione s = new Sessione(LocalDate.of(2023, 8, 3), LocalTime.of(18, 00), LocalTime.of(19,00), new Locazione(24, "Quindar", new Luogo(58, "50 Westerfield Plaza", "Technology Institute")),c, par);
        //  sessionePGDAO.InsertSessione(s);
        //  System.out.println("Id della sessione creata:  " + s.getId());
    } // verifica

}