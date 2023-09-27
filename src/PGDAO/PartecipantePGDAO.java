package PGDAO;

import DAO.PartecipanteDAO;
import DTO.Partecipante;
import principale.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;

public class PartecipantePGDAO implements PartecipanteDAO {

    public ArrayList<Partecipante> getAll(){
    	Connection connection = DBConnection.getDBConnection().getConnection();
        ArrayList<Partecipante> partecipanti = new ArrayList<Partecipante>();
        try {
            String sql = "SELECT * FROM partecipante ORDER BY nome, cognome, email;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {         	
            	int id = rs.getInt("id_partecipante");
            	String nome = rs.getString("nome");
            	String cognome = rs.getString("cognome");
            	String email = rs.getString("email");
            	String istituzione = rs.getString("istituzione");
            	partecipanti.add(new Partecipante(id, nome, cognome, email, istituzione));
            }
            
            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return partecipanti;
    }

	public ArrayList<Partecipante>  getSpeakerOfYear(Year year){
		ArrayList<Partecipante> speakers = new ArrayList<Partecipante>();
		Connection connection = DBConnection.getDBConnection().getConnection();
		try {
			String sql = "SELECT DISTINCT p.id_partecipante, p.nome, p.cognome, p.email, p.istituzione "
					+ "FROM partecipante p JOIN speaker s ON p.id_partecipante = s.id_partecipante JOIN conferenza c ON s.id_conferenza = c.id_conferenza "
					+ "WHERE EXTRACT(YEAR FROM c.data_inizio) = " + year + " OR EXTRACT(YEAR FROM c.data_fine) = " + year;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				int id = rs.getInt("id_partecipante");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String istituzione = rs.getString("istituzione");

				Partecipante partecipante = new Partecipante(id, nome, cognome, email, istituzione);
				speakers.add(partecipante);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return speakers;
	}
	
	public ArrayList<Partecipante>  getSpeakerOfYearAndMonth(Month month, Year year){
		ArrayList<Partecipante> speakers = new ArrayList<Partecipante>();
		Connection connection = DBConnection.getDBConnection().getConnection();
		try {
			String sql = "SELECT DISTINCT p.id_partecipante, p.nome, p.cognome, p.email, p.istituzione "
					+ "FROM partecipante p JOIN speaker s ON p.id_partecipante = s.id_partecipante JOIN conferenza c ON s.id_conferenza = c.id_conferenza "
					+ "WHERE (EXTRACT(YEAR FROM c.data_inizio) = " + year + " OR EXTRACT(YEAR FROM c.data_fine) = " + year + ") AND ("
					+ "EXTRACT(MONTH FROM c.data_inizio) = " + month + " OR EXTRACT(MONTH FROM c.data_fine) = " + month;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				int id = rs.getInt("id_partecipante");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String istituzione = rs.getString("istituzione");

				Partecipante partecipante = new Partecipante(id, nome, cognome, email, istituzione);
				speakers.add(partecipante);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return speakers;
	}
}
