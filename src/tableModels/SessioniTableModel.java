package tableModels;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.table.DefaultTableModel;

import DTO.Locazione;
import DTO.Partecipante;
import DTO.Sessione;

public class SessioniTableModel extends DefaultTableModel{

	public SessioniTableModel() {
		super();
		
        addColumn("ID");
        addColumn("Data");
        addColumn("Ora Inizio");
        addColumn("Ora Fine");
        addColumn("Locazione");
        addColumn("Sessione");
        addColumn("Coordinatore");
        addColumn("Keynote Speaker");
        
	}
	
    @Override
    public Class getColumnClass(int column) {
        switch (column) {
        	case 0:
        		return Integer.class;
            case 1:
                return LocalDate.class;
            case 2:
                return LocalTime.class;
            case 3:
            	return LocalTime.class;
            case 4:
            	return Locazione.class;
            case 5:
            	return Sessione.class;
            case 6:
            	return Partecipante.class;
            case 7:
            	return Partecipante.class;
            default:
                return String.class;
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
    	return false;
    }
}
