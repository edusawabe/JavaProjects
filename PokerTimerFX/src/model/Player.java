/*
] * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author eduardo.sawabe
 */
public class Player {
    private String playerName;
    private String playerMail;
    private ArrayList<ResultadoRodada> resultados;
    private int pontuacaoTotal;
    private int posicaoAtual;

    public Player() {
    	resultados = new ArrayList<ResultadoRodada>();
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
    	resultados.add(new ResultadoRodada());
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

	public int getPontuacaoTotal() {
		return pontuacaoTotal;
	}

	public void setPontuacaoTotal(int pontuacaoTotal) {
		this.pontuacaoTotal = pontuacaoTotal;
	}

	public int getPosicaoAtual() {
		return posicaoAtual;
	}

	public void setPosicaoAtual(int posicaoAtual) {
		this.posicaoAtual = posicaoAtual;
	}
}
