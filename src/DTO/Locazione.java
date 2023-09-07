package DTO;

public class Locazione {
    private Integer id;
    private String nome;
    private Luogo luogo;

    public Locazione(Integer id_locazione, String nome, Luogo luogo) {
        this.id = id_locazione;
        this.nome = nome;
        this.luogo = luogo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id_locazione) {
        this.id = id_locazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Luogo getLuogo() {
        return luogo;
    }

    public void setLuogo(Luogo luogo) {
        this.luogo = luogo;
    }
}
