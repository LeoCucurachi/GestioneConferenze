package prova;

public class Main {
    public static void main(String[] args) {
        System.out.println("Prova");
        PartecipanteDAO parDAO = new PartecipanteDAO();
        Partecipante par;

        par = parDAO.getPartecipante("msaunton0@printfriendly.com");
        if(par != null){
            System.out.println(par.getId() + "  " + par.getNome() + "  " + par.getCognome());
        }
    } // verifica

}