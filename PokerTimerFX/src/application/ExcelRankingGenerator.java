package application;

import java.util.LinkedList;

import model.Player;
import util.ExcelManager;

public class ExcelRankingGenerator {
	private LinkedList<Player> lPlayer;
	private ExcelManager excelManager;

	public ExcelRankingGenerator(){
		excelManager = new ExcelManager();
	}

	public void generateExcel() {
		double pontuacao;
		excelManager.setFileName("./RankingPontuação.xlsx");
		excelManager.getlConlumns().clear();
		excelManager.getlRowValue().clear();

		excelManager.getlConlumns().add("Jogador");
		excelManager.getlConlumns().add("Jan");
		excelManager.getlConlumns().add("Fev");
		excelManager.getlConlumns().add("Mar");
		excelManager.getlConlumns().add("Abr");
		excelManager.getlConlumns().add("Mai");
		excelManager.getlConlumns().add("Jun");
		excelManager.getlConlumns().add("Jul");
		excelManager.getlConlumns().add("Ago");
		excelManager.getlConlumns().add("Set");
		excelManager.getlConlumns().add("Out");
		excelManager.getlConlumns().add("Nov");
		excelManager.getlConlumns().add("Dez");

		for (int i = 0; i < lPlayer.size(); i++) {
			excelManager.getlRowValue().add(new LinkedList<String>());
			excelManager.getlRowValue().getLast().add(lPlayer.get(i).getPlayerName());
			pontuacao = 0;

			for (int j = 0; j < lPlayer.get(i).getResultados().size(); j++) {
				pontuacao = pontuacao + Double.parseDouble("" + lPlayer.get(i).getResultados().get(j).getPontuacaoEtapa());
				excelManager.getlRowValue().getLast().add("" + pontuacao);
			}
		}
		excelManager.generateExcelFile();
	}

	public LinkedList<Player> getlPlayer() {
		return lPlayer;
	}

	public void setlPlayer(LinkedList<Player> lPlayer) {
		this.lPlayer = lPlayer;
	}



}
