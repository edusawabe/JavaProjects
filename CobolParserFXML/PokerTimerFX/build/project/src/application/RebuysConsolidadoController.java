package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RebuysConsolidadoLine;

public class RebuysConsolidadoController implements Initializable{
	@FXML
	private TableColumn<RebuysConsolidadoLine, String> tcNome;
	@FXML
	private TableColumn<RebuysConsolidadoLine, String> tcRebuys;
	@FXML
	private TableColumn<RebuysConsolidadoLine, Boolean> tcCbPago;
	@FXML
	private TableView<RebuysConsolidadoLine> tvRebuysConsolidado;

	private ObservableList<RebuysConsolidadoLine> olRebuysConsolidado;

	public RebuysConsolidadoController() {
		init();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}

	private void init() {
		tcNome.setCellValueFactory(new PropertyValueFactory<RebuysConsolidadoLine, String>("nome"));
		tcRebuys.setCellValueFactory(new PropertyValueFactory<RebuysConsolidadoLine, String>("rebuys"));
		tcCbPago.setCellValueFactory(new PropertyValueFactory<>("pago"));
		tcCbPago.setCellFactory(CheckBoxTableCell.forTableColumn(tcCbPago));
		tcCbPago.setOnEditCommit(
	        event -> tvRebuysConsolidado.getItems().get(event.getTablePosition().getRow())
	            .setPago(event.getNewValue()));
		olRebuysConsolidado = FXCollections.observableArrayList();
		tvRebuysConsolidado.setItems(olRebuysConsolidado);
	}

	public ObservableList<RebuysConsolidadoLine> getOlRebuysConsolidado() {
		return olRebuysConsolidado;
	}
}
