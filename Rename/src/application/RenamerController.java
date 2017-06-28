package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
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
	@FXML
	private TableColumn<RenameModel, String> tcRenomeado;

	private ObservableList<RenameModel> olTabelaArquivos = FXCollections.observableArrayList();
	private File dir;
	private CheckBox cb;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcAtual = new TableColumn<>("Atual");
		tcApos = new TableColumn<>("Apos");
		tcRenomeado = new TableColumn<>("Renomeado");

		tcAtual.setSortable(false);
		tcApos.setSortable(false);
		tcRenomeado.setSortable(false);
		tcSelecionado.setSortable(false);

		//Criando checkbox
		cb = new CheckBox();

		//Tabela Editavel
		tvArquivos.setEditable(true);

		 //Change ValueFactory for each column
		tcSelecionado.setCellValueFactory(param -> param.getValue().isSelected(this));
		tcAtual.setCellValueFactory(new PropertyValueFactory<>("nomeAtual"));
		tcApos.setCellValueFactory(new PropertyValueFactory<>("nomeApos"));
		tcRenomeado.setCellValueFactory(new PropertyValueFactory<>("renomeado"));

		tcSelecionado.setEditable(true);

		//tcSelecionado.setCellFactory(CheckBoxTableCell.forTableColumn(tcSelecionado));

		//Definindo a cellfactory com checkbox e definindo os eventos de atualização de valores das outras colunas da linha
		//de acordo com a atualização do valor da celula (Selecionado\Deselecionado)
		tcSelecionado.setCellFactory(p -> {
		    CheckBox checkBox = new CheckBox();
		    TableCell<RenameModel, Boolean> tableCell = new TableCell<RenameModel, Boolean>() {
		        @Override
		        protected void updateItem(Boolean item, boolean empty) {
		            super.updateItem(item, empty);
		            if (empty || item == null){
		            	setGraphic(null);
	                }
		            else {
		                setGraphic(checkBox);
		                checkBox.setSelected(item);
		            }
		        }
		    };
		    //Filtro para tratar evento de clique do mouse
		    checkBox.addEventFilter(MouseEvent.MOUSE_PRESSED, event ->
		    	validate(checkBox, (RenameModel) tableCell.getTableRow().getItem(), event));

		    //Filtro para tratar evento de space
		    checkBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
		        if(event.getCode() == KeyCode.SPACE)
		        	validate(checkBox, (RenameModel) tableCell.getTableRow().getItem(), event);
		    });

		    //Definindo o alihamento
		    tableCell.setAlignment(Pos.CENTER);
		    tableCell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		    return tableCell;
		});

		 // Header CheckBox da primeira coluna
		tcSelecionado.setGraphic(cb);
        cb.setOnAction(e -> handleSelectAllCheckbox(e));

        //Limpando o titulo da coluna checkbox
        tcSelecionado.setText("");

        //Adicionando colunas no codigo ao inves de adicionar via FXML
        tvArquivos.getColumns().addAll(tcAtual, tcApos, tcRenomeado);
        tvArquivos.setItems(olTabelaArquivos);

        //Seleção Multipla
        tvArquivos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Evento para tratar a multipla seleção de linhas e marcação\desmarcação do checkbox em caso de space acionado
        tvArquivos.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
	        if(event.getCode() == KeyCode.SPACE){
	        	ObservableList<Integer> olInd = tvArquivos.getSelectionModel().getSelectedIndices();
	        	for (int i = 0; i < olInd.size(); i++) {
	        		olTabelaArquivos.get(olInd.get(i)).setSelected(!olTabelaArquivos.get(olInd.get(i)).isSelected().get());
				}
	        }
	    });

        //Definindo a largura das colunas livres automaticamente
        // Largura da Tabela - 75 (Largura da Coluna de largura fixa) dividido por 3 colunas livres
        tcApos.prefWidthProperty().bind(tvArquivos.widthProperty().subtract(75).divide(3)); // w * 1/4
        tcAtual.prefWidthProperty().bind(tvArquivos.widthProperty().subtract(75).divide(3)); // w * 1/2
        tcRenomeado.prefWidthProperty().bind(tvArquivos.widthProperty().subtract(75).divide(3)); // w * 1/4
	}

	//tratando evento de teclado e mouse da celula com checkbox
	private void validate(CheckBox checkBox, RenameModel item, Event event){
	    // Validate here
	    event.consume();
	    checkBox.setSelected(!checkBox.isSelected());
	    item.setSelected(checkBox.isSelected());
	    renomear(new ActionEvent());
	}

    /**
     *
     * @return
     * Evento para tratar o checkbox do Header para realizar o Select ALL
     */
	private void handleSelectAllCheckbox(ActionEvent e) {
		for (RenameModel c : olTabelaArquivos) {
			c.setSelected(((CheckBox) e.getSource()).isSelected());
		}
		resetTable(e);
	}

	/**
    *
    * @return
    * Evento do item de Menu Arquivo->Abrir
    */
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

	/**
    *
    * @return
    * Realiza a abertura do arquivo
    */
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

	/**
    *
    * @return
    * Reseta valores da tabela a atualiza valores de acordo com as opções selecionadas
    */
	public void resetTable(Event evt){
		for (int i = 0; i < olTabelaArquivos.size(); i++) {
			olTabelaArquivos.get(i).setNomeApos(olTabelaArquivos.get(i).getNomeAtual());
		}
		renomear(evt);
	}

	/**
    *
    * @return
    * Ação do Radio Substituir
    */
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

	/**
    *
    * @return
    * Ação do textfield de novo texto
    */
	private void tratarNovoTextoAction(){
		if (tfQtdeCaracter.getText().isEmpty()) {
			return;
		}
		String novo = tfNovoTexto.getText();
		int qtde = Integer.parseInt(tfQtdeCaracter.getText());

		//substituição inicio do nome
		if(rbIniciais.isSelected()){
			for (int i = 0; i < olTabelaArquivos.size(); i++) {
				if(olTabelaArquivos.get(i).isSelected().get()){
					String tmp = olTabelaArquivos.get(i).getNomeAtual();
					tmp = tmp.substring(qtde,tmp.length());
					tmp = tfNovoTexto.getText() + tmp;
					olTabelaArquivos.get(i).setNomeApos(tmp);
				}
			}
		}

		//substituição final do nome
		if(rbFinais.isSelected()){
			for (int i = 0; i < olTabelaArquivos.size(); i++) {
				if(olTabelaArquivos.get(i).isSelected().get()){
					String tmp = olTabelaArquivos.get(i).getNomeAtual();
					tmp = tmp.substring(tmp.length() - qtde,tmp.length());
					tmp = tmp + tfNovoTexto.getText();
					olTabelaArquivos.get(i).setNomeApos(tmp);
				}
			}
		}
		tvArquivos.refresh();
	}

	/**
    *
    * @return
    * Ação do Radio Incluir
    */
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

	/**
    *
    * @return
    * Ação do Radio remover
    */
	@FXML
	private void removerAction(Event evt){

		btRenomear.setDisable(false);
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

	/**
    *
    * @return
    * Ação do botão Renomear
    */
	@FXML
	private void btRenomearAction(Event evt){
		for (int i = 0; i < olTabelaArquivos.size(); i++) {
			if(olTabelaArquivos.get(i).isSelected().get()){
				File f = new File(dir.getAbsolutePath() + "\\" + olTabelaArquivos.get(i).getNomeAtual());
				File fRenamed = new File(dir.getAbsolutePath() + "\\" + olTabelaArquivos.get(i).getNomeApos());
				f.renameTo(fRenamed);
				olTabelaArquivos.get(i).setRenomeado("Renomeado!");
			}else{
				olTabelaArquivos.get(i).setRenomeado("");
			}
			olTabelaArquivos.get(i).setSelected(false);
		}
		tvArquivos.refresh();
	}

	/**
    *
    * @return
    * Ação do Radio Iniciais
    */
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

	/**
    *
    * @return
    * Ação do radio Finais
    */
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

	/**
    *
    * @return
    * Ação do renomear para atualizar a tabela de acordo com as opções selecionadas
    */
	@FXML
	private void renomear(Event ev){
		if(rbSubstituir.isSelected())
			substituirExtensao();

		if(rbIncluir.isSelected())
			incluirExtensao();

		if(rbRemover.isSelected())
			removerExtensao();

		tratarNovoTextoAction();

		tvArquivos.refresh();

		if (((!rbIncluir.isSelected() || !rbSubstituir.isSelected()) && !tfExtensao.getText().isEmpty()))
				btRenomear.setDisable(false);

		if(rbRemover.isSelected())
			btRenomear.setDisable(false);


	}

	/**
    *
    * @return
    * Ação do campo quantidade de caracteres para habilitar\desabilitar os radios iniciais\finais
    */
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

	/**
    *
    * @return
    * tratamento de substituição de extensão para atualização do valor na tabela
    */
	private void substituirExtensao() {
		int posPonto;

		for (int i = 0; i < olTabelaArquivos.size(); i++) {
			if (olTabelaArquivos.get(i).isSelected().get()) {
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

	/**
    *
    * @return
    * tratamento de remoção de extensão para atualização do valor na tabela
    */
	private void removerExtensao() {
		int posPonto;

		for (int i = 0; i < olTabelaArquivos.size(); i++) {
			if (olTabelaArquivos.get(i).isSelected().get()) {
				String item = olTabelaArquivos.get(i).getNomeAtual();
				posPonto = item.lastIndexOf('.');
				if (posPonto > 0) {
					item = item.substring(0, posPonto);
					olTabelaArquivos.get(i).setNomeApos(item);
				}
			}
		}
	}

	/**
    *
    * @return
    * tratamento de inclusão de extensão para atualização do valor na tabela
    */
	private void incluirExtensao() {
		if (!tfExtensao.getText().isEmpty()) {
			for (int i = 0; i < olTabelaArquivos.size(); i++) {
				if (olTabelaArquivos.get(i).isSelected().get()) {
					String item = olTabelaArquivos.get(i).getNomeAtual();
					item = item + "." + tfExtensao.getText().replace(".", "");
					olTabelaArquivos.get(i).setNomeApos(item);
				}
			}
		}
	}
}

