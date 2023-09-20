package prova;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.table.DefaultTableModel;

import DTO.ElementoProgramma;
import DTO.Locazione;
import DTO.Partecipante;
import DTO.Sessione;

public class ProgrammaTableModel extends DefaultTableModel{
	
	public ProgrammaTableModel() {
		super();
		
		addColumn("ID");
		addColumn("Tipo");
		addColumn("Ora Inizio");
		addColumn("Ora Fine");
		addColumn("Abstract");
		addColumn("Speaker");
		addColumn("Elemento Programma");
	}
	
    @Override
    public Class getColumnClass(int column) {
        switch (column) {
        	case 0:
        		return Integer.class;
        	case 1:
        		return String.class;
            case 2:
                return LocalTime.class;
            case 3:
                return LocalTime.class;
            case 4:
            	return String.class;
            case 5:
            	return Partecipante.class;
            case 6:
            	return ElementoProgramma.class;
            default:
                return String.class;
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
    	return false;
    }

}
