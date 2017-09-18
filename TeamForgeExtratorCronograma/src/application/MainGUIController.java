package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.ConfigReader;
import view.ConfigItem;

public class MainGUIController implements Initializable{
	private ConfigReader reader;
	private LinkedList<ConfigItem> lConfigitem;
	private ObservableList<String> olCentroCusto = FXCollections.observableArrayList();
	private ObservableList<String> olPT = FXCollections.observableArrayList();
	private ObservableList<String> olGrupo = FXCollections.observableArrayList();
	private ObservableList<String> olPlanningFolder = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> cbCentroCusto;
	@FXML
	private ComboBox<String> cbPT;
	@FXML
	private ComboBox<String> cbGrupo;
	@FXML
	private ComboBox<String> cbPlanningFolder;
	@FXML
	private TextField tfArquivoCronograma;
	@FXML
	private Label lbProcesso;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reader = new ConfigReader();
		reader.readFile();
		lConfigitem = reader.getLconfig();
		olCentroCusto.add("Selecione");
		for (int i = 0; i < lConfigitem.size(); i++) {
			if(!olCentroCusto.contains(lConfigitem.get(i).getCentroCusto())){
				olCentroCusto.add(lConfigitem.get(i).getCentroCusto());
			}
		}
		cbCentroCusto.setItems(olCentroCusto);
		cbCentroCusto.getSelectionModel().select(0);
	}

}
