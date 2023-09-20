package prova;

import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import DTO.Conferenza;
import DTO.Evento;
import DTO.Intervallo;
import DTO.Intervento;
import DTO.Sessione;
import DTO.Partecipante;
import PGDAO.ConferenzaPGDAO;
import PGDAO.EventoPGDAO;
import PGDAO.IntervalloPGDAO;
import PGDAO.LocazionePGDAO;
import PGDAO.SessionePGDAO;
import PGDAO.InterventoPGDAO;

public class Main2 {
	public static void main(String[] args) {
		Connection connection = DBConnection.getDBConnection().getConnection();
		try {
			PrintStream fileStream = new PrintStream(new File("sql2.txt"));
			ConferenzaPGDAO conferenzaPGDAO = new ConferenzaPGDAO();
			SessionePGDAO sessionePGDAO = new SessionePGDAO();
			LocazionePGDAO locazionePGDAO = new LocazionePGDAO();
			InterventoPGDAO interventoPGDAO = new InterventoPGDAO();
			IntervalloPGDAO intervalloPGDAO = new IntervalloPGDAO();
			EventoPGDAO eventoPGDAO = new EventoPGDAO();
			
			ArrayList<Conferenza> conferenze = conferenzaPGDAO.getAll();
			ArrayList<Sessione> sessioni = new ArrayList<Sessione>();
			for (Conferenza conferenza : conferenze) {
				for (Sessione sessione : sessionePGDAO.getSessioniOfConferenza(conferenza)) {
					sessioni.add(sessione);
				}
			}
			
			Random random = new Random();
			
			LocalTime curLowTime;
			LocalTime curHighTime;
			int i = 0;
			ArrayList<String> eventoEnum = eventoPGDAO.getEnumTypes();
			ArrayList<String> interEnum = intervalloPGDAO.getEnumTypes();
			for (Sessione sessione : sessioni) {
				curLowTime = sessione.getOra_inizio();
				curHighTime = curLowTime.plusMinutes((1 + random.nextInt(5)) * 15);
				boolean ripeto = true;
				while(ripeto) {
					if(curHighTime.isAfter(sessione.getOra_fine())){
						Intervento intervento = new Intervento(0, curLowTime, sessione.getOra_fine(), "",sessione, new Partecipante(random.nextInt(299) + 1));
						interventoPGDAO.insert(intervento);
						break;
					}
					Intervento intervento = new Intervento(0, curLowTime, curHighTime, "",sessione, new Partecipante(random.nextInt(299) + 1));
					interventoPGDAO.insert(intervento);
					curLowTime = curHighTime;
					if(curLowTime.equals(sessione.getOra_fine()))
						break;
					curHighTime = curLowTime.plusMinutes((1 + random.nextInt(2)) * 5);
					if(curHighTime.isAfter(sessione.getOra_fine())){
						Intervallo intervallo = new Intervallo(0, curLowTime, sessione.getOra_fine(), interEnum.get(random.nextInt(interEnum.size())), sessione);
						intervalloPGDAO.insert(intervallo);
						break;
					}
					Intervallo intervallo = new Intervallo(0, curLowTime, curHighTime, interEnum.get(random.nextInt(interEnum.size())), sessione);
					intervalloPGDAO.insert(intervallo);
					curLowTime = curHighTime;
					if(curLowTime.equals(sessione.getOra_fine()))
						break;
					curHighTime = curLowTime.plusMinutes((1 + random.nextInt(5)) * 15);
					if(curHighTime.isAfter(sessione.getOra_fine())){
						Intervento intervento2 = new Intervento(0, curLowTime, sessione.getOra_fine(), "",sessione, new Partecipante(random.nextInt(299) + 1));
						interventoPGDAO.insert(intervento2);
						break;
					}
					Intervento intervento2 = new Intervento(0, curLowTime, curHighTime, "",sessione, new Partecipante(random.nextInt(299) + 1));
					interventoPGDAO.insert(intervento2);
					curLowTime = curHighTime;
					if(curLowTime.equals(sessione.getOra_fine()))
						break;
					curHighTime = curLowTime.plusMinutes((1 + random.nextInt(3)) * 15);
					if(curHighTime.isAfter(sessione.getOra_fine())){
						Evento evento = new Evento(0, curLowTime, sessione.getOra_fine(), eventoEnum.get(random.nextInt(eventoEnum.size())), sessione);
						eventoPGDAO.insert(evento);
						break;
					}
					Evento evento = new Evento(0, curLowTime, curHighTime, eventoEnum.get(random.nextInt(eventoEnum.size())), sessione);
					eventoPGDAO.insert(evento);
					curLowTime = curHighTime;
					if(curLowTime.equals(sessione.getOra_fine()))
						break;
					curHighTime = curLowTime.plusMinutes((1 + random.nextInt(3)) * 15);
				}
				System.out.println(++i);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
