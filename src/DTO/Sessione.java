package DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class Sessione {
    private Integer id;
    private LocalDate data_sessione;
    private LocalTime ora_inizio;
    private LocalTime ora_fine;
    private Locazione locazione;
    private Conferenza conferenza;
    private Partecipante coordinatore;
    private Partecipante keynote;

    public Sessione(Integer id, LocalDate data_sessione, LocalTime ora_inizio, LocalTime ora_fine, Locazione locazione, Conferenza conferenza, Partecipante coordinatore) {
        this.id = id;
        this.data_sessione = data_sessione;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.locazione = locazione;
        this.conferenza = conferenza;
        this.coordinatore = coordinatore;
    }

    public Sessione(LocalDate data_sessione, LocalTime ora_inizio, LocalTime ora_fine, Locazione locazione, Conferenza conferenza, Partecipante coordinatore) {
        this.id = null;
        this.data_sessione = data_sessione;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.locazione = locazione;
        this.conferenza = conferenza;
        this.coordinatore = coordinatore;
    }
    
    public LocalDate getData_sessione() {
        return data_sessione;
    }

    public Sessione(LocalDate data_sessione, LocalTime ora_inizio, LocalTime ora_fine, Locazione locazione, Conferenza conferenza, Partecipante coordinatore, Partecipante keynote) {
	super();
	this.data_sessione = data_sessione;
	this.ora_inizio = ora_inizio;
	this.ora_fine = ora_fine;
	this.locazione = locazione;
	this.conferenza = conferenza;
	this.coordinatore = coordinatore;
	this.keynote = keynote;
}
    
    public Sessione(Integer id, LocalDate data_sessione, LocalTime ora_inizio, LocalTime ora_fine, Locazione locazione, Conferenza conferenza, Partecipante coordinatore, Partecipante keynote) {
	super();
	this.id = id;
	this.data_sessione = data_sessione;
	this.ora_inizio = ora_inizio;
	this.ora_fine = ora_fine;
	this.locazione = locazione;
	this.conferenza = conferenza;
	this.coordinatore = coordinatore;
	this.keynote = keynote;
}

	public void setData_sessione(LocalDate data_sessione) {
        this.data_sessione = data_sessione;
    }

    public LocalTime getOra_inizio() {
        return ora_inizio;
    }

    public void setOra_inizio(LocalTime ora_inizio) {
        this.ora_inizio = ora_inizio;
    }

    public LocalTime getOra_fine() {
        return ora_fine;
    }

    public void setOra_fine(LocalTime ora_fine) {
        this.ora_fine = ora_fine;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public Partecipante getCoordinatore() {
        return coordinatore;
    }

    public void setCoordinatore(Partecipante coordinatore) {
        this.coordinatore = coordinatore;
    }

    public Integer getId(){return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    public Locazione getLocazione() {
        return locazione;
    }

    public void setLocazione(Locazione locazione) {
        this.locazione = locazione;
    }
    
    

    public Partecipante getKeynote() {
		return keynote;
	}

	public void setKeynote(Partecipante keynote) {
		this.keynote = keynote;
	}

	@Override
    public String toString() {
        return "Sessione{" +
                "id=" + id +
                ", data_sessione=" + data_sessione +
                ", ora_inizio=" + ora_inizio +
                ", ora_fine=" + ora_fine +
                ", locazione=" + locazione +
                ", conferenza=" + conferenza +
                ", coordinatore=" + coordinatore +
                '}';
    }
    
    @Override
    public boolean equals(final Object obj) {
    	if(obj == this) {
    		return true;
    	}
    	else if (obj == null || !(obj instanceof Sessione)) {
    		return false;
    	}
    	
    	Sessione sessione = (Sessione)obj;
    	
    	return (this.id == sessione.id && this.data_sessione.equals(sessione.getData_sessione()) && this.ora_inizio.equals(sessione.getOra_inizio()) && this.ora_fine.equals(sessione.getOra_fine()) && this.locazione.equals(sessione.locazione) && this.conferenza.equals(sessione.getConferenza()));
    }
}
