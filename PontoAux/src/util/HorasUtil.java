package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class HorasUtil {

	public static String operateHoursCalendar(String hours, String toOperate, String operation) {
		String ret = null;
		Calendar operCalendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String[] hoursSplit;
		int hh, mm;
		Date d1 = null;

		try {
			hoursSplit = toOperate.split(":");
			hh = Integer.parseInt(hoursSplit[0]);
			mm = Integer.parseInt(hoursSplit[1]);
			d1 = format.parse("01/01/2010" + " " + hours + ":00");
			operCalendar.setTimeInMillis(0);
			operCalendar.setTime(d1);
			if(operation.equals("+")){
				operCalendar.add(Calendar.HOUR_OF_DAY,  hh);
				operCalendar.add(Calendar.MINUTE,  mm);
			}
			if(operation.equals("-")){
				operCalendar.add(Calendar.HOUR_OF_DAY,  (hh*-1));
				operCalendar.add(Calendar.MINUTE,  (mm*-1));
			}
			format = new SimpleDateFormat("HH:mm");
			ret = format.format(operCalendar.getTime());
		} catch (ParseException e) {
			Alert al = new Alert(AlertType.ERROR);
			al.setTitle("Erro");
			al.setContentText("Erro no calculo de Horas");
			al.show();
			e.printStackTrace();
		}
		return ret;
	}

	public static String subTractHours(String hours, String toSub) {
		int hh, toSubHH, retHH;
		int mm, toSubMM, retMM;
		String[] hoursSplit;
		String ret;

		hoursSplit = hours.split(":");
		hh = Integer.parseInt(hoursSplit[0]);
		mm = Integer.parseInt(hoursSplit[1]);

		hoursSplit = toSub.split(":");
		toSubHH = Integer.parseInt(hoursSplit[0]);
		toSubMM = Integer.parseInt(hoursSplit[1]);

		retHH = hh - toSubHH;
		retMM = mm - toSubMM;
		if(retMM < 0){
			retHH--;
			retMM = 60 + retMM;
		}

		if(retHH < 10)
			ret = "0" + retHH;
		else
			ret = "" + retHH;

		if(retMM < 10)
			ret = ret+ ":0" + retMM;
		else
			ret = ret+ ":" + retMM;

		return ret;
	}
}
