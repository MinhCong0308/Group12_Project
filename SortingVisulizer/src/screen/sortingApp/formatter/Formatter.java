package screen.sortingApp.formatter;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.text.ParseException;
public class Formatter extends NumberFormatter{
	public static final long serialVersionUID = 1L; //serialization.
	public Formatter(NumberFormat format) {
		super(format);
	}
	public Object stringToValue(String text) throws ParseException
	{
		if ("".equals(text)) { // supposing that empty string is value 0.
			return 0;
		}
		return super.stringToValue(text);
	}
}
