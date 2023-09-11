package PGDAO;

import DAO.ConferenzaDAO;
import DTO.Conferenza;
import DTO.Luogo;
import DTO.Sessione;
import prova.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ConferenzaPGDAO implements ConferenzaDAO {
    @Override
    public Conferenza get(Integer id) {
        Connection connection = DBConnection.getDBConnection().getConnection();
        Conferenza conferenza = null;
        try{
            String sql = "SELECT * FROM conferenza AS c NATURAL JOIN luogo AS l WHERE id_conferenza = ?";
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
                conferenza = new Conferenza(id_conferenza, data_inizio, data_fine, luogo);
            }
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
            String sql = "SELECT * FROM conferenza NATURAL JOIN luogo";
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
            String sql = "SELECT * FROM conferenza AS c NATURAL JOIN luogo AS l WHERE id_conferenza = ?";
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
                conferenza.addSessione(new Sessione(id_sessione, data_sessione, ora_inizio, ora_fine, conferenza));
            }

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
            String sql = "SELECT * FROM conferenza AS c NATURAL JOIN luogo";
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
                    conferenza.addSessione(new Sessione(id_sessione, data_sessione, ora_inizio, ora_fine, conferenza));
                }
            }

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
            String sql = "SELECT * FROM conferenza AS c NATURAL JOIN luogo WHERE c.data_inizio <= NOW() AND c.data_fine >= NOW()";
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
            String sql = "SELECT * FROM conferenza AS c NATURAL JOIN luogo WHERE c.data_fine >= NOW()";
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

            statement.setDate(1, Date.valueOf(conferenza.getData_inizio().toString()));
            statement.setDate(2, Date.valueOf(conferenza.getData_fine()));
            statement.setString(3, conferenza.getDescrizione());
            statement.setInt(4, conferenza.getLuogo().getId_luogo());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next()){
                conferenza.setId(rs.getInt(1));
            }

            return true;
        } catch(Exception e) {
            e.printStackTrace();
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
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Integer id, Conferenza conferenza) {
        Connection connection = DBConnection.getDBConnection().getConnection();
        try{
            String sql = "UPDATE conferenza SET (data_inizio = ?, data_fine = ?, descrizione = ?, id_luogo = ?) WHERE id_conferenza = " + id + ";";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDate(1, Date.valueOf(conferenza.getData_inizio().toString()));
            statement.setDate(2, Date.valueOf(conferenza.getData_fine().toString()));
            statement.setString(3, conferenza.getDescrizione());
            statement.setInt(4, conferenza.getLuogo().getId_luogo());

            statement.executeUpdate();
            
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
