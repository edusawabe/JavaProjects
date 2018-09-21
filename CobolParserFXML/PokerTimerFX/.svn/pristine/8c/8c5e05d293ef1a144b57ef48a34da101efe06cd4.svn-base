/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import model.JogadorConfigFile;
import model.Player;
import model.ProjecaoLine;
import model.ResultadoRodada;
import util.Constants;
import util.Util;

/**
 *
 * @author eduardo.sawabe
 */
public class ConfigManager {
    private String configFileName;
    private LinkedList<Player> listPlayer;

    public LinkedList<Player> getListPlayer() {
		return listPlayer;
	}

	public void setListPlayer(LinkedList<Player> listPlayer) {
		this.listPlayer = listPlayer;
	}

	//public void readFile(ListView<String> jltJogando){
	public void readFile(){
		File confgFile  = new File(configFileName);
        BufferedReader reader;
        String[] results;
        listPlayer = new LinkedList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(confgFile),"Cp1252"));
            String line = reader.readLine();
            while (line != null) {
                if (line.equals("#Jogadores"))
                    line = reader.readLine();
                if (line == null)
                    break;
                while (!line.contains("#Jogadores")){
                    JogadorConfigFile j = new JogadorConfigFile();
                    j.parseFileLine(line);
                    Player p = new Player();
                    p.setPlayed(false);
                    p.setPlayerName(j.getNome());
                    p.setPlayerMail(j.getEmail());
                    results = j.getResults();
                    for (int i = 0; i < results.length; i++) {
                    	p.getResultados().add(new ResultadoRodada(results[i]));
                    	if(!(p.getResultados().get(i).getColocacao().equals("00")) && !(p.getResultados().get(i).getColocacao().equals("0")))
                    			p.setPlayed(true);
					}
                    listPlayer.add(p);
                    //((ObservableList<String>) jltJogando.getItems()).add(p.getPlayerName());
                    line = reader.readLine();
                    if (line == null)
                        break;
                }
                reader.close();
                return;
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

	public void getPlayers(){
        File confgFile  = new File(configFileName);
        BufferedReader reader;
        String[] results;
        listPlayer = new LinkedList<Player>();
        int[] lQtdeJogadoresRodada = new int[12];

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(confgFile),"Cp1252"));
            String line = reader.readLine();
            while (line != null) {
                if (line.equals("#Jogadores"))
                    line = reader.readLine();
                if (line == null)
                    break;
                while (!line.contains("#Jogadores")){
                    JogadorConfigFile j = new JogadorConfigFile();
                    j.parseFileLine(line);
                    Player p = new Player();
                    p.setPlayerName(j.getNome());
                    p.setPlayerMail(j.getEmail());
                    results = j.getResults();
                    for (int i = 0; i < results.length; i++) {
                    	p.getResultados().add(new ResultadoRodada(results[i]));
                    	if(!(p.getResultados().get(i).getColocacao().equals("0") || p.getResultados().get(i).getColocacao().equals("00")))
                    		lQtdeJogadoresRodada[i]++;
					}
                    listPlayer.add(p);
                    line = reader.readLine();
                    if (line == null)
                        break;
                }
                reader.close();
                for (int i = 0; i < listPlayer.size(); i++) {
                	for (int j = 0; j < lQtdeJogadoresRodada.length; j++) {
                		listPlayer.get(i).getResultados().get(j).setQtdeJogadores(lQtdeJogadoresRodada[j]);
					}
                	listPlayer.get(i).updatePontuacaoTotal();
				}
                return;
            }
            reader.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	public void addPlayer(String player){
		String complemento = "; ;0@0@0.00@0.00;0@0@0.00@0.00;0@0@0.00@0.00;0@0@0.00@0.00;0@0@0.00@0.00;0@0@0.00@0.00;0@0@0.00@0.00;0@0@0.00@0.00;0@0@0.00@0.00;0@0@0.00@0.00;0@0@0.00@0.00;0@0@0.00@0.00;";
		File confgFile  = new File(configFileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Date date = new Date();
		String[] configBackup = configFileName.split("\\.t");
        BufferedReader reader;
        BufferedWriter writer;
        BufferedWriter writer2;
        JogadorConfigFile j = new JogadorConfigFile();
        boolean newPlayer = true;
        int cont = 0;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(confgFile),"Cp1252"));
            String line = reader.readLine();
            String readLines = new String();
            String backupLine = new String();
            while (line != null) {
				backupLine += line + "\n";
				if (line.equals("#Jogadores"))
					cont++;
				else
					j.parseFileLine(line);

				if (cont == 2) {
					if(newPlayer)
						readLines = readLines + player + complemento + "\n" + line + "\n";
					cont++;
				} else {
					if(j.getNome().equals(player))
						newPlayer = false;
					readLines += line + "\n";
				}
				line = reader.readLine();
			}
            reader.close();
			if (newPlayer) {
				writer = new BufferedWriter(new FileWriter(confgFile));
				writer2 = new BufferedWriter(new FileWriter(configBackup[0] + dateFormat.format(date) + ".txt"));
				writer.write(readLines);
				writer.flush();
				writer.close();
				writer2.write(backupLine);
				writer2.flush();
				writer2.close();
			}
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	public void updatePlayersResult(ObservableList<String> oListFora, ObservableList<String> oListRebuys, double total1l
			, double total2l, double total3l, double total4l, double total5l){
		File confgFile  = new File(configFileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Date date = new Date();
		SimpleDateFormat dataDia = new SimpleDateFormat("dd/MM/yyyy");
		int mesEtapa = Integer.parseInt(dataDia.format(date).substring(3, 5));
		if(Constants.CURRENT_MONTH > 0)
			mesEtapa = Constants.CURRENT_MONTH;
		String[] configBackup = configFileName.split("\\.t");
        BufferedReader reader;
        BufferedWriter writer;
        BufferedWriter writer2;
        int cont = 0;
        LinkedList<Player> lPlayer = new LinkedList<Player>();
        JogadorConfigFile j = new JogadorConfigFile();
        String[] results;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(confgFile),"Cp1252"));
            String line = reader.readLine();
            String readLines = new String();
            String backupLine = new String();
            while (line != null) {
				backupLine += line + "\n";
				readLines = readLines + line + "\n";
				if(cont > 1){
					line = reader.readLine();
					if (line == null)
						break;
				}
				if (line.equals("#Jogadores")) {
					cont++;
					line = reader.readLine();
					while (line != null && (!line.equals("#Jogadores")) && cont < 2) {
						j.parseFileLine(line);
						Player p = new Player();
						p.setPlayerName(j.getNome());
						p.setPlayerMail(j.getEmail());
						results = j.getResults();
						int rebuys = 0, pos = 0;
						for (int i = 0; i < oListRebuys.size(); i++) {
							if(oListRebuys.get(i).equals(p.getPlayerName()))
								rebuys++;
						}
						for (int i = 0; i < oListFora.size(); i++) {
							if(oListFora.get(i).equals(p.getPlayerName())){
								pos = oListFora.size() - i;
								break;
							}
						}
						for (int i = 0; i < results.length; i++) {
							ResultadoRodada r = new ResultadoRodada();
							r.getResultadoFromFileLine(results[i]);
							if ((i+1) == mesEtapa){
								r.setColocacao(Util.completeZeros(pos, 1));
								r.setRebuys(rebuys);
								switch (pos) {
								case 1:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos, total1l));
									r.setPremiacao(total1l);
									break;
								case 2:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos, total2l));
									r.setPremiacao(total2l);
									break;
								case 3:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos, total3l));
									r.setPremiacao(total3l);
									break;
								case 4:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos, total4l));
									r.setPremiacao(total4l);
									break;
								case 5:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos, total5l));
									r.setPremiacao(total5l);
									break;
								default:
									r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(oListFora.size(), rebuys, pos, 0.00));
									r.setPremiacao(0.00);
									break;
								}
								results[mesEtapa -1] = r.getResultLine();
							}
							else
								if ((i+1) >  mesEtapa)
									break;
						}
						readLines = readLines + j.generateFileLine() + "\n";
						line = reader.readLine();
						if(!line.equals("#Jogadores"))
							backupLine += line + "\n";
					}
				}
			}
            reader.close();

            writer = new BufferedWriter(new FileWriter(confgFile));
            writer2 = new BufferedWriter(new FileWriter(configBackup[0] + dateFormat.format(date) + ".txt"));
            writer.write(readLines);
            writer.flush();
            writer.close();
            writer2.write(backupLine);
            writer2.flush();
            writer2.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	public ObservableList<ProjecaoLine> projetarResultado(ObservableList<String> oListRebuys, ObservableList<String> oListFora, int totalJogadores
			, double total1l, double total2l, double total3l, double total4l, double total5l){

		ObservableList<ProjecaoLine> projecaoList = FXCollections.observableArrayList();
		ObservableList<ProjecaoLine> projecaoListOrdered = FXCollections.observableArrayList();
		File confgFile  = new File(configFileName);
		Date date = new Date();
		SimpleDateFormat dataDia = new SimpleDateFormat("dd/MM/yyyy");
		int mesEtapa = Integer.parseInt(dataDia.format(date).substring(6, 7));
		if(Constants.CURRENT_MONTH > 0)
			mesEtapa = Constants.CURRENT_MONTH;
        BufferedReader reader;
        int cont = 0;
        int qtdeJogadoresTotal = 0;
        int posAtual = 0;
        LinkedList<Player> lPlayer = new LinkedList<Player>();
        JogadorConfigFile j = new JogadorConfigFile();
        String[] results;
		double soma = 0.0;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(confgFile),"Cp1252"));
            String line = reader.readLine();
            String readLines = new String();
            String backupLine = new String();
            int m = 0;
            double projecao = 0;
            while (line != null) {
				backupLine += line + "\n";
				readLines = readLines + line + "\n";
				if(cont > 1){
					line = reader.readLine();
					if (line == null)
						break;
				}
				if (line.equals("#Jogadores")) {
					cont++;
					line = reader.readLine();
					while (line != null && (!line.equals("#Jogadores")) && cont < 2) {
						posAtual = 0;
						m = 0;
						j.parseFileLine(line);
						Player p = new Player();
						p.setPlayerName(j.getNome());
						p.setPlayerMail(j.getEmail());
						results = j.getResults();
						ArrayList<ResultadoRodada> aResults = new ArrayList<ResultadoRodada>();;
						for (int i = 0; i < oListFora.size(); i++) {
							if(oListFora.get(i).equals(p.getPlayerName())){
								posAtual = totalJogadores - m;
								break;
							}
							m++;
						}
						for (int i = 0; i < results.length; i++) {
							aResults.add(new ResultadoRodada());
							aResults.get(i).getResultadoFromFileLine(results[i]);
							if (!aResults.get(i).getColocacao().equals("0") && !aResults.get(i).getColocacao().equals("00"))
								qtdeJogadoresTotal++;
						}
						p.setResultados(aResults);
						int rebuys = 0;
						for (int i = 0; i < oListRebuys.size(); i++) {
							if(oListRebuys.get(i).equals(p.getPlayerName()))
								rebuys++;
						}

						for (int i = 0; i < results.length; i++) {
							ResultadoRodada r = new ResultadoRodada();
							ProjecaoLine pl = new ProjecaoLine();
							pl.setJogador(j.getNome());
							p.updatePontuacaoTotal();
							pl.setAtual(Util.completeZerosDouble(p.getPontuacaoTotal(), 3));
							r.getResultadoFromFileLine(results[i]);
							if ((i+1) == mesEtapa){
								for (int k = 0; k < 5; k++) {
									r.setColocacao(Util.completeZeros(k+1, 1));
									r.setRebuys(rebuys);
									switch (k+1) {
									case 1:
										r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(totalJogadores, rebuys, k+1, total1l));
										r.setPremiacao(total1l);
										projecao =  Util.arredondar(r.getPontuacaoEtapa() + p.getPontuacaoTotal());
										pl.setProjecao1(Util.completeZerosDouble(projecao, 3));
										break;
									case 2:
										r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(totalJogadores, rebuys, k+1, total2l));
										r.setPremiacao(total2l);
										projecao =  Util.arredondar(r.getPontuacaoEtapa() + p.getPontuacaoTotal());
										pl.setProjecao2(Util.completeZerosDouble(projecao, 3));
										break;
									case 3:
										r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(totalJogadores, rebuys, k+1, total3l));
										r.setPremiacao(total3l);
										projecao =  Util.arredondar(r.getPontuacaoEtapa() + p.getPontuacaoTotal());
										pl.setProjecao3(Util.completeZerosDouble(projecao, 3));
										break;
									case 4:
										r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(totalJogadores, rebuys, k+1, total4l));
										r.setPremiacao(total4l);
										projecao =  Util.arredondar(r.getPontuacaoEtapa() + p.getPontuacaoTotal());
										pl.setProjecao4(Util.completeZerosDouble(projecao, 3));
										break;
									case 5:
										r.setPontuacaoEtapa(getPontuacaoJogadorEtapa(totalJogadores, rebuys, k+1, total5l));
										r.setPremiacao(total5l);
										projecao =  Util.arredondar(r.getPontuacaoEtapa() + p.getPontuacaoTotal());
										pl.setProjecao5(Util.completeZerosDouble(projecao, 3));
										break;
									}

									switch (posAtual) {
									case 1:
										pl.setNestaRodada(Util.completeZeros(posAtual, 2) + "º" + " / " + getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, total1l));
										soma = getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, total1l);
										break;
									case 2:
										pl.setNestaRodada(Util.completeZeros(posAtual, 2) + "º" + " / " + getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, total2l));
										soma = getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, total2l);
										break;
									case 3:
										pl.setNestaRodada(Util.completeZeros(posAtual, 2) + "º" + " / " + getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, total3l));
										soma = getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, total3l);
										break;
									case 4:
										pl.setNestaRodada(Util.completeZeros(posAtual, 2) + "º" + " / " + getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, total4l));
										soma = getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, total4l);
										break;
									case 5:
										pl.setNestaRodada(Util.completeZeros(posAtual, 2) + "º" + " / " + getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, total5l));
										soma = getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, total5l);
										break;
									default:
										pl.setNestaRodada(Util.completeZeros(posAtual, 2) + "º" + " / " + getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, 0));
										soma = getPontuacaoJogadorEtapa(totalJogadores, rebuys, posAtual, 0);
										break;
									}
									results[mesEtapa -1] = r.getResultLine();
								}
								soma = soma + p.getPontuacaoTotal();
								pl.setPosRodada(""+Util.completeZerosDouble(Util.arredondar(soma),3));
								projecaoList.add(pl);
							}
							else
								if ((i+1) >  mesEtapa)
									break;
						}
						readLines = readLines + j.generateFileLine() + "\n";
						line = reader.readLine();
						//if(!line.equals("#Jogadores"))
						//	backupLine += line + "\n";
					}
				}
			}
            Double itemOrdenado;
            Double itemLista;

			for (int l = 0; l < projecaoList.size(); l++) {
				if (l == 0) {
					projecaoListOrdered.add(projecaoList.get(l));
				}
				else {
					itemLista = new Double(projecaoList.get(l).getAtual());
					for (int l2 = 0; l2 < projecaoListOrdered.size(); l2++) {
						itemOrdenado = new Double(projecaoListOrdered.get(l2).getAtual());
						if (itemOrdenado.compareTo(itemLista) <= 0) {
							projecaoListOrdered.add(l2, projecaoList.get(l));
							break;
						} else {
							if (Double.parseDouble(projecaoListOrdered.get(l2).getAtual()) == 0
									&& Double.parseDouble(projecaoList.get(l).getAtual()) < 0) {
								projecaoListOrdered.add(l2, projecaoList.get(l));
								break;
							}
							if(itemLista.compareTo(new Double("0")) == 0) {
								projecaoListOrdered.add(projecaoList.get(l));
								break;
							}
							if (itemOrdenado.compareTo(itemLista) > 0
									&& l2 == (projecaoListOrdered.size() - 1)) {
								projecaoListOrdered.add(projecaoList.get(l));
								break;
							}
						}
					}
				}
			}
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int k = 0; k < projecaoListOrdered.size(); k++) {
        	int h = k+1;
        	if(h < 10)
        		projecaoListOrdered.get(k).setJogador("0" + h + "º - " + projecaoListOrdered.get(k).getJogador());
        	else
        		projecaoListOrdered.get(k).setJogador(h + "º - " + projecaoListOrdered.get(k).getJogador());
		}

        return projecaoListOrdered;
	}

	/*
	 * Pontos = A + B + C + D
			A: Pontos pela posição inversa:
				( 3 * qtde de jogadores ) – ( 3 * (posição-1) )
			B: Pontos pelo prêmio recebido
				( 0,6 * prêmio recebido em dinheiro )
			C: Cada um dos 8 jogadores da mesa final ganha 20 pontos
			D: Cada Rebuy realizado vale -15 pontos
	 */
	public double getPontuacaoJogadorEtapa(int qtdJogadores, int rebuys, int pos, double premio){
		double resultado = 0;
		if (pos > 0){
			resultado = ((3 * qtdJogadores) - (3 * (pos - 1)));
			if (pos <= Constants.MAX_PLAYERS_FINAL_TABLE)
				resultado = resultado + 20.00;
			resultado = resultado + (premio * 0.6);
			resultado = resultado + (rebuys * (-15.00));
		}
		return resultado;
	}

	public String getMailList(){
        String ret = new String();
        for (int i = 0; i < listPlayer.size(); i++) {
            Player get = listPlayer.get(i);
            if (!(get.getPlayerMail().isEmpty()))
            	if (!(get.getPlayerMail().equals(" ")))
            		ret = ret + get.getPlayerMail() + ";";
        }
        ret = ret.substring(0, ret.length()-1);
        return ret;
    }
    /**
     * @return the configFileName
     */
    public String getConfigFileName() {
        return configFileName;
    }

    /**
     * @param configFileName the configFileName to set
     */
    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }
}
