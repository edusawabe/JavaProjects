package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
			/*
			tProjecao.setItems(listProjecaoLines);

			ProjecaoLine r1 = new ProjecaoLine();
			r1.setJogador("Teste1");
			r1.setatual("3º");
			r1.setnestaRodada("2º");
			r1.setprojecao1("1º");
			r1.setprojecao2("2º");
			r1.setprojecao3("2º");
			r1.setprojecao4("3º");
			r1.setprojecao5("3º");

			listProjecaoLines.add(r1);

			ProjecaoLine r2 = new ProjecaoLine();
			r2.setJogador("Teste2");
			r2.setatual("5º");
			r2.setnestaRodada("3º");
			r2.setprojecao1("1º");
			r2.setprojecao2("2º");
			r2.setprojecao3("4º");
			r2.setprojecao4("5º");
			r2.setprojecao5("5º");

			listProjecaoLines.add(r2);
			*/
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

}
