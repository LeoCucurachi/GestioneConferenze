package DAO;

import DTO.Conferenza;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

public interface ConferenzaDAO {
    public Conferenza get(Integer id);
    public ArrayList<Conferenza> getAll();
    public Conferenza getWithSessions(Integer id);
    public ArrayList<Conferenza> getContainsSede(String src);
    public ArrayList<Conferenza> getAllWithSessions();
    public ArrayList<Conferenza> getCurrent();
    public ArrayList<Conferenza> getCurrentAndFuture();
    public boolean insert(Conferenza conferenza) throws Exception;
    public void update(Integer id, Conferenza conferenza) throws Exception;
    public boolean delete(Integer id);
    public ArrayList<Year> getAnni();
}
