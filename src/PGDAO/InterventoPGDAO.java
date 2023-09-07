package PGDAO;

import DAO.InterventoDAO;
import DTO.Intervento;
import DTO.Sessione;
import prova.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InterventoPGDAO implements InterventoDAO {
    private DBConnection db;
    private Connection connection;
    private Statement statement;

    public InterventoPGDAO(){
        db = DBConnection.getDBConnection();
        connection = db.getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Intervento> getInterventi(Sessione sessione){
        ArrayList<Intervento> interventi = new ArrayList<Intervento>();
        try {
            String query = "SELECT * FROM intervento WHERE id_sessione = " + sessione.getId() + ";";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                Intervento intervento = new Intervento();
                intervento.setId_intervento(resultSet.getInt("id_intervento"));
                intervento.setOra_inizio(resultSet.getTime("ora_inizio").toLocalTime());
                intervento.setOra_fine(resultSet.getTime("ora_fine").toLocalTime());
                intervento.setAbstract(resultSet.getString("abstract"));
                intervento.setSessione(sessione);

                interventi.add(intervento);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return interventi;

    }
}
