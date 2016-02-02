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
import javafx.scene.paint.Color;
import model.Player;
import model.Round;
import util.Constants;
import util.Mp3Player;

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
	@FXML
	private Label lbTimer;

	private ConfigManager configManager;
	private ObservableList<String> oListJogadores = FXCollections.observableArrayList();
	private ObservableList<String> oListrRodadas = FXCollections.observableArrayList();

    private boolean paused;
    private boolean play;
    private int seconds;
    private int breakSeconds;
    private int minutes;
    private int breakMinutes;
    private int maxRound;
    private int currentRound;
    private int totalSeconds;
    private int currentSecond;
    private LinkedList<Round> roundList;
    private RoundManager roundManager;
    private Task timerTask;

	public PokerTimerFXController() {

	}


	@FXML
	private void addJogador(Event evt){
		//oListJogadores = listJogadores.getItems();
		oListJogadores.add(tfJogador.getText());
		listJogadores.setItems(oListJogadores);
	}

	@FXML
	private void removerJogador(Event evt){
		int i = listJogadores.getSelectionModel().getSelectedIndex();
		oListJogadores.remove(i);
		listJogadores.setItems(oListJogadores);
	}

	@FXML
	private void play(Event evt){
		play = true;
		timerTask = new Task();
		timerTask.run();
		lbTimer.setStyle("-fx-background-color: #dc143c");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//ConfigManager obtem a lista de jogadores cadastrados
		configManager = new ConfigManager();
		configManager.readFile(listJogadores, "./config.txt");
		LinkedList<Player> lp = configManager.getListPlayer();
		for (int i = 0; i < lp.size(); i++) {
			oListJogadores.add(lp.get(i).getPlayerName());
		}

		//Define a lista de rounds do Torneio
		roundList = new LinkedList<Round>();
		listJogadores.setItems(oListJogadores);
		roundManager = new RoundManager();
		roundManager.setRoundList(roundList);
		roundManager.setRoundListValues();
		for (int i = 0; i < roundList.size(); i++) {
			oListrRodadas.add(roundList.get(i).getRoundName());
		}
		currentRound = 0;
		listRodadas.setItems(oListrRodadas);
		listRodadas.getSelectionModel().select(currentRound);

		//Desabilita edição e seleção da lista de rodadas
		listRodadas.setEditable(false);
		listRodadas.setMouseTransparent(true);
		listRodadas.setFocusTraversable(false);


	}

    public void setRound(){
        if (roundList.get(currentRound).getBigBlind() != 0)
            bigAtual.setText("" + roundList.get(currentRound).getBigBlind());
        else
        	bigAtual.setText("");

        if (roundList.get(currentRound).getSmallBlind()!= 0)
            smallAtual.setText("" + roundList.get(currentRound).getSmallBlind());
        else
        	smallAtual.setText("");

        if (roundList.get(currentRound).getAnte()!= 0){
            lbAnteAtual.setText("Ante:");
            valorAnteAtual.setText("" + roundList.get(currentRound).getAnte());
        }
        else
            if (roundList.get(currentRound).getBigBlind() != 0)
            	lbAnteAtual.setText("  ");
            else
            	valorAnteAtual.setText("BREAK");

        if (currentRound < Constants.MAX_ROUNDS){
            if (roundList.get(currentRound+1).getBigBlind() != 0)
                bigSeguinte.setText("" + roundList.get(currentRound+1).getBigBlind());
            else
            	bigSeguinte.setText("");

            if (roundList.get(currentRound+1).getSmallBlind()!= 0)
                smallSeguinte.setText("" + roundList.get(currentRound+1).getSmallBlind());
            else
                smallSeguinte.setText("");

            if (roundList.get(currentRound+1).getAnte()!= 0){
                lbAnteSeguinte.setText("Ante:");
                valorAnteSeguinte.setText("" + roundList.get(currentRound+1).getAnte());
            }
            else
                if (roundList.get(currentRound+1).getBigBlind() != 0)
                	lbAnteSeguinte.setText("  ");
                else
                	valorAnteAtual.setText("BREAK");
        }
        else{
        	bigSeguinte.setText("");
        	smallSeguinte.setText("");
        	lbAnteSeguinte.setText("BREAK");
        }
    }

    public void restartTimer() {
        seconds = 0;
        currentSecond = 0;
        breakSeconds = 0;

        if (roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).isBreakRound()) {
            minutes = Constants.MAX_MINUTES_BREAK;
        } else {
            minutes = Constants.MAX_MINUTES;
        }
        jProgressBar1.setMaximum(Constants.SECONDS_IN_MINUTE * minutes);

        int resto  = ((listRodadas.getSelectionModel().getSelectedIndex()+1) % 5);

        if (resto == 0){
            breakMinutes = Constants.MAX_MINUTES * 4 + Constants.MAX_MINUTES_BREAK;
            breakSeconds = 0;
        }
        else{
            int qtdeRounds = 0;
            qtdeRounds = 5 - resto;
            breakMinutes = Constants.MAX_MINUTES * qtdeRounds;
            breakSeconds = 0;
        }

        if (!play) {
            timerTask = new Task();
            timerTask.addPropertyChangeListener(this);
            timerTask.execute();
        }
    }

	private class Task extends Thread {
        /*
         * Main task. Executed in background thread.
         */

        @Override
        public void run() {
            while (play) {
                if (seconds == 17 && minutes == 0){
                    playCountdown();
                }
                if (seconds == 2 && minutes == 0){
                    playFinish();
                }
                if (seconds == 0) {
                    if (minutes > 0) {
                        minutes--;
                        seconds = 59;
                        currentSecond = currentSecond + 1;
                    } else {
                        currentRound++;
                        listRodadas.getSelectionModel().select(currentRound);
                        //timerBar.setCs(Color.WHITE);
                        //timerBar.setForeground(Color.BLACK);
                        if (roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).isBreakRound()) {
                            minutes = Constants.MAX_MINUTES_BREAK;
                            seconds = 0;
                        } else {
                            minutes = Constants.MAX_MINUTES;
                            seconds = 0;
                        }
                        //setRound();
                        currentSecond = 0;
                    }
                } else {
                    seconds--;
                    currentSecond = currentSecond + 1;
                    if (seconds < 31){
                        timerBar.toString();
                    	//timerBar.setBackground(Color.white);
                        //timerBar.setForeground(Color.RED);
                    }
                }
                if (minutes < 10){
                    if (seconds < 10) {
                        lbTimer.setText("0" + minutes + ":0" + seconds);
                    } else {
                    	lbTimer.setText("0" + minutes + ":" + seconds);
                    }
                }
                else{
                    if (seconds < 10) {
                    	lbTimer.setText(minutes + ":0" + seconds);
                    } else {
                    	lbTimer.setText(minutes + ":" + seconds);
                    }
                }
                timerBar.setProgress(currentSecond);

                //setBreakTime();
                //atualizarEstatisticas();

                //Sleep for up to one second.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                }
            }

            while (paused) {
                if (seconds < 10) {
                	lbTimer.setText(minutes + ":0" + seconds);
                } else {
                	lbTimer.setText(minutes + ":" + seconds);
                }
                timerBar.setProgress(currentSecond);
                //Sleep for up to one second.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                }
            }
        }
	}

    public void playCountdown() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("sounds/gate.wav"));
        player.start();
    }

    public void playFinish() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("sounds/emergency003.wav"));
        player.start();
    }
}
