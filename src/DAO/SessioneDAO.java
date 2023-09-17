package DAO;

import java.time.LocalDate;
import java.util.ArrayList;

import DTO.Conferenza;
import DTO.Locazione;
import DTO.Sessione;

public interface SessioneDAO {
	
    public void insert(Sessione s) throws Exception;
    public ArrayList<Sessione> getSessioniOfConferenza(Conferenza conferenza);
    public void delete(int id);
    public void update(Integer id, Sessione s) throws Exception;
    public ArrayList<Sessione> getSessioniFromDateAndLocation(LocalDate data, Locazione locazione);
}
