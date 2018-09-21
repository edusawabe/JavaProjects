/*
] * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import application.ConfigManager;
import util.Constants;
import util.DateUtil;

/**
 *
 * @author eduardo.sawabe
 */
public class Player {
    private String playerName;
    private String playerMail;
    private ArrayList<ResultadoRodada> resultados;
    private double pontuacaoTotal;
    private double pontuacaoTotalComDescarte;
    private int posicaoAtual;
    private boolean played;
    private Statistic statistic;

    public Player() {
    	resultados = new ArrayList<ResultadoRodada>();
	}

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param playerName the playerName to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return the playerMail
     */
    public String getPlayerMail() {
        return playerMail;
    }

    /**
     * @param playerMail the playerMail to set
     */
    public void setPlayerMail(String playerMail) {
        this.playerMail = playerMail;
    }

	public ArrayList<ResultadoRodada> getResultados() {
		return resultados;
	}

	public void setResultados(ArrayList<ResultadoRodada> resultados) {
		this.resultados = resultados;
	}

	public double getPontuacaoTotal() {
		return pontuacaoTotal;
	}

	public void setPontuacaoTotal(double pontuacaoTotal) {
		this.pontuacaoTotal = pontuacaoTotal;
	}

	public int getPosicaoAtual() {
		return posicaoAtual;
	}

	public void setPosicaoAtual(int posicaoAtual) {
		this.posicaoAtual = posicaoAtual;
	}

	public void updatePontuacaoTotal() {
		pontuacaoTotal = 0;
		LinkedList<Double> resultadosEtapas = new LinkedList<Double>();
		int mesEtapa = Integer.parseInt(DateUtil.getDate().substring(3, 5));
		boolean added;

		for (int i = 0; i < mesEtapa; i++) {
			added = false;
			if (resultadosEtapas.isEmpty()) {
				ConfigManager cfg = new ConfigManager();
				resultadosEtapas.add(cfg.getPontuacaoJogadorEtapa(resultados.get(i)));
				resultados.get(i).setPontuacaoEtapa(cfg.getPontuacaoJogadorEtapa(resultados.get(i)));
			} else {
				for (int j = 0; j < resultadosEtapas.size(); j++) {
					if (resultadosEtapas.get(j).compareTo(new Double(resultados.get(i).getPontuacaoEtapa())) >= 0) {
						resultadosEtapas.add(j, new Double(resultados.get(i).getPontuacaoEtapa()));
						added = true;
						break;
					}
				}
				if (!added)
					resultadosEtapas.add(new Double(resultados.get(i).getPontuacaoEtapa()));
			}
			pontuacaoTotal = pontuacaoTotal + resultados.get(i).getPontuacaoEtapa();
		}

		pontuacaoTotalComDescarte = pontuacaoTotal;
		if(mesEtapa > 2){
			pontuacaoTotalComDescarte = pontuacaoTotal - resultadosEtapas.get(0);
			pontuacaoTotalComDescarte = pontuacaoTotalComDescarte - resultadosEtapas.get(1);
		}
		pontuacaoTotal = Math.round(pontuacaoTotal);
		pontuacaoTotalComDescarte = Math.round(pontuacaoTotalComDescarte);
		statistic = new Statistic();
		statistic.generateStatistic(this);
	}

	public Resumo getResumo(){
		Resumo resumo = new Resumo();
		int rebuys = 0;
		double totalGasto = 0, totalGanho = 0, saldo = 0;

		for (int i = 0; i < resultados.size(); i++) {
			rebuys = rebuys + resultados.get(i).getRebuys();
			if ((!resultados.get(i).getColocacao().equals("00")) && (!resultados.get(i).getColocacao().equals("0")))
				totalGasto = totalGasto + Constants.SUBSCRIPTION_VALUE + Constants.BUY_IN_VALUE + (Constants.REBUY_VALUE * resultados.get(i).getRebuys());
			totalGanho = totalGanho + resultados.get(i).getPremiacao();
		}
		saldo  = totalGanho - totalGasto;
		resumo.setRebuys(rebuys);
		resumo.setSaldo(saldo);
		resumo.setTotalGanho(totalGanho);
		resumo.setTotalGasto(totalGasto);
		return resumo;
	}

	public boolean isPlayed() {
		return played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
	}

	public double getPontuacaoTotalComDescarte() {
		return pontuacaoTotalComDescarte;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}
}
