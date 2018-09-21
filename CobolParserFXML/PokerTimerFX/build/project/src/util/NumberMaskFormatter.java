package util;

public final class NumberMaskFormatter {

	public static final String formatMask(int value) {
		String retAux = "" + value;
		String ret = "";
		int cont = 0;

		for (int i = (retAux.length()-1); i >= 0; i--) {
			cont++;
			if(cont == 3){
				ret = "." + retAux.charAt(i) + ret;
				cont = 0;
			} else {
				ret = retAux.charAt(i) + ret;
			}
		}
		if(ret.charAt(0) == '.')
			return ret.substring(1);
		else
			return ret;
	}
}
