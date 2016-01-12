package util;

public final class Util {

	public static String completeSpaces(String s, int size){
		int spacesToComplete = size - s.length();
		for (int i = 0; i < spacesToComplete; i++) {
			s = s + " ";
		}
		return s;
	}

	public static String completeZeros(String s, int size){
		int zerosToComplete = size - s.length();
		for (int i = 0; i < zerosToComplete; i++) {
			s = "0" + s;
		}
		return s;
	}

}
