package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.collections.ObservableList;

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
		boolean negative = false;
		String ret = new String();
		if (num < 0) {
			negative = true;
			num = num * -1;
		}
		ret = "" + num;
		double compare = 10;

		for (int i = 0; i < (qtdeCasas - 1); i++) {
			if (num < compare)
				ret = "0" + ret;
			compare = (compare * 10);
		}
		if (negative) {
			ret = "-" + ret;
		}
		return ret;
	}

	public static double arredondar(double valor) {
		if (Double.isFinite(valor) || Double.isNaN(valor) || Double.isInfinite(valor))
			return 0;
		BigDecimal bd = new BigDecimal(valor);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return (bd.doubleValue());
	}

	public static void addJogadorListaOrdenadamente(String jogador, ObservableList<String> l) {
		boolean added = false;
		if(l.size() == 0)
			l.add(jogador);
		else{
			for (int i = 0; i < l.size(); i++) {
				if (jogador.compareTo(l.get(i)) < 0) {
					l.add(i, jogador);
					added = true;
					break;
				}
			}
			if(!added)
				l.add(jogador);
		}
	}

}
