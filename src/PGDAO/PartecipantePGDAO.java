package PGDAO;

import DAO.PartecipanteDAO;
import DTO.Istituzione;
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
            	partecipanti.add(new Partecipante(id, nome, cognome, email, new Istituzione(istituzione)));
            }
            
            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return partecipanti;
    }

}
