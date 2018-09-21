/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;

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

	public void readFile(ListView<String> jltJogando, String fileName){
        File confgFile  = new File(fileName);
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
