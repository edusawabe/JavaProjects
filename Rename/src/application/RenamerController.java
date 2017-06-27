package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.TabableView;

import br.org.util.validator.MaskTextField;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import model.RenameModel;

public class RenamerController implements Initializable{
	@FXML
	private RadioButton rbTodosSim;
	@FXML
	private RadioButton rbTodosNao;
	@FXML
	private TextField tfExtensao;
	@FXML
	private RadioButton rbSubstituir;
	@FXML
	private RadioButton rbIncluir;
	@FXML
	private RadioButton rbRemover;
	@FXML
	private TextField tfQtdeCaracter;
	@FXML
	private RadioButton rbIniciais;
	@FXML
	private RadioButton rbFinais;
	@FXML
	private TextField tfPadrao;
	@FXML
	private Button btRenomear;
	@FXML
	private Label lbDiretorio;
	@FXML
	private ListView<String> lvAntes;
	@FXML
	private ListView<String> lvDepois;
	@FXML
	private TableView<RenameModel> tvArquivos;
	@FXML
	private TableColumn<RenameModel, Boolean> tcSelecionado;
	@FXML
	private TableColumn<RenameModel, String> tcAtual;
	@FXML
	private TableColumn<RenameModel, String> tcApos;

	private ObservableList<String> olAntes = FXCollections.observableArrayList();
	private ObservableList<String> olDepois = FXCollections.observableArrayList();
	private ObservableList<RenameModel> olTabelaArquivos = FXCollections.observableArrayList();
	private File dir;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcSelecionado.setCellValueFactory(new PropertyValueFactory<RenameModel, Boolean>("selecionado"));
		tcSelecionado.setCellFactory(column -> new CheckBoxTableCell());
		tcSelecionado.setEditable(true);
		tcAtual.setCellValueFactory(new PropertyValueFactory<RenameModel, String>("nomeAtual"));
		tcApos.setCellValueFactory(new PropertyValueFactory<RenameModel, String>("nomeApos"));
		tvArquivos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		 // Header CheckBox
        CheckBox cb = new CheckBox();
        cb.setUserData(tcSelecionado);
        cb.setOnAction(handleSelectAllCheckbox());
        tcSelecionado.setGraphic(cb);

        tvArquivos.getItems().clear();
        tvArquivos.setItems(olTabelaArquivos);
	}

    /**
     *
     * @return
     */
	private EventHandler<ActionEvent> handleSelectAllCheckbox() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CheckBox cb = (CheckBox) event.getSource();
				TableColumn column = (TableColumn) cb.getUserData();
				if (cb.isSelected()) {
					for (RenameModel c : olTabelaArquivos) {
						c.setSelecionado(true);
					}
				} else {
					for (RenameModel c : olTabelaArquivos) {
						c.setSelecionado(false);
					}
				}
			}
		};
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
		olAntes.clear();
		olDepois.clear();

		for (int i = 0; i < files.length; i++) {
			if(files[i].isFile()){
				olAntes.add(files[i].getName());
				olDepois.add(files[i].getName());
				RenameModel item = new RenameModel();
				item.setSelecionado(false);
				item.setNomeAtual(files[i].getName());
				item.setNomeApos(files[i].getName());
				olTabelaArquivos.add(item);
			}
		}

		lvAntes.setItems(olAntes);
		lvDepois.setItems(olDepois);
		tvArquivos.setItems(olTabelaArquivos);
	}

	@FXML
	private void renomearTodosSimSelected(Event ev){
		rbTodosNao.setSelected(false);
		if(!rbTodosNao.isSelected() && !rbTodosSim.isSelected()){
			tfExtensao.setDisable(true);
			tfQtdeCaracter.setDisable(true);
			rbSubstituir.setDisable(true);
			rbIncluir.setDisable(true);
			rbRemover.setDisable(true);
		}else{
			tfExtensao.setDisable(false);
			tfQtdeCaracter.setDisable(false);
			rbSubstituir.setDisable(false);
			rbIncluir.setDisable(false);
			rbRemover.setDisable(false);
		}
	}

	@FXML
	private void renomearTodosNaoSelected(Event ev){
		rbTodosSim.setSelected(false);
		if(!rbTodosNao.isSelected() && !rbTodosSim.isSelected()){
			tfExtensao.setDisable(true);
			tfQtdeCaracter.setDisable(true);
			rbSubstituir.setDisable(true);
			rbIncluir.setDisable(true);
			rbRemover.setDisable(true);
		}else{
			tfExtensao.setDisable(false);
			tfQtdeCaracter.setDisable(false);
			rbSubstituir.setDisable(false);
			rbIncluir.setDisable(false);
			rbRemover.setDisable(false);
		}
	}

	@FXML
	private void substituirAction(Event evt){
		if(rbSubstituir.isSelected()){
			rbIncluir.setSelected(false);
			rbRemover.setSelected(false);
			btRenomear.setDisable(false);
		}
		if(!rbSubstituir.isSelected() && !rbIncluir.isSelected() && !rbRemover.isSelected())
			btRenomear.setDisable(true);
	}

	@FXML
	private void incluirAction(Event evt){
		if(rbIncluir.isSelected()){
			rbSubstituir.setSelected(false);
			rbRemover.setSelected(false);
			btRenomear.setDisable(false);
		}
		if(!rbSubstituir.isSelected() && !rbIncluir.isSelected() && !rbRemover.isSelected())
			btRenomear.setDisable(true);
	}

	@FXML
	private void removerAction(Event evt){
		if(rbRemover.isSelected()){
			rbIncluir.setSelected(false);
			rbSubstituir.setSelected(false);
			btRenomear.setDisable(false);
		}
		if(!rbSubstituir.isSelected() && !rbIncluir.isSelected() && !rbRemover.isSelected())
			btRenomear.setDisable(true);
	}

	@FXML
	private void iniciaisAction(Event evt){
		if(rbIniciais.isSelected()){
			rbFinais.setSelected(false);
			btRenomear.setDisable(false);
		}
		if(!rbIniciais.isSelected() && !rbFinais.isSelected())
			btRenomear.setDisable(true);
	}

	@FXML
	private void finaisAction(Event evt){
		if(rbFinais.isSelected()){
			rbIniciais.setSelected(false);
			btRenomear.setDisable(false);
		}
		if(!rbIniciais.isSelected() && !rbFinais.isSelected())
			btRenomear.setDisable(true);
	}

	@FXML
	private void renomear(Event ev){
		Alert al = new Alert(AlertType.ERROR);
		if(!rbTodosNao.isSelected() && !rbTodosSim.isSelected())
		{
			al.setAlertType(AlertType.ERROR);
			al.setTitle("Selecione Renomear Todos Sim\\Não");
			al.setContentText("Favor selecionar se será realizada a renomeação de todos os arquivos: Sim\\Não");
			al.show();
			return;
		}
		if(rbTodosSim.isSelected())
			renomearTodos();
		if(rbTodosNao.isSelected())
			renomearSelecionados();
	}

	@FXML
	private void habilitarIniciaisFinais(Event evt){
		if(tfQtdeCaracter.getText().isEmpty()){
			rbIniciais.setSelected(false);
			rbFinais.setSelected(false);
			rbIniciais.setDisable(true);
			rbFinais.setDisable(true);
			tfPadrao.setDisable(true);
		} else {
			rbIniciais.setSelected(false);
			rbFinais.setSelected(false);
			rbIniciais.setDisable(false);
			rbFinais.setDisable(false);
			tfPadrao.setDisable(false);
			try {
				int qtdeCar = Integer.parseInt(tfQtdeCaracter.getText());
			}
			catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Quantidade de Carateres Inválida");
				alert.setContentText("A Informação de Quantidade de Caracteres é inválida");
				alert.show();
			}
		}
	}

	private void renomearTodos(){
		if(rbSubstituir.isSelected())
			substituirExtensao();

		if(rbIncluir.isSelected())
			incluirExtensao();

		if(rbRemover.isSelected())
			removerExtensao();

		lvDepois.setItems(olDepois);
	}

	private void renomearSelecionados(){

	}

	private void substituirExtensao() {
		int posPonto;

		for (int i = 0; i < olAntes.size(); i++) {
			String item = olAntes.get(i);
			posPonto = olAntes.get(i).lastIndexOf('.');
			if (posPonto > 0) {
				item = item.substring(0, posPonto);
				item = item + "." + tfExtensao.getText().replace(".", "");
				olDepois.set(i, item);
			}
		}
	}

	private void removerExtensao() {
		int posPonto;

		for (int i = 0; i < olAntes.size(); i++) {
			String item = olAntes.get(i);
			posPonto = olAntes.get(i).lastIndexOf('.');
			if (posPonto > 0) {
				item = item.substring(0, posPonto);
				olDepois.set(i, item);
			}
		}
	}

	private void incluirExtensao() {
		for (int i = 0; i < olAntes.size(); i++) {
			String item = olAntes.get(i);
			item = item + "." + tfExtensao.getText().replace(".", "");
			olDepois.set(i, item);
		}
	}

	@FXML
	private void itemSelecionado(Event ev) {
		if (tfPadrao.isDisable()) {
			tfPadrao.setText("");
		} else {
			if (lvAntes.getSelectionModel().getSelectedIndex() >= 0)
				tfPadrao.setText(olAntes.get(lvAntes.getSelectionModel().getSelectedIndex()));
		}
	}

}

