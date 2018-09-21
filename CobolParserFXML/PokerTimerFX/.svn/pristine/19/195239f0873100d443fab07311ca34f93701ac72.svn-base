package application;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Player;
import model.ProjecaoLine;
import model.RankingLine;
import model.ResultadoRodada;
import model.Resumo;
import model.Round;
import util.Constants;
import util.MailResultContent;
import util.MailSender;
import util.Mp3Player;
import util.Util;

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
	private ListView<String> listMesa1;
	@FXML
	private ListView<String> listMesa2;
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
	private ComboBox<String> cbJogador;
	@FXML
	private Label lbTimer;
	@FXML
	private AnchorPane parent;
	@FXML
	private HBox painelInferior;
	@FXML
	private VBox painelInferiorJogadores;
	@FXML
	private HBox hbBotoesJogadores;
	@FXML
	private Label lbJogadorSelecionado;
	@FXML
	private Button btSortear;
	@FXML
	private Button btTrocarMesa;
	@FXML
	private Label lbMesa1;
	@FXML
	private Label lbMesa2;


	private ConfigManager configManager;

	private ObservableList<String> oListJogadores = FXCollections.observableArrayList();
	private ObservableList<String> oListRebuys = FXCollections.observableArrayList();
	private ObservableList<String> oListFora = FXCollections.observableArrayList();
	private ObservableList<String> oListrRodadas = FXCollections.observableArrayList();
	private ObservableList<String> oListComboJogador = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa1 = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadoresMesa2 = FXCollections.observableArrayList();

    private boolean paused;
    private boolean play;
    private int seconds;
    private int minutes;
    private int breakMinutes;
    private int maxRound;
    private int currentRound;
    private int currentSecond;
    private LinkedList<Round> roundList;
    private RoundManager roundManager;
    private Timeline timeLine;
    private double total1l = 0;
    private double total2l = 0;
    private double total3l = 0;
    private double total4l = 0;
    private double total5l = 0;
    private LinkedList<String> llMesa1;
    private LinkedList<String> llMesa2;
    private Timeline updateGuitask;

	public PokerTimerFXController() {

	}

	@FXML
	private void selecaoMesa1(Event evt){
		listMesa2.getSelectionModel().select(-1);
		listJogadores.getSelectionModel().select(-1);
	}

	@FXML
	private void selecaoMesa2(Event evt){
		listMesa1.getSelectionModel().select(-1);
		listJogadores.getSelectionModel().select(-1);
	}

	@FXML
	private void selecaojogadores(Event evt){
		listMesa1.getSelectionModel().select(-1);
		listMesa2.getSelectionModel().select(-1);
	}

	@FXML
	private void trocarJogadorMesa(Event evt){
		if (listMesa1.getSelectionModel().getSelectedIndex() < 0
				&& listMesa2.getSelectionModel().getSelectedIndex() < 0) {

			Alert alError = new Alert(AlertType.ERROR);
			alError.setTitle("Selecionar Jogador");
			alError.setContentText("Por favor selecionar o jogador da troca em uma das Mesas");
			alError.show();
			return;
		}

		Random gerador = new Random();
		int maxMesa;
		int origem = 0, destino = 0;
		Alert al = new Alert(AlertType.CONFIRMATION);

		if (listMesa1.getSelectionModel().getSelectedIndex() >= 0) {
			origem = listMesa1.getSelectionModel().getSelectedIndex();
			maxMesa = listMesa2.getItems().size();
			destino = gerador.nextInt(maxMesa);

			al.setTitle("Troca de Mesa");
			al.setContentText("Confirmar Troca:" + "\nMesa 1: " + "\n - " + oListJogadoresMesa1.get(origem)
					+ "\nMesa 2: " + "\n - " + oListJogadoresMesa2.get(destino));
			al.showAndWait();
			if (al.getResult() == ButtonType.OK) {
				String j1, j2;
				j1 = oListJogadoresMesa1.get(origem);
				j2 = oListJogadoresMesa2.get(destino);
				oListJogadoresMesa1.remove(origem);
				oListJogadoresMesa2.remove(destino);
				oListJogadoresMesa2.add(destino, j1);
				oListJogadoresMesa1.add(origem, j2);
				listMesa1.getSelectionModel().select(origem);
				listMesa2.getSelectionModel().select(destino);
				listMesa1.scrollTo(origem);
				listMesa2.scrollTo(destino);
			}

		} else {
			origem = listMesa2.getSelectionModel().getSelectedIndex();
			maxMesa = listMesa1.getItems().size();
			destino = gerador.nextInt(maxMesa);

			al.setTitle("Troca de Mesa");
			al.setContentText("Confirmar Troca:" + "\nMesa 2: " + "\n - " + oListJogadoresMesa2.get(origem)
					+ "\nMesa 1: " + "\n - " + oListJogadoresMesa1.get(destino));
			al.showAndWait();
			if (al.getResult() == ButtonType.OK) {
				String j1, j2;
				j1 = oListJogadoresMesa2.get(origem);
				j2 = oListJogadoresMesa1.get(destino);
				oListJogadoresMesa2.remove(origem);
				oListJogadoresMesa1.remove(destino);
				oListJogadoresMesa1.add(destino, j1);
				oListJogadoresMesa2.add(origem, j2);
			}
	}
	}

	@FXML
	private void sortearMesas(Event evt){
		btSortear.setDisable(true);
		Random gerador = new Random();
		Random geradorMesa = new Random();
		ObservableList<String> copyList =  FXCollections.observableArrayList(oListJogadores);

		int size = oListJogadores.size();
		int totalSize = oListJogadores.size();
		int maxMesa = (totalSize/2) + 1;
		int numero = gerador.nextInt(size);
		int mesa = geradorMesa.nextInt(1);

		if ((totalSize % 2) == 0)
			maxMesa = totalSize/2;
		else
			maxMesa = (totalSize/2) + 1;

		while (size > 0 ){
			if (llMesa1.size() < maxMesa && llMesa2.size() < maxMesa) {
				if ((mesa % 2) == 0)
					llMesa1.add(copyList.get(numero));
				else
					llMesa2.add(copyList.get(numero));
			} else {
				if (llMesa1.size() < maxMesa) {
					llMesa1.add(copyList.get(numero));
				} else {
					llMesa2.add(copyList.get(numero));
				}
			}
			copyList.remove(numero);
			mesa = geradorMesa.nextInt(100);
			size = copyList.size();
			if(size > 0)
				numero = gerador.nextInt(size);
		}
		for (int i = 0; i < llMesa1.size(); i++) {
			oListJogadoresMesa1.add(i, llMesa1.get(i));
		}

		for (int i = 0; i < llMesa2.size(); i++) {
			oListJogadoresMesa2.add(i, llMesa2.get(i));
		}
		listMesa1.setItems(oListJogadoresMesa1);
		listMesa2.setItems(oListJogadoresMesa2);
	}

	@FXML
	private void abrirSorteio(Event evt){
		Stage primaryStage = new Stage();
		SorteioMesasController sorteioMesasController = new SorteioMesasController();
		sorteioMesasController.setoListJogadores(oListJogadores);

		//obtem Loader
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SorteioMesas.fxml"));
		try {
			//carrega o loader
			Pane myPane = (Pane) fxmlLoader.load();

			// definindo a nova janela
			Scene scene = new Scene(myPane, 600, 400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sorteio de Mesas");

			// obtem o controller da nova janela
			sorteioMesasController = fxmlLoader.<SorteioMesasController> getController();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void abrirProjecao(Event evt){
		configManager.getPlayers();
		LinkedList<Player> lPlayer = configManager.getListPlayer();
    	Stage primaryStage = new Stage();
    	ObservableList<ProjecaoLine> lprojecaoLine = FXCollections.observableArrayList();
    	ProjecaoController projecaoController = new ProjecaoController();

    	int totalJogadoresAno = 0;

    	for (int i = 0; i < lPlayer.size(); i++) {
    		ArrayList<ResultadoRodada> listResultados = lPlayer.get(i).getResultados();
    		for (int j = 0; j < listResultados.size(); j++) {
				if(!listResultados.get(j).getColocacao().equals("0") && !listResultados.get(j).getColocacao().equals("00"))
					totalJogadoresAno++;
    		}
		}
    	totalJogadoresAno = totalJogadoresAno + oListJogadores.size() + oListFora.size();

    	double premioTotal = (totalJogadoresAno * 15);
    	double premio1 = premioTotal * 0.6;
    	double premio2 = premioTotal * 0.3;
    	double premio3 = premioTotal * 0.1;

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
			lprojecaoLine = ordenarProjecaoRodada(lprojecaoLine);

			// obtem o controller da nova janela
			projecaoController = fxmlLoader.<ProjecaoController> getController();

			// inclui as informações do texto a abre a janela nova
			projecaoController.gettProjecao().setItems(lprojecaoLine);

			projecaoController.setListProjecaoLines(lprojecaoLine);

			projecaoController.getLabel1().setText("R$ "+ Math.round(premio1));
			projecaoController.getLabel2().setText("R$ "+Math.round(premio2));
			projecaoController.getLabel3().setText("R$ "+Math.round(premio3));
			projecaoController.getLabelAnual().setText("R$ "+Math.round(premioTotal));

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param evt
	 */
	/**
	 * @param evt
	 */
	@FXML
	private void enviarResultados(Event evt){
		configManager.updatePlayersResult(oListFora, oListRebuys, total1l, total2l, total3l, total4l, total5l);

		MailResultContent mailContent = new MailResultContent();
        mailContent.setArrecadado(statsTotalArrecadado.getText());
        mailContent.setOuts(oListFora);
        mailContent.setPlayers(oListJogadores);
        mailContent.setPremio1(statsPremio1.getText());
        mailContent.setPremio2(statsPremio2.getText());
        mailContent.setPremio3(statsPremio3.getText());
        mailContent.setPremio4(statsPremio4.getText());
        mailContent.setPremio5(statsPremio5.getText());
        mailContent.setRebuy(oListRebuys);
        mailContent.setRoundFinal(oListrRodadas.get(listRodadas.getSelectionModel().getSelectedIndex()));
        String msgHtml = mailContent.toStringCssHtml();
        String msg = mailContent.toString();
        MailSender sender = new MailSender();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String subject = "Resultados Etapa "+ dateFormat.format(date);

		Alert alert = new Alert(AlertType.INFORMATION);
		WebView webView = new WebView();
		alert.setTitle("Resultados da Etapa");
		alert.setHeaderText("Resultados da Etapa");
		//alert.setContentText(msg);
		webView.getEngine().loadContent(msgHtml);
		webView.setPrefSize(800, 600);
		//alert.getDialogPane().setContent(webView);
		TextArea t = new TextArea();
		t.setFont(new Font("Courrier New", 12));
		t.setText(msg + "\n\n\n" + configManager.getMailList());
		alert.getDialogPane().setContent(t);
		alert.setWidth(800);
		alert.setHeight(600);
		alert.setResizable(true);
		alert.show();
		/*
        try {
			sender.sendMail(subject, msgHtml, configManager.getMailList(), true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
				if (lOrderedPlayer.isEmpty())
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
							if (lOrderedPlayer.get(j).getPontuacaoTotal() > p.getPontuacaoTotal()
									&& j == (lOrderedPlayer.size() - 1)) {
								lOrderedPlayer.add((lOrderedPlayer.size() - 1), p);
								break;
							}
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
		int index = 0;

		if (play) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Torneio em Andamento");
			alert.setContentText("Torneio Já Iniciado. Não é mais permitido adicionar jogadores.");
			alert.show();
		} else {
			// oListJogadores = listJogadores.getItems();
			//Verifica se jogador já existe no combo ou é novo item (index < 0)
			index = cbJogador.getItems().indexOf(cbJogador.editorProperty().get().getText());

			if (index < 0 && cbJogador.editorProperty().get().getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Jogador Inválido!");
				alert.setContentText("Não é Permitido Incluir Jogador Sem Nome!");
				alert.show();
				return;
			}

			//Adiciona jogador no arquivo caso não exista
			configManager.addPlayer(cbJogador.getSelectionModel().getSelectedItem());

			//Se existe, remove jogador do combo e adiociona na lista de jogadores
			//Senão apenas adiciona na lista de jogadores
			if (index > 0){
				addJogadorLista(cbJogador.getItems().get(index), oListJogadores);
				cbJogador.getItems().remove(index);
			}
			else {
				addJogadorLista(cbJogador.editorProperty().get().getText(), oListJogadores);
			}
			listJogadores.setItems(oListJogadores);
			cbJogador.setItems(oListComboJogador);
			cbJogador.editorProperty().get().setText("");
		}
	}

	@FXML
	private void removerJogador(Event evt){
		String jogador = null;
		if (play) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Torneio em Andamento");
			alert.setContentText("Torneio Já Iniciado. Não é mais permitido excluir jogadores.");
			alert.show();
			return;
		} else {
			//obtem indice do jogador e nome do jogador
			int i = listJogadores.getSelectionModel().getSelectedIndex();
			if (i < 0){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Selecionar Jogador");
				alert.setContentText("Favor Selecionar Jogador a Excluir.");
				alert.show();
				return;
			}
			jogador = listJogadores.getItems().get(i);

			//remove da lista de jogadores
			oListJogadores.remove(i);
			listJogadores.setItems(oListJogadores);

			//Adiciona na lista de jogadores do Combo ordenadamente
			addJogadorLista(jogador, oListComboJogador);
			cbJogador.getItems().setAll(oListComboJogador);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private void play(Event evt){
		int s = painelInferiorJogadores.getChildren().size();
		for (int i = 0; i < s; i++) {
			if (painelInferiorJogadores.getChildren().get(i).getId() != null) {
				if (painelInferiorJogadores.getChildren().get(i).getId().equals("painelJogadores")) {
					painelInferiorJogadores.getChildren().remove(i);
					break;
				}
			}
		}

		s = painelInferiorJogadores.getChildren().size();
		for (int i = 0; i < s; i++) {
			if (painelInferiorJogadores.getChildren().get(i).getId() != null) {
				if (painelInferiorJogadores.getChildren().get(i).getId().equals("comboJogadores")) {
					painelInferiorJogadores.getChildren().remove(i);
					break;
				}
			}
		}

		setRound();
		playMario();
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
		iRodada = listRodadas.getSelectionModel().getSelectedIndex();
		setCurrentRound();
		if (iRodada > Constants.MAX_ROUND_REBUY) {
			btAdicionaRebuy.setDisable(true);
			btExcluirRebuy.setDisable(true);
		} else {
			btAdicionaRebuy.setDisable(false);
			btExcluirRebuy.setDisable(false);
		}
		restartTimer();
	}

	@FXML
	private void avanca(Event evt){
		int iRodada = listRodadas.getSelectionModel().getSelectedIndex();
		if(iRodada < Constants.MAX_ROUNDS)
			listRodadas.getSelectionModel().select(iRodada+1);
		else
			listRodadas.getSelectionModel().select(Constants.MAX_ROUNDS);
		iRodada = listRodadas.getSelectionModel().getSelectedIndex();
		setCurrentRound();
		if (iRodada >= Constants.MAX_ROUND_REBUY) {
			btAdicionaRebuy.setDisable(true);
			btExcluirRebuy.setDisable(true);
		} else {
			btAdicionaRebuy.setDisable(false);
			btExcluirRebuy.setDisable(false);
		}
		restartTimer();
	}

	@FXML
	private void adicionaRebuy(Event evt){
		playRebuy();
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
		listJogadores.requestFocus();
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
		listJogadores.requestFocus();
	}

	@FXML
	private void removeJogadorTorneio(Event evt){
		playElimina();
		int i = listJogadores.getSelectionModel().getSelectedIndex();
		int size1, size2, diferenca, posicaoTroca, posicaoEliminacao, mesa, sorteado, sortedSize;
		Alert al = new Alert(AlertType.INFORMATION);
		al.setTitle("Mudar Jogador de Mesa");
		Random gerador = new Random();
		String eliminado, jogadorReposicionado;

		if (i < 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Selecionar Jogador");
			alert.setContentText("Favor Selecionar Jogador Eliminado na Lista de Inscritos!");
			alert.show();
		}
		else{
			//nome do jogador eliminado
			eliminado = oListJogadores.get(i);

			//adiciona na lista de eliminados
			oListFora.add(oListJogadores.get(i));

			//verifica de qual mesa o jogador esta sendo eliminado e elimina jogador da mesa
			mesa = 1;
			posicaoEliminacao = oListJogadoresMesa1.indexOf(eliminado);
			if (posicaoEliminacao < 0 ) {
				mesa = 2;
				posicaoEliminacao = oListJogadoresMesa2.indexOf(eliminado);
				oListJogadoresMesa2.remove(oListJogadores.get(i));
			}
			else
			{
				oListJogadoresMesa1.remove(oListJogadores.get(i));
			}
			//elimina jogador da lista de inscritos
			oListJogadores.remove(i);

			//recalcula tamanho das mesas
			size1 = oListJogadoresMesa1.size();
			size2 = oListJogadoresMesa2.size();

			//tratamento para sorteio da mesa final
			if (oListJogadores.size() == Constants.MAX_PLAYERS_FINAL_TABLE){
				oListJogadoresMesa1.clear();
				oListJogadoresMesa2.clear();
				sortedSize = oListJogadoresMesa1.size();

				while(sortedSize != oListJogadores.size()){
					sorteado = gerador.nextInt(oListJogadores.size());
					if(oListJogadoresMesa1.indexOf(oListJogadores.get(sorteado)) < 0){
						oListJogadoresMesa1.add(oListJogadores.get(sorteado));
						sortedSize = oListJogadoresMesa1.size();
					}
				}
				Alert alMesaFinal = new Alert(AlertType.INFORMATION);
				alMesaFinal.setTitle("Mesa Final");
				alMesaFinal.setContentText("Numero de Jogadores da Mesa Final Atingido."
						+ "\nFavor Reposicionar Jogadores Conforme o Novo Sorteio Realizado!");
				DialogPane dialogPane = alMesaFinal.getDialogPane();
				dialogPane.getStylesheets().add(
				   getClass().getResource("dialog.css").toExternalForm());
				dialogPane.getStyleClass().add("myDialog");
				dialogPane.setPrefSize(600, 400);
				alMesaFinal.setResizable(true);
				alMesaFinal.setWidth(600);
				alMesaFinal.setHeight(400);
				alMesaFinal.show();
				return;
			}

			// verfica se existe necessidade de balancear jogadores
			if (size1 > 0 && size2 > 0) {
				if (size1 > size2) {
					diferenca = size1 - size2;
				} else {
					diferenca = size2 - size1;
				}
				// balanceando jogadores
				if (diferenca > 1) {
					// se jogador eliminado é da mesa 2
					if (mesa == 2) {
						// obtem jogador da mesa 1 a ser trocado
						posicaoTroca = gerador.nextInt(size1);
						jogadorReposicionado = oListJogadoresMesa1.get(posicaoTroca);

						al.setContentText("Trocar de Mesa!" + "\n" + " - Jogador:\n   " + jogadorReposicionado
								+ "\nIr para Mesa 2: " + "\n  - Na posição em que estava:\n  " + eliminado);

						oListJogadoresMesa2.add(posicaoEliminacao, oListJogadoresMesa1.get(posicaoTroca));
						oListJogadoresMesa1.remove(posicaoTroca);
						listJogadores.getSelectionModel().select(-1);
						listMesa2.getSelectionModel().select(jogadorReposicionado);
					} else {
						// se jogador eliminado é da mesa 1
						// obtem jogador da mesa 2 a ser trocado
						posicaoTroca = gerador.nextInt(size2);
						jogadorReposicionado = oListJogadoresMesa2.get(posicaoTroca);

						al.setContentText("Trocar de Mesa!" + "\n" + " - Jogador:\n   " + jogadorReposicionado
								+ "\nIr para Mesa 1: " + "\n  - Na posição em que estava:\n  " + eliminado);
						oListJogadoresMesa1.add(posicaoEliminacao, oListJogadoresMesa2.get(posicaoTroca));
						oListJogadoresMesa2.remove(posicaoTroca);
						listJogadores.getSelectionModel().select(-1);
						listMesa1.getSelectionModel().select(jogadorReposicionado);
					}
					DialogPane dialogPane = al.getDialogPane();
					dialogPane.getStylesheets().add(
					   getClass().getResource("dialog.css").toExternalForm());
					dialogPane.getStyleClass().add("myDialog");
					dialogPane.setPrefSize(600, 500);
					al.setResizable(true);
					al.setWidth(600);
					al.setHeight(500);
					al.show();
				}
			}
		}
		listJogadores.requestFocus();
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
			addJogadorLista(oListFora.get(i), oListJogadores);
			if(llMesa1.indexOf(oListFora.get(i)) >= 0)
				retornarJogadorMesa(oListFora.get(i), oListJogadoresMesa1, llMesa1);
			else
				retornarJogadorMesa(oListFora.get(i), oListJogadoresMesa2, llMesa2);
			oListFora.remove(i);
			atualizarEstatisticas();
		}
		listJogadores.requestFocus();
	}

	private void addJogadorLista(String jogador, ObservableList<String> l) {
		boolean added = false;
		if(l.size() == 0)
			l.add(jogador);
		else{
			for (int i = 0; i < l.size(); i++) {
				if (jogador.compareTo(l.get(i)) < 0) {
					l.add(i, jogador);
					added = true;
					break;
				}
			}
			if(!added)
				l.add(jogador);

		}
	}

	private void retornarJogadorMesa(String jogador, ObservableList<String> l, LinkedList<String> lOriginal) {
		boolean added = false;
		if(l.size() == 0)
			l.add(jogador);
		else{
			int posOriginal = lOriginal.indexOf(jogador);
			for (int i = 0; i < l.size(); i++) {
				if(lOriginal.indexOf(l.get(i)) > posOriginal){
					l.add(i,jogador);
					added = true;
					break;
				}
			}
			if(!added)
				l.add(jogador);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//ConfigManager obtem a lista de jogadores cadastrados
		configManager = new ConfigManager();
		configManager.setConfigFileName("./config.txt");
		configManager.readFile();
		LinkedList<Player> lp = configManager.getListPlayer();
		for (int i = 0; i < lp.size(); i++) {
			if(lp.get(i).isPlayed())
				addJogadorLista(lp.get(i).getPlayerName(),oListJogadores);
			else
				oListComboJogador.add(lp.get(i).getPlayerName());
		}

		//Define a lista de rounds do Torneio
		roundList = new LinkedList<Round>();
		roundManager = new RoundManager();
		//construindo lista de rounds
		roundManager.setRoundList(roundList);
		roundManager.setRoundListValues();

		//Definindo CellFactory da Listview das Rodadas
		listRodadas.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new MyListCell();
			}
		});

		//Alimentando valores na lista de rodadas
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
		listJogadores.setItems(oListJogadores);
		listMesa1.setItems(oListJogadoresMesa1);
		listMesa2.setItems(oListJogadoresMesa2);
		listFora.setItems(oListFora);
		listRebuys.setItems(oListRebuys);
		llMesa1 = new LinkedList<>();
		llMesa2 = new LinkedList<>();

		//Inicializa tempos
        breakMinutes = 4 * Constants.MAX_MINUTES;
        seconds = 0;
        minutes = Constants.MAX_MINUTES;
        maxRound = minutes * Constants.SECONDS_IN_MINUTE;

        cbJogador.getItems().setAll(oListComboJogador);
        cbJogador.setPromptText("Jogador");

        //new Thread(updateGuitask).start();
        //Platform.setImplicitExit(false);

		updateGuitask = new Timeline();
		updateGuitask.setCycleCount(Timeline.INDEFINITE);
		updateGuitask.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), new EventHandler() {
			// KeyFrame event handler
			@Override
			public void handle(Event event) {
				setRound();
				atualizarEstatisticas();
			}
		}));
		updateGuitask.playFromStart();

	}

    private void setRound(){

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
            if (roundList.get(currentRound).isBreakRound()){
            	lbAnteAtual.setText("");
            	valorAnteAtual.setText("BREAK");
            	}
            else{
            	valorAnteAtual.setText("");
            	lbAnteAtual.setText("Ante:");
            }

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
                if (roundList.get(currentRound+1).isBreakRound()){
                	lbAnteSeguinte.setText("");
                	valorAnteSeguinte.setText("BREAK");
                	}
                else{
                	lbAnteSeguinte.setText("Ante:");
                	valorAnteAtual.setText("");
                	}
        }
        else{
        	bigSeguinte.setText("");
        	smallSeguinte.setText("");
        	lbAnteSeguinte.setText(" ");
        	valorAnteSeguinte.setText("BREAK");
        }
        listRodadas.getFocusModel().focus(currentRound);
        listRodadas.scrollTo(currentRound);
    }

    public void restartTimer() {
        seconds = 0;
        currentSecond = 0;
        timerBar.setStyle("-fx-accent: #6699ff");

        if (roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).isBreakRound()) {
            minutes = Constants.MAX_MINUTES_BREAK;
            breakMinutes = Constants.MAX_MINUTES * 4 + Constants.MAX_MINUTES_BREAK;
        } else {
            int resto  = ((listRodadas.getSelectionModel().getSelectedIndex()+1) % 5);

        	minutes = Constants.MAX_MINUTES;
            int qtdeRounds = 0;
            qtdeRounds = 5 - resto;
            breakMinutes = Constants.MAX_MINUTES * qtdeRounds;
        }
        timerBar.setProgress(0);
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
					breakMinutes--;
					seconds = 59;
					currentSecond = currentSecond + 1;
				} else {
					currentRound++;
					listRodadas.getSelectionModel().select(currentRound);
					timerBar.setStyle("-fx-accent: #6699ff");

					if (roundList.get(listRodadas.getSelectionModel().getSelectedIndex()).isBreakRound()) {
						minutes = Constants.MAX_MINUTES_BREAK;
						breakMinutes = Constants.MAX_MINUTES_BREAK + (4 * Constants.MAX_MINUTES);
						seconds = 0;
					} else {
						minutes = Constants.MAX_MINUTES;
						seconds = 0;
			            int resto  = ((listRodadas.getSelectionModel().getSelectedIndex()+1) % 5);
			            int qtdeRounds = 0;
			            qtdeRounds = 5 - resto;
			            breakMinutes = Constants.MAX_MINUTES * qtdeRounds;
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
        int tmpHours = breakMinutes / 60;

        if (tmpHours > 0) {
            int tmpMinutes = breakMinutes % 60;
            if (tmpMinutes < 10) {
                if (seconds < 10)
                    lbProximoBreak.setText("0" + tmpHours + ":0" + tmpMinutes + ":0" + seconds);
                else
                	lbProximoBreak.setText("0" + tmpHours + ":0" + tmpMinutes + ":" + seconds);
            } else {
                if (seconds < 10)
                	lbProximoBreak.setText("0" + tmpHours + ":" + tmpMinutes + ":0" + seconds);
                else
                	lbProximoBreak.setText("0" + tmpHours + ":" + tmpMinutes + ":" + seconds);
            }
        } else {
            if (breakMinutes < 10) {
                if (seconds < 10)
                	lbProximoBreak.setText("0" + breakMinutes + ":0" + seconds);
                else
                	lbProximoBreak.setText("0" + breakMinutes + ":" + seconds);
            } else {
                if (seconds < 10)
                	lbProximoBreak.setText(breakMinutes + ":0" + seconds);
                else
                	lbProximoBreak.setText(breakMinutes + ":" + seconds);
            }
        }
    }

	private void setCurrentRound(){
		currentRound = listRodadas.getSelectionModel().getSelectedIndex();
		int round = (currentRound + 1)/5;

		if(roundList.get(currentRound).isBreakRound() && round < 3)
    		oListRebuys.add("========= BREAK " + round + "===========");

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
            else{
            	lbAnteAtual.setText(" ");
            	valorAnteAtual.setText("BREAK");
            	}

        if (currentRound < Constants.MAX_ROUNDS){
            if (roundList.get(currentRound+1).getBigBlind() != 0)
                bigSeguinte.setText("" + roundList.get(currentRound+1).getBigBlind());
            else
            	bigSeguinte.setText("");

            if (roundList.get(currentRound+1).getSmallBlind()!= 0)
                smallSeguinte.setText("" + roundList.get(currentRound+1).getSmallBlind());
            else
            	smallSeguinte.setText("");

			if (roundList.get(currentRound + 1).getAnte() != 0)
				lbAnteSeguinte.setText("" + roundList.get(currentRound + 1).getAnte());
			else if (roundList.get(currentRound + 1).getBigBlind() != 0)
				lbAnteSeguinte.setText("  ");
			else {
				lbAnteSeguinte.setText(" ");
				valorAnteSeguinte.setText("BREAK");
			}
		}        else{
            bigSeguinte.setText("");
            smallSeguinte.setText("");
            lbAnteSeguinte.setText(" ");
            valorAnteSeguinte.setText("BREAK");
        }
        listRodadas.getFocusModel().focus(currentRound);
        listRodadas.scrollTo(currentRound);
    }

	public void atualizarEstatisticas(){
        int totalJogadores = (oListJogadores.size() + oListFora.size());
        int totalRebuy = oListRebuys.size();
        int totalJogando = oListJogadores.size();
        int totalFora = oListFora.size();
        int totalMesa1 = oListJogadoresMesa1.size();
        int totalMesa2 = oListJogadoresMesa2.size();
        double totalArrecadado = 0;
        String jogadorSelecionado;

        if(listJogadores.getSelectionModel().getSelectedIndex() >= 0 ){
        	jogadorSelecionado = oListJogadores.get(listJogadores.getSelectionModel().getSelectedIndex());
        	lbJogadorSelecionado.setText(jogadorSelecionado);
			if (oListJogadoresMesa1.indexOf(jogadorSelecionado) >= 0) {
				listMesa1.getSelectionModel().select(oListJogadoresMesa1.indexOf(jogadorSelecionado));
				listMesa1.scrollTo(oListJogadoresMesa1.indexOf(jogadorSelecionado));
				listMesa2.getSelectionModel().select(-1);
			} else {
				listMesa2.getSelectionModel().select(oListJogadoresMesa2.indexOf(jogadorSelecionado));
				listMesa2.scrollTo(oListJogadoresMesa2.indexOf(jogadorSelecionado));
				listMesa1.getSelectionModel().select(-1);
			}
        }
        lbMesa1.setText("Mesa 1 ("+ totalMesa1 + "/" + totalJogando +")");
        lbMesa2.setText("Mesa 2 ("+ totalMesa2 + "/" + totalJogando +")");

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

	private ObservableList<ProjecaoLine> ordenarProjecaoRodada(ObservableList<ProjecaoLine> l) {
		LinkedList<ProjecaoLine> lOrdered = new LinkedList<ProjecaoLine>();
		Double d1 = new Double(0.0);
		Double d2 = new Double(0.0);

		// prepara os dados do jogador e rodadas
		for (int i = 0; i < l.size(); i++) {
			ProjecaoLine p = l.get(i);

			if (lOrdered.isEmpty())
				lOrdered.add(p);
			else {
				for (int j = 0; j < lOrdered.size(); j++) {
					d1 = d1.parseDouble(lOrdered.get(j).getPosRodada());
					d2 = d2.parseDouble(p.getPosRodada());
					if (d2.equals(new Double(0.0))) {
						lOrdered.add(p);
						break;
					}
					if (d2 >= d1) {
						lOrdered.add(j, p);
						break;
					} else {
						if (d2 < 0 && d1.equals(new Double(0.0))) {
							lOrdered.add(j, p);
							break;
						}
						if (d1 > d2 && j == (lOrdered.size() - 1)) {
							lOrdered.add(p);
							break;
						}
					}
				}
			}
		}
		l.clear();
		for (int i = 0; i < lOrdered.size(); i++) {
			lOrdered.get(i).setPosRodada(Util.completeZeros(i+1, 2) + "º/" + lOrdered.get(i).getPosRodada());
			l.add(lOrdered.get(i));
		}
		return l;
	}

    public void playCountdown() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("gate.wav"));
        player.start();
    }

    public void playRebuy() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("PowerUp.wav"));
        player.start();
    }

    public void playElimina() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("SuperMario3-Die.wav"));
        player.start();
    }

    public void playMario() {
        Mp3Player player;
        player = new Mp3Player(PokerTimerFXController.class.getResource("MarioRiff.wav"));
        player.start();
    }

    public void playFinish() {
        Mp3Player player;
         player = new Mp3Player(PokerTimerFXController.class.getResource("Warning Siren-SoundBible.com-898272278.wav"));
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
