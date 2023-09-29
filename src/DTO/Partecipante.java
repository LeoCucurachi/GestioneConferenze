package DTO;

public class Partecipante {
    private Integer id;
    private String nome;
    private String cognome;
    private String email;
    private Istituzione istituzione;
    
    public Partecipante(int id, String nome, String cognome, String email, Istituzione istituzione) {
    	this.id = id;
    	this.nome = nome;
    	this.cognome = cognome;
    	this.email = email;
    	this.istituzione = istituzione;
    }



	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Istituzione getIstituzione() {
        return istituzione;
    }

    public void setIstituzione(Istituzione istituzione) {
        this.istituzione = istituzione;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + ", " + email + ", " + istituzione;
    }
}
