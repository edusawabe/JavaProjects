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
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
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
                    String[] lineSplit;
                    lineSplit = line.split(";");
                    Player p = new Player();
                    p.setPlayerMail(lineSplit[1]);
                    p.setPlayerName(lineSplit[0]);
                    LinkedList<ResultadoRodada> listResultado = new LinkedList<ResultadoRodada>();
                    ResultadoRodada r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12;
                    r1 = new ResultadoRodada();
                    r1.getResultadoFromFileLine(lineSplit[2]);
                    listResultado.add(r1);
                    r2 = new ResultadoRodada();
                    r2.getResultadoFromFileLine(lineSplit[3]);
                    listResultado.add(r2);
                    r3 = new ResultadoRodada();
                    r3.getResultadoFromFileLine(lineSplit[4]);
                    listResultado.add(r3);
                    r4 = new ResultadoRodada();
                    r4.getResultadoFromFileLine(lineSplit[5]);
                    listResultado.add(r4);
                    r5 = new ResultadoRodada();
                    r5.getResultadoFromFileLine(lineSplit[6]);
                    listResultado.add(r5);
                    r6 = new ResultadoRodada();
                    r6.getResultadoFromFileLine(lineSplit[7]);
                    listResultado.add(r6);
                    r7 = new ResultadoRodada();
                    r7.getResultadoFromFileLine(lineSplit[8]);
                    listResultado.add(r7);
                    r8 = new ResultadoRodada();
                    r8.getResultadoFromFileLine(lineSplit[9]);
                    listResultado.add(r8);
                    r9 = new ResultadoRodada();
                    r9.getResultadoFromFileLine(lineSplit[10]);
                    listResultado.add(r9);
                    r10 = new ResultadoRodada();
                    r10.getResultadoFromFileLine(lineSplit[11]);
                    listResultado.add(r10);
                    r11 = new ResultadoRodada();
                    r11.getResultadoFromFileLine(lineSplit[12]);
                    listResultado.add(r11);
                    r12 = new ResultadoRodada();
                    r12.getResultadoFromFileLine(lineSplit[13]);
                    listResultado.add(r12);
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
