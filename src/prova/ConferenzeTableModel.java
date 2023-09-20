package prova;

import java.time.LocalDate;

import javax.swing.table.DefaultTableModel;

import DTO.Conferenza;
import DTO.Luogo;

public class ConferenzeTableModel extends DefaultTableModel{
	
	public ConferenzeTableModel() {
		super();
		
        addColumn("ID");
        addColumn("Descrizione");
        addColumn("Data Inizio");
        addColumn("Data Fine");
        addColumn("Luogo");
        addColumn("Conferenza");
	}
	
        @Override
        public Class getColumnClass(int column) {
            switch (column) {
            	case 0:
            		return Integer.class;
                case 1:
                    return String.class;
                case 2:
                    return LocalDate.class;
                case 3:
                	return LocalDate.class;
                case 4:
                	return Luogo.class;
                case 5:
                	return Conferenza.class;
                default:
                    return String.class;
            }
        }
        
        @Override
        public boolean isCellEditable(int row, int column) {
        	return false;
        }
}
