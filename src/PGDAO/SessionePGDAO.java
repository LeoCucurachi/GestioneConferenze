package PGDAO;

import DAO.SessioneDAO;
import DTO.Conferenza;
import DTO.Istituzione;
import DTO.Locazione;
import DTO.Partecipante;
import DTO.Sessione;
import Exceptions.DataFineDopoDataInizioException;
import principale.DBConnection;

import java.security.DrbgParameters.NextBytes;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SessionePGDAO implements SessioneDAO {

    @Override
    public void insert(Sessione s) throws Exception{
    	Connection connection = DBConnection.getDBConnection().getConnection();
        try{
            Statement statement = connection.createStatement();
            String comando;
            if(s.getKeynote() != null) {
            	comando = "INSERT INTO sessione (data_sessione, ora_inizio, ora_fine, id_conferenza, id_locazione, coordinatore, keynote) VALUES" +
                    "(" + "'" + s.getData_sessione().toString() + "', '" + s.getOra_inizio().toString() + "', '" + s.getOra_fine() + "', " + s.getConferenza().getId() + ", " + s.getLocazione().getId() + ", " + s.getCoordinatore().getId() + ", " + s.getKeynote().getId() + ");";
            }
            else {
            	comando = "INSERT INTO sessione (data_sessione, ora_inizio, ora_fine, id_conferenza, id_locazione, coordinatore) VALUES" +
                        "(" + "'" + s.getData_sessione().toString() + "', '" + s.getOra_inizio().toString() + "', '" + s.getOra_fine() + "', " + s.getConferenza().getId() + ", " + s.getLocazione().getId() + ", " + s.getCoordinatore().getId() + ");";
            }
            statement.executeUpdate(comando, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            s.setId(rs.getInt(1));
            
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public ArrayList<Sessione> getSessioniOfConferenza(Conferenza conferenza) {
    	ArrayList<Sessione> sessioni = new ArrayList<Sessione>();
    	Connection connection = DBConnection.getDBConnection().getConnection();
    	try {
    		String sql = "SELECT s.id_sessione, s.data_sessione, s.ora_inizio, s.ora_fine, s.id_locazione, l.nome AS nome_locazione, s.coordinatore, p.nome AS nome_coordinatore, p.cognome AS cognome_coordinatore, p.email AS email_coordinatore, p.istituzione AS istituzione_coordinatore, s.keynote, k.nome AS nome_keynote, k.cognome AS cognome_keynote, k.email AS email_keynote, k.istituzione AS istituzione_keynote FROM sessione s LEFT JOIN locazione l ON s.id_locazione = l.id_locazione LEFT JOIN partecipante p ON s.coordinatore = p.id_partecipante LEFT JOIN partecipante k ON k.id_partecipante = s.keynote WHERE s.id_conferenza = " + conferenza.getId() + " ORDER BY data_sessione, ora_inizio, ora_fine;";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet rs = statement.executeQuery();
			
            while(rs.next()){
                Integer id_sessione = rs.getInt("id_sessione");
                LocalDate data_sessione = rs.getDate("data_sessione").toLocalDate();
                LocalTime ora_inizio = rs.getTime("ora_inizio").toLocalTime();
                LocalTime ora_fine = rs.getTime("ora_fine").toLocalTime();
                Locazione locazione = new Locazione(rs.getInt("id_locazione"), rs.getString("nome_locazione"), conferenza.getLuogo());
                Partecipante coordinatore = new Partecipante(rs.getInt("coordinatore"), rs.getString("nome_coordinatore"), rs.getString("cognome_coordinatore"), rs.getString("email_coordinatore"), new Istituzione(rs.getString("istituzione_coordinatore")));
                Partecipante keynote = new Partecipante(rs.getInt("keynote"), rs.getString("nome_keynote"), rs.getString("cognome_keynote"), rs.getString("email_keynote"), new Istituzione(rs.getString("istituzione_keynote")));
                
                Sessione sessione = new Sessione(id_sessione, data_sessione, ora_inizio, ora_fine, locazione, conferenza, coordinatore, keynote);
                conferenza.addSessione(sessione);
                
                sessioni.add(sessione);
                
            }
            rs.close();
            statement.close();
            connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    	return sessioni;
    }
    
    @Override
    public ArrayList<Sessione> getSessioniFromDateAndLocation(LocalDate data, Locazione locazione) {
    	ArrayList<Sessione> sessioni = new ArrayList<Sessione>();
    	Connection connection = DBConnection.getDBConnection().getConnection();
    	try {
    		String sql = "SELECT s.id_sessione, s.data_sessione, s.ora_inizio, s.ora_fine, s.id_locazione, l.nome AS nome_locazione, s.coordinatore, p.nome AS nome_coordinatore, p.cognome AS cognome_coordinatore, p.email AS email_coordinatore, p.istituzione AS istituzione_coordinatore, s.keynote, k.nome AS nome_keynote, k.cognome AS cognome_keynote, k.email AS email_keynote, k.istituzione AS istituzione_keynote FROM sessione s JOIN locazione l ON s.id_locazione = l.id_locazione JOIN conferenza c ON s.id_conferenza = c.id_conferenza LEFT JOIN partecipante p ON s.coordinatore = p.id_partecipante LEFT JOIN partecipante k ON s.speaker = k.keynote WHERE s.data_sessione = '" + data.toString() + "' ORDER BY data_sessione, ora_inizio, ora_fine;";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet rs = statement.executeQuery();
			
            while(rs.next()){
                Integer id_sessione = rs.getInt("id_sessione");
                LocalDate data_sessione = rs.getDate("data_sessione").toLocalDate();
                LocalTime ora_inizio = rs.getTime("ora_inizio").toLocalTime();
                LocalTime ora_fine = rs.getTime("ora_fine").toLocalTime();
                Conferenza conferenza = new Conferenza(rs.getInt("id_conferenza"), rs.getDate("data_inizio").toLocalDate(), rs.getDate("data_fine").toLocalDate(), rs.getString("descrizione"), locazione.getLuogo());
                Partecipante coordinatore = new Partecipante(rs.getInt("coordinatore"), rs.getString("nome_coordinatore"), rs.getString("cognome_coordinatore"), rs.getString("email_coordinatore"), new Istituzione(rs.getString("istituzione_coordinatore")));
                Partecipante keynote = new Partecipante(rs.getInt("keynote"), rs.getString("nome_keynote"), rs.getString("cognome_keynote"), rs.getString("email_keynote"), new Istituzione(rs.getString("istituzione_keynote")));
                Sessione sessione = new Sessione(id_sessione, data_sessione, ora_inizio, ora_fine, locazione, conferenza, coordinatore, keynote);
                conferenza.addSessione(sessione);
                
                sessioni.add(sessione);
                
            }
            rs.close();
            statement.close();
            connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    	return sessioni;
    }
    
    @Override
    public void delete(int id) {
    	Connection connection = DBConnection.getDBConnection().getConnection();
    	try {
			Statement statement = connection.createStatement();
			String sql = "DELETE FROM sessione WHERE id_sessione = " + id + ";";
			statement.executeUpdate(sql);
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    @Override
    public void update(Integer id, Sessione s) throws Exception{
    	Connection connection = DBConnection.getDBConnection().getConnection();
        try{
        	String sql = "UPDATE sessione SET data_sessione = ?, ora_inizio = ?, ora_fine = ?, id_conferenza = ?, id_locazione = ?, coordinatore = ? WHERE id_sessione = " + id + ";";            
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setDate(1, Date.valueOf(s.getData_sessione()));
            statement.setTime(2, Time.valueOf(s.getOra_inizio()));
            statement.setTime(3, Time.valueOf(s.getOra_fine()));
            statement.setInt(4, s.getConferenza().getId());
            statement.setInt(5, s.getLocazione().getId());
            statement.setInt(6, s.getCoordinatore().getId());
            statement.setInt(7, s.getKeynote().getId());
            
            statement.executeUpdate();
            
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
