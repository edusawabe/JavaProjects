package application;

import java.net.URL;
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

	private ObservableList<RankingLine> listRanking;

	public RankingController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listRanking =  FXCollections.observableArrayList();

		//tRanking.getColumns().addAll(cJogador,cResultado1, cResultado2, cResultado3, cResultado4
		//		,cResultado5, cResultado6, cResultado7, cResultado8, cResultado9, cResultado10, cResultado11, cResultado12, cTotal);

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

/*		tRanking.setItems(listRanking);

		RankingLine r1 = new RankingLine();
		r1.setJogador("Teste1");
		r1.setResult1("1\nR$ 480.00");
		r1.setResult2("1\nR$ 480.00");
		r1.setResult3("1\nR$ 480.00");
		r1.setResult4("1\nR$ 480.00");
		r1.setResult5("1\nR$ 480.00");
		r1.setResult6("1\nR$ 480.00");
		r1.setResult7("1\nR$ 480.00");
		r1.setResult8("1\nR$ 480.00");
		r1.setResult9("1\nR$ 480.00");
		r1.setResult10("1\nR$ 480.00");
		r1.setResult11("1\nR$ 480.00");
		r1.setResult12("1\nR$ 480.00");
		r1.setTotal("TOTAL ");
		listRanking.add(r1);

		RankingLine r2 = new RankingLine();
		r2.setJogador("Teste1");
		r2.setResult1("1\nR$ 480.00");
		r2.setResult2("1\nR$ 480.00");
		r2.setResult3("1\nR$ 480.00");
		r2.setResult4("1\nR$ 480.00");
		r2.setResult5("1\nR$ 480.00");
		r2.setResult6("1\nR$ 480.00");
		r2.setResult7("1\nR$ 480.00");
		r2.setResult8("1\nR$ 480.00");
		r2.setResult9("1\nR$ 480.00");
		r2.setResult10("1\nR$ 480.00");
		r2.setResult11("1\nR$ 480.00");
		r2.setResult12("1\nR$ 480.00");
		r2.setTotal("TOTAL ");
		listRanking.add(r2);
*/	}

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
