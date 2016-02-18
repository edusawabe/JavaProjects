package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Util {

	public static String completeZeros(int num, int qtdeCasas){
		String ret = new String();
		ret = "" + num;
		int compare = 0;
		String sCompare = new String();

		for (int i = 0; i < qtdeCasas; i++) {
			if (compare == 0)
				compare = 10;
			else
				compare = compare * 10;
		}
		sCompare = "" + compare;

		for (int i = 0; i < qtdeCasas; i++) {
			if (ret.length() < sCompare.length()) {
				ret = "0" + ret;
			}
			else
				break;
		}
		return ret;
	}

	public static String completeZerosSouble(double num, int qtdeZeros){
		String ret = new String();
		ret = "" + num;
		double compare = 0;

		for (int i = 0; i < qtdeZeros; i++) {
			if (compare == 0)
				compare = 10;
			else
				compare = compare * 10;
		}
		if (num < compare) {
			for (int i = 0; i < qtdeZeros; i++) {
				ret = "0" + ret;
			}
		}
		return ret;
	}

	public static double arredondar(double valor) {
		BigDecimal bd = new BigDecimal(valor);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return (bd.doubleValue());
	}

}
