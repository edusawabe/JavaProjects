/*
] * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

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

		for (int i = 0; i < resultados.size(); i++) {
			pontuacaoTotal = pontuacaoTotal + resultados.get(i).getPontuacaoEtapa();
		}
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
}