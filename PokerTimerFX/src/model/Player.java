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

/**
 *
 * @author eduardo.sawabe
 */
public class Player {
    private String playerName;
    private String playerMail;
    private ArrayList<ResultadoRodada> resultados;
    private double pontuacaoTotal;
    private int posicaoAtual;
    private boolean played;

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
		Date date = new Date();
		SimpleDateFormat dataDia = new SimpleDateFormat("dd/MM/yyyy");
		int mesEtapa = Integer.parseInt(dataDia.format(date).substring(3, 5));
		boolean added;

		for (int i = 0; i < resultados.size(); i++) {
			added = false;
			if (resultadosEtapas.isEmpty()) {
				ConfigManager cfg = new ConfigManager();
				resultadosEtapas.add(cfg.getPontuacaoJogadorEtapa(resultados.get(i)));
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

		if (mesEtapa >= 4) {
			pontuacaoTotal = pontuacaoTotal - resultadosEtapas.get(0);
			pontuacaoTotal = pontuacaoTotal - resultadosEtapas.get(1);
		} else {
			if (mesEtapa == 3) {
				pontuacaoTotal = pontuacaoTotal - resultadosEtapas.get(0);
			}
		}
		pontuacaoTotal = Math.round(pontuacaoTotal);
	}

	public Resumo getResumo(){
		Resumo resumo = new Resumo();
		int rebuys = 0;
		double totalGasto = 0, totalGanho = 0, saldo = 0;

		for (int i = 0; i < resultados.size(); i++) {
			rebuys = rebuys + resultados.get(i).getRebuys();
			if ((!resultados.get(i).getColocacao().equals("00")) && (!resultados.get(i).getColocacao().equals("0")))
				totalGasto = totalGasto + 15 + 30 + (30 * resultados.get(i).getRebuys());
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
}
