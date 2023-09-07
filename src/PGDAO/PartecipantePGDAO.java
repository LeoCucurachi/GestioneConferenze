package PGDAO;

import DAO.PartecipanteDAO;
import DTO.Partecipante;
import prova.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PartecipantePGDAO implements PartecipanteDAO {
    private DBConnection db;
    private Connection connection;
    private Statement statement;

    public PartecipantePGDAO(){
        db = DBConnection.getDBConnection();
        connection = db.getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Partecipante getPartecipante(Integer id){
        Partecipante partecipante = new Partecipante();
        try {
            String query = "SELECT * FROM partecipante WHERE id_partecipante = " + id + ";";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            partecipante.setId(resultSet.getInt("id_partecipante"));
            partecipante.setNome(resultSet.getString("nome"));
            partecipante.setCognome(resultSet.getString("cognome"));
            partecipante.setEmail(resultSet.getString("email"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return partecipante;
    }
}
