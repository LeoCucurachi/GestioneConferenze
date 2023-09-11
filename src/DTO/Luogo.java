package DTO;

import java.util.ArrayList;

public class Luogo {
    private Integer id_luogo;
    private String indirizzo;
    private String sede;
    private ArrayList<Conferenza> conferenze;

    public Luogo(Integer id_luogo, String indirizzo, String sede, ArrayList<Conferenza> conferenze) {
        this.id_luogo = id_luogo;
        this.indirizzo = indirizzo;
        this.sede = sede;
        this.conferenze = conferenze;
    }

    public Luogo(String indirizzo, String sede) {
        this.indirizzo = indirizzo;
        this.sede = sede;
    }

    public ArrayList<Conferenza> getConferenze() {
        return conferenze;
    }

    public void setConferenze(ArrayList<Conferenza> conferenze) {
        this.conferenze = conferenze;
    }

    public Luogo(Integer id_luogo, String indirizzo, String sede) {
        this.id_luogo = id_luogo;
        this.indirizzo = indirizzo;
        this.sede = sede;
    }

    public Integer getId_luogo() {
        return id_luogo;
    }

    public void setId_luogo(Integer id_luogo) {
        this.id_luogo = id_luogo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    @Override
    public String toString() {
        return  (sede + ", " + indirizzo);
    }
}
