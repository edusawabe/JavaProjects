/*
 *
 */

package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Pair;
import model.Campo;
import model.ListItem;
import util.HashMapHexAscii;
import util.Util;
import view.MaskTextField;
import view.PairKeyFactory;
import view.PairValueCell;
import view.PairValueFactory;

public class FXMLTableViewController implements Initializable{
    @FXML private TableView<Pair<String,Object>> tableView;
    @FXML private TableColumn<Pair<String,Object>,String> campoColumn;
    @FXML private TableColumn<Pair<String,Object>,Object> valorColumn;

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
	      private boolean development;
	      private boolean hasOccurs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		development = true;
		hasOccurs = false;
		initComponents();
		fluxoField.setMask("********");
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
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


    /*
     * ======================================================================================================
     * Actions
     * ======================================================================================================
     */
	@FXML
	protected void gerarAreaPorGlog(ActionEvent event) {
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
	}

	@FXML
	protected void openBookFile(ActionEvent event) {
		commArea.setText("");
		bookArea.setText("");
		String line;
		String book = new String();
		FileChooser fch = new FileChooser();
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
			bookArea.setText(book);
			reader.close();

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
		int posPonto = line.indexOf('.');
		int posOccurs = line.indexOf("OCCURS");
		int posTo = line.indexOf("TO");
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
		else
			hasOccurs = false;
		listCampos.add(c);
		getNomeNivelCampo(line,c);
		return c;
	}

	private void getNomeNivelCampo(String line, Campo c){
		String breakLine[]  = line.split("\\s+");
		int cont = 0;

		for (int i = 0; i < breakLine.length; i++) {
				if (!breakLine[i].isEmpty())
					cont++;
				if(cont == 1)
					c.setNivel(breakLine[i]);
				if(cont == 2)
					c.setNome(breakLine[i]);
		}
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

		posPonto = line.indexOf('.');
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

		posPonto = line.indexOf('.');
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
		posPonto = line.indexOf('.');
		c.setType(line.substring(beginPic - 6, posPonto));
		return c;
	}

	private String getCommareaByHex(){
		String line[] = commArea.getText().split("\\r?\\n");
		String subLine[];
		String commArea = new String();
		for (int i = 0; i < line.length; i++) {
			if (line[i].contains(":")) {
				subLine = line[i].split(": ");
				if (subLine[1].length() > 80)
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
			if (line.charAt(i + 2) == ' '){
				ret = ret + " " + line.charAt(i) + line.charAt(i + 1);
				i = i + 2;
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
				if (campo.getTam() > 0){
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
							item.setCampo("   " + listCampo.getNivel() + " - " + listCampo.getNome() + "(" + i + ")"
									+ " - " + listCampo.getType());
							item.setValor(
									commArea.substring(listCampo.getPos(), listCampo.getPos() + listCampo.getTam()));
							listCampo.setValor(commArea.substring(listCampo.getPos(), listCampo.getPos()));
							item.setMask(listCampo.getMask());
							commAreaList.add(new Pair<String, Object>(item.getCampo(), new String(item.getValor())));
						}
					}
				}
			}
		}
	}

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
				if (campo.getTam() > 0){
					int size = 0;
					commAreaList.add(new Pair<String, Object>(campo.getNome(),new MaskTextField(campo.getValor(),"",false)));
					if (campo.getDependingOn() == null)
						size = campo.getListOccurs().size();
					else{
						if (campo.getDependingOn().isEmpty())
							size = campo.getListOccurs().size();
						else
							size = 0;
					}
					for (int i = 0; i < size; i++) {
						LinkedList<Campo> listItem = campo.getListOccurs().get(i);
						for(Campo listCampo : listItem){
							ListItem item = new ListItem("   " + listCampo.getNivel() + " - " + listCampo.getNome(), "", "");
							item.setCampo("   " + listCampo.getNivel() + " - " + listCampo.getNome() + "(" + i + ")" + " - " + listCampo.getType());
							item.setMask(listCampo.getMask());
							if (campo.getValor() != null)
								commAreaList.add(new Pair<String, Object>(item.getCampo(),new MaskTextField(campo.getValor(),item.getMask(),campo.isDependingOnField())));
							else
								commAreaList.add(new Pair<String, Object>(item.getCampo(),new MaskTextField("",item.getMask(),campo.isDependingOnField())));
						}
					}
				}
			}
		}
	}

	private int getDependingOnListIndex(ObservableList<Pair<String,Object>> commAreaList){
		int index = 0;
		String campoOccurs = new String();
		for (Campo campo : listCampos){
			if(campo.getListOccurs() != null){
				campoOccurs = campo.getNome();
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
			if(campo.isOccurs()){
				for (int i = 0; i < commAreaList.size(); i++) {
					if(commAreaList.get(i).getKey().contains(campo.getDependingOn())){
						size = Integer.parseInt(((MaskTextField)commAreaList.get(i).getValue()).getText());
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
			    "FRWKGL010021600"+ sCommAreaSize +"                                51174335457216TERM\n"+
				"00011                 "+ fluxo +"0023700001050TERM0001       012008121215\n"+
				"4737NNNIEA700013                       E                              \n"+
				"217230GSEGGLAA00230                    EI910940                       \n"+
				"NN                                                                    \n"+
		        "                                                                      \n"+
				"                          GSEGGLAE00041006Nsenha002                ";
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
			area = area + enteredArea.substring(begin, end) + "\n";
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
			area = area + enteredArea.substring(begin, end) + "\n";
		}

		return area;
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
						 +"              15 RFINWJ6S-S-LIST OCCURS 0 TO 60 TIMES                   \n"
						 +"                 DEPENDING ON RFINWJ6S-S-QTDE-PARCELAS.                \n"
						 + "                20 RFINWJ6S-S-DATA-PARCELA       PIC X(010).           \n"
						 + "                20 RFINWJ6S-S-DATA-AMORTIZACAO   PIC X(010).           \n"
						 +"              15 RFINWJ6S-S-VTOT                  PIC 9(015)V99.        \n"
				);

		commArea.setText(
				            "00001: D9 C6 C9 D5 E6 D1 F6 E2 F0 F0 F5 F0 F6 F2 F0 F0 F0 F0 F0 F0  RFINWJ6S005062000000\n"
						  + "00021: F1 F0 F0 F0 F0 F8 F0 C5 D4 40 D7 D9 D6 C3 C5 E2 E2 D6 40 C4  1000080EM PROCESSO D\n"
						  + "00041: C5 40 D3 C9 C2 C5 D9 C1 C3 C1 D6 40 D5 D6 40 C2 D5 C4 C5 E2  E LIBERACAO NO BNDES\n"
						  + "00061: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00081: 40 40 40 40 40 40 40 F0 F0 F1 F0 F1 F8 F8 F1 D5 F2 F0 F1 F2         00101881N2012\n"
						  + "00101: F0 F5 F3 F1 F0 F0 F0 F1 F2 F5 D9 C1 E9 C1 D6 40 E3 C5 E2 E3  0531000125RAZAO TEST\n"
						  + "00121: C5 40 E3 C5 E2 E3 C5 40 40 40 40 40 40 40 40 40 40 40 40 40  E TESTE             \n"
						  + "00141: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00161: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00181: 40 40 40 40 40 40 40 40 40 40 F8 F4 F1 F4 F2 F2 F0 F8 F0 F0            8414220800\n"
						  + "00201: F0 F1 F3 F5 C6 C1 C2 D9 C9 C3 C1 D5 E3 C5 40 C5 D8 E4 C9 D7  0135FABRICANTE EQUIP\n"
						  + "00221: C1 D4 C5 D5 E3 D6 40 40 40 40 40 40 40 40 40 40 40 40 40 40  AMENTO              \n"
						  + "00241: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00261: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00281: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00301: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00321: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00341: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00361: 40 40 40 40 F1 F9 F8 F7 E3 C5 E2 E3 C5 40 C3 D6 D5 C4 C9 C3      1987TESTE CONDIC\n"
						  + "00381: C1 D6 F1 F2 F3 F1 40 40 40 40 40 40 40 40 40 40 40 40 40 40  AO1231              \n"
						  + "00401: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00421: 40 40 40 40 40 40 40 40 F0 F0 F0 F0 F0 F0 F0 F0 40 40 40 40          00000000    \n"
						  + "00441: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40                      \n"
						  + "00461: 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 F1 F5 4B F1                  15.1\n"
						  + "00481: F2 4B F2 F0 F1 F5 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F0 F1 F0  2.201500000000000010\n"
						  + "00501: F0 F0 F0 F0 F0 F2 F1 F5 4B F1 F2 4B F2 F0 F1 F5 F1 F6 4B F1  00000215.12.201516.1\n"
						  + "00000: F2 4B F2 F0 F1 F5 F2 F5 4B F1 F2 4B F2 F0 F1 F5 F2 F6 4B F1  2.201525.12.201526.1\n"
						  + "00000: F2 4B F2 F0 F1 F5 40 40 40 40 40 40 40 40 40 40 40 40 40 40  2.2015              \n"
						  + "00000: 40 40 40 40 40 40            "
						   );
	}

}
