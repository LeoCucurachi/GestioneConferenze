package DTO;

import java.time.*;

public class Intervento {
    Integer id_intervento;
    LocalTime ora_inizio;
    LocalTime ora_fine;
    String Abstract;
    Sessione sessione;

    public Integer getId_intervento() {
        return id_intervento;
    }

    public void setId_intervento(Integer id_intervento) {
        this.id_intervento = id_intervento;
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
