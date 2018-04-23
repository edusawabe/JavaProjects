/*
 *
 */

package business;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import model.Campo;
import model.ListItem;
import util.ConfigManager;
import br.com.utils.excel.ExcelManager;
import util.HashMapHexAscii;
import util.Util;
import view.MaskTextField;
import view.PairKeyFactory;
import view.PairValueCell;
import view.PairValueFactory;

public class FormatadorCommareaController implements Initializable{
    @FXML private TableView<Pair<String,Object>> tableView;
    @FXML private TableColumn<Pair<String,Object>,String> campoColumn;
    @FXML private TableColumn<Pair<String,Object>,Object> valorColumn;

    @FXML private CheckBox chLineNumber;
    @FXML private TextField tfbookFile;
    @FXML private Button bookSelectButton;
    @FXML private MaskTextField fluxoField;
    @FXML private TextArea bookArea;
    @FXML private TextArea commArea;
		  private LinkedList<Campo> listCampos;
	@FXML private RadioButton entradaRadio;
	@FXML private RadioButton saidaRadio;
	@FXML private RadioButton glogRadio;
	@FXML private RadioButton textoRadio;
	@FXML private RadioButton yy03Radio;
	@FXML private RadioButton yy06Radio;
	@FXML private Button extrairButton;
	@FXML private Button gerarAreaButton;
	@FXML private Button processButton;
	@FXML private Button gerarOccursButton;
	@FXML private Button exportarButton;
	@FXML private CheckBox incluirFinal;
	@FXML private TextCommAreaController txtController;
	      private TextArea txtArea;
	      private boolean development;
	      private boolean hasOccurs;
	      private ConfigManager configManager;
	      final static Logger logger = Logger.getLogger(FormatadorCommareaController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.info("Inicializando GUI");
		logger.error("teste erro GUI");
		development = true;
		hasOccurs = false;
		initComponents();
		fluxoField.setMask("********");
        addFiltersListeners();
        /*exportarButton.setStyle("-fx-background-image: url('/images/excel.png')");*/

     // new Image(url)
        Image image = new Image(FormatadorCommareaController.class.getResource("/images/excel.png").toString());
        // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
        BackgroundSize backgroundSize = new BackgroundSize(40, 40, true, true, true, false);
        // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        // new Background(images...)
        Background background = new Background(backgroundImage);
        exportarButton.setBackground(background);
        //exportarButton.setOpaqueInsets(new Insets(10, 10, 10, 10));
        exportarButton.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
        //exportarButton.setText("Exportar");
	}

	/*
	 * Adicionando os filtros para tratamento de copy paste nos campos de textarea e textfield
	 */
	private void addFiltersListeners() {
		commArea.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if( e.isControlDown() && e.getCode() == KeyCode.V) {
                tratarPaste();
            }
        });
        bookArea.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if( e.isControlDown() && e.getCode() == KeyCode.V) {
            	tratarPaste();
            }
        });
        fluxoField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if( e.isControlDown() && e.getCode() == KeyCode.V) {
            	tratarPasteFluxo();
            }
        });
        commArea.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if( e.isControlDown() && e.getCode() == KeyCode.C) {
                tratarCopy();
            }
        });
        fluxoField.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue.toUpperCase();
            fluxoField.setText(newValue);
        });
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		configManager = new ConfigManager();
		configManager.setConfigFile(new File("./config.txt"));
		String lastDir = configManager.getLastDir(configManager.openConfigFile());
		if (lastDir == null){
			lastDir = "C:\\E!SuperCopia";
		}
		configManager.saveLastDir(true, lastDir);
		hasOccurs = false;
		if (development)
			initializeAreas();
		else{
			tableView.getItems().setAll(FXCollections.observableArrayList());

			listCampos = new LinkedList<Campo>();
		}

		//setting Button states
		extrairButton.setDisable(true);
		gerarAreaButton.setDisable(true);
		processButton.setDisable(true);
		gerarOccursButton.setDisable(true);

		//setting radio states
		yy03Radio.setDisable(true);
		yy06Radio.setDisable(true);
		glogRadio.setDisable(true);
		textoRadio.setDisable(true);

		//setting TextArea configurations
		commArea.setFont(Font.font("Courier New", 12));
		bookArea.setFont(Font.font("Courier New", 12));

		//setting TableView and TableColumns
		tableView.setEditable(true);
		campoColumn.setCellValueFactory(new PairKeyFactory());
		valorColumn.setCellValueFactory(new PairValueFactory());
		valorColumn.setCellFactory(new Callback<TableColumn<Pair<String, Object>, Object>, TableCell<Pair<String, Object>, Object>>() {
			@Override
            public TableCell<Pair<String, Object>, Object> call(TableColumn<Pair<String, Object>, Object> column) {
                return new PairValueCell();
            }
        });

		if (tableView.getColumns().isEmpty()) {
			tableView.getColumns().addAll(campoColumn, valorColumn);
		}

		if (tfbookFile == null)
			tfbookFile = new TextField();
		tfbookFile.setPromptText("Book");
	}

	@FXML
	protected void insereBookRFINW00W(ActionEvent event) {
		inputReturnBook();
	}

	@FXML
	protected void gerarAreaPorGlog(ActionEvent event) {
		try{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Campos Obrigatórios Não Preenchidos");

			if (!yy03Radio.isSelected() && !yy06Radio.isSelected()){
				if (alert.getContentText().isEmpty())
					alert.setContentText("Favor Selecionar o tipo de Área");
				else
					alert.setContentText(alert.getContentText() + "\nFavor Selecionar o tipo de Área");
			}
			if (yy06Radio.isSelected() && fluxoField.getText().isEmpty()){
				if (alert.getContentText().isEmpty())
					alert.setContentText("Favor Preencher o Fluxo");
				else
					alert.setContentText(alert.getContentText() + "\nFavor Preencher o Fluxo");
			}
			if (alert.getContentText().isEmpty()) {
				if(yy03Radio.isSelected())
					commArea.setText(breakLinesYY03(getCommareaByHex()));
				if(yy06Radio.isSelected())
					commArea.setText(breakLinesYY06(getCommareaByHex()));
			}
			else{
				alert.show();
			}
		}catch (Exception e){
			logger.error(e.getMessage());
		}
	}

	@FXML
	protected void openBookFile(ActionEvent event) {
		commArea.setText("");
		if (!incluirFinal.isSelected())
			bookArea.setText("");
		String line;
		String book = new String();
		FileChooser fch = new FileChooser();
		fch.setInitialDirectory(configManager.getLastDirFile());
		File file = fch.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
		tfbookFile.setText(file.getAbsolutePath());
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			line = reader.readLine();
			while (line != null){
				if (line.charAt(6) == ' ')
					book = book + line + "\n";
				line = reader.readLine();
			}
			if (incluirFinal.isSelected())
				bookArea.setText(bookArea.getText() + book);
			else
				bookArea.setText(book);
			reader.close();
			configManager.saveLastDir(false, file.getParentFile().getPath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	protected void extrairBookAction(ActionEvent event) {
		development = false;
		initComponents();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro");
		alert.setHeaderText("Campos Obrigatórios Não Preenchidos");

		if (bookArea.getText().isEmpty())  {
			alert.setContentText("Favor Preencher o Book");
		}
		if (!yy03Radio.isSelected() && !yy06Radio.isSelected()){
			if (alert.getContentText().isEmpty())
				alert.setContentText("Favor Selecionar o tipo de Área");
			else
				alert.setContentText(alert.getContentText() + "\nFavor Selecionar o tipo de Área");
		}
		if (yy06Radio.isSelected() && fluxoField.getText().isEmpty()){
			if (alert.getContentText().isEmpty())
				alert.setContentText("Favor Preencher o Fluxo");
			else
				alert.setContentText(alert.getContentText() + "\nFavor Preencher o Fluxo");
		}
		if (alert.getContentText().isEmpty()) {
			process();
			calculateFieldPosition(null);
			generateTable();
			setRadiosEntrada();
			if (hasOccurs){
				gerarOccursButton.setDisable(false);
				gerarAreaButton.setDisable(true);
			}
			else{
				gerarOccursButton.setDisable(true);
				gerarAreaButton.setDisable(false);
			}
			entradaRadio.setSelected(true);
		}
		else{
			alert.show();
			entradaRadio.setSelected(true);
			setRadiosEntrada();
			yy03Radio.requestFocus();
		}
	}

	@FXML
	protected void extrairSaidaAction(ActionEvent event) {
		try {
			development = false;
			initComponents();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Campos Obrigatórios Não Preenchidos");
			alert.setTitle("Erro");
			alert.setContentText("");

			if (bookArea.getText().isEmpty() && commArea.getText().isEmpty()) {
				alert.setContentText("Favor Preencher o Book e a Commarea");
			} else {
				if (bookArea.getText().isEmpty()) {
					alert.setContentText("Favor Preencher o Book");
				}
				if (commArea.getText().isEmpty()) {
					alert.setContentText("Favor Preenchera Commarea");
				}
			}
			if (!glogRadio.isSelected() && !textoRadio.isSelected()){
				alert.setContentText("Favor Selecionar o tipo de Área");
			}
			if (alert.getContentText().isEmpty()) {
				process();
				if (glogRadio.isSelected())
					processGlog();
				if (textoRadio.isSelected())
					processText();
				saidaRadio.setSelected(true);
				setRadiosSaida();
				processButton.setDisable(false);
			}
			else{
				alert.show();
				saidaRadio.setSelected(true);
				setRadiosSaida();
				processButton.setDisable(false);
				glogRadio.requestFocus();
			}
		} catch (Exception e) {
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        e.printStackTrace(new PrintStream(os));
	        logger.error(new String(os.toByteArray()));
		}
	}

    @FXML
    protected void entradaSelected(ActionEvent event) {
    	if (entradaRadio.isSelected()){
    		setRadiosEntrada();
    	}
    }

    @FXML
    protected void yy06Selected(ActionEvent event) {
    	if (yy06Radio.isSelected()){
    		yy03Radio.setSelected(false);
    	}
    }

    @FXML
    protected void yy03Selected(ActionEvent event) {
    	if (yy03Radio.isSelected()){
    		yy06Radio.setSelected(false);
    	}
    }

    @FXML
    protected void glogSelected(ActionEvent event) {
    	if (glogRadio.isSelected()){
    		textoRadio.setSelected(false);
    	}
    }

    @FXML
    protected void textoSelected(ActionEvent event) {
    	if (textoRadio.isSelected()){
    		glogRadio.setSelected(false);
    	}
    }


	private void setRadiosEntrada() {
		saidaRadio.setSelected(false);
		yy03Radio.setDisable(false);
		yy06Radio.setDisable(false);
		glogRadio.setDisable(true);
		glogRadio.setSelected(false);
		textoRadio.setDisable(true);
		gerarAreaButton.setDisable(true);
		extrairButton.setDisable(false);
		processButton.setDisable(true);
	}

    @FXML
    protected void saidaSelected(ActionEvent event) {
    	if (saidaRadio.isSelected()){
    		setRadiosSaida();
    	}
    }

    @FXML
    private void abrirTXT(ActionEvent event){
    	Stage primaryStage = new Stage();

    	//obtem Loader
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TextCommArea.fxml"));
		try {
			//carrega o loader
			Pane myPane = (Pane) fxmlLoader.load();
			//obtem as informações da tabela
			ObservableList<Pair<String,Object>> oList = this.tableView.getItems();

			//prepara o texto a ser inserido na area de texto da nova janela
			String text = new String();
			Pair<String, Object> item;
			for (int i = 0; i < oList.size(); i++) {
				item = oList.get(i);
				text = text + item.getKey() + "\t" +(String)item.getValue() + "\n";
			}
			//definindo a nova janela
			Scene scene = new Scene(myPane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Commarea em TXT");

			//obtem o controller da nova janela
			txtController =  fxmlLoader.<TextCommAreaController>getController();

			//inclui as informações do texto a abre a janela nova
			txtController.setText(text);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

	@FXML
	private void exportarTabelaExcel(ActionEvent event) {
		ExcelManager excelManager = new ExcelManager();
		excelManager.setFileName("./Export.xlsx");
		excelManager.getlConlumns().add("Campo");
		excelManager.getlConlumns().add("Valor");
		excelManager.getlConlumns().add("Válido?");

		ObservableList<Pair<String, Object>> oList = this.tableView.getItems();
		Pair<String, Object> item;
		for (int i = 0; i < oList.size(); i++) {
			item = oList.get(i);
			excelManager.getlRowValue().add(new LinkedList<String>());
			excelManager.getlRowValue().getLast().add(item.getKey());
			excelManager.getlRowValue().getLast().add((String) item.getValue());
			if(item.getKey().replaceAll(" ", "").contains("PIC9"))
				if(containsOnlyNumbers((String) item.getValue()))
					excelManager.getlRowValue().getLast().add("OK");
				else
					excelManager.getlRowValue().getLast().add("NOK");
			else
				excelManager.getlRowValue().getLast().add("OK");
		}

		excelManager.generateExcelFile();
		Alert al = new Alert(AlertType.INFORMATION);
		al.setContentText("Arquivo Gerado com Sucesso: " + excelManager.getFileName().replaceAll("./", ""));
		al.setTitle("Exportação Realizada");
		al.setHeaderText("Exportação");
		al.show();

	}

	 private boolean containsOnlyNumbers(String str) {
	        //It can't contain only numbers if it's null or empty...
	        if (str == null || str.length() == 0)
	            return false;

	        for (int i = 0; i < str.length(); i++) {

	            //If we find a non-digit character we return false.
	            if (!Character.isDigit(str.charAt(i)))
	                return false;
	        }

	        return true;
	    }

	private void setRadiosSaida() {
    		entradaRadio.setSelected(false);
    		yy03Radio.setSelected(false);
    		yy06Radio.setSelected(false);
    		glogRadio.setSelected(false);
    		textoRadio.setSelected(false);

    		yy03Radio.setDisable(true);
    		yy06Radio.setDisable(true);
    		glogRadio.setDisable(false);
    		textoRadio.setDisable(false);

    		gerarAreaButton.setDisable(true);
    		extrairButton.setDisable(true);
    		processButton.setDisable(false);
    		gerarOccursButton.setDisable(true);
	}

    /*
     * ======================================================================================================
     * END - Actions
     * ======================================================================================================
     */


    /*
     * ======================================================================================================
     * Commarea Processing Methods
     * ======================================================================================================
     */
	private void process() {
		listCampos = new LinkedList<Campo>();
		Campo occursCampo = null;
		Campo campoAtual;
		int aux = 0;

		String bookLine[] = bookArea.getText().split("\\r?\\n");
		for (int i = 0; i < bookLine.length; i++) {
			if (!(bookLine[i].charAt(6) == '*')) {
				aux = i;
				while (!bookLine[i].contains(".")) {
					aux = aux + 1;
					if (aux < bookLine.length) {
						bookLine[i] = bookLine[i] + bookLine[aux];
					}
					else
						break;
				}
				if (bookLine[i].contains(" PIC")) {
					if (bookLine[i].contains(")V9")) {
						campoAtual = processDecimal(bookLine[i]);
					} else {
						if (bookLine[i].contains("9(")) {
							if (bookLine[i].contains("VALUE")) {
								campoAtual = processNumericValue(bookLine[i]);
							} else
								campoAtual = processNumeric(bookLine[i]);
						} else {
							if (bookLine[i].contains("VALUE"))
								campoAtual = processStringValue(bookLine[i]);
							else
								campoAtual = processString(bookLine[i]);
						}
					}

					if (occursCampo != null) {
						if (campoAtual.getNivel().compareTo(occursCampo.getNivel()) > 0)
							occursCampo.getListOccurs().get(0).add(campoAtual);
						else {
							occursCampo = null;
							listCampos.add(campoAtual);
							}
					} else {
						occursCampo = null;
						listCampos.add(campoAtual);
					}
					i = aux;
				} else {
					//esvazia o campo de ocurs para identificar fim da lista de ocurs
					if(occursCampo != null)
						occursCampo = null;
					if (bookLine[i].contains("OCCURS")) {
						if (bookLine[i].contains("."))
							occursCampo = processOccursLine(bookLine[i]);
						else {
							int j = i;
							String toProcess = new String();
							while (!bookLine[j].contains(".")) {
								toProcess = toProcess + bookLine[j];
								j++;
							}
							toProcess = toProcess + bookLine[j];
							occursCampo = processOccursLine(toProcess);
							i = j;
						}
					}
					i = aux;
				}
			}
		}

	}

	private Campo processOccursLine(String line){
		Campo c = new Campo();
		c.setOccurs(true);
		c.newListOccurs();
		int posPonto = line.lastIndexOf('.');
		int posOccurs = line.indexOf("OCCURS");
		int posTo = line.indexOf(" TO ");
		int posTimes = line.indexOf("TIMES");
		int posDependingON = line.indexOf("DEPENDING ON");
		if (posTo > 0)
			c.setTimes(Integer.parseInt(line.substring(posTo + 3, posTimes -1).replaceAll(" ", "")));
		else{
			c.setTimes(Integer.parseInt(line.substring(posOccurs + 7, posTimes -1).replaceAll(" ", "")));
		}
		if (posDependingON > 0){
			c.setDependingOn(line.substring(posDependingON + 13, posPonto).replaceAll(" ", ""));
			setCampoDependingOnValue(c.getDependingOn());
			hasOccurs = true;
		}
		else{
			if (!hasOccurs)
				hasOccurs = false;
		}
		listCampos.add(c);
		getNomeNivelCampo(line,c);
		return c;
	}

	private void getNomeNivelCampo(String line, Campo c){
		String breakLine[]  = line.split("\\s+");
		int cont = 0;

		for (int i = 0; i < breakLine.length; i++) {
				if (!breakLine[i].isEmpty()) {
					if(cont == 0){
						if(isNumeric(breakLine[i])){
							cont++;
						}
					} else {
						cont++;
					}
				}
				if(cont == 1)
					c.setNivel(breakLine[i]);
				if(cont == 2)
					c.setNome(breakLine[i]);
		}
	}

	private boolean isNumeric(String n){
		try {
			Integer.parseInt(n);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private Campo processStringValue(String line){
		int beginPic, endPic, beginValue, endValue;
		beginPic = line.indexOf("X(") + 2;
		endPic = line.indexOf(") ");
		beginValue = line.indexOf(" '") + 2;
		endValue = line.indexOf("'.");

		Campo c = new Campo();
		getNomeNivelCampo(line, c);
		c.setTam(Integer.parseInt(line.substring(beginPic, endPic)));
		c.setType(line.substring(beginPic-6, endPic+1));
		c.setValor(line.substring(beginValue, endValue));
		return c;
	}

	private Campo processString(String line){
		int beginPic, endPic, posPonto;
		beginPic = line.indexOf("X(") + 2;
		endPic = line.indexOf(").");

		Campo c = new Campo();
		getNomeNivelCampo(line, c);
		c.setTam(Integer.parseInt(line.substring(beginPic, endPic)));

		posPonto = line.lastIndexOf('.');
		c.setType(line.substring(beginPic - 6, posPonto));
		return c;
	}

	private Campo processNumeric(String line){
		int beginPic, endPic, posPonto;

		beginPic = line.indexOf("9(") + 2;
		endPic = line.indexOf(").");

		Campo c = new Campo();
		getNomeNivelCampo(line, c);
		c.setTam(Integer.parseInt(line.substring(beginPic, endPic)));

		posPonto = line.lastIndexOf('.');
		c.setType(line.substring(beginPic - 6, posPonto));
		return c;
	}

	private Campo processNumericValue(String line){
		int beginPic, endPic, beginValue, endValue;
		beginPic = line.indexOf("9(") + 2;
		endPic = line.indexOf(") ");
		beginValue = line.indexOf("VALUE ") + 6;
		endValue = line.indexOf(".");

		Campo c = new Campo();
		getNomeNivelCampo(line, c);
		c.setTam(Integer.parseInt(line.substring(beginPic, endPic)));
		c.setType(line.substring(beginPic - 6, endPic + 1));
		c.setValor((line.substring(beginValue, endValue)).replaceAll(" ", ""));
		return c;
	}

	private Campo processDecimal(String line){
		int beginPic = 0, beginV, endV, endPic, posPonto;
		Campo c = new Campo();

		if (line.contains(")V9(")){
			beginPic = line.indexOf(" 9(") + 3;
			beginV = line.indexOf(")V9(");
			endV = line.indexOf(")V9(") + 4;
			endPic = line.indexOf(").");
			c.setTam((Integer.parseInt(line.substring(beginPic, beginV))) + (Integer.parseInt(line.substring(endV, endPic))));
		}

		if (line.contains(")V99")){
			beginPic = line.indexOf(" 9(") + 3;
			beginV = line.indexOf(")V9");
			endPic = line.indexOf(").");
			c.setTam((Integer.parseInt(line.substring(beginPic, beginV)) + (2)));
		}

		if (line.contains(")V999")){
			beginPic = line.indexOf(" 9(") + 3;
			beginV = line.indexOf(")V9");
			endPic = line.indexOf(").");
			c.setTam((Integer.parseInt(line.substring(beginPic, beginV)) + (3)));
		}

		if (line.contains(")V9999")){
			beginPic = line.indexOf(" 9(") + 3;
			beginV = line.indexOf(")V9");
			endPic = line.indexOf(").");
			c.setTam((Integer.parseInt(line.substring(beginPic, beginV)) + (4)));
		}

		getNomeNivelCampo(line, c);
		posPonto = line.lastIndexOf('.');
		c.setType(line.substring(beginPic - 6, posPonto));
		return c;
	}

	private String getCommareaByHex(){
		String line[] = commArea.getText().split("\\r?\\n");
		String subLine[];
		String commArea = new String();
		for (int i = 0; i < line.length; i++) {
			if ((line[i].charAt(5) == (':')) && (!(line[i].substring(0, 5).equals("Grupo")))) {
				subLine = line[i].split(": ");
				if (i < (line.length-1))
					if (commArea.isEmpty())
						commArea = commArea + subLine[1].substring(0, 59);
					else
						commArea = commArea + " " + subLine[1].substring(0, 59);
				else
					commArea = commArea + " " + processGlogFinalLine(subLine[1]);

			}
		}
		return convertHextoText(commArea);
	}

	private void processGlog(){
		String commArea = getCommareaByHex();
		calculateFieldPosition(commArea);
		generateCommAreaTable(commArea);
		/*for (Iterator<Campo> iterator = listCampos.iterator(); iterator.hasNext();) {
			Campo campo = (Campo) iterator.next();
			System.out.println(campo.getNivel() + " - " + campo.getNome() + " - " + campo.getTam() + " - "
					+ campo.getPos() + ": " + campo.getValor());
		}*/
	}

	private void processText(){
		String line[] = commArea.getText().split("\\r?\\n");
		String commArea = new String();
		for (int i = 0; i < line.length; i++) {
			commArea = commArea + (line[i].replaceAll("\\n", ""));
		}
		calculateFieldPosition(commArea);
		generateCommAreaTable(commArea);
	}

	private String processGlogFinalLine(String line){
		String ret = new String();
		int i = 0;

		while (i < line.length()) {
			if((i + 1) < line.length()){
				if ((line.charAt(i) != ' ') && (line.charAt(i) != '\n') && (line.charAt(i) != '\t')){
					if (ret.isEmpty())
						ret = "" + line.charAt(i) + line.charAt(i + 1);
					else
						ret = ret + " " + line.charAt(i) + line.charAt(i + 1);
					i = i + 3;
				}
				else
					break;
			}
			else
				break;
		}
		return ret;
	}

	private String convertHextoText(String line){
		String converted = new String();
		String charToConvert[];
		charToConvert = line.split("\\s+");
		HashMapHexAscii converter = new HashMapHexAscii();

		for (int i = 0; i < charToConvert.length; i++) {
			if(converter.convertHexToAscii(charToConvert[i]) != null)
				converted = converted + converter.convertHexToAscii(charToConvert[i]);
		}

		return converted;
	}

	private void calculateFieldPosition(String commArea){
		int pos = 0;
		for (Campo campo : listCampos) {
			if(campo.isOccurs()){
				if (campo.getDependingOn() == null || campo.getDependingOn().isEmpty()) {
					for (int i = 0; i < campo.getTimes(); i++) {
						if(i > 0){
							campo.getListOccurs().add(new LinkedList<Campo>());
							for (Iterator<Campo> iterator = campo.getListOccurs().get(0).iterator(); iterator.hasNext();) {
								Campo campo2 = (Campo) iterator.next();
								Campo campoCopy = (Campo) campo2.clone();
								campo.getListOccurs().get(i).add(campoCopy);
							}
						}
						for (Campo listC : campo.getListOccurs().get(i)) {
							listC.setPos(pos);
							pos = pos + listC.getTam();
						}
					}
				}
				else {
					int size;
					if (commArea == null)
						size = campo.getTimes();
					else
						size = getDependingOnValue(campo.getDependingOn(), commArea);
					for (int i = 0; i < size; i++) {
						if(i > 0){
							campo.getListOccurs().add(new LinkedList<Campo>());
							for (Iterator<Campo> iterator = campo.getListOccurs().get(0).iterator(); iterator.hasNext();) {
								Campo campo2 = (Campo) iterator.next();
								Campo campoCopy = (Campo) campo2.clone();
								campo.getListOccurs().get(i).add(campoCopy);
							}
						}
						for (Campo listC : campo.getListOccurs().get(i)) {
							listC.setPos(pos);
							pos = pos + listC.getTam();
						}
					}
				}
			}
			else {
				campo.setPos(pos);
				pos = pos + campo.getTam();
			}
		}
	}

	private int setCampoDependingOnValue(String dependingOn){
		for (Campo campo : listCampos) {
			if (campo.getNome().equals(dependingOn)){
				campo.setDependingOnField(true);
			}
		}
		return 0;
	}

	private int getDependingOnValue(String dependingOn, String commArea){
		for (Campo campo : listCampos) {
			if (campo.getNome().equals(dependingOn)){
				campo.setDependingOnField(true);
				return (Integer.parseInt(commArea.substring(campo.getPos(), campo.getPos() + campo.getTam())));
			}
		}
		return 0;
	}

	private void generateCommAreaTable(String commArea){
		ObservableList<Pair<String,Object>> commAreaList = tableView.getItems();

		for (Campo campo : listCampos) {
			if(!campo.isOccurs()){
				ListItem item = new ListItem();
				item.setCampo(campo.getNivel() + " - " + campo.getNome() + " - " + campo.getType());
				item.setValor(commArea.substring(campo.getPos(), campo.getPos() + campo.getTam()));
				campo.setValor(commArea.substring(campo.getPos(), campo.getPos() + campo.getTam()));
				item.setMask(campo.getMask());
				commAreaList.add(new Pair<String, Object>(item.getCampo(), new String(campo.getValor())));
			}
			else{
				int size = 0;
				if (campo.getDependingOn() == null) {
					size = campo.getListOccurs().size();
				} else {
					if (campo.getDependingOn().isEmpty())
						size = campo.getListOccurs().size();
					else
						size = getDependingOnValue(campo.getDependingOn(), commArea);
				}
				for (int i = 0; i < size; i++) {
					LinkedList<Campo> listItem = campo.getListOccurs().get(i);
					for (Campo listCampo : listItem) {
						ListItem item = new ListItem();
						item.setCampo("   " + listCampo.getNivel() + " - " + listCampo.getNome() + "(" + i + ")" + " - "
								+ listCampo.getType());
						item.setValor(commArea.substring(listCampo.getPos(), listCampo.getPos() + listCampo.getTam()));
						listCampo.setValor(commArea.substring(listCampo.getPos(), listCampo.getPos()));
						item.setMask(listCampo.getMask());
						commAreaList.add(new Pair<String, Object>(item.getCampo(), new String(item.getValor())));
					}
				}
			}
		}
	}

	/**
	 *
	 */
	private void generateTable(){
		ObservableList<Pair<String,Object>> commAreaList = tableView.getItems();
		commAreaList.clear();

		for (Campo campo : listCampos) {
			if(!campo.isOccurs()){
				ListItem item = new ListItem(campo.getNivel() + " - " + campo.getNome(), "", "");
				item.setCampo(campo.getNivel() + " - " + campo.getNome() + " - " + campo.getType());
				item.setMask(campo.getMask());
				if (campo.getValor() != null)
					commAreaList.add(new Pair<String, Object>(item.getCampo(),new MaskTextField(campo.getValor(),item.getMask(),campo.isDependingOnField())));
				else
					commAreaList.add(new Pair<String, Object>(item.getCampo(),new MaskTextField("",item.getMask(),campo.isDependingOnField())));
			}
			else{
				int size = 0;
				if (campo.getDependingOn() == null)
					size = campo.getListOccurs().size();
				else {
					if (campo.getDependingOn().isEmpty())
						size = campo.getListOccurs().size();
					else {
						commAreaList.add(new Pair<String, Object>(campo.getNome(),
								new MaskTextField(campo.getValor(), "", false)));
						size = 0;
					}
				}
				for (int i = 0; i < size; i++) {
					LinkedList<Campo> listItem = campo.getListOccurs().get(i);
					for (Campo listCampo : listItem) {
						ListItem item = new ListItem("   " + listCampo.getNivel() + " - " + listCampo.getNome(), "",
								"");
						item.setCampo("   " + listCampo.getNivel() + " - " + listCampo.getNome() + "(" + i + ")" + " - "
								+ listCampo.getType());
						item.setMask(listCampo.getMask());
						if (campo.getValor() != null)
							commAreaList.add(new Pair<String, Object>(item.getCampo(),
									new MaskTextField(campo.getValor(), item.getMask(), campo.isDependingOnField())));
						else
							commAreaList.add(new Pair<String, Object>(item.getCampo(),
									new MaskTextField("", item.getMask(), campo.isDependingOnField())));
					}
				}
			}
		}
	}

	private int getDependingOnListIndex(ObservableList<Pair<String,Object>> commAreaList){
		int index = 0;
		String campoOccurs = new String();
		for (Campo campo : listCampos){
			if(campo.getListOccurs() != null && campo.getDependingOn() != null){
				campoOccurs = campo.getNome();
				break;
			}
		}

		for (int i = 0; i < commAreaList.size(); i++) {
			if (commAreaList.get(i).getKey().equals(campoOccurs)){
					index = i;
					break;
			}
		}
		return index;
	}

	@FXML
	private void generateOccursTable(ActionEvent event){
		gerarAreaButton.setDisable(false);
		gerarOccursButton.setDisable(true);
		ObservableList<Pair<String,Object>> commAreaList = tableView.getItems();
		int index = getDependingOnListIndex(commAreaList);
		int addedOccurs = 0;
		commAreaList.remove(index);
		int size = 0;
		for (Campo campo : listCampos) {
			if(campo.isOccurs() && campo.getDependingOn() != null){
				for (int i = 0; i < commAreaList.size(); i++) {
					if(isDependingOnField(commAreaList.get(i).getKey().split(" "), campo.getDependingOn())){
						size = Integer.parseInt(((MaskTextField)commAreaList.get(i).getValue()).getText());
						break;
					}
				}
				for (int i = 0; i < size; i++) {
					LinkedList<Campo> listItem = campo.getListOccurs().get(i);
					for(Campo listCampo : listItem){
						ListItem item = new ListItem("   " + listCampo.getNivel() + " - " + listCampo.getNome(), "", "");
						item.setCampo("   " + listCampo.getNivel() + " - " + listCampo.getNome() + "(" + i + ")" + " - " + listCampo.getType());
						item.setMask(listCampo.getMask());
						if (listCampo.getValor() != null)
							commAreaList.add(index + addedOccurs,new Pair<String, Object>(item.getCampo(), new MaskTextField(listCampo.getValor(),item.getMask(),campo.isDependingOnField())));
						else
							commAreaList.add(index + addedOccurs, new Pair<String, Object>(item.getCampo(), new MaskTextField("",item.getMask(),campo.isDependingOnField())));
						addedOccurs++;
					}
				}
			}
		}
	}

	@FXML
	private void generateCommArea(ActionEvent event){
		commArea.setText("");

		if (yy06Radio.isSelected())
			commArea.setText(generateCommAreaYY06());

		if (yy03Radio.isSelected())
			commArea.setText(generateCommAreaYY03());
	}

	private String generateCommAreaYY06(){
		ObservableList<Pair<String,Object>> commAreaList = tableView.getItems();
		String area;

		String enteredArea = new String();

		for (int i = 0; i < commAreaList.size(); i++) {
			if (!(((MaskTextField) commAreaList.get(i).getValue()).getText() == null)) {
				if (((MaskTextField)commAreaList.get(i).getValue()).getInformedMask().contains("N"))
					enteredArea = enteredArea + Util.completeZeros(((MaskTextField)commAreaList.get(i).getValue()).getText(), ((MaskTextField)commAreaList.get(i).getValue()).getInformedMask().length());
				else
					enteredArea = enteredArea + Util.completeSpaces(((MaskTextField)commAreaList.get(i).getValue()).getText(), ((MaskTextField)commAreaList.get(i).getValue()).getInformedMask().length());
			}
		}

		area = breakLinesYY06(enteredArea);

		return area;
	}

	private String breakLinesYY06(String enteredArea){
		String sCommAreaSize = new String(), area;
		int commAreaSize;
		String fluxo;
		int begin = 0;
		int end = 0;
		int aux;

		if (fluxoField.getText() != null)
			fluxo = fluxoField.getText();
		else
			fluxo = "RFINIAAR";

		if (fluxo.isEmpty())
			fluxo = "RFINIAAR";

		commAreaSize = Integer.parseInt(enteredArea.substring(8, 13)) + 271;
		sCommAreaSize = sCommAreaSize + commAreaSize;
		sCommAreaSize = Util.completeZeros(sCommAreaSize, 5);

		area =
				generateLineNumber(0) + "FRWKGL010021600"+ sCommAreaSize +"                                51174335457216TERM\n"+
			    generateLineNumber(70) + "00011                 "+ fluxo +"0023700001050TERM0001       012008121215\n"+
			    generateLineNumber(70*2) + "4737NNNIEI910940                       E                              \n"+
			    generateLineNumber(70*3) + "217230GSEGGLAA00230                    EI910940                       \n"+
			    generateLineNumber(70*4) + "NN                                                                    \n"+
			    generateLineNumber(70*5) + "                                                                      \n"+
			    generateLineNumber(70*6) + "                          GSEGGLAE00041006Nsenha002                ";

		area = area + enteredArea.substring(0, 3) +  "\n";

		for (int i = 0; i < (enteredArea.length()); i++) {
			begin = 3 + (70 * i);
			end = 3 + (70 + (70 * i));
			if(begin > enteredArea.length())
				break;
			if (end > enteredArea.length()) {
				aux = enteredArea.length() - begin;
				end = begin + aux;
			}
			area = area + generateLineNumber((70 * (7 + i))) + enteredArea.substring(begin, end) + "\n";
		}

		return area;
	}

	private String generateCommAreaYY03(){
		ObservableList<Pair<String,Object>> commAreaList = tableView.getItems();
		int commAreaSize;
		String sCommAreaSize = new String();
		String area;

		String enteredArea = new String();

		for (int i = 0; i < commAreaList.size(); i++) {
			if (!(((MaskTextField) commAreaList.get(i).getValue()).getText() == null)) {
				if (((MaskTextField) commAreaList.get(i).getValue()).getInformedMask().contains("N"))
					enteredArea = enteredArea
							+ Util.completeZeros(((MaskTextField) commAreaList.get(i).getValue()).getText(),
									((MaskTextField) commAreaList.get(i).getValue()).getInformedMask().length());
				else
					enteredArea = enteredArea
							+ Util.completeSpaces(((MaskTextField) commAreaList.get(i).getValue()).getText(),
									((MaskTextField) commAreaList.get(i).getValue()).getInformedMask().length());
			}
		}

		commAreaSize = Integer.parseInt(enteredArea.substring(8, 13)) + 271;
		sCommAreaSize = sCommAreaSize + commAreaSize;
		sCommAreaSize = Util.completeZeros(sCommAreaSize, 5);

		area = breakLinesYY03(enteredArea);

		return area;
	}

	private String breakLinesYY03(String enteredArea){
		int begin = 0;
		int end = 0;
		int aux;
		String area = new String();

		for (int i = 0; i < (enteredArea.length()); i++) {
			begin = (70 * i);
			end = (70 + (70 * i));
			if(begin > enteredArea.length())
				break;
			if (end > enteredArea.length()) {
				aux = enteredArea.length() - begin;
				end = begin + aux;
			}
			area = area + generateLineNumber(begin) + enteredArea.substring(begin, end) + "\n";
		}

		return area;
	}

	private String generateLineNumber(int begin){
		if(chLineNumber.isSelected()){
			String lineNumber = new String();
			begin++;
			if (begin < 10)
				lineNumber = "    " + begin;
			else{
				if(begin < 100)
					lineNumber = "   " + begin;
				else {
					if(begin < 1000)
						lineNumber = "  " + begin;
					else {
						if(begin < 10000)
							lineNumber = " " + begin;
						else
							lineNumber =  "" + begin;
					}
				}
			}
			return lineNumber + ": ";
		}
		return "";
	}

    /*
     * ======================================================================================================
     * END - Commarea Processing Methods
     * ======================================================================================================
     */


	/*
	 * ======================================================================================================
	 * Development Methods
	 * ======================================================================================================
	 */
	public void initializeAreas(){
		bookArea.setText(
		          "       05  RFINWJ6S-HEADER.                                             \n"
				 +"           10 RFINWJ6S-COD-LAYOUT          PIC X(008)  VALUE 'RFINWJ6S'.\n"
				 +"           10 RFINWJ6S-TAM-LAYOUT          PIC 9(005)  VALUE 00506.     \n"
				 +"       05  RFINWJ6S-REGISTRO.                                           \n"
				 +"           10 RFINWJ6S-BLOCO-SAIDA.                                     \n"
				 +"              15 RFINWJ6S-S-CTPO-SISTC-REPAS      PIC 9(001).           \n"
				 +"              15 RFINWJ6S-S-CEXTER-OPER-REPAS     PIC 9(010).           \n"
				 +"              15 RFINWJ6S-S-CESTAG-REPAS-FINAN    PIC 9(003).           \n"
				 +"              15 RFINWJ6S-S-IESTAG-REPAS-FINAN    PIC X(060).           \n"
				 +"              15 RFINWJ6S-S-CSIT-REPAS-FINAN      PIC 9(003).           \n"
				 +"              15 RFINWJ6S-S-NCARAC-PROG-REPAS     PIC 9(005).           \n"
				 +"              15 RFINWJ6S-S-CREPAS-CSCIO-BCO      PIC X(001).           \n"
				 +"              15 RFINWJ6S-S-CCNPJ-FORNC           PIC 9(014).           \n"
				 +"              15 RFINWJ6S-S-IPSSOA-REPAS-FINAN    PIC X(080).           \n"
				 +"              15 RFINWJ6S-S-CCNPJ-FABR            PIC 9(014).           \n"
				 +"              15 RFINWJ6S-S-IPSSOA-FABR           PIC X(080).           \n"
				 +"              15 RFINWJ6S-S-IPSSOA-PROMO          PIC X(080).           \n"
				 +"              15 RFINWJ6S-S-CEXTER-CONDC-OPER     PIC 9(004).           \n"
				 +"              15 RFINWJ6S-S-IEXTER-OPER-REPAS     PIC X(060).           \n"
				 +"              15 RFINWJ6S-S-CCONVE-LIM            PIC 9(005).           \n"
				 +"              15 RFINWJ6S-S-CSEQ-CONVE-LIM        PIC 9(003).           \n"
				 +"              15 RFINWJ6S-S-NOME-CONVENIO         PIC X(040).           \n"
				 +"              15 RFINWJ6S-S-DVALDD-RESU-SOLTC     PIC X(010).           \n"
				 +"              15 RFINWJ6S-S-VTOT-SDO-LIB          PIC 9(015)V99.        \n"
				 +"              15 RFINWJ6S-S-QTDE-PARCELAS         PIC 9(003).           \n"
				 +"              15 RFINWJ6S-S-LIST OCCURS 3 TIMES.                        \n"
				 + "                20 RFINWJ6S-S-DATA-PARCELA       PIC X(010).           \n"
				 + "                20 RFINWJ6S-S-DATA-AMORTIZACAO   PIC X(010).           \n"
		);

		bookArea.setText(
				            "           05 RFINW12E-HEADER.                                         \n"
						  + "             10 RFINW12E-COD-LAYOUT    PIC X(08)       VALUE 'RFINW12E'.\n"
						  + "             10 RFINW12E-TAM-LAYOUT    PIC 9(05)       VALUE 01308.     \n"
						  + "           05 RFINW12E-REGISTRO.                                        \n"
						  + "             10 RFINW12E-BLOCO-ENTRADA.                                 \n"
						  + "                15 RFINW12E-NCARAC-PROG-REPAS          PIC 9(005).      \n"
						  + "                15 RFINW12E-FORMA-PGTO                 PIC 9(001).      \n"
						  + "                15 RFINW12E-CPRODT-SERVC               PIC 9(008).      \n"
						  + "                15 RFINW12E-CTPO-PROG                  PIC 9(001).      \n"
						  + "                15 RFINW12E-CPROG-FINAN                PIC 9(008).      \n"
						  + "                15 RFINW12E-CPROG-ORIGE                PIC 9(008).      \n"
						  + "                15 RFINW12E-DINIC-VIG-PROG             PIC X(010).      \n"
						  + "                15 RFINW12E-DFIM-VIG-PROG              PIC X(010).      \n"
						  + "                15 RFINW12E-CFNALD-PROG-FINAN          PIC 9(002).      \n"
						  + "                15 RFINW12E-CPROG-FONTE-REPAS          PIC 9(003).      \n"
						  + "                15 RFINW12E-NSUB-PROG-REPAS            PIC 9(003).      \n"
						  + "                15 RFINW12E-CTPO-RENDA-REPAS           PIC 9(001).      \n"
						  + "                15 RFINW12E-PCOMPS-AGROP-REPAS         PIC 9(003)V9(04).\n"
						  + "                15 RFINW12E-CPERM-AMPL-REPAS           PIC X(001).      \n"
						  + "                15 RFINW12E-CUTILZ-ATVDD-REPAS         PIC X(001).      \n"
						  + "                15 RFINW12E-CUTILZ-MUN-REPAS           PIC X(001).      \n"
						  + "                15 RFINW12E-CPROG-CERTF-ENQUA          PIC X(001).      \n"
						  + "                15 RFINW12E-VMIN-CERTF-REPAS           PIC 9(013)V9(02).\n"
						  + "                15 RFINW12E-QMAX-IDADE-EQPMT           PIC 9(003).      \n"
						  + "                15 RFINW12E-CPERM-INCTV-REPAS          PIC X(001).      \n"
						  + "                15 RFINW12E-CEXCVD-CRRTT-REPAS         PIC X(001).      \n"
						  + "                15 RFINW12E-CPROG-LIM-FINAN            PIC X(001).      \n"
						  + "                15 RFINW12E-VLIM-FINAN-CLI             PIC 9(015)V9(02).\n"
						  + "                15 RFINW12E-DCOMPT-CONSL               PIC X(010).      \n"
						  + "                15 RFINW12E-DCOMPT-CONSL-CLI           PIC X(010).      \n"
						  + "                15 RFINW12E-VLIM-CONTR-COLET           PIC 9(013)V9(02).\n"
						  + "                15 RFINW12E-CFINAN-LIQDC-ANTER         PIC X(001).      \n"
						  + "                15 RFINW12E-CCOMPS-EMPTO-REPAS         PIC X(001).      \n"
						  + "                15 RFINW12E-CRENDA-NVEL-REPAS          PIC 9(001).      \n"
						  + "                15 RFINW12E-CRENDA-JURO-REPAS          PIC 9(001).      \n"
						  + "                15 RFINW12E-CUTILZ-PROG-ATVDD          PIC X(001).      \n"
						  + "                15 RFINW12E-CGARTD-INVES-REPAS         PIC 9(001).      \n"
						  + "                15 RFINW12E-PMIN-RISCO-REPAS           PIC 9(003)V9(04).\n"
						  + "                15 RFINW12E-PMAX-RISCO-REPAS           PIC 9(003)V9(04).\n"
						  + "                15 RFINW12E-FORMA-PAGTO                PIC 9(001).      \n"
						  + "                15 RFINW12E-TMIN-CAREN-REPAS           PIC 9(003).      \n"
						  + "                15 RFINW12E-TMAX-CAREN-REPAS           PIC 9(003).      \n"
						  + "                15 RFINW12E-TMIN-AMOTZ-REPAS           PIC 9(003).      \n"
						  + "                15 RFINW12E-TMAX-AMOTZ-REPAS           PIC 9(003).      \n"
						  + "                15 RFINW12E-CTPO-SISTC-REPAS           PIC 9(001).      \n"
						  + "                15 RFINW12E-VLIM-FINAN-SIMP            PIC 9(013)V9(02).\n"
						  + "                15 RFINW12E-CPROG-LIM-SIMP             PIC X(001).      \n"
						  + "                15 RFINW12E-DINIC-SIMP-REPAS           PIC X(010).      \n"
						  + "                15 RFINW12E-DFIM-SIMP-REPAS            PIC X(010).      \n"
						  + "                15 RFINW12E-TVALDD-DOCTO-FONTE         PIC 9(003).      \n"
						  + "                15 RFINW12E-TMAX-SOLTC-PDIDO           PIC 9(003).      \n"
						  + "                15 RFINW12E-CREGRA-MNUAL-RURAL         PIC X(001).      \n"
						  + "                15 RFINW12E-CPROG-FSCAL-RURAL          PIC X(001).      \n"
						  + "                15 RFINW12E-CPCELA-FSCAL-RURAL         PIC 9(005).      \n"
						  + "                15 RFINW12E-CPRMSS-COMCZ-REPAS         PIC 9(001).      \n"
						  + "                15 RFINW12E-CSISTC-DEPTO-REPAS         PIC 9(001).      \n"
						  + "                15 RFINW12E-VLIM-DEPTO-REPAS           PIC 9(013)V9(02).\n"
						  + "                15 RFINW12E-CSISTC-AG-REPAS            PIC 9(001).      \n"
						  + "                15 RFINW12E-VLIM-AG-REPAS              PIC 9(013)V9(02).\n"
						  + "                15 RFINW12E-CPRODT-PROG-DECLR          PIC X(001).      \n"
						  + "                15 RFINW12E-VBASE-SOLTC-VIABL          PIC 9(013)V9(02).\n"
						  + "                15 RFINW12E-TAVISO-LCTO-FUTUR          PIC 9(003).      \n"
						  + "                15 RFINW12E-TENVIO-COBR-REPAS          PIC 9(003).      \n"
						  + "                15 RFINW12E-TMAX-EXCUC-PROJ            PIC 9(003).      \n"
						  + "                15 RFINW12E-TMIN-REFIN-REPAS           PIC 9(003).      \n"
						  + "                15 RFINW12E-TTRNSF-CREDT-ATRSO         PIC 9(003).      \n"
						  + "                15 RFINW12E-TTRNSF-LUCRO-PERDA         PIC 9(003).      \n"
						  + "                15 RFINW12E-CSIT-PROG-REPAS            PIC X(001).      \n"
						  + "                15 RFINW12E-CPRODT-EXIGE-BEM           PIC X(001).      \n"
						  + "                15 RFINW12E-CPROG-COMPS-LIM            PIC X(001).      \n"
						  + "                15 RFINW12E-CTPO-COMPV-REPAS           PIC 9(001).      \n"
						  + "                15 RFINW12E-CATVDD-PRINC-REPAS         PIC X(001).      \n"
						  + "                15 RFINW12E-CEXCVD-FABRT-REPAS         PIC X(001).      \n"
						  + "                15 RFINW12E-CEXCVD-FORNC-REPAS         PIC X(001).      \n"
						  + "                15 RFINW12E-CCOOP-PERM-SING            PIC X(001).      \n"
						  + "                15 RFINW12E-CCOOP-PERM-FED             PIC X(001).      \n"
						  + "                15 RFINW12E-CCOOP-PERM-CONF            PIC X(001).      \n"
						  + "                15 RFINW12E-CCOOP-PERM-NA              PIC X(001).      \n"
						  + "                15 RFINW12E-CEXIGE-LIM-COOP            PIC X(001).      \n"
						  + "                15 RFINW12E-VLIM-SINGR-REPAS           PIC 9(013)V99.   \n"
						  + "                15 RFINW12E-VLIM-CNTRL-REPAS           PIC 9(013)V99.   \n"
						  + "                15 RFINW12E-VLIM-ASSOC-REPAS           PIC 9(013)V99.   \n"
						  + "                15 RFINW12E-CPERM-SEG-REPAS            PIC X(001).      \n"
						  + "                15 RFINW12E-CSEGUR-CONTR-REPAS         PIC 9(001).      \n"
						  + "                15 RFINW12E-CNIVEL-PART-SEG            PIC X(001).      \n"
						  + "                15 RFINW12E-CGARNT-REAL                PIC X(001).      \n"
						  + "                15 RFINW12E-CGARNT-PESSOAL             PIC X(001).      \n"
						  + "                15 RFINW12E-PGARNT-REAL-REPAS          PIC 9(003)V9(04).\n"
						  + "                15 RFINW12E-CEXIGE-STUDO-VIABL         PIC X(001).      \n"
						  + "                15 RFINW12E-CSIT-BEM                   PIC X(001).      \n"
						  + "                15 RFINW12E-QTDE-OCOR-LIM              PIC 9(005).      \n"
						  + "                15 RFINW12E-LISTA-LIM                  OCCURS 50 TIMES. \n"
						  + "                   20 RFINW12E-CPROG-FINAN-LIM         PIC 9(008).      \n"
						  + "                15 RFINW12E-QTDE-OCOR-SIST             PIC 9(005).      \n"
						  + "                15 RFINW12E-LISTA-SIST                 OCCURS 50 TIMES. \n"
						  + "                   20 RFINW12E-CPROG-FINAN-SIST        PIC 9(008).      \n"
						  + "ARC             15 RFINW12E-CEXTER-PROG-BACEN          PIC 9(004).      \n"
						  + ".               15 RFINW12E-TVALDD-DOCTO-COMPV         PIC 9(003).      \n"
						  + ".               15 RFINW12E-TMAX-CAREN-AMOTZ           PIC 9(003).      \n"
						  + "ARC             15 RFINW12E-TMIN-CAREN-AMOTZ           PIC 9(003).      \n"
						  + "THIAGO          15 RFINW12E-VLIM-RECTA-OPER            PIC 9(015)V99.   \n"
						  + "THIAGO          15 RFINW12E-VLIM-RECTA-AGROP           PIC 9(015)V99.   \n"
						  + "IP60 T          15 RFINW12E-CSGMTO-EQPMT-REPAS         PIC 9(001).      \n"
						  + "IP60 T          15 RFINW12E-CCLASF-EQPMT-REPAS         PIC 9(003).      \n"
						  + "RLOS            15 RFINW12E-VMIN-PROG-REPAS            PIC 9(015)V9(02).\n"
						  + ".               15 RFINW12E-VMAX-PROG-REPAS            PIC 9(015)V9(02).\n"
						  + ".               15 RFINW12E-CUTILZ-GIRO-REPAS          PIC X(001).      \n"
						  + ".               15 RFINW12E-CJURO-GARTD-REPAS          PIC X(001).      \n"
						  + "RLOS            15 RFINW12E-TVALDD-FONTE-CRONO         PIC 9(003).      \n"
						  + "ARC1            15 RFINW12E-CEXTER-SUB-BACEN           PIC 9(004).      \n"
						  + "ARC1            15 RFINW12E-NPROG-BACEN-REPAS          PIC 9(004).      \n"
						  + "ARC1            15 RFINW12E-NSUB-BACEN-REPAS           PIC 9(003).      \n"
						  + "TND             15 RFINW12E-CFNALD-EXCVD-OPER          PIC 9(001).      \n"
						  + "TND             15 RFINW12E-CPROG-EXIGE-PROJ           PIC X(001).      \n"
						  + "TND             15 RFINW12E-CUTILZ-APOIO-RURAL         PIC X(001).      \n"
						  + "TND             15 RFINW12E-CEXIGE-ELABO-PROJ          PIC X(001).      \n"
						  + "TND             15 RFINW12E-VLIM-POR-CONTR-C           PIC 9(013)V9(02).\n"
				);

		commArea.setText(
				            "00001: D9 C6 C9 D5 E6 F1 F2 C5 F0 F1 F3 F1 F0 F0 F3 F2 F3 F6 F0 F0  RFINW12E013100323600\n"
						  + "00021: F0 F0 F0 F1 F7 F2 F3 F1 F0 F0 F0 F0 F2 F8 F0 F9 F0 F0 F0 F0  00017231000028090000\n"
						  + "00041: F0 F0 F0 F0 F0 F1 4B F0 F1 4B F2 F0 F1 F7 F3 F1 4B F1 F2 4B  000001.01.201731.12.\n"
						  + "00061: F2 F0 F2 F1 F0 F0 F0 F0 F0 F0 F0 F0 F1 F0 F0 F0 F0 F0 F0 F0  20210000000010000000\n"
						  + "00081: D5 D7 D7 40 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  NPP 0000000000000000\n"
						  + "00101: F0 F0 D5 D5 D5 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00NNN000000000000000\n"
						  + "00121: F0 F0 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40  00                  \n"
						  + "00141: 40 40 F0 F0 F0 F0 F0 F0 F0 F0 F1 F0 F0 F0 F0 F0 F0 D5 D5 F1    000000001000000NN1\n"
						  + "00161: F1 D5 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  1N000000000000000000\n"
						  + "00181: F5 F0 F1 F0 F0 F0 F5 F0 F1 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  50100050100000000000\n"
						  + "00201: F0 F0 F0 F0 F0 F0 D5 40 40 40 40 40 40 40 40 40 40 40 40 40  000000N             \n"
						  + "00221: 40 40 40 40 40 40 40 F0 F0 F5 F0 F1 F0 D5 40 F0 F0 F0 F0 F0         005010N 00000\n"
						  + "00241: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00261: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 D5 F0 F0 F0 F0 F0 F0  0000000000000N000000\n"
						  + "00281: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00301: F0 F0 F0 F0 F0 F0 F0 C9 40 40 F0 D5 40 D5 D5 D5 D5 D5 40 F0  0000000I  0N NNNNN 0\n"
						  + "00321: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00341: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00361: F0 F0 F0 F0 D5 F0 40 D5 E2 F0 F0 F0 F0 F0 F0 F0 D5 E4 F0 F0  0000N0 NS0000000NU00\n"
						  + "00381: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00401: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00421: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00441: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00461: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00481: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00501: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00521: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00541: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00561: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00581: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00601: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00621: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00641: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00661: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00681: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00701: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00721: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00741: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00761: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00781: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00801: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00821: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00841: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00861: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00881: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00901: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00921: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00941: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00961: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "00981: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "01001: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "01021: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "01041: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "01061: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "01081: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "01101: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "01121: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "01141: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "01161: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000000000000000\n"
						  + "01181: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F5 F0 F1 F0 F0 F0  00000000000000501000\n"
						  + "01201: F5 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  50000000000000000000\n"
						  + "01221: F0 F0 F0 F0 F0 F0 F0 F0 F0 F1 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000010000000000\n"
						  + "01241: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F1 F0 F0 F0 F0 F0 F0 F0 F0 F0  00000000001000000000\n"
						  + "01261: F0 F0 F0 F0 F0 F0 F1 F0 F0 F0 F0 F0 F0 40 D5 F0 F1 F0 F0 F0  0000001000000 N01000\n"
						  + "01281: F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 D5 D5 D5 F0 F0 F0 F0 F0 F0 F0  0000000000NNN0000000\n"
						  + "01301: F0 F0 F0 F0 F0 F5 F0 F0 F0 F0  0000050000"
						   );
	}

	private void inputReturnBook(){
		bookArea.setText(
				      "       05 RFINW00W-HEADER.                                          \n"
					 +"          10 RFINW00W-COD-LAYOUT   PIC X(008)      VALUE 'RFINW00W'.\n"
					 +"          10 RFINW00W-TAM-LAYOUT   PIC 9(005)      VALUE 00527.     \n"
					 +"       05 RFINW00W-REGISTRO.                                        \n"
					 +"          10 RFINW00W-BLOCO-RETORNO.                                \n"
					 +"             15 RFINW00W-COD-RETORNO               PIC 9(002).      \n"
					 +"             15 RFINW00W-COD-ERRO                  PIC X(004).      \n"
					 +"             15 RFINW00W-COD-MENSAGEM              PIC X(008).      \n"
					 +"             15 RFINW00W-MEN-PARAMETRO             PIC X(500).      \n"
					 + bookArea.getText()
			);

		commArea.setText("");
	}

	private boolean isDependingOnField(String[] field, String dependingOnField){
		for (int i = 0; i < field.length; i++) {
			if(field[i].equals(dependingOnField))
				return true;
		}
		return false;
	}

	/*
	 * Auxiliares para tratamento de copy Paste
	 */
	private void tratarPaste() {
		/*return;
		try {
			ClipboardContent content = new ClipboardContent();
			String s  = Clipboard.getSystemClipboard().getString();
			String[] lines = s.split("\n");
			String s2 = new String();

			for (int i = 0; i < lines.length; i++) {
					s2 = lines[i] + "\n";
				}
			 content.putString(s2);
			 Clipboard.getSystemClipboard().setContent(content);
		} catch (Exception e) {
			logger.error("Erro paste", e);
		}*/
	}

	private void tratarPasteFluxo() {
		try {
			ClipboardContent content = new ClipboardContent();
			String s  = Clipboard.getSystemClipboard().getString();
			String s2 = s.substring(0, 8);
			content.putString(s2);
			Clipboard.getSystemClipboard().setContent(content);
		} catch (Exception e) {
			logger.error("Erro paste fluxo", e);
		}

	}

	private void tratarCopy() {
		ClipboardContent content = new ClipboardContent();
		String[] lines = commArea.getSelectedText().split("\n");
		String sContent = new String();
		for (int i = 0; i < lines.length; i++) {
			sContent = sContent + lines[i].substring(7, lines[i].length()) + "\n";
		}
		content.putString("");
		Clipboard.getSystemClipboard().setContent(content);
		content.putString(sContent);
		Clipboard.getSystemClipboard().setContent(content);
	}


}
