package util;

public final class HorasUtil {

	public static String addHours(String hours, String toAddHours) {
		int hh, toAddHH, retHH;
		int mm, toAddMM, retMM;
		String[] hoursAplit;
		String ret;

		hoursAplit = hours.split(":");
		hh = Integer.parseInt(hoursAplit[0]);
		mm = Integer.parseInt(hoursAplit[1]);

		hoursAplit = toAddHours.split(":");
		toAddHH = Integer.parseInt(hoursAplit[0]);
		toAddMM = Integer.parseInt(hoursAplit[1]);

		retHH = hh + toAddHH;
		retMM = mm + toAddMM;
		if(retMM >= 60){
			retHH++;
			retMM = retMM - 60;
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
