package DAO;

import DTO.Conferenza;

import java.util.ArrayList;

public interface ConferenzaDAO {
    public Conferenza get(Integer id);
    public ArrayList<Conferenza> getAll();
    public Conferenza getWithSessions(Integer id);
    public ArrayList<Conferenza> getAllWithSessions();
}
