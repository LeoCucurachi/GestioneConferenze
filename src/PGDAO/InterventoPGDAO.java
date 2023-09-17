package PGDAO;

import DAO.InterventoDAO;
import DTO.Intervento;
import DTO.Sessione;
import prova.DBConnection;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InterventoPGDAO implements InterventoDAO {

    public InterventoPGDAO(){
    }

    public ArrayList<Intervento> getInterventi(Sessione sessione){
    	Connection connection = DBConnection.getDBConnection().getConnection();
        ArrayList<Intervento> interventi = new ArrayList<Intervento>();
        try {
            String sql = "SELECT * FROM intervento WHERE id_sessione = " + sessione.getId() + " ORDER BY ora_inizio, ora_fine;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                Intervento intervento = new Intervento();
                intervento.setId(resultSet.getInt("id_intervento"));
                intervento.setOraInizio(resultSet.getTime("ora_inizio").toLocalTime());
                intervento.setOraFine(resultSet.getTime("ora_fine").toLocalTime());
                intervento.setAbstract(resultSet.getString("abstract"));
                intervento.setSessione(sessione);

                interventi.add(intervento);
            }
            
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return interventi;

    }
}
