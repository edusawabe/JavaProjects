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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import model.Etapa;
import model.JogadorConfigFile;
import model.Player;
import model.ResultadoRodada;

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

	public void readFile(ListView<String> jltJogando){
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
                    p.setPlayerName(j.getNome());
                    p.setPlayerMail(j.getEmail());
                    results = j.getResults();
                    for (int i = 0; i < results.length; i++) {
                    	p.getResultados().add(new ResultadoRodada(results[i]));
					}
                    listPlayer.add(p);
                    ((ObservableList<String>) jltJogando.getItems()).add(p.getPlayerName());
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

	public void addPlayer(String player){
		String complemento = "; ;0@0@0.00;0@0@0.00;0@0@0.00;0@0@0.00;0@0@0.00;0@0@0.00;0@0@0.00;0@0@0.00;0@0@0.00;0@0@0.00;0@0@0.00;0@0@0.00";
		File confgFile  = new File(configFileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Date date = new Date();
		String[] configBackup = configFileName.split("\\.t");
        BufferedReader reader;
        BufferedWriter writer;
        BufferedWriter writer2;
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
				if (cont == 2) {
					readLines = readLines + player + complemento + "\n" + line + "\n";
					cont++;
				}
				else
					readLines += line + "\n";
				line = reader.readLine();
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

	public void updatePlayersResult(ObservableList<String> oListFora, ObservableList<String> oListRebuys, double total1l
			, double total2l, double total3l, double total4l, double total5l){
		File confgFile  = new File(configFileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Date date = new Date();
		SimpleDateFormat dataDia = new SimpleDateFormat("dd/MM/yyyy");
		int mesEtapa = Integer.parseInt(dataDia.format(date).substring(5, 6));
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
				readLines += readLines + "\n";
				if (line.equals("#Jogadores")) {
					readLines += readLines + "\n";
					line = reader.readLine();
					while (line != null && (!line.equals("#Jogadores"))) {
						j.parseFileLine(line);
						Player p = new Player();
						p.setPlayerName(j.getNome());
						p.setPlayerMail(j.getEmail());
						results = j.getResults();
						for (int i = 0; i < results.length; i++) {
							ResultadoRodada r = new ResultadoRodada();
							r.getResultadoFromFileLine(results[i]);
						}
						line = reader.readLine();
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

	/*
	 * Pontos = A + B + C + D
			A: Pontos pela posição inversa:
				( 3 * qtde de jogadores ) – ( 3 * (posição-1) )
			B: Pontos pelo prêmio recebido
				( 0,6 * prêmio recebido em dinheiro )
			C: Cada um dos 8 jogadores da mesa final ganha 20 pontos
			D: Cada Rebuy realizado vale -15 pontos
	 */
	private double getPontuacaoJogadorEtapa(int qtdJogadores, int rebuys, int pos, double premio){
		double resultado = 0;
		resultado = ((3 * qtdJogadores) + (3 * (pos - 1)));
		if (pos > 9)
			resultado = resultado + 20.00;
		resultado = resultado + (premio * 0.6);
		resultado = resultado + (rebuys * (-15.00));
		return resultado;
	}

	public String getMailList(){
        String ret = new String();

        for (int i = 0; i < listPlayer.size()-1; i++) {
            Player get = listPlayer.get(i);
            if (!(get.getPlayerMail().isEmpty() && getMailList().equals(" ")))
                ret = ret + get.getPlayerMail() + ";";
        }

        Player get = listPlayer.get(listPlayer.size()-1);
        ret = ret + get.getPlayerMail();

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
