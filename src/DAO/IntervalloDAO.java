package DAO;

import java.util.ArrayList;

import DTO.Intervallo;
import DTO.Sessione;

public interface IntervalloDAO {
	public ArrayList<Intervallo> getIntervalli(Sessione sessione);
	public void insert(Intervallo intervallo) throws Exception;
	 public ArrayList<String> getEnumTypes();
		public void delete(int id);
}
