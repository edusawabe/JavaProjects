package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Calendar;
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

	public static boolean isWeekDay(String strDate){
		Calendar c = Calendar.getInstance();
		Date d = new Date(strDate);
		c.setTime(d);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case Calendar.MONDAY:
			return true;
		case Calendar.TUESDAY:
			return true;
		case Calendar.WEDNESDAY:
			return true;
		case Calendar.THURSDAY:
			return true;
		case Calendar.FRIDAY:
			return true;
		case Calendar.SATURDAY:
			return false;
		case Calendar.SUNDAY:
			return false;
		default:
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public static Date dataAtual() {
		boolean test = false;

		if(test)
			return new Date(2017-1900,7,31);
		else
			return new Date();
	}
}
