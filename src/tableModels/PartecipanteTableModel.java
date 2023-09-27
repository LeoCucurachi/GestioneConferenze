package tableModels;

import java.time.LocalDate;

import javax.swing.table.DefaultTableModel;

import DTO.Conferenza;
import DTO.Luogo;
import DTO.Partecipante;

public class PartecipanteTableModel extends DefaultTableModel{
	public PartecipanteTableModel() {
		super();
		
        addColumn("ID");
        addColumn("Nome");
        addColumn("Cognome");
        addColumn("Email");
        addColumn("Istituzione");
        addColumn("Partecipante");
	}
	
        @Override
        public Class getColumnClass(int column) {
            switch (column) {
            	case 0:
            		return Integer.class;
                case 1:
                    return String.class;
                case 2:
                    return String.class;
                case 3:
                	return String.class;
                case 4:
                	return String.class;
                case 5:
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
