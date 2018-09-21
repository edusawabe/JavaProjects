package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ProjecaoLine;
import model.RankingLine;
import util.Util;

public class ProjecaoController implements Initializable {
	@FXML
	private TableView<ProjecaoLine> tProjecao;
	@FXML
	private TableColumn cJogador;
	@FXML
	private TableColumn cAtual;
	@FXML
	private TableColumn cNestaRodada;
	@FXML
	private TableColumn cPosRodada;
	@FXML
	private TableColumn cProjecao1;
	@FXML
	private TableColumn cProjecao2;
	@FXML
	private TableColumn cProjecao3;
	@FXML
	private TableColumn cProjecao4;
	@FXML
	private TableColumn cProjecao5;
	@FXML
	private TableColumn cProjecao6;
	@FXML
	private TableColumn cProjecao7;
	@FXML
	private TableColumn cProjecao8;
	@FXML
	private TableColumn cProjecao9;
	@FXML
	private TableColumn cProjecao10;
	@FXML
	private TableColumn cProjecao11;
	@FXML
	private TableColumn cProjecao12;
	@FXML
	private TableColumn cProjecao13;
	@FXML
	private TableColumn cProjecao14;
	@FXML
	private TableColumn cProjecao15;
	@FXML
	private TableColumn cProjecaoSelecionada;
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private Label label3;
	@FXML
	private Label label4;
	@FXML
	private Label label5;
	@FXML
	private Label labelAnual;
	@FXML
	private ComboBox<String> cbColocacao;
	@FXML
	private TextField txQtdeRebuys;
	@FXML
	private ComboBox<String> cbJogadores;

	private ObservableList<ProjecaoLine> listProjecaoLines;
	private ObservableList<String> listComboColocacao;
	private ObservableList<String> oListJogadores = FXCollections.observableArrayList();
	private ObservableList<String> oListRebuys = FXCollections.observableArrayList();
	private ObservableList<String> oListFora = FXCollections.observableArrayList();
	private ObservableList<String> oListJogando = FXCollections.observableArrayList();
	private ConfigManager configManager;

	public ProjecaoController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listProjecaoLines = FXCollections.observableArrayList();
		listComboColocacao = FXCollections.observableArrayList();

		cJogador.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("jogador"));
		cAtual.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("atual"));
		cNestaRodada.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("nestaRodada"));
		cPosRodada.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("posRodada"));
		cProjecao1.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao1"));
		cProjecao2.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao2"));
		cProjecao3.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao3"));
		cProjecao4.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao4"));
		cProjecao5.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao5"));
		cProjecao6.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao6"));
		cProjecao7.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao7"));
		cProjecao8.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao8"));
		cProjecao9.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao9"));
		cProjecao10.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao10"));
		cProjecao11.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao11"));
		cProjecao12.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao12"));
		cProjecao13.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao13"));
		cProjecao14.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao14"));
		cProjecao15.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao15"));
		cProjecaoSelecionada.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecaoCustom"));
	}

	@FXML
	private void tratarCombo(Event evt) {
			listProjecaoLines.clear();
	}

	@FXML
	private void projetar(Event evt) {
		listProjecaoLines.clear();
		int rebuys = 0;
		int colocacaoProjecao = 0;
		String jogadorSelecionado = null;
		try {
			rebuys = Integer.parseInt(txQtdeRebuys.getText());
			colocacaoProjecao = Integer.parseInt(cbColocacao.getSelectionModel().getSelectedItem().replaceAll("º", ""));
		} catch (Exception e) {

		}
		jogadorSelecionado = cbJogadores.getSelectionModel().getSelectedItem();

		listProjecaoLines = configManager.projetarResultado(oListRebuys, oListFora, oListJogadores,
				oListFora.size() + oListJogadores.size(),
				colocacaoProjecao, rebuys,
				jogadorSelecionado);
		// listProjecaoLines =
		// controller.ordenarProjecaoRodada(listProjecaoLines);
		tProjecao.setItems(listProjecaoLines);
	}

	public TableView<ProjecaoLine> gettProjecao() {
		return tProjecao;
	}

	public void settProjecao(TableView<ProjecaoLine> tProjecao) {
		this.tProjecao = tProjecao;
	}

	public ObservableList<ProjecaoLine> getListProjecaoLines() {
		return listProjecaoLines;
	}

	public void setListProjecaoLines(ObservableList<ProjecaoLine> listProjecaoLines) {
		this.listProjecaoLines = listProjecaoLines;
	}

	public Label getLabel1() {
		return label1;
	}

	public void setLabel1(Label label1) {
		this.label1 = label1;
	}

	public Label getLabel2() {
		return label2;
	}

	public void setLabel2(Label label2) {
		this.label2 = label2;
	}

	public Label getLabel3() {
		return label3;
	}

	public void setLabel3(Label label3) {
		this.label3 = label3;
	}

	public Label getLabelAnual() {
		return labelAnual;
	}

	public void setLabelAnual(Label labelAnual) {
		this.labelAnual = labelAnual;
	}

	public ObservableList<String> getListComboColocacao() {
		return listComboColocacao;
	}

	public ComboBox<String> getCbColocacao() {
		return cbColocacao;
	}

	public void setCbColocacao(ComboBox<String> cbColocacao) {
		this.cbColocacao = cbColocacao;
	}

	public Label getLabel4() {
		return label4;
	}

	public Label getLabel5() {
		return label5;
	}

	public void setoListJogadores(ObservableList<String> oListJogadores) {
		this.oListJogadores = oListJogadores;
	}

	public void setoListRebuys(ObservableList<String> oListRebuys) {
		this.oListRebuys = oListRebuys;
	}

	public void setoListFora(ObservableList<String> oListFora) {
		this.oListFora = oListFora;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public void setConfigManager(ConfigManager configManager) {
		this.configManager = configManager;
	}

	public ObservableList<String> getoListJogadores() {
		return oListJogadores;
	}

	public ObservableList<String> getoListRebuys() {
		return oListRebuys;
	}

	public ObservableList<String> getoListFora() {
		return oListFora;
	}

	public ComboBox<String> getCbJogadores() {
		return cbJogadores;
	}

	public ObservableList<String> getoListJogando() {
		return oListJogando;
	}

	public void setoListJogando(ObservableList<String> oListJogando) {
		this.oListJogando = oListJogando;
	}

	public void sortoListJogando() {
		ObservableList<String> temp = FXCollections.observableArrayList();
		for (int i = 0; i < oListJogando.size(); i++) {
			Util.addJogadorListaOrdenadamente(oListJogando.get(i), temp);
		}
		oListJogando.clear();
		oListJogando.addAll(temp);
		temp.clear();
	}

}
