package DTO;

import java.time.*;

public class Intervento implements ElementoProgramma{
    Integer id_intervento;
    LocalTime ora_inizio;
    LocalTime ora_fine;
    String Abstract;
    Sessione sessione;
    Partecipante speaker;

    public int getId() {
        return id_intervento;
    }

    public void setId(Integer id_intervento) {
        this.id_intervento = id_intervento;
    }
    

    public Intervento(Integer id_intervento, LocalTime ora_inizio, LocalTime ora_fine, String abstract1,
			Sessione sessione, Partecipante speaker) {
		super();
		this.id_intervento = id_intervento;
		this.ora_inizio = ora_inizio;
		this.ora_fine = ora_fine;
		Abstract = abstract1;
		this.sessione = sessione;
		this.speaker = speaker;
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

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }
    
    public String getTipoNom() {
    	return "intervento";
    }
    
    

    public Partecipante getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Partecipante speaker) {
		this.speaker = speaker;
	}

	@Override
    public String toString() {
        return "Intervento{" +
                "id_intervento=" + id_intervento +
                ", ora_inizio=" + ora_inizio +
                ", ora_fine=" + ora_fine +
                ", Abstract='" + Abstract + '\'' +
                '}';
    }
}
