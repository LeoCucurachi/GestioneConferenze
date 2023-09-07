package DTO;

import java.time.LocalDate;
import java.util.ArrayList;

public class Conferenza {
    private Integer id;
    private LocalDate data_inizio;
    private LocalDate data_fine;
    private Luogo luogo;
    private ArrayList<Sessione> sessioni;

    @Override
    public String toString() {
        return  "id=" + id +
                ", data_inizio=" + data_inizio +
                ", data_fine=" + data_fine + ", " +
                luogo;
    }

    public Conferenza(Integer id, LocalDate data_inizio, LocalDate data_fine, Luogo luogo) {
        this.id = id;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.luogo = luogo;
    }

    public Conferenza(LocalDate data_inizio, LocalDate data_fine, Luogo luogo) {
        id = null;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.luogo = luogo;
    }

    public Conferenza(Integer id, LocalDate data_inizio, LocalDate data_fine, Luogo luogo, ArrayList<Sessione> sessioni) {
        this.id = id;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.luogo = luogo;
        this.sessioni = sessioni;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(LocalDate data_inizio) {
        this.data_inizio = data_inizio;
    }

    public LocalDate getData_fine() {
        return data_fine;
    }

    public void setData_fine(LocalDate data_fine) {
        this.data_fine = data_fine;
    }

    public Luogo getLuogo() {
        return luogo;
    }

    public void setLuogo(Luogo luogo) {
        this.luogo = luogo;
    }

    public void addSessione(Sessione sessione){
        sessioni.add(sessione);
    }

    public ArrayList<Sessione> getSessioni() {
        return sessioni;
    }
}
