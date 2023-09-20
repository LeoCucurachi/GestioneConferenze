package prova;

import java.io.PrintStream;
import java.io.File;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.random.RandomGenerator;

import DTO.Conferenza;
import DTO.Locazione;
import DTO.Partecipante;
import DTO.Sessione;
import PGDAO.ConferenzaPGDAO;
import PGDAO.LocazionePGDAO;
import PGDAO.SessionePGDAO;

public class Main {
	public static void main(String[] args) {
		Connection connection = DBConnection.getDBConnection().getConnection();
		
		try {
			PrintStream fileStream = new PrintStream(new File("sql.txt"));
			ConferenzaPGDAO conferenzaPGDAO = new ConferenzaPGDAO();
			SessionePGDAO sessionePGDAO = new SessionePGDAO();
			LocazionePGDAO locazionePGDAO = new LocazionePGDAO();
			for (Conferenza conferenza : conferenzaPGDAO.getAll()) {
				LocalDate curDate = conferenza.getData_inizio();
				while(!curDate.isAfter(conferenza.getData_fine())) {
					LocalTime time1 = LocalTime.of(8, 0, 0);
					LocalTime time2 = LocalTime.of(22, 0, 0);
					int secondOfDayTime1 = time1.toSecondOfDay();
					int secondOfDayTime2 = time2.toSecondOfDay();
					Random random = new Random();
					int randomSecondOfDay = secondOfDayTime1 + random.nextInt(secondOfDayTime2-secondOfDayTime1);   
					LocalTime randomLocalTime = LocalTime.ofSecondOfDay(randomSecondOfDay);
					randomLocalTime = randomLocalTime.truncatedTo(ChronoUnit.HOURS).plusMinutes(15 * (randomLocalTime.getMinute() / 15));
					
					LocalTime time3 = LocalTime.of(9, 0, 0);
					LocalTime time4 = LocalTime.of(23, 0, 0);
					LocalTime randomLocalTime2;
					
					int secondOfDayTime3 = time3.toSecondOfDay();
					int secondOfDayTime4 = time4.toSecondOfDay();
					
					do {
						int randomSecondOfDay2 = secondOfDayTime3 + random.nextInt(secondOfDayTime4-secondOfDayTime3);   
						randomLocalTime2 = LocalTime.ofSecondOfDay(randomSecondOfDay2);
						randomLocalTime2 = randomLocalTime2.truncatedTo(ChronoUnit.HOURS).plusMinutes(15 * (randomLocalTime2.getMinute() / 15));
					} while (!randomLocalTime2.isAfter(randomLocalTime));
					
					Locazione l1; 
					boolean cond = true;
					ArrayList<Locazione> locaziones = locazionePGDAO.getFromLuogo(conferenza.getLuogo());
					int volte = 0;
					do {
						Collections.shuffle(locaziones);
						l1 = locaziones.get(0);
						ArrayList<Sessione> sess = sessionePGDAO.getSessioniFromDateAndLocation(curDate, l1);
						for (Sessione s : sess) {
							cond = s.getOra_inizio().isAfter(randomLocalTime2) || s.getOra_fine().isBefore(randomLocalTime);
						}
						volte++;
					} while (!cond && volte < 10);
					System.out.println("0");

					if(volte == 10){
						curDate = curDate.plusDays(1);
						continue;
					}
					System.out.println("1");

					Sessione sessione = new Sessione(null, curDate, randomLocalTime, randomLocalTime2, l1, conferenza, new Partecipante(1 + random.nextInt(299)));
					sessionePGDAO.insert(sessione);
					String comando1 = "INSERT INTO sessione (data_sessione, ora_inizio, ora_fine, id_conferenza, id_locazione, coordinatore) VALUES" +
		                    "(" + "'" + sessione.getData_sessione().toString() + "', '" + sessione.getOra_inizio().toString() + "', '" + sessione.getOra_fine() + "', " + sessione.getConferenza().getId() + ", " + sessione.getLocazione().getId() + ", " + sessione.getCoordinatore().getId() + ");";

					randomSecondOfDay = secondOfDayTime1 + random.nextInt(secondOfDayTime2-secondOfDayTime1);   
					randomLocalTime = LocalTime.ofSecondOfDay(randomSecondOfDay);
					randomLocalTime = randomLocalTime.truncatedTo(ChronoUnit.HOURS).plusMinutes(15 * (randomLocalTime.getMinute() / 15));
					
					do {
						int randomSecondOfDay2 = secondOfDayTime3 + random.nextInt(secondOfDayTime4-secondOfDayTime3);   
						randomLocalTime2 = LocalTime.ofSecondOfDay(randomSecondOfDay2);
						randomLocalTime2 = randomLocalTime2.truncatedTo(ChronoUnit.HOURS).plusMinutes(15 * (randomLocalTime2.getMinute() / 15));
					} while (!randomLocalTime2.isAfter(randomLocalTime));
					System.out.println("2");
					volte = 0;
					cond = true;
					do {
						Collections.shuffle(locaziones);
						l1 = locaziones.get(0);
						ArrayList<Sessione> sess = sessionePGDAO.getSessioniFromDateAndLocation(curDate, l1);
						for (Sessione s : sess) {
							cond = s.getOra_inizio().isAfter(randomLocalTime2) || s.getOra_fine().isBefore(randomLocalTime);
						}
						volte++;
					} while (!cond && volte < 10);
					System.out.println("3");

					if(volte == 10){
						curDate = curDate.plusDays(1);
						continue;
					}
					Sessione sessione2 = new Sessione(null, curDate, randomLocalTime, randomLocalTime2, l1, conferenza, new Partecipante(1 + random.nextInt(299)));
					sessionePGDAO.insert(sessione2);
		            
					String comando2 = "INSERT INTO sessione (data_sessione, ora_inizio, ora_fine, id_conferenza, id_locazione, coordinatore) VALUES" +
		                    "(" + "'" + sessione2.getData_sessione().toString() + "', '" + sessione2.getOra_inizio().toString() + "', '" + sessione2.getOra_fine() + "', " + sessione2.getConferenza().getId() + ", " + sessione2.getLocazione().getId() + ", " + sessione2.getCoordinatore().getId() + ");";
					fileStream.println(comando1);
					fileStream.println(comando2);
					curDate = curDate.plusDays(1);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
