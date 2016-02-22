package application;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.Pair;
import model.RankingLine;
import view.ColumnCell;
import view.ColumnFactory;

public class RankingController implements Initializable{
	@FXML
	private TableView<RankingLine> tRanking;
	@FXML
	private TableColumn cJogador;
	@FXML
	private TableColumn cResultado1;
	@FXML
	private TableColumn cResultado2;
	@FXML
	private TableColumn cResultado3;
	@FXML
	private TableColumn cResultado4;
	@FXML
	private TableColumn cResultado5;
	@FXML
	private TableColumn cResultado6;
	@FXML
	private TableColumn cResultado7;
	@FXML
	private TableColumn cResultado8;
	@FXML
	private TableColumn cResultado9;
	@FXML
	private TableColumn cResultado10;
	@FXML
	private TableColumn cResultado11;
	@FXML
	private TableColumn cResultado12;
	@FXML
	private TableColumn cTotal;
	@FXML
	private TableColumn cTotalRebuys;
	@FXML
	private TableColumn cTotalGasto;
	@FXML
	private TableColumn cTotalGanho;
	@FXML
	private TableColumn cSaldo;

	private ObservableList<RankingLine> listRanking;

	public RankingController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listRanking =  FXCollections.observableArrayList();

		cJogador.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("jogador"));
		cResultado1.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result1"));
		cResultado2.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result2"));
		cResultado3.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result3"));
		cResultado4.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result4"));
		cResultado5.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result5"));
		cResultado6.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result6"));
		cResultado7.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result7"));
		cResultado8.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result8"));
		cResultado9.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result9"));
		cResultado10.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result10"));
		cResultado11.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result11"));
		cResultado12.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("result12"));
		cTotal.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("total"));
		cTotalRebuys.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("totalRebuys"));
		cTotalGasto.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("totalGasto"));
		cTotalGanho.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("totalGanho"));
		cSaldo.setCellValueFactory(new PropertyValueFactory<RankingLine, String>("saldo"));
		cResultado1.setVisible(false);
		cResultado2.setVisible(false);
		cResultado3.setVisible(false);
		cResultado4.setVisible(false);
		cResultado5.setVisible(false);
		cResultado6.setVisible(false);
		cResultado7.setVisible(false);
		cResultado8.setVisible(false);
		cResultado9.setVisible(false);
		cResultado10.setVisible(false);
		cResultado11.setVisible(false);
		cResultado12.setVisible(false);
		Date date = new Date();
		SimpleDateFormat dataDia = new SimpleDateFormat("dd/MM/yyyy");
		int mesEtapa = Integer.parseInt(dataDia.format(date).substring(6, 7));

		switch (mesEtapa) {
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:

			break;
		case 8:

			break;
		case 9:

			break;
		case 10:

			break;
		case 11:

			break;
		case 12:

			break;
		default:
			break;
		}
}

	public ObservableList<RankingLine> getListRanking() {
		return listRanking;
	}

	public void setListRanking(ObservableList<RankingLine> listRanking) {
		this.listRanking = listRanking;
	}

	public TableView<RankingLine> gettRanking() {
		return tRanking;
	}

	public void settRanking(TableView<RankingLine> tRanking) {
		this.tRanking = tRanking;
	}
}
