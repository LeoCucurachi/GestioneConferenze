package PGDAO;

import DAO.LuogoDAO;
import DTO.Luogo;
import prova.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class LuogoPGDAO implements LuogoDAO {
    public ArrayList<Luogo> getAll() {
        ArrayList<Luogo> luoghi = new ArrayList<>();
        Connection connection = DBConnection.getDBConnection().getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM luogo ORDER BY sede;";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                Luogo luogo = new Luogo(rs.getInt("id_luogo"),rs.getString("indirizzo"), rs.getString("sede"));
                luoghi.add(luogo);
            }

            connection.close();
            statement.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return luoghi;

    }

    public void insert(Luogo luogo) throws Exception{
        Connection connection = DBConnection.getDBConnection().getConnection();
        try {
            String sql = "INSERT INTO luogo (indirizzo, sede) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, luogo.getIndirizzo());
            statement.setString(2, luogo.getSede());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                luogo.setId_luogo(rs.getInt(1));
            }
            
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
