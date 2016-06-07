package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ManutTableItem;


public class MainGUIController implements Initializable{
	@FXML
	private ComboBox<String> cbTipo;
	@FXML
	private ComboBox<String> cbTipoFonte;
	@FXML
	private TextField tfQuantidade;
	@FXML
	private TableView<ManutTableItem> tvManutencoes;
	@FXML
	private TableColumn<ManutTableItem, String> tcTipo;
	@FXML
	private TableColumn<ManutTableItem, String> tcTipoFonte;
	@FXML
	private TableColumn<ManutTableItem, String> tcQuantidade;
	@FXML
	private Button btIncluir;
	@FXML
	private Button btAlterar;
	@FXML
	private Button btExcluir;
	@FXML
	private Button btGerarNomes;

	private ObservableList<ManutTableItem> olTableManut = FXCollections.observableArrayList();
	private ObservableList<String> olTipo = FXCollections.observableArrayList();
	private ObservableList<String> olTipoFonteCoordenador = FXCollections.observableArrayList();
	private ObservableList<String> olTipoFonteFuncional = FXCollections.observableArrayList();
	private ObservableList<String> olTipoFonteBasico = FXCollections.observableArrayList();
	private ObservableList<String> olTipoFonteBatch = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcTipo.setCellValueFactory(new PropertyValueFactory<ManutTableItem, String>("tipo"));
		tcTipoFonte.setCellValueFactory(new PropertyValueFactory<ManutTableItem, String>("tipoFonte"));
		tcQuantidade.setCellValueFactory(new PropertyValueFactory<ManutTableItem, String>("quantidade"));
		tvManutencoes.getItems().setAll(olTableManut);
		cbTipoFonte.setDisable(true);
		btIncluir.setDisable(true);
		btAlterar.setDisable(true);
		btExcluir.setDisable(true);

		olTipo.add("Coordenador");
		olTipo.add("Funcional");
		olTipo.add("Basico");
		olTipo.add("Batch");
		cbTipo.setItems(olTipo);

		olTipoFonteBatch.add("B");
		olTipoFonteBatch.add("C");
		olTipoFonteBatch.add("E");
		olTipoFonteBatch.add("F");
		olTipoFonteBatch.add("O");
		olTipoFonteBatch.add("P");
		olTipoFonteBatch.add("T");
		olTipoFonteBatch.add("R");
		olTipoFonteBatch.add("V");

		olTipoFonteCoordenador.add("A");
		olTipoFonteCoordenador.add("C");
		olTipoFonteCoordenador.add("E");
		olTipoFonteCoordenador.add("F");
		olTipoFonteCoordenador.add("I");
		olTipoFonteCoordenador.add("L");
		olTipoFonteCoordenador.add("M");
		olTipoFonteCoordenador.add("O");

		olTipoFonteFuncional.add("A");
		olTipoFonteFuncional.add("C");
		olTipoFonteFuncional.add("E");
		olTipoFonteFuncional.add("I");
		olTipoFonteFuncional.add("L");
		olTipoFonteFuncional.add("K");
		olTipoFonteFuncional.add("O");

		olTipoFonteBasico.add("U");
		olTipoFonteBasico.add("D");
		olTipoFonteBasico.add("I");
		olTipoFonteBasico.add("S");
	}

	@FXML
	public void tipoSelecionado(Event evt) {
		String selecionado = cbTipo.getSelectionModel().getSelectedItem();
		cbTipoFonte.setDisable(false);
		if (selecionado.equals("Coordenador")) {
			cbTipoFonte.setItems(olTipoFonteCoordenador);
		} else if (selecionado.equals("Funcional")) {
			cbTipoFonte.setItems(olTipoFonteFuncional);
		} else if (selecionado.equals("Basico")) {
			cbTipoFonte.setItems(olTipoFonteBasico);
		} else if (selecionado.equals("Batch")) {
			cbTipoFonte.setItems(olTipoFonteBatch);
		} else {
			cbTipoFonte.setDisable(true);
		}
	}

	@FXML
	public void tipoFonteSelecionado(Event evt) {
		if (cbTipoFonte.getSelectionModel().getSelectedIndex() >= 0)
			btIncluir.setDisable(false);
		else
			btIncluir.setDisable(true);
	}

	@FXML
	public void gerarNomes(Event evt) {
		ObservableList<String> olNomesGerados = FXCollections.observableArrayList();

	}
}
