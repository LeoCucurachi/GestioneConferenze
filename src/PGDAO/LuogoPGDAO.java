package PGDAO;

import DAO.LuogoDAO;
import DTO.Luogo;
import prova.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
