package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import util.Constants;

public class Statistic {
	private double avgRebuy;
	private int maxRebuy;
	private int numMonthRebuy;
	private int numFinalTable;
	private int num1pos;
	private int num2pos;
	private int num3pos;
	private int num4pos;
	private int num5pos;
	private int numTop3;
	private int totalRebuy;
	private double totalPrize;
	private double totalCost;
	private double totalScore;
	private double maxScore;
	private double minScore;
	private double avgScore;
	private double min2Score;
	private int numFault;

	public double getAvgRebuy() {
		return avgRebuy;
	}

	public void setAvgRebuy(double avgRebuy) {
		this.avgRebuy = avgRebuy;
	}

	public int getMaxRebuy() {
		return maxRebuy;
	}

	public void setMaxRebuy(int maxRebuy) {
		this.maxRebuy = maxRebuy;
	}

	public int getNumMonthRebuy() {
		return numMonthRebuy;
	}

	public void setNumMonthRebuy(int numMonthRebuy) {
		this.numMonthRebuy = numMonthRebuy;
	}

	public int getNumFinalTable() {
		return numFinalTable;
	}

	public void setNumFinalTable(int numFinalTable) {
		this.numFinalTable = numFinalTable;
	}

	public int getNum1pos() {
		return num1pos;
	}

	public void setNum1pos(int num1pos) {
		this.num1pos = num1pos;
	}

	public int getNum2pos() {
		return num2pos;
	}

	public void setNum2pos(int num2pos) {
		this.num2pos = num2pos;
	}

	public int getNum3pos() {
		return num3pos;
	}

	public void setNum3pos(int num3pos) {
		this.num3pos = num3pos;
	}

	public int getNum4pos() {
		return num4pos;
	}

	public void setNum4pos(int num4pos) {
		this.num4pos = num4pos;
	}

	public int getNum5pos() {
		return num5pos;
	}

	public void setNum5pos(int num5pos) {
		this.num5pos = num5pos;
	}

	public int getNumTop3() {
		return numTop3;
	}

	public void setNumTop3(int numTop3) {
		this.numTop3 = numTop3;
	}

	public double getTotalPrize() {
		return totalPrize;
	}

	public void setTotalPrize(double totalPrize) {
		this.totalPrize = totalPrize;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(double maxScore) {
		this.maxScore = maxScore;
	}

	public double getMinScore() {
		return minScore;
	}

	public void setMinScore(double minScore) {
		this.minScore = minScore;
	}

	public double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
	}

	public double getMin2Score() {
		return min2Score;
	}

	public void setMin2Score(double min2Score) {
		this.min2Score = min2Score;
	}

	public int getNumFault() {
		return numFault;
	}

	public void setNumFault(int numFault) {
		this.numFault = numFault;
	}

	public String toString() {
		return "" + "";
	}

	public int getTotalRebuy() {
		return totalRebuy;
	}

	public void setTotalRebuy(int totalRebuy) {
		this.totalRebuy = totalRebuy;
	}


	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public void generateStatistic(Player p) {
		ArrayList<ResultadoRodada> l = p.getResultados();
		ResultadoRodada currentResult = null;
		Date date = new Date();
		SimpleDateFormat dataDia = new SimpleDateFormat("dd/MM/yyyy");
		int mesEtapa = 0;

		for (int i = 0; i < l.size(); i++) {
			currentResult = l.get(i);
			if(currentResult.getPontuacaoEtapa() > maxScore)
				maxScore = currentResult.getPontuacaoJogadorEtapa();
			if(currentResult.getRebuys() > maxRebuy)
				maxRebuy = currentResult.getRebuys();
			if(currentResult.getPontuacaoEtapa() < minScore)
				minScore = currentResult.getPontuacaoEtapa();
			totalRebuy += currentResult.getRebuys();
			totalPrize += currentResult.getPremiacao();
			if(!currentResult.getColocacao().equals("0"))
				totalCost += totalCost + 40 + (currentResult.getRebuys() * 30);
			else
				numFault++;
			totalScore += currentResult.getPontuacaoEtapa();

			if(currentResult.getRebuys() > 0)
				numMonthRebuy++;

			switch (Integer.parseInt(currentResult.getColocacao())) {
			case 1:
				numTop3++;
				num1pos++;
				numFinalTable++;
				break;
			case 2:
				numTop3++;
				num2pos++;
				break;
			case 3:
				numTop3++;
				num3pos++;
				break;
			case 4:
				num4pos++;
				break;
			case 5:
				num5pos++;
				break;
			case 6:
				numFinalTable++;
				break;
			case 7:
				numFinalTable++;
				break;
			case 8:
				numFinalTable++;
				break;
			case 9:
				numFinalTable++;
				break;
			default:
				break;
			}
		}
		mesEtapa = Integer.parseInt(dataDia.format(date).substring(3, 5));
		if(Constants.CURRENT_MONTH > 0)
			mesEtapa = Constants.CURRENT_MONTH;
		double dTotalRebuy = totalRebuy;
		double dNumFault = numFault;
		avgRebuy = dTotalRebuy/(mesEtapa - dNumFault);
		avgScore = totalScore/(mesEtapa - numFault);
	}
}
