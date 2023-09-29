package PGDAO;

import DAO.InterventoDAO;
import DTO.Intervento;
import DTO.Istituzione;
import DTO.Partecipante;
import DTO.Sessione;
import principale.DBConnection;

import java.lang.reflect.UndeclaredThrowableException;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class InterventoPGDAO implements InterventoDAO {

    public InterventoPGDAO(){
    }

    public ArrayList<Intervento> getInterventi(Sessione sessione){
    	Connection connection = DBConnection.getDBConnection().getConnection();
        ArrayList<Intervento> interventi = new ArrayList<Intervento>();
        try {
            String sql = "SELECT * FROM intervento NATURAL JOIN partecipante WHERE id_sessione = " + sessione.getId() + " ORDER BY ora_inizio, ora_fine;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
            	int id = rs.getInt("id_intervento");
            	LocalTime ora_inizio = rs.getTime("ora_inizio").toLocalTime();
            	LocalTime ora_fine = rs.getTime("ora_fine").toLocalTime();
            	String abstr = rs.getString("abstract");
            	Partecipante speaker = new Partecipante(rs.getInt("id_partecipante"), rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), new Istituzione(rs.getString("istituzione")));
            	
                Intervento intervento = new Intervento(id, ora_inizio, ora_fine, abstr, sessione, speaker);
                interventi.add(intervento);
            }
            
            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return interventi;

    }
    
    @Override
    public void insert(Intervento intervento) throws Exception {
    	Connection connection = DBConnection.getDBConnection().getConnection();
    	try {
    		String sql = "INSERT INTO intervento (ora_inizio, ora_fine, abstract, id_sessione, id_partecipante) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			statement.setTime(1, Time.valueOf(intervento.getOraInizio()));
			statement.setTime(2, Time.valueOf(intervento.getOraFine()));
			statement.setString(3, intervento.getAbstract());
			statement.setInt(4, intervento.getSessione().getId());
			statement.setInt(5, intervento.getSpeaker().getId());
			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			
			if(rs.next()) {
				intervento.setId(rs.getInt(1));
			}
			
			rs.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
    	
    }
    
	public void delete(int id) {
		Connection connection = DBConnection.getDBConnection().getConnection();
		try {
			Statement statement = connection.createStatement();
			String sql = "DELETE FROM intervento WHERE id_intervento = " + id + ";";
			statement.executeUpdate(sql);
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
