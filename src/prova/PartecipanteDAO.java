package prova;

import java.sql.*;

public class PartecipanteDAO {
    private DBConnection db;
    private Connection connection;
    private Statement statement;

    public PartecipanteDAO(){
        db = DBConnection.getDBConnection();
        connection = db.getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Partecipante getPartecipante(String email){
        Partecipante partecipante = new Partecipante();
        try {
            String query = "SELECT * FROM partecipante WHERE email = '" + email + "';";
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
