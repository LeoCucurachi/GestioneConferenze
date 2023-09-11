package DAO;

import DTO.Conferenza;

import java.util.ArrayList;

public interface ConferenzaDAO {
    public Conferenza get(Integer id);
    public ArrayList<Conferenza> getAll();
    public Conferenza getWithSessions(Integer id);
    public ArrayList<Conferenza> getAllWithSessions();
    public ArrayList<Conferenza> getCurrent();
    public ArrayList<Conferenza> getCurrentAndFuture();
    public boolean insert(Conferenza conferenza);
    public boolean update(Integer id, Conferenza conferenza);
    public boolean delete(Integer id);
}
