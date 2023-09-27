package PGDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import com.github.lgooddatepicker.components.TimePickerSettings;

import DAO.IntervalloDAO;
import DTO.Intervallo;
import DTO.Sessione;
import principale.DBConnection;

public class IntervalloPGDAO implements IntervalloDAO{
	   public ArrayList<Intervallo> getIntervalli(Sessione sessione){
	    	Connection connection = DBConnection.getDBConnection().getConnection();
	        ArrayList<Intervallo> intervalli = new ArrayList<Intervallo>();
	        try {
	            String sql = "SELECT * FROM intervallo WHERE id_sessione = " + sessione.getId() + " ORDER BY ora_inizio, ora_fine;";
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(sql);

	            while(resultSet.next()) {
	            	Intervallo intervallo = new Intervallo();
	                intervallo.setId(resultSet.getInt("id_intervallo"));
	                intervallo.setOraInizio(resultSet.getTime("ora_inizio").toLocalTime());
	                intervallo.setOraFine(resultSet.getTime("ora_fine").toLocalTime());
	                intervallo.setSessione(sessione);
	                intervallo.setTipo(resultSet.getString("tipo"));

	                intervalli.add(intervallo);
	            }
	            
	            resultSet.close();
	            statement.close();
	            connection.close();

	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }

	        return intervalli;

	    }
	   
	   public void insert(Intervallo intervallo) throws Exception {
	    	Connection connection = DBConnection.getDBConnection().getConnection();
	    	try {
	    		String sql = "INSERT INTO intervallo (ora_inizio, ora_fine, tipo, id_sessione) VALUES (?, ?, '" + intervallo.getTipo() + "', ?)";
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				statement.setTime(1, Time.valueOf(intervallo.getOraInizio()));
				statement.setTime(2, Time.valueOf(intervallo.getOraFine()));
				statement.setInt(3, intervallo.getSessione().getId());
				
				statement.executeUpdate();
				ResultSet rs = statement.getGeneratedKeys();
				if(rs.next()) {
					intervallo.setId(rs.getInt(1));
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
			   String sql = "SELECT unnest(enum_range(NULL::tintervallo)) AS tipo;";
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
				String sql = "DELETE FROM intervallo WHERE id_intervallo = " + id + ";";
				statement.executeUpdate(sql);
				
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}
