package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class PokerTimerFXController implements Initializable{
	@FXML
	private Label bigAtual;
	@FXML
	private Label smallAtual;
	@FXML
	private Label lbAnteAtual;
	@FXML
	private Label valorAnteAtual;
	@FXML
	private Label bigSeguinte;
	@FXML
	private Label smallSeguinte;
	@FXML
	private Label lbAnteSeguinte;
	@FXML
	private Label valorAnteSeguinte;
	@FXML
	private ProgressBar timerBar;
	@FXML
	private Button btAnterior;
	@FXML
	private Button btPause;
	@FXML
	private Button btPlay;
	@FXML
	private Button btSeguinte;
	@FXML
	private Label proximoBreak;
	@FXML
	private Label statsJogando;
	@FXML
	private Label statsRebuys;
	@FXML
	private Label statsFora;
	@FXML
	private Label statsTotalArrecadado;
	@FXML
	private Label statsPremio1;
	@FXML
	private Label statsPremio2;
	@FXML
	private Label statsPremio3;
	@FXML
	private Label statsPremio4;
	@FXML
	private Label statsPremio5;
	@FXML
	private ListView<String> listRodadas;
	@FXML
	private ListView<String> listJogadores;
	@FXML
	private ListView<String> listRebuys;
	@FXML
	private ListView<String> listFora;
	@FXML
	private Button btAdicionaJogador;
	@FXML
	private Button btExcluirJogador;
	@FXML
	private Button btAdicionaRebuy;
	@FXML
	private Button btExcluirRebuy;
	@FXML
	private Button btAdicionaFora;
	@FXML
	private Button btExcluirFora;
	@FXML
	private Button btEnviarResultados;
	@FXML
	private Button btRanking;
	@FXML
	private TextField tfJogador;

	private ConfigManager configManager;
	private ObservableList<String> oListJogadores = FXCollections.observableArrayList();

	public PokerTimerFXController() {
/*		listJogadores = new ListView<String>();
		configManager = new ConfigManager();
		configManager.readFile(listJogadores, "./config.txt");
		LinkedList<Player> lp = configManager.getListPlayer();
		for (int i = 0; i < lp.size(); i++) {
			oListJogadores.add(lp.get(i).getPlayerName());
		}
		listJogadores.setItems(oListJogadores);*/
	}


	@FXML
	private void addJogador(Event evt){
		//oListJogadores = listJogadores.getItems();
		oListJogadores.add(tfJogador.getText());
		listJogadores.setItems(oListJogadores);
	}

	@FXML
	private void removerJogador(Event evt){
		//oListJogadores = listJogadores.getItems();
		int i = listJogadores.getSelectionModel().getSelectedIndex();
		oListJogadores.remove(i);
		listJogadores.setItems(oListJogadores);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//listJogadores = new ListView<String>();
		configManager = new ConfigManager();
		configManager.readFile(listJogadores, "./config.txt");
		LinkedList<Player> lp = configManager.getListPlayer();
		for (int i = 0; i < lp.size(); i++) {
			oListJogadores.add(lp.get(i).getPlayerName());
		}
		listJogadores.setItems(oListJogadores);
	}
}
