package prova;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.table.DefaultTableCellRenderer;

public class DateTableRenderer extends DefaultTableCellRenderer{
	
	public DateTableRenderer() {
		super();
	}
	
	@Override
	public void setValue(Object obj) {
		if(obj == null) {
			setText("");
		} 
		else if (obj instanceof LocalDate){
			LocalDate date = (LocalDate)obj;
			setText(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
		}
		else {
			setText("");
		}
	}
}
