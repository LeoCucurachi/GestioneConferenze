package DAO;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;

import DTO.Partecipante;

public interface PartecipanteDAO {
	public ArrayList<Partecipante> getAll();
	public ArrayList<Partecipante>  getSpeakerOfYear(Year year);
	public ArrayList<Partecipante>  getSpeakerOfYearAndMonth(Month month, Year year);
}
