/*
 *
 */

package fxmltableview;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.util.Callback;
import javafx.util.Pair;

public class FXMLTableViewController implements Initializable{
    @FXML private TableView<Pair<String,Object>> tableView;
    @FXML private TableColumn<Pair<String,Object>,String> campoColumn;
    @FXML private TableColumn<Pair<String,Object>,Object> valorColumn;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextArea bookArea;
    @FXML private TextArea commArea;
	private LinkedList<Campo> listCampos;
	@FXML RadioButton entradaRadio;
	@FXML RadioButton saidaRadio;
	@FXML RadioButton glogRadio;
	@FXML RadioButton textoRadio;
	@FXML RadioButton yy03Radio;
	@FXML RadioButton yy06Radio;
	@FXML Button extrairButtom;
	@FXML Button gerarAreaButtom;
	@FXML Button processButtom;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComponents();

		//valorColumn.setCellFactory(TextFieldTableCell.<ListItem>forTableColumn());

		/*valorColumn.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<ListItem, String>>(){
			@Override
			public void handle(CellEditEvent<ListItem, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).
			}
		});
*/
	}

	private void initComponents() {
		boolean development = true;
		if (development)
			initializeAreas();
		extrairButtom.setDisable(true);
		gerarAreaButtom.setDisable(true);
		processButtom.setDisable(true);
		commArea.setFont(Font.font("Courier New", 12));
		bookArea.setFont(Font.font("Courier New", 12));

		//tableView.getColumns().addAll(campoColumn, valorColumn);
		tableView.setEditable(true);
		campoColumn.setCellValueFactory(new PairKeyFactory());
		valorColumn.setCellValueFactory(new PairValueFactory());
		valorColumn.setCellFactory(new Callback<TableColumn<Pair<String, Object>, Object>, TableCell<Pair<String, Object>, Object>>() {
            @Override
            public TableCell<Pair<String, Object>, Object> call(TableColumn<Pair<String, Object>, Object> column) {
                return new PairValueCell();
            }
        });
	}

	@FXML
	protected void extrairBookAction(ActionEvent event) {
		initComponents();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Campos Obrigat�rios N�o Preenchidos");
		alert.setHeaderText("Erro");

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
		if (!yy03Radio.isSelected() && !yy06Radio.isSelected()){
			alert.setContentText("Favor Selecionar o tipo de �rea");
		}

		if (alert.getContentText().isEmpty()) {
			process();
			calculateFieldPosition(null);
			generateTable();
			gerarAreaButtom.setDisable(false);
			if (yy03Radio.isSelected())
				gerarCommAreaYY03();
			if (yy06Radio.isSelected())
				gerarCommAreaYY06();
		}
		else{
			alert.show();
		}
	}

	private void gerarCommAreaYY03() {

	}

	private void gerarCommAreaYY06() {

	}

	@FXML
	protected void extrairSaidaAction(ActionEvent event) {
		initComponents();

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Campos Obrigat�rios N�o Preenchidos");
		alert.setHeaderText("Erro");
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
			alert.setContentText("Favor Selecionar o tipo de �rea");
		}
		if (alert.getContentText().isEmpty()) {
			process();
			if (glogRadio.isSelected())
				processGlog();
		}
		else{
			alert.show();
		}
	}

    @FXML
    protected void entradaSelected(ActionEvent event) {
    	if (entradaRadio.isSelected()){
    		saidaRadio.setSelected(false);
    		yy03Radio.setDisable(false);
    		yy06Radio.setDisable(false);
    		glogRadio.setDisable(true);
    		glogRadio.setSelected(false);
    		textoRadio.setDisable(true);
    		gerarAreaButtom.setDisable(true);
    		extrairButtom.setDisable(false);
    		processButtom.setDisable(true);
    	}
    }

    @FXML
    protected void saidaSelected(ActionEvent event) {
    	if (saidaRadio.isSelected()){
    		entradaRadio.setSelected(false);
    		yy03Radio.setSelected(false);
    		yy06Radio.setSelected(false);
    		glogRadio.setSelected(false);
    		textoRadio.setSelected(false);

    		yy03Radio.setDisable(true);
    		yy06Radio.setDisable(true);
    		glogRadio.setDisable(false);
    		textoRadio.setDisable(false);

    		gerarAreaButtom.setDisable(true);
    		extrairButtom.setDisable(true);
    		processButtom.setDisable(false);
    	}
    }

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
						 +"              15 RFINWJ6S-S-LIST OCCURS 0 TO 60 TIMES                   \n"
						 +"                 DEPPENDING ON RFINWJ6S-S-QTDE-PARCELAS.                \n"
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
				 +"              15 RFINWJ6S-S-LIST OCCURS 3 TIMES.                        \n"
				 + "                20 RFINWJ6S-S-DATA-PARCELA       PIC X(010).           \n"
				 + "                20 RFINWJ6S-S-DATA-AMORTIZACAO   PIC X(010).           \n"
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

	private void process(){
		listCampos = new LinkedList<Campo>();
		Campo occursCampo = null;
		Campo campoAtual;

		String bookLine[] = bookArea.getText().split("\\r?\\n");
		for(int i = 0; i < bookLine.length; i++) {
			if(bookLine[i].contains(" PIC")){
				if(bookLine[i].contains(")V9")){
					campoAtual = processDecimal(bookLine[i]);
				}
				else{
					if(bookLine[i].contains("PIC 9(")){
						if(bookLine[i].contains("VALUE")){
							campoAtual = processNumericValue(bookLine[i]);
						}
						else
							campoAtual = processNumeric(bookLine[i]);
					}
					else{
						if(bookLine[i].contains("VALUE"))
							campoAtual = processStringValue(bookLine[i]);
						else
							campoAtual = processString(bookLine[i]);
					}
				}

				if (occursCampo != null){
					if(campoAtual.getNivel().compareTo(occursCampo.getNivel()) > 0)
						occursCampo.getListOccurs().get(0).add(campoAtual);
				}
				else{
					occursCampo = null;
					listCampos.add(campoAtual);
				}
			}
			else
				if (bookLine[i].contains("OCCURS")){
					if (bookLine[i].contains("."))
						occursCampo = processOccursLine(bookLine[i]);
					else{
						int j = i;
						String toProcess = new String();
						while(!bookLine[j].contains(".")){
							toProcess = toProcess + bookLine[j];
							j++;
						}
						toProcess = toProcess + bookLine[j];
						occursCampo = processOccursLine(toProcess);
						i = j;
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
		int posDeppendingON = line.indexOf("DEPPENDING ON");
		if (posTo > 0)
			c.setTimes(Integer.parseInt(line.substring(posTo + 3, posTimes -1).replaceAll(" ", "")));
		else{
			c.setTimes(Integer.parseInt(line.substring(posOccurs + 7, posTimes -1).replaceAll(" ", "")));
		}
		if (posDeppendingON > 0)
			c.setDeppendingOn(line.substring(posDeppendingON + 13, posPonto).replaceAll(" ", ""));
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
		int beginPic, endPic;
		beginPic = line.indexOf("PIC X(") + 6;
		endPic = line.indexOf(") ");

		Campo c = new Campo();
		getNomeNivelCampo(line, c);
		c.setTam(Integer.parseInt(line.substring(beginPic, endPic)));
		c.setType(line.substring(beginPic-6, endPic+1));
		return c;
	}

	private Campo processString(String line){
		int beginPic, endPic, posPonto;
		beginPic = line.indexOf("PIC X(") + 6;
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

		beginPic = line.indexOf("PIC 9(") + 6;
		endPic = line.indexOf(").");

		Campo c = new Campo();
		getNomeNivelCampo(line, c);
		c.setTam(Integer.parseInt(line.substring(beginPic, endPic)));

		posPonto = line.indexOf('.');
		c.setType(line.substring(beginPic - 6, posPonto));
		return c;
	}

	private Campo processNumericValue(String line){
		int beginPic, endPic;
		beginPic = line.indexOf("PIC 9(") + 6;
		endPic = line.indexOf(") ");
		Campo c = new Campo();
		getNomeNivelCampo(line, c);
		c.setTam(Integer.parseInt(line.substring(beginPic, endPic)));
		c.setType(line.substring(beginPic - 6, endPic + 1));
		return c;
	}

	private Campo processDecimal(String line){
		int beginPic = 0, beginV, endV, endPic, posPonto;
		Campo c = new Campo();

		if (line.contains(")V9(")){
			beginPic = line.indexOf("PIC 9(") + 6;
			beginV = line.indexOf(")V9(");
			endV = line.indexOf(")V9(") + 4;
			endPic = line.indexOf(").");
			c.setTam((Integer.parseInt(line.substring(beginPic, beginV))) + (Integer.parseInt(line.substring(endV, endPic))));
		}

		if (line.contains(")V99")){
			beginPic = line.indexOf("PIC 9(") + 6;
			beginV = line.indexOf(")V9");
			endPic = line.indexOf(").");
			c.setTam((Integer.parseInt(line.substring(beginPic, beginV)) + (2)));
		}

		if (line.contains(")V999")){
			beginPic = line.indexOf("PIC 9(") + 6;
			beginV = line.indexOf(")V9");
			endPic = line.indexOf(").");
			c.setTam((Integer.parseInt(line.substring(beginPic, beginV)) + (3)));
		}

		if (line.contains(")V9999")){
			beginPic = line.indexOf("PIC 9(") + 6;
			beginV = line.indexOf(")V9");
			endPic = line.indexOf(").");
			c.setTam((Integer.parseInt(line.substring(beginPic, beginV)) + (4)));
		}

		getNomeNivelCampo(line, c);
		posPonto = line.indexOf('.');
		c.setType(line.substring(beginPic - 6, posPonto));
		return c;
	}

	private void processGlog(){
		String line[]  = commArea.getText().split("\\r?\\n");
		String subLine[];
		String commArea = new String();
		for (int i = 0; i < line.length; i++) {
			subLine = line[i].split(": ");
			if (subLine[1].length() == 81)
				if (commArea.isEmpty())
					commArea = commArea +subLine[1].substring(0, 59);
				else
					commArea = commArea + " " + subLine[1].substring(0, 59);
			else
				commArea = commArea + " " +processGlogFinalLine(subLine[1]);

		}
		calculateFieldPosition(convertHextoText(commArea));
/*		for (Iterator<Campo> iterator = listCampos.iterator(); iterator.hasNext();) {
			Campo campo = (Campo) iterator.next();
			System.out.println(campo.getNivel() + " - " + campo.getNome() + " - " + campo.getTam() + " - " + campo.getPos());
		}*/
		generateCommAreaTable(convertHextoText(commArea));
				for (Iterator<Campo> iterator = listCampos.iterator(); iterator.hasNext();) {
		Campo campo = (Campo) iterator.next();
		System.out.println(campo.getNivel() + " - " + campo.getNome() + " - " + campo.getTam() + " - " + campo.getPos() + ": " + campo.getValor());
	}
	}

	private String processGlogFinalLine(String line){
		String ret = new String();
		ret = "" + line.charAt(0);
		ret = ret + line.charAt(1);
		int i = 2;

		while (i < line.length()) {
			if (line.charAt(i) == ' '){
				ret = ret + " " + line.charAt(i + 1) + line.charAt(i + 2);
				i = i + 3;
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
				if (campo.getDeppendingOn() == null || campo.getDeppendingOn().isEmpty()) {
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
						size = getDeppendingOnValue(campo.getDeppendingOn(), commArea);
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

	private int getDeppendingOnValue(String deppendingOn, String commArea){
		for (Campo campo : listCampos) {
			if (campo.getNome().equals(deppendingOn))
				return (Integer.parseInt(commArea.substring(campo.getPos(), campo.getPos() + campo.getTam())));
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
				commAreaList.add(new Pair(item.getCampo(), new MyValue(campo.getValor(),campo.getMask())));
			}
			else{
				for (int i = 0; i < campo.getListOccurs().size(); i++) {
					LinkedList<Campo> listItem = campo.getListOccurs().get(i);
					for(Campo listCampo : listItem){
						ListItem item = new ListItem();
						item.setCampo("   " + listCampo.getNivel() + " - " + listCampo.getNome() + "(" + i + ")" + " - " + listCampo.getType());
						item.setValor(commArea.substring(listCampo.getPos(), listCampo.getPos() + listCampo.getTam()));
						listCampo.setValor(commArea.substring(listCampo.getPos(), listCampo.getPos()));
						item.setMask(campo.getMask());
						commAreaList.add(new Pair(campo.getNome(),new MyValue(campo.getValor(),campo.getMask())));
					}
				}
			}
		}
	}

	private void generateTable(){
		ObservableList<Pair<String,Object>> commAreaList = tableView.getItems();

		for (Campo campo : listCampos) {
			if(!campo.isOccurs()){
				ListItem item = new ListItem(campo.getNivel() + " - " + campo.getNome(), "", "");
				item.setCampo(campo.getNivel() + " - " + campo.getNome() + " - " + campo.getType());
				item.setMask(campo.getMask());
				commAreaList.add(new Pair(campo.getNome(),new MyValue("",campo.getMask())));
			}
			else{
				for (int i = 0; i < campo.getListOccurs().size(); i++) {
					LinkedList<Campo> listItem = campo.getListOccurs().get(i);
					for(Campo listCampo : listItem){
						ListItem item = new ListItem(listCampo.getNivel() + " - " + listCampo.getNome(), "", "");
						item.setCampo("   " + listCampo.getNivel() + " - " + listCampo.getNome() + "(" + i + ")" + " - " + listCampo.getType());
						item.setMask(campo.getMask());
						commAreaList.add(new Pair(campo.getNome(),new MyValue("",campo.getMask())));
					}
				}
			}
		}
	}

	class PairKeyFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Object>, String>, ObservableValue<String>> {
	    @Override
	    public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, Object>, String> data) {
	        return new ReadOnlyObjectWrapper(data.getValue().getKey());
	    }
	}

	class PairValueFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Object>, Object>, ObservableValue<Object>> {
	    @SuppressWarnings("unchecked")
	    @Override
	    public ObservableValue<Object> call(TableColumn.CellDataFeatures<Pair<String, Object>, Object> data) {
	        Object value = data.getValue().getValue();
	        return (value instanceof ObservableValue)
	                ? (ObservableValue) value
	                : new ReadOnlyObjectWrapper<>(value);
	    }
	}

	class PairValueCell extends TableCell<Pair<String, Object>, Object> {
	    private MaskTextField textField;


		@Override
	    protected void updateItem(Object item, boolean empty) {
	        super.updateItem(item, empty);

	        if (item != null) {
	            if (item instanceof String) {
	                setText((String) item);
	                textField = new MaskTextField();
	                setGraphic(textField);
	            } else if (item instanceof MyValue) {
	            	textField = new MaskTextField();
	                if(((MyValue) item).getMask()!=null)
	                	textField.setMask(((MyValue) item).getMask());
	            	if(((MyValue) item).getValue() != null)
						textField.setText(((MyValue) item).getValue());
					else
						setText("");
	            	if (glogRadio.isSelected())
	            		setText(((MyValue) item).getValue());
	            	else{
	            		textField.setText("");
	            		setGraphic(textField);
	            	}
	            } else {
	                setText("");
	                textField = new MaskTextField();
	                setGraphic(textField);
	            }
	        } else {
	            textField = new MaskTextField();
                setGraphic(textField);
	        }
	    }
	}


}
