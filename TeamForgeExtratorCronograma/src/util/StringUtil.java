package util;

public final class StringUtil {

	public static String eliminateInitialSpaces(String str){
		String ret = new String();

		for (int i = 0; i < str.length() ; i++) {
			if(str.charAt(i) != ' '){
				return str.substring(i, str.length());
			}
		}
		return ret;
	}

	public static String eliminateWeekDayNames(String str){
		str = str.replaceAll("Seg ", "");
		str = str.replaceAll("Ter ", "");
		str = str.replaceAll("Qua ", "");
		str = str.replaceAll("Qui ", "");
		str = str.replaceAll("Sex ", "");
		str = str.replaceAll("Sab ", "");
		str = str.replaceAll("Dom ", "");
		return str;
	}

	public static String eliminateHourLabel(String str){
		str = str.replaceAll("h", "");
		return str;
	}

}
