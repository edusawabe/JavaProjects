package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ProjecaoLine;
import model.RankingLine;

public class ProjecaoController implements Initializable{
		@FXML
		private TableView<ProjecaoLine> tProjecao;
		@FXML
		private TableColumn cJogador;
		@FXML
		private TableColumn cAtual;
		@FXML
		private TableColumn cNestaRodada;
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
		private Label label1;
		@FXML
		private Label label2;
		@FXML
		private Label label3;
		@FXML
		private Label labelAnual;


		private ObservableList<ProjecaoLine> listProjecaoLines;

		public ProjecaoController() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			listProjecaoLines =  FXCollections.observableArrayList();

			cJogador.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("jogador"));
			cAtual.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("atual"));
			cNestaRodada.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("nestaRodada"));
			cProjecao1.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao1"));
			cProjecao2.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao2"));
			cProjecao3.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao3"));
			cProjecao4.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao4"));
			cProjecao5.setCellValueFactory(new PropertyValueFactory<ProjecaoLine, String>("projecao5"));
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


}
