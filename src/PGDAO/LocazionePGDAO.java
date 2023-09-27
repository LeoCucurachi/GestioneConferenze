package PGDAO;

import java.rmi.ConnectIOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DAO.LocazioneDAO;
import DTO.Locazione;
import DTO.Luogo;
import principale.DBConnection;

public class LocazionePGDAO implements LocazioneDAO{
	@Override
	public ArrayList<Locazione> getFromLuogo(Luogo luogo) {
		ArrayList<Locazione> locazioni = new ArrayList<Locazione>();
		Connection connection = DBConnection.getDBConnection().getConnection();
		try {
			String sql = "SELECT * FROM locazione WHERE id_luogo = " + luogo.getId_luogo() + " ORDER BY nome;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				int id_locazione = rs.getInt("id_locazione");
				String nome = rs.getString("nome");
				
				Locazione locazione = new Locazione(id_locazione, nome, luogo);
				
				locazioni.add(locazione);
			}
			
            rs.close();
            statement.close();
            connection.close();
			
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return locazioni;
	}
}
