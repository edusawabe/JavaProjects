package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import model.RenameModel;

public class RenamerController implements Initializable{
	@FXML
	private RadioButton rbSubstituir;
	@FXML
	private RadioButton rbIncluir;
	@FXML
	private RadioButton rbRemover;
	@FXML
	private TextField tfExtensao;
	@FXML
	private TextField tfQtdeCaracter;
	@FXML
	private RadioButton rbIniciais;
	@FXML
	private RadioButton rbFinais;
	@FXML
	private TextField tfNovoTexto;
	@FXML
	private Button btRenomear;
	@FXML
	private Label lbDiretorio;
	@FXML
	private TableView<RenameModel> tvArquivos;
	@FXML
	private TableColumn<RenameModel, Boolean> tcSelecionado;
	@FXML
	private TableColumn<RenameModel, String> tcAtual;
	@FXML
	private TableColumn<RenameModel, String> tcApos;

	private ObservableList<RenameModel> olTabelaArquivos = FXCollections.observableArrayList();
	private File dir;
	private CheckBox cb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcAtual = new TableColumn<>("Atual");
		tcApos = new TableColumn<>("Apos");

		//Criando checkbox
		cb = new CheckBox();

		//Tabela Editavel
		tvArquivos.setEditable(true);

		 //Make one column use checkboxes instead of text
		tcSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(tcSelecionado));

		 //Change ValueFactory for each column
		tcSelecionado.setCellValueFactory(new PropertyValueFactory<>("selected"));
		tcAtual.setCellValueFactory(new PropertyValueFactory<>("nomeAtual"));
		tcApos.setCellValueFactory(new PropertyValueFactory<>("nomeApos"));
		tvArquivos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		 // Header CheckBox
		tcSelecionado.setGraphic(cb);
        cb.setOnAction(e -> handleSelectAllCheckbox(e));

        tcSelecionado.setText("");

        tvArquivos.getColumns().addAll(tcAtual, tcApos);
        tvArquivos.setItems(olTabelaArquivos);
	}

    /**
     *
     * @return
     */
	private void handleSelectAllCheckbox(ActionEvent e) {
		for (RenameModel c : olTabelaArquivos) {
			c.setSelected(((CheckBox) e.getSource()).isSelected());
		}
		resetTable(e);
	}

	@FXML
	private void abrirArquivo(Event ev){
		DirectoryChooser dirch = new DirectoryChooser();
		dirch.setInitialDirectory(new File("c:\\E!Supercopia"));
		dirch.setTitle("Selecione o Diretorio");
		dir = dirch.showDialog(((MenuItem) ev.getSource()).getParentPopup().getOwnerWindow());
		if(dir != null){
			lbDiretorio.setText(dir.getAbsolutePath());
			doOpen(dir);
		}
	}

	private void doOpen(File dir){
		File[] files = dir.listFiles();
		tvArquivos.getItems().clear();

		for (int i = 0; i < files.length; i++) {
			if(files[i].isFile()){
				RenameModel item = new RenameModel();
				item.setSelected(false);
				item.setNomeAtual(files[i].getName());
				item.setNomeApos(files[i].getName());
				olTabelaArquivos.add(item);
			}
		}
		tvArquivos.setItems(olTabelaArquivos);
	}

	private void resetTable(Event evt){
		for (int i = 0; i < olTabelaArquivos.size(); i++) {
			olTabelaArquivos.get(i).setNomeApos(olTabelaArquivos.get(i).getNomeAtual());
		}
		renomear(evt);
	}

	@FXML
	private void substituirAction(Event evt){
		btRenomear.setDisable(true);

		if(rbSubstituir.isSelected()){
			rbIncluir.setSelected(false);
			rbRemover.setSelected(false);
			tfExtensao.setDisable(false);
			if(tfExtensao.getText().isEmpty())
				btRenomear.setDisable(true);
			else
				btRenomear.setDisable(false);
		}
		if(!rbSubstituir.isSelected() && !rbIncluir.isSelected() && !rbRemover.isSelected()){
			btRenomear.setDisable(true);
			tfExtensao.setDisable(true);
		}
		resetTable(evt);
	}

	@FXML
	private void incluirAction(Event evt){
		btRenomear.setDisable(true);

		if(rbIncluir.isSelected()){
			rbSubstituir.setSelected(false);
			rbRemover.setSelected(false);
			tfExtensao.setDisable(false);
			if(tfExtensao.getText().isEmpty())
				btRenomear.setDisable(true);
			else
				btRenomear.setDisable(false);
		}
		if(!rbSubstituir.isSelected() && !rbIncluir.isSelected() && !rbRemover.isSelected()){
			btRenomear.setDisable(true);
			tfExtensao.setDisable(true);
		}
		resetTable(evt);
	}

	@FXML
	private void removerAction(Event evt){

		btRenomear.setDisable(true);
		tfExtensao.clear();
		tfExtensao.setDisable(true);

		if(rbRemover.isSelected()){
			rbIncluir.setSelected(false);
			rbSubstituir.setSelected(false);
		}
		if(!rbSubstituir.isSelected() && !rbIncluir.isSelected() && !rbRemover.isSelected()){
			btRenomear.setDisable(true);
			tfExtensao.setDisable(true);
		}
		resetTable(evt);
	}

	@FXML
	private void iniciaisAction(Event evt){
		btRenomear.setDisable(true);

		if(rbIniciais.isSelected()){
			rbFinais.setSelected(false);
			tfNovoTexto.setDisable(false);
			if(tfNovoTexto.getText().isEmpty())
				btRenomear.setDisable(true);
			else
				btRenomear.setDisable(false);
		}
		if(!rbIniciais.isSelected() && !rbFinais.isSelected()){
			btRenomear.setDisable(true);
			tfNovoTexto.setDisable(true);
		}
		resetTable(evt);
	}

	@FXML
	private void finaisAction(Event evt){
		btRenomear.setDisable(true);

		if(rbFinais.isSelected()){
			rbIniciais.setSelected(false);
			tfNovoTexto.setDisable(false);
			if(tfNovoTexto.getText().isEmpty())
				btRenomear.setDisable(true);
			else
				btRenomear.setDisable(false);
		}
		if(!rbIniciais.isSelected() && !rbFinais.isSelected()){
			btRenomear.setDisable(true);
			tfNovoTexto.setDisable(true);
		}
		resetTable(evt);
	}

	@FXML
	private void renomear(Event ev){
		if(rbSubstituir.isSelected())
			substituirExtensao();

		if(rbIncluir.isSelected())
			incluirExtensao();

		if(rbRemover.isSelected())
			removerExtensao();

		tvArquivos.refresh();
	}

	@FXML
	private void habilitarIniciaisFinais(Event evt){
		tfNovoTexto.setDisable(true);

		if(tfQtdeCaracter.getText().isEmpty()){
			rbIniciais.setSelected(false);
			rbFinais.setSelected(false);
			rbIniciais.setDisable(true);
			rbFinais.setDisable(true);
			btRenomear.setDisable(true);
		} else {
			rbIniciais.setSelected(false);
			rbFinais.setSelected(false);
			rbIniciais.setDisable(false);
			rbFinais.setDisable(false);
			if(tfNovoTexto.getText().isEmpty())
				btRenomear.setDisable(true);
			else
				btRenomear.setDisable(false);
			try {
				int qtdeCar = Integer.parseInt(tfQtdeCaracter.getText());
			}
			catch (Exception e) {
				btRenomear.setDisable(true);
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Quantidade de Carateres Inválida");
				alert.setContentText("A Informação de Quantidade de Caracteres é inválida");
				alert.show();
			}
		}
	}

	private void substituirExtensao() {
		int posPonto;

		for (int i = 0; i < olTabelaArquivos.size(); i++) {
			if (olTabelaArquivos.get(i).selectedProperty().get()) {
				String item = olTabelaArquivos.get(i).getNomeAtual();
				posPonto = item.lastIndexOf('.');
				if (posPonto > 0) {
					item = item.substring(0, posPonto);
					if(!tfExtensao.getText().isEmpty())
						item = item + "." + tfExtensao.getText().replace(".", "");
					olTabelaArquivos.get(i).setNomeApos(item);
				}
			}
		}
		return;
	}

	private void removerExtensao() {
		int posPonto;

		for (int i = 0; i < olTabelaArquivos.size(); i++) {
			if (olTabelaArquivos.get(i).selectedProperty().get()) {
				String item = olTabelaArquivos.get(i).getNomeAtual();
				posPonto = item.lastIndexOf('.');
				if (posPonto > 0) {
					item = item.substring(0, posPonto);
					olTabelaArquivos.get(i).setNomeApos(item);
				}
			}
		}
	}

	private void incluirExtensao() {
		if (!tfExtensao.getText().isEmpty()) {
			for (int i = 0; i < olTabelaArquivos.size(); i++) {
				if (olTabelaArquivos.get(i).selectedProperty().get()) {
					String item = olTabelaArquivos.get(i).getNomeAtual();
					item = item + "." + tfExtensao.getText().replace(".", "");
					olTabelaArquivos.get(i).setNomeApos(item);
				}
			}
		}
	}
}

