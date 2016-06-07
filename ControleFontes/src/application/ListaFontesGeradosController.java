package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ListaFontesGeradosController implements Initializable{

	@FXML
	private ObservableList<String> olFontesGerados;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public ObservableList<String> getOlFontesGerados() {
		return olFontesGerados;
	}

	public void setOlFontesGerados(ObservableList<String> olFontesGerados) {
		this.olFontesGerados = olFontesGerados;
	}

}
