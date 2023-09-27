package PGDAO;

import DAO.ConferenzaDAO;
import DTO.Conferenza;
import DTO.Luogo;
import DTO.Sessione;
import principale.DBConnection;

import java.lang.reflect.UndeclaredThrowableException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;

public class ConferenzaPGDAO implements ConferenzaDAO {
    @Override
    public Conferenza get(Integer id) {
        Connection connection = DBConnection.getDBConnection().getConnection();
        Conferenza conferenza = null;
        try{
            String sql = "SELECT * FROM conferenza AS c NATURAL JOIN luogo AS l WHERE id_conferenza = ? ORDER BY id_conferenza";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                Integer id_conferenza = rs.getInt("id_conferenza");
                LocalDate data_inizio = rs.getDate("data_inizio").toLocalDate();
                LocalDate data_fine = rs.getDate("data_fine").toLocalDate();
                Integer id_luogo = rs.getInt("id_luogo");
                String indirizzo = rs.getString("indirizzo");
                String sede = rs.getString("sede");
                Luogo luogo = new Luogo(id_luogo, indirizzo, sede);
                String descrizione = rs.getString("descrizione");
                conferenza = new Conferenza(id_conferenza, data_inizio, data_fine, descrizione, luogo);
            }
            
            rs.close();
            statement.close();
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return conferenza;
    }

    @Override
    public ArrayList<Conferenza> getAll() {
        Connection connection = DBConnection.getDBConnection().getConnection();
        ArrayList<Conferenza> conferenze = new ArrayList<Conferenza>();
        try{
            String sql = "SELECT * FROM conferenza NATURAL JOIN luogo ORDER BY id_conferenza";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Integer id_conferenza = rs.getInt("id_conferenza");
                LocalDate data_inizio = rs.getDate("data_inizio").toLocalDate();
                LocalDate data_fine = rs.getDate("data_fine").toLocalDate();
                Integer id_luogo = rs.getInt("id_luogo");
                String indirizzo = rs.getString("indirizzo");
                String sede = rs.getString("sede");
                String descrizione = rs.getString("descrizione");
                Luogo luogo = new Luogo(id_luogo, indirizzo, sede);
                conferenze.add(new Conferenza(id_conferenza, data_inizio, data_fine, descrizione, luogo));
            }
            
            rs.close();
            statement.close();
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return conferenze;
    }

    @Override
    public ArrayList<Conferenza> getContainsSede(String src) {
        Connection connection = DBConnection.getDBConnection().getConnection();
        ArrayList<Conferenza> conferenze = new ArrayList<Conferenza>();
        try{
            String sql = "SELECT * FROM conferenza NATURAL JOIN luogo WHERE LOWER(luogo.sede) LIKE LOWER('%" + src + "%') ORDER BY id_conferenza";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Integer id_conferenza = rs.getInt("id_conferenza");
                LocalDate data_inizio = rs.getDate("data_inizio").toLocalDate();
                LocalDate data_fine = rs.getDate("data_fine").toLocalDate();
                Integer id_luogo = rs.getInt("id_luogo");
                String indirizzo = rs.getString("indirizzo");
                String sede = rs.getString("sede");
                String descrizione = rs.getString("descrizione");
                Luogo luogo = new Luogo(id_luogo, indirizzo, sede);
                conferenze.add(new Conferenza(id_conferenza, data_inizio, data_fine, descrizione, luogo));
            }
            
            rs.close();
            statement.close();
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return conferenze;
    }
    
    @Override
    public Conferenza getWithSessions(Integer id) {
        Connection connection = DBConnection.getDBConnection().getConnection();
        Conferenza conferenza = null;
        try{
            String sql = "SELECT * FROM conferenza AS c NATURAL JOIN luogo AS l WHERE id_conferenza = ? ORDER BY id_conferenza";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                Integer id_conferenza = rs.getInt("id_conferenza");
                LocalDate data_inizio = rs.getDate("data_inizio").toLocalDate();
                LocalDate data_fine = rs.getDate("data_fine").toLocalDate();
                Integer id_luogo = rs.getInt("id_luogo");
                String indirizzo = rs.getString("indirizzo");
                String sede = rs.getString("sede");
                Luogo luogo = new Luogo(id_luogo, indirizzo, sede);
                conferenza = new Conferenza(id_conferenza, data_inizio, data_fine, luogo, new ArrayList<Sessione>());
            }

            String sql1 = "SELECT * FROM sessione NATURAL JOIN conferenza WHERE conferenza.id_conferenza = " + id + ";";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            rs = statement1.executeQuery();
            while(rs.next()){
                Integer id_sessione = rs.getInt("id_sessione");
                LocalDate data_sessione = rs.getDate("data_sessione").toLocalDate();
                LocalTime ora_inizio = rs.getTime("ora_inizio").toLocalTime();
                LocalTime ora_fine = rs.getTime("ora_fine").toLocalTime();
                conferenza.addSessione(new Sessione(id_sessione, data_sessione, ora_inizio, ora_fine, null, conferenza, null));
            }
            
            rs.close();
            statement.close();
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return conferenza;
    }

    @Override
    public ArrayList<Conferenza> getAllWithSessions() {
        Connection connection = DBConnection.getDBConnection().getConnection();
        ArrayList<Conferenza> conferenze = new ArrayList<Conferenza>();
        try{
            String sql = "SELECT * FROM conferenza AS c NATURAL JOIN luogo ORDER BY id_conferenza";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Integer id_conferenza = rs.getInt("id_conferenza");
                LocalDate data_inizio = rs.getDate("data_inizio").toLocalDate();
                LocalDate data_fine = rs.getDate("data_fine").toLocalDate();
                Integer id_luogo = rs.getInt("id_luogo");
                String indirizzo = rs.getString("indirizzo");
                String sede = rs.getString("sede");
                Luogo luogo = new Luogo(id_luogo, indirizzo, sede);
                conferenze.add(new Conferenza(id_conferenza, data_inizio, data_fine, luogo, new ArrayList<Sessione>()));
            }
            String sql1 = "SELECT * FROM sessione NATURAL JOIN conferenza WHERE conferenza.id_conferenza = ?;";
            PreparedStatement statement1 = connection.prepareStatement(sql1);

            for (Conferenza conferenza : conferenze) {
                statement1.setInt(1, conferenza.getId());
                rs = statement1.executeQuery();
                while(rs.next()){
                    Integer id_sessione = rs.getInt("id_sessione");
                    LocalDate data_sessione = rs.getDate("data_sessione").toLocalDate();
                    LocalTime ora_inizio = rs.getTime("ora_inizio").toLocalTime();
                    LocalTime ora_fine = rs.getTime("ora_fine").toLocalTime();
                    conferenza.addSessione(new Sessione(id_sessione, data_sessione, ora_inizio, ora_fine, null, conferenza, null));
                }
            }
            
            rs.close();
            statement.close();
            connection.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return conferenze;
    }

    @Override
    public ArrayList<Conferenza> getCurrent() {
        Connection connection = DBConnection.getDBConnection().getConnection();
        ArrayList<Conferenza> conferenze = new ArrayList<Conferenza>();
        try{
            String sql = "SELECT * FROM conferenza AS c NATURAL JOIN luogo WHERE c.data_inizio <= NOW() AND c.data_fine >= NOW() ORDER BY id_conferenza";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Integer id_conferenza = rs.getInt("id_conferenza");
                LocalDate data_inizio = rs.getDate("data_inizio").toLocalDate();
                LocalDate data_fine = rs.getDate("data_fine").toLocalDate();
                Integer id_luogo = rs.getInt("id_luogo");
                String indirizzo = rs.getString("indirizzo");
                String sede = rs.getString("sede");
                Luogo luogo = new Luogo(id_luogo, indirizzo, sede);
                conferenze.add(new Conferenza(id_conferenza, data_inizio, data_fine, luogo));
            }
            
            rs.close();
            statement.close();
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return conferenze;
    }

    @Override
    public ArrayList<Conferenza> getCurrentAndFuture() {
        Connection connection = DBConnection.getDBConnection().getConnection();
        ArrayList<Conferenza> conferenze = new ArrayList<Conferenza>();
        try{
            String sql = "SELECT * FROM conferenza AS c NATURAL JOIN luogo WHERE c.data_fine >= NOW() ORDER BY id_conferenza";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Integer id_conferenza = rs.getInt("id_conferenza");
                LocalDate data_inizio = rs.getDate("data_inizio").toLocalDate();
                LocalDate data_fine = rs.getDate("data_fine").toLocalDate();
                Integer id_luogo = rs.getInt("id_luogo");
                String indirizzo = rs.getString("indirizzo");
                String sede = rs.getString("sede");
                Luogo luogo = new Luogo(id_luogo, indirizzo, sede);
                conferenze.add(new Conferenza(id_conferenza, data_inizio, data_fine, luogo));
            }
            
            rs.close();
            statement.close();
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return conferenze;
    }

    @Override
    public boolean insert(Conferenza conferenza) throws Exception{
        Connection connection = DBConnection.getDBConnection().getConnection();
        try{
            String sql = "INSERT INTO conferenza (data_inizio, data_fine, descrizione, id_luogo) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setDate(1, Date.valueOf(conferenza.getData_inizio()));
            statement.setDate(2, Date.valueOf(conferenza.getData_fine()));
            statement.setString(3, conferenza.getDescrizione());
            statement.setInt(4, conferenza.getLuogo().getId_luogo());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next()){
                conferenza.setId(rs.getInt(1));
            }
            
            rs.close();
            statement.close();
            connection.close();

            return true;
        } catch(SQLException e) {

            throw e;
        }
    }

    @Override
    public boolean delete(Integer id) {
        Connection connection = DBConnection.getDBConnection().getConnection();
        try{
            String sql = "DELETE FROM conferenza WHERE id_conferenza = " + id + ";";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            
            statement.close();
            connection.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(Integer id, Conferenza conferenza) throws Exception{
        Connection connection = DBConnection.getDBConnection().getConnection();
        try{
            String sql = "UPDATE conferenza SET data_inizio = ?, data_fine = ?, descrizione = ?, id_luogo = ? WHERE id_conferenza = " + id + ";";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDate(1, Date.valueOf(conferenza.getData_inizio().toString()));
            statement.setDate(2, Date.valueOf(conferenza.getData_fine().toString()));
            statement.setString(3, conferenza.getDescrizione());
            statement.setInt(4, conferenza.getLuogo().getId_luogo());

            statement.executeUpdate();
            
            statement.close();
            connection.close();
            
        } catch(SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public ArrayList<Year> getAnni() {
        Connection connection = DBConnection.getDBConnection().getConnection();
        ArrayList<Year> anni = new ArrayList<Year>();
        try{
            String sql = "((SELECT DISTINCT EXTRACT(year FROM data_inizio) AS anno FROM conferenza) UNION (SELECT DISTINCT EXTRACT(year FROM data_fine) AS anno FROM conferenza)) ORDER BY anno";
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
            	anni.add(Year.of(rs.getInt("anno")));
            }
            
            rs.close();
            statement.close();
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return anni;
    }
}
