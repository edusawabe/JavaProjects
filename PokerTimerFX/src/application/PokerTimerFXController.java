package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Player;
import model.ProjecaoLine;
import model.RankingLine;
import model.Resumo;
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
	private Label lbProximoBreak;
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
	private Label statsMedia;
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
	private ObservableList<String> oListRebuys = FXCollections.observableArrayList();
	private ObservableList<String> oListFora = FXCollections.observableArrayList();
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
    private Timeline timeLine;
    private double total1l = 0;
    private double total2l = 0;
    private double total3l = 0;
    private double total4l = 0;
    private double total5l = 0;
	public PokerTimerFXController() {

	}
	@FXML
	private void abrirProjecao(Event evt){
		configManager.getPlayers();
		LinkedList<Player> lPlayer = configManager.getListPlayer();
    	Stage primaryStage = new Stage();
    	ObservableList<ProjecaoLine> lprojecaoLine = FXCollections.observableArrayList();
    	ProjecaoController projecaoController = new ProjecaoController();

    	//obtem Loader
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Projecao.fxml"));
		try {
			//carrega o loader
			Pane myPane = (Pane) fxmlLoader.load();

			// definindo a nova janela
			Scene scene = new Scene(myPane, 900, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Projeções");

			lprojecaoLine = configManager.projetarResultado(oListRebuys, oListFora, oListFora.size() + oListJogadores.size(), total1l, total2l, total3l, total4l, total5l);

			// obtem o controller da nova janela
			projecaoController = fxmlLoader.<ProjecaoController> getController();

			// inclui as informações do texto a abre a janela nova
			projecaoController.gettProjecao().setItems(lprojecaoLine);

			projecaoController.setListProjecaoLines(lprojecaoLine);

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	private void enviarResultados(Event evt){
		configManager.updatePlayersResult(oListFora, oListRebuys, total1l, total2l, total3l, total4l, total5l);
	}

	@FXML
	private void loadRanking(Event evt){
		configManager.getPlayers();
		LinkedList<Player> lPlayer = configManager.getListPlayer();
		LinkedList<Player> lOrderedPlayer = new LinkedList<Player>();
    	Stage primaryStage = new Stage();
    	ObservableList<RankingLine> lRanking = FXCollections.observableArrayList();
    	RankingController rankingController;
    	Resumo resumo = new Resumo();

    	//obtem Loader
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ranking.fxml"));
		try {
			//carrega o loader
			Pane myPane = (Pane) fxmlLoader.load();

			//prepara os dados do jogador e rodadas
			for (int i = 0; i < lPlayer.size(); i++) {
				Player p = lPlayer.get(i);
				p.updatePontuacaoTotal();
				if(lOrderedPlayer.isEmpty())
					lOrderedPlayer.add(p);
				else {
					for (int j = 0; j < lOrderedPlayer.size(); j++) {
						if (p.getPontuacaoTotal() == 0) {
							lOrderedPlayer.add(p);
							break;
						}
						if (lOrderedPlayer.get(j).getPontuacaoTotal() <= p.getPontuacaoTotal()) {
							lOrderedPlayer.add(j, p);
							break;
						} else {
							if (p.getPontuacaoTotal() < 0 && lOrderedPlayer.get(j).getPontuacaoTotal() == 0) {
								lOrderedPlayer.add(j, p);
								break;
							}
							if(lOrderedPlayer.get(j).getPontuacaoTotal() > p.getPontuacaoTotal() && j == (lOrderedPlayer.size() - 1))
								lOrderedPlayer.add((lOrderedPlayer.size()-1), p);

						}

					}
				}
			}

			int pos = 0;
			//Adiciona os jogadores ordenados na lista de Ranking
			for (int i = 0; i < lOrderedPlayer.size(); i++) {
				Player p = lOrderedPlayer.get(i);
				pos = i + 1;
				RankingLine r = new RankingLine();
				r.setJogador(util.Util.completeZeros(pos, 2) + "º - " + p.getPlayerName());
				r.setResult1(p.getResultados().get(0).getColocacao() + "/" + p.getResultados().get(0).getRebuys() + "/" + p.getResultados().get(0).getPontuacaoEtapa());
				r.setResult2(p.getResultados().get(1).getColocacao() + "/" + p.getResultados().get(1).getRebuys() + "/" + p.getResultados().get(1).getPontuacaoEtapa());
				r.setResult3(p.getResultados().get(2).getColocacao() + "/" + p.getResultados().get(2).getRebuys() + "/" + p.getResultados().get(2).getPontuacaoEtapa());
				r.setResult4(p.getResultados().get(3).getColocacao() + "/" + p.getResultados().get(3).getRebuys() + "/" + p.getResultados().get(3).getPontuacaoEtapa());
				r.setResult5(p.getResultados().get(4).getColocacao() + "/" + p.getResultados().get(4).getRebuys() + "/" + p.getResultados().get(4).getPontuacaoEtapa());
				r.setResult6(p.getResultados().get(5).getColocacao() + "/" + p.getResultados().get(5).getRebuys() + "/" + p.getResultados().get(5).getPontuacaoEtapa());
				r.setResult7(p.getResultados().get(6).getColocacao() + "/" + p.getResultados().get(6).getRebuys() + "/" + p.getResultados().get(6).getPontuacaoEtapa());
				r.setResult8(p.getResultados().get(7).getColocacao() + "/" + p.getResultados().get(7).getRebuys() + "/" + p.getResultados().get(7).getPontuacaoEtapa());
				r.setResult9(p.getResultados().get(8).getColocacao() + "/" + p.getResultados().get(8).getRebuys() + "/" + p.getResultados().get(8).getPontuacaoEtapa());
				r.setResult10(p.getResultados().get(9).getColocacao() + "/" + p.getResultados().get(9).getRebuys() + "/" + p.getResultados().get(9).getPontuacaoEtapa());
				r.setResult11(p.getResultados().get(10).getColocacao() + "/" + p.getResultados().get(10).getRebuys() + "/" + p.getResultados().get(10).getPontuacaoEtapa());
				r.setResult12(p.getResultados().get(11).getColocacao() + "/" + p.getResultados().get(11).getRebuys() + "/" + p.getResultados().get(11).getPontuacaoEtapa());
				resumo = p.getResumo();
				r.setTotal("" + p.getPontuacaoTotal());
				r.setTotalRebuys(""+resumo.getRebuys());
				r.setTotalGanho("R$ " + resumo.getTotalGanho());
				r.setTotalGasto("R$ -" + resumo.getTotalGasto());
				r.setSaldo("R$ " + resumo.getSaldo());
				lRanking.add(r);
			}

			//definindo a nova janela
			Scene scene = new Scene(myPane,900,600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ranking Anual");

			//obtem o controller da nova janela
			rankingController =  fxmlLoader.<RankingController>getController();
			rankingController.gettRanking().setItems(lRanking);

			//inclui as informações do texto a abre a janela nova
			rankingController.setListRanking(lRanking);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void addJogador(Event evt){
		if (play) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Torneio em Andamento");
			alert.setContentText("Torneio Já Iniciado. Não é mais permitido adicionar jogadores.");
			alert.show();
		} else {
			// oListJogadores = listJogadores.getItems();
			addJogadorLista(tfJogador.getText());
			listJogadores.setItems(oListJogadores);
			configManager.addPlayer(tfJogador.getText());
		}
	}

	@FXML
	private void removerJogador(Event evt){
		if (play) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Torneio em Andamento");
			alert.setContentText("Torneio Já Iniciado. Não é mais permitido excluir jogadores.");
			alert.show();
		} else {
			int i = listJogadores.getSelectionModel().getSelectedIndex();
			oListJogadores.remove(i);
			listJogadores.setItems(oListJogadores);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private void play(Event evt){
		if(!play){
			play = true;
			timeLine = new Timeline();
			timeLine.setCycleCount(Timeline.INDEFINITE);
			timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler() {
				// KeyFrame event handler
				@Override
				public void handle(Event event) {
					timerAction();
				}
			}));
			timeLine.playFromStart();
		}
		if(paused)
			paused = false;
	}

	@FXML
	private void pause(Event evt){
		if(!paused)
			paused = true;
		else
			paused = false;
	}

	@FXML
	private void volta(Event evt){
		int iRodada = listRodadas.getSelectionModel().getSelectedIndex();
		if(iRodada > 0)
			listRodadas.getSelectionModel().select(iRodada-1);
		else
			listRodadas.getSelectionModel().select(0);
		setCurrentRound();
		restartTimer();
	}

	@FXML
	private void avanca(Event evt){
		int iRodada = listRodadas.getSelectionModel().getSelectedIndex();
		if(iRodada < Constants.MAX_ROUNDS)
			listRodadas.getSelectionModel().select(iRodada+1);
		else
			listRodadas.getSelectionModel().select(Constants.MAX_ROUNDS);
		setCurrentRound();
		restartTimer();
	}

	@FXML
	private void adicionaRebuy(Event evt){
		int i = listJogadores.getSelectionModel().getSelectedIndex();
		if (i < 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Selecionar Jogador");
			alert.setContentText("Favor Selecionar Jogador do Rebuy");
			alert.show();
		}
		else{
			oListRebuys.add(oListJogadores.get(i));
			atualizarEstatisticas();
		}
	}

	@FXML
	private void removeRebuy(Event evt){
		int i = listRebuys.getSelectionModel().getSelectedIndex();
		if (i < 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Selecionar Jogador");
			alert.setContentText("Favor Selecionar Jogador a Cancelar Rebuy");
			alert.show();
		}
		else{
			oListRebuys.remove(i);
			atualizarEstatisticas();
		}
	}

	@FXML
	private void removeJogadorTorneio(Event evt){
		int i = listJogadores.getSelectionModel().getSelectedIndex();
		if (i < 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Selecionar Jogador");
			alert.setContentText("Favor Selecionar Jogador Eliminado");
			alert.show();
		}
		else{
			oListFora.add(oListJogadores.get(i));
			oListJogadores.remove(i);
			atualizarEstatisticas();
		}
	}

	@FXML
	private void cancelarRemocaoJogadorTorneio(Event evt){
		int i = listFora.getSelectionModel().getSelectedIndex();
		if (i < 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Selecionar Jogador");
			alert.setContentText("Favor Selecionar Jogador a Cancelar Eliminação");
			alert.show();
		}
		else{
			addJogadorLista(oListFora.get(i));
			oListFora.remove(i);
			atualizarEstatisticas();
		}
	}

	private void addJogadorLista(String jogador) {
		boolean added = false;
		if(oListJogadores.size() == 0)
			oListJogadores.add(jogador);
		else{
			for (int i = 0; i < oListJogadores.size(); i++) {
				if (jogador.compareTo(oListJogadores.get(i)) < 0) {
					oListJogadores.add(i, jogador);
					added = true;
					break;
				}
			}
			if(!added)
				oListJogadores.add(jogador);

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//ConfigManager obtem a lista de jogadores cadastrados
		configManager = new ConfigManager();
		configManager.setConfigFileName("./config.txt");
		configManager.readFile(listJogadores);
		LinkedList<Player> lp = configManager.getListPlayer();
		for (int i = 0; i < lp.size(); i++) {
			addJogadorLista(lp.get(i).getPlayerName());
		}

		//Define a lista de rounds do Torneio
		roundList = new LinkedList<Round>();
		listJogadores.setItems(oListJogadores);
		roundManager = new RoundManager();
		roundManager.setRoundList(roundList);
		roundManager.setRoundListValues();

		//listRodadas.setCellFactory(TextFieldListCell.forListView());

		listRodadas.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new MyListCell();
			}
		});

		for (int i = 0; i < roundList.size(); i++) {
			oListrRodadas.add(roundList.get(i).getRoundName());
		}

		currentRound = 0;
		listRodadas.setItems(oListrRodadas);
		listRodadas.getSelectionModel().select(currentRound);

		//Desabilita edição e seleção da lista de rodadas
		listRodadas.setEditable(false);
		//listRodadas.setMouseTransparent(true);
		//listRodadas.setFocusTraversable(false);

		//Define Demais Listas
		listFora.setItems(oListFora);
		listRebuys.setItems(oListRebuys);

		//Inicializa tempos
        breakMinutes = 4 * Constants.MAX_MINUTES;
        breakSeconds = 0;
        seconds = 0;
        minutes = Constants.MAX_MINUTES;
        maxRound = minutes * Constants.SECONDS_IN_MINUTE;
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

        timerBar.setStyle("-fx-accent: #6699ff");

        if (roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).isBreakRound()) {
            minutes = Constants.MAX_MINUTES_BREAK;
        } else {
            minutes = Constants.MAX_MINUTES;
        }
        timerBar.setProgress(0);

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
    }

	private void timerAction() {
		if (!paused && play){
			if (seconds == 17 && minutes == 0) {
				playCountdown();
			}
			if (seconds == 2 && minutes == 0) {
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
					timerBar.setStyle("-fx-accent: #6699ff");

					if (roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).isBreakRound()) {
						minutes = Constants.MAX_MINUTES_BREAK;
						seconds = 0;
					} else {
						minutes = Constants.MAX_MINUTES;
						seconds = 0;
					}
					maxRound = minutes * Constants.SECONDS_IN_MINUTE;
					setCurrentRound();
					currentSecond = 0;
				}
			} else {
				seconds--;
				currentSecond = currentSecond + 1;
				if (minutes == 0 && seconds < 31) {
					timerBar.setStyle("-fx-accent: #ff4d4d");
				}
			}
			if (minutes < 10) {
				if (seconds < 10) {
					lbTimer.setText("0" + minutes + ":0" + seconds);
				} else {
					lbTimer.setText("0" + minutes + ":" + seconds);
				}
			} else {
				if (seconds < 10) {
					lbTimer.setText(minutes + ":0" + seconds);
				} else {
					lbTimer.setText(minutes + ":" + seconds);
				}
			}
			setBreakTime();
		}
		double dMax, dcurr;
		dMax = maxRound;
		dcurr = currentSecond;

		timerBar.setProgress((dMax - (dMax - dcurr))/dMax);

		atualizarEstatisticas();
	}

	private void setBreakTime() {
        // Time to Break Update
        if (breakSeconds == 0) {
            if (breakMinutes > 0) {
                breakMinutes--;
                breakSeconds = 59;
            } else {
                if ((roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).isBreakRound())) {
                    breakMinutes = Constants.MAX_MINUTES_BREAK + (4 * Constants.MAX_MINUTES);
                    breakSeconds = 0;
                } else {
                    breakMinutes = 0;
                    breakSeconds = 0;
                }
            }
        } else {
            breakSeconds--;
        }

        int tmpHours = breakMinutes / 60;

        if (tmpHours > 0) {
            int tmpMinutes = breakMinutes % 60;
            if (tmpMinutes < 10) {
                if (breakSeconds > 10)
                    lbProximoBreak.setText("0" + tmpHours + ":0" + tmpMinutes + ":" + breakSeconds);
                else
                	lbProximoBreak.setText("0" + tmpHours + ":0" + tmpMinutes + ":0" + breakSeconds);
            } else {
                if (breakSeconds > 10)
                	lbProximoBreak.setText("0" + tmpHours + ":" + tmpMinutes + ":" + breakSeconds);
                else
                	lbProximoBreak.setText("0" + tmpHours + ":" + tmpMinutes + ":0" + breakSeconds);
            }
        } else {
            if (breakMinutes < 10) {
                if (breakSeconds > 10)
                	lbProximoBreak.setText("0" + breakMinutes + ":" + breakSeconds);
                else
                	lbProximoBreak.setText("0" + breakMinutes + ":0" + breakSeconds);
            } else {
                if (breakSeconds > 10)
                	lbProximoBreak.setText(breakMinutes + ":" + breakSeconds);
                else
                	lbProximoBreak.setText(breakMinutes + ":0" + breakSeconds);
            }
        }
    }

	private void setCurrentRound(){
		currentRound = listRodadas.getSelectionModel().getSelectedIndex();
		int round = (currentRound + 1)/5;
    	if(roundList.get(currentRound).isBreakRound())
    		oListRebuys.add("============= BREAK " + round + "===============");

        if (roundList.get(currentRound).getBigBlind() != 0)
            bigAtual.setText("" + roundList.get(currentRound).getBigBlind());
        else
        	bigAtual.setText("");

        if (roundList.get(currentRound).getSmallBlind()!= 0)
            smallAtual.setText("" + roundList.get(currentRound).getSmallBlind());
        else
        	smallAtual.setText("");

        if (roundList.get(currentRound).getAnte()!= 0)
            lbAnteAtual.setText("" + roundList.get(currentRound).getAnte());
        else
            if (roundList.get(currentRound).getBigBlind() != 0)
            	lbAnteAtual.setText("  ");
            else
            	lbAnteAtual.setText("BREAK");

        if (currentRound < Constants.MAX_ROUNDS){
            if (roundList.get(currentRound+1).getBigBlind() != 0)
                bigSeguinte.setText("" + roundList.get(currentRound+1).getBigBlind());
            else
            	bigSeguinte.setText("");

            if (roundList.get(currentRound+1).getSmallBlind()!= 0)
                smallSeguinte.setText("" + roundList.get(currentRound+1).getSmallBlind());
            else
            	smallSeguinte.setText("");

            if (roundList.get(currentRound+1).getAnte()!= 0)
                lbAnteSeguinte.setText("" + roundList.get(currentRound+1).getAnte());
            else
                if (roundList.get(currentRound+1).getBigBlind() != 0)
                	lbAnteSeguinte.setText("  ");
                else
                	lbAnteSeguinte.setText("BREAK");
        }
        else{
            bigSeguinte.setText("");
            smallSeguinte.setText("");
            lbAnteSeguinte.setText("BREAK");
        }
    }

	public void atualizarEstatisticas(){
        int totalJogadores = (oListJogadores.size() + oListFora.size());
        int totalRebuy = oListRebuys.size();
        int totalJogando = oListJogadores.size();
        int totalFora = oListFora.size();
        double totalArrecadado = 0;


        for (int i = 0; i < oListRebuys.size(); i++) {
        	if (oListRebuys.get(i).contains("==="))
        		totalRebuy--;
		}

        statsJogando.setText("" + totalJogando  + "/" + totalJogadores);
        statsFora.setText("" +  totalFora +  "/" + totalJogadores);
        statsRebuys.setText("" + totalRebuy);

        totalArrecadado = ((totalJogadores * Constants.BUY_IN_VALUE)
                + (totalRebuy * Constants.REBUY_VALUE));
        statsTotalArrecadado.setText("R$ " + totalArrecadado);

        double total1 = 0;
        double total2 = 0;
        double total3 = 0;
        double total4 = 0;
        double total5 = 0;

        switch (totalJogadores){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                total1 = totalArrecadado * 0.65;
                total2 = totalArrecadado * 0.35;
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                total1 = totalArrecadado * 0.55;
                total2 = totalArrecadado * 0.35;
                total3 = totalArrecadado * 0.15;
                break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                total1 = totalArrecadado * 0.50;
                total2 = totalArrecadado * 0.25;
                total3 = totalArrecadado * 0.15;
                total4 = totalArrecadado * 0.10;
                break;
            default:
                total1 = totalArrecadado * 0.45;
                total2 = totalArrecadado * 0.20;
                total3 = totalArrecadado * 0.15;
                total4 = totalArrecadado * 0.10;
                total5 = totalArrecadado * 0.05;
                break;
        }
        total1l = Math.round(total1);
        total2l = Math.round(total2);
        total3l = Math.round(total3);
        total4l = Math.round(total4);
        total5l = Math.round(total5);

        statsTotalArrecadado.setText("R$ " + totalArrecadado);
        statsPremio1.setText("R$ " + total1l);
        statsPremio2.setText("R$ " + total2l);
        statsPremio3.setText("R$ " + total3l);
        statsPremio4.setText("R$ " + total4l);
        statsPremio5.setText("R$ " + total5l);
        if (totalJogando > 0)
        	statsMedia.setText("" + (((totalJogadores + totalRebuy) * 3000)/totalJogando));
    }

    public void playCountdown() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("..//sounds//gate.wav"));
        player.start();
    }

    public void playFinish() {
        Mp3Player player;
         player = new Mp3Player(PokerTimerFXController.class.getResource("..//sounds//Warning Siren-SoundBible.com-898272278.wav"));
        player.start();
    }

	 public class MyListCell extends ListCell<String> {

	     public MyListCell() {
	     }

	     @Override protected void updateItem(String item, boolean empty) {
	         // calling super here is very important - don't skip this!
	         super.updateItem(item, empty);
	         // change the text fill based on whether it is positive (green)
	         // or negative (red). If the cell is selected, the text will
	         // always be white (so that it can be read against the blue
	         // background), and if the value is zero, we'll make it black.
	         if (item != null) {
	        	 if (item.equals("BREAK"))
	        		 setTextFill(Color.RED);
	        	 else
	        		 setTextFill(Color.BLACK);
	        	 super.setText(item);
	         }
	     }
	 }
}
