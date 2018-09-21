package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Util {

	public static String completeZeros(int num, int qtdeCasas){
		String ret = new String();
		ret = "" + num;
		int compare = 10;

		for (int i = 0; i < (qtdeCasas - 1); i++) {
			if (num < compare)
				ret = "0" + ret;
			compare = (compare * 10);
		}
		return ret;
	}

	public static String completeZerosDouble(double num, int qtdeCasas){
		String ret = new String();
		if (num < 0) {
			return ret = "" + num;
		}
		ret = "" + num;
		double compare = 10;

		for (int i = 0; i < (qtdeCasas - 1); i++) {
			if (num < compare)
				ret = "0" + ret;
			compare = (compare * 10);
		}
		return ret;
	}

	public static double arredondar(double valor) {
		BigDecimal bd = new BigDecimal(valor);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return (bd.doubleValue());
	}

}
