package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class SorteioMesasController implements Initializable{
	@FXML
	private ComboBox<String> cbQtdeMesas;
	@FXML
	private ListView<String> ltMesa1;
	@FXML
	private ListView<String> ltMesa2;
	@FXML
	private ListView<String> ltjogadores;

	private ObservableList<String> olQdteMesas = FXCollections.observableArrayList();
	private ObservableList<String> olMesa1 = FXCollections.observableArrayList();
	private ObservableList<String> olMesa2 = FXCollections.observableArrayList();
	private ObservableList<String> oListJogadores;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		olQdteMesas.add("1");
		olQdteMesas.add("2");

		cbQtdeMesas.setItems(olQdteMesas);
	}

	public ObservableList<String> getoListJogadores() {
		return oListJogadores;
	}

	public void setoListJogadores(ObservableList<String> oListJogadores) {
		this.oListJogadores = oListJogadores;
	}

	public ComboBox<String> getCbQtdeMesas() {
		return cbQtdeMesas;
	}

	public void setCbQtdeMesas(ComboBox<String> cbQtdeMesas) {
		this.cbQtdeMesas = cbQtdeMesas;
	}

	public ObservableList<String> getOlQdteMesas() {
		return olQdteMesas;
	}

	public void setOlQdteMesas(ObservableList<String> olQdteMesas) {
		this.olQdteMesas = olQdteMesas;
	}
}
