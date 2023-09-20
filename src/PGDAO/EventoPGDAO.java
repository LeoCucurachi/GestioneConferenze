package PGDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import DAO.EventoDAO;
import DTO.Evento;
import DTO.Sessione;
import prova.DBConnection;

public class EventoPGDAO implements EventoDAO {
	   public ArrayList<Evento> getEventi(Sessione sessione){
	    	Connection connection = DBConnection.getDBConnection().getConnection();
	        ArrayList<Evento> eventi = new ArrayList<Evento>();
	        try {
	            String sql = "SELECT * FROM evento WHERE id_sessione = " + sessione.getId() + " ORDER BY ora_inizio, ora_fine;";
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(sql);

	            while(resultSet.next()) {
	            	Evento evento = new Evento();
	                evento.setId(resultSet.getInt("id_evento"));
	                evento.setOraInizio(resultSet.getTime("ora_inizio").toLocalTime());
	                evento.setOraFine(resultSet.getTime("ora_fine").toLocalTime());
	                evento.setSessione(sessione);
	                evento.setTipo(resultSet.getString("tipo"));

	                eventi.add(evento);
	            }
	            
	            resultSet.close();
	            statement.close();
	            connection.close();

	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }

	        return eventi;

	    }
	   
	   public void insert(Evento evento) throws Exception {
	    	Connection connection = DBConnection.getDBConnection().getConnection();
	    	try {
	    		String sql = "INSERT INTO evento (ora_inizio, ora_fine, tipo, id_sessione) VALUES (?, ?, '" + evento.getTipo() + "', ?)";
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				statement.setTime(1, Time.valueOf(evento.getOraInizio()));
				statement.setTime(2, Time.valueOf(evento.getOraFine()));
				statement.setInt(3, evento.getSessione().getId());
				
				statement.executeUpdate();
				ResultSet rs = statement.getGeneratedKeys();
				if(rs.next()) {
					evento.setId(rs.getInt(1));
				}
				
				rs.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
	   }
	   
	   public ArrayList<String> getEnumTypes() {
		   ArrayList<String> tipi = new ArrayList<String>();
		   Connection connection = DBConnection.getDBConnection().getConnection();
		   try {
			   String sql = "SELECT unnest(enum_range(NULL::tevento)) AS tipo;";
			   Statement statement = connection.createStatement();
			   
			   ResultSet rs = statement.executeQuery(sql);
			   
			   while (rs.next()) {
				tipi.add(rs.getString("tipo"));
			   }
		   } catch (SQLException e) {
			e.printStackTrace();
		   }
		   return tipi;
	   }
	   
		public void delete(int id) {
			Connection connection = DBConnection.getDBConnection().getConnection();
			try {
				Statement statement = connection.createStatement();
				String sql = "DELETE FROM evento WHERE id_evento = " + id + ";";
				statement.executeUpdate(sql);
				
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
}
