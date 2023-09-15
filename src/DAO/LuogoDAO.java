package DAO;

import DTO.Luogo;

import java.util.ArrayList;

public interface LuogoDAO {
    public ArrayList<Luogo> getAll();
    public void insert(Luogo luogo) throws Exception;
}
