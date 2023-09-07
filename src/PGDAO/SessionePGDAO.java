package PGDAO;

import DAO.SessioneDAO;
import DTO.Sessione;
import prova.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SessionePGDAO implements SessioneDAO {
    private DBConnection db;
    private Connection connection;
    public SessionePGDAO(){
        db = DBConnection.getDBConnection();
        connection = db.getConnection();
    }

/*    private int getNextVal(){
        int id_sessione = 1;
        try{
            Statement statement = connection.createStatement();
            String getNextVal = "SELECT nextval('sessione_id_sessione_seq');";
            ResultSet rs = statement.executeQuery(getNextVal);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return id_sessione;
    }*/
    @Override
    public void InsertSessione(Sessione s) {
        try{
            Statement statement = connection.createStatement();
            String comando = "INSERT INTO sessione (data_sessione, ora_inizio, ora_fine, id_conferenza, id_locazione, coordinatore) VALUES" +
                    "(" + "'" + s.getData_sessione().toString() + "', '" + s.getOra_inizio().toString() + "', '" + s.getOra_fine() + "', " + s.getConferenza().getId() + ", " + s.getLocazione().getId() + ", " + s.getCoordinatore().getId() + ");";
            statement.executeUpdate(comando, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            s.setId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
