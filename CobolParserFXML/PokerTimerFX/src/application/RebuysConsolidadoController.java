package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RebuysConsolidadoLine;
import view.ColumnFactory;

public class RebuysConsolidadoController implements Initializable{
	@FXML
	private TableColumn<RebuysConsolidadoLine, String> tcNome;
	@FXML
	private TableColumn<RebuysConsolidadoLine, String> tcRebuys;
	@FXML
	private TableColumn<RebuysConsolidadoLine, String> tcCbPago;
	@FXML
	private TableView<RebuysConsolidadoLine> tvRebuysConsolidado;

	private ObservableList<RebuysConsolidadoLine> olRebuysConsolidado;

	public RebuysConsolidadoController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}

	private void init() {
		olRebuysConsolidado = FXCollections.observableArrayList();

		tcNome = new TableColumn<RebuysConsolidadoLine, String>();
		tcRebuys = new TableColumn<RebuysConsolidadoLine, String>();
		tcCbPago = new TableColumn<RebuysConsolidadoLine, String>();
		tvRebuysConsolidado = new TableView<RebuysConsolidadoLine>();
		tcNome.setCellValueFactory(new PropertyValueFactory<RebuysConsolidadoLine, String>("nome"));
		tcRebuys.setCellValueFactory(new PropertyValueFactory<RebuysConsolidadoLine, String>("rebuys"));
		tcCbPago.setCellValueFactory(new PropertyValueFactory<RebuysConsolidadoLine, String>("pago"));
		/*
		tcCbPago.setCellFactory(CheckBoxTableCell.forTableColumn(tcCbPago));
		tcCbPago.setOnEditCommit(
	        event -> tvRebuysConsolidado.getItems().get(event.getTablePosition().getRow())
	            .setPago(event.getNewValue()));
	            */
	}

	public ObservableList<RebuysConsolidadoLine> getOlRebuysConsolidado() {
		return olRebuysConsolidado;
	}

	public TableView<RebuysConsolidadoLine> getTvRebuysConsolidado() {
		return tvRebuysConsolidado;
	}

	public void setOlRebuysConsolidado(ObservableList<RebuysConsolidadoLine> olRebuysConsolidado) {
		this.olRebuysConsolidado = olRebuysConsolidado;
	}
}
