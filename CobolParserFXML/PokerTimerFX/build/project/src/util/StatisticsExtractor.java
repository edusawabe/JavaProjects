package util;

import java.util.ArrayList;
import java.util.LinkedList;

import model.Player;
import model.ResultadoRodada;
import model.Statistic;

public class StatisticsExtractor {
	private LinkedList<Player> lPlayers;

	public LinkedList<Player> getlPlayers() {
		return lPlayers;
	}

	public void setlPlayers(LinkedList<Player> lPlayers) {
		this.lPlayers = lPlayers;
	}

	public void updateStatistics(){
		ArrayList<ResultadoRodada> alRodada;
		for (int i = 0; i < lPlayers.size(); i++) {
			lPlayers.get(i).updatePontuacaoTotal();
			alRodada = lPlayers.get(i).getResultados();
			Statistic s = new Statistic();
			for (int j = 0; j < alRodada.size(); j++) {
				s.setAvgRebuy(s.getAvgRebuy()  + alRodada.get(j).getRebuys());
				s.setAvgScore(s.getAvgScore()  + alRodada.get(j).getPontuacaoJogadorEtapa());
			}
		}
	}

	public void print() {
		for (int i = 0; i < lPlayers.size(); i++) {
			System.out.println(lPlayers.get(i).getStatistic());
		}
	}
}
