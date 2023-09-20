package DAO;

import java.util.ArrayList;

import DTO.Evento;
import DTO.Sessione;

public interface EventoDAO {
	public ArrayList<Evento> getEventi(Sessione sessione);
	public void insert(Evento evento) throws Exception;
	public ArrayList<String> getEnumTypes();
	public void delete(int id);
}
