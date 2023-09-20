package DAO;

import java.time.LocalTime;
import java.util.ArrayList;

import DTO.Intervento;
import DTO.Sessione;

public interface InterventoDAO {
	public ArrayList<Intervento> getInterventi(Sessione sessione);
	public void insert(Intervento intervento) throws Exception;
	public void delete(int id);
}
