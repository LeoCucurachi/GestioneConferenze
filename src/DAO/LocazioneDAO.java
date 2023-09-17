package DAO;

import java.util.ArrayList;

import DTO.Locazione;
import DTO.Luogo;

public interface LocazioneDAO {
	public ArrayList<Locazione> getFromLuogo(Luogo luogo);
}
