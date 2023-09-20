package DTO;

import java.time.LocalTime;

public class Intervallo implements ElementoProgramma {
	int id;
	LocalTime ora_inizio;
	LocalTime ora_fine;
	String tipo;
	Sessione sessione;
	
	public Intervallo() {
		
	}
	
	public Intervallo(int id, LocalTime ora_inizio, LocalTime ora_fine, String tipo, Sessione sessione) {
		super();
		this.id = id;
		this.ora_inizio = ora_inizio;
		this.ora_fine = ora_fine;
		this.tipo = tipo;
		this.sessione = sessione;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalTime getOraInizio() {
		return ora_inizio;
	}
	public void setOraInizio(LocalTime ora_inizio) {
		this.ora_inizio = ora_inizio;
	}
	public LocalTime getOraFine() {
		return ora_fine;
	}
	public void setOraFine(LocalTime ora_fine) {
		this.ora_fine = ora_fine;
	}
	public String getTipo() {
		return tipo;
	}
	public String getTipoNom() {
		return "intervallo, " + tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Sessione getSessione() {
		return sessione;
	}
	public void setSessione(Sessione sessione) {
		this.sessione = sessione;
	}
	
	
}
