package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;

public final class DateUtil {
	public static boolean isDateValid(String strDate) {
	    String dateFormat = "dd/MM/uuuu";

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)
				.withResolverStyle(ResolverStyle.STRICT);
	    try {
	    	LocalDate.parse(strDate, dateTimeFormatter);
	        return true;
	    } catch (DateTimeParseException e) {
	       return false;
	    }
	}

	@SuppressWarnings("deprecation")
	public static Date dataAtual() {
		boolean test = false;

		if(test)
			return new Date(2017-1900,05,30);
		else
			return new Date();
	}
}
