package DAO;

import java.util.ArrayList;

import DTO.Intervento;
import DTO.Sessione;

public interface InterventoDAO {
	public ArrayList<Intervento> getInterventi(Sessione sessione);
}
