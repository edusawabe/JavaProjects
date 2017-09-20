package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import util.CSVReader;
import util.ConfigReader;
import view.ConfigItem;

public class MainGUIController implements Initializable{
	private ConfigReader reader;
	private LinkedList<ConfigItem> lConfigitem;
	private ObservableList<String> olCentroCusto = FXCollections.observableArrayList();
	private ObservableList<String> olPT = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> cbCentroCusto;
	@FXML
	private ComboBox<String> cbPT;
	@FXML
	private Label lbDescricaoPT;
	@FXML
	private Label lbGrupo;
	@FXML
	private Label lbPlanningFolder;
	@FXML
	private TextField tfArquivoCronograma;
	@FXML
	private TextArea taProcesso;
	@FXML
	private Button btGerarArquivo;
	@FXML
	private Button btAbrir;
	@FXML
	private RadioButton rbNumeroPT;
	@FXML
	private RadioButton rbDescricaoPT;
	private CSVReader csvReader = new CSVReader();

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
		taProcesso.setText("");
		cbPT.setDisable(true);
		tfArquivoCronograma.setDisable(true);
		btGerarArquivo.setDisable(true);
		btAbrir.setDisable(true);
		lbGrupo.setText("");
		lbPlanningFolder.setText("");
		lbDescricaoPT.setText("");
	}

	@FXML
	private void SelecionaCentroCusto(Event e){
		if(cbCentroCusto.getSelectionModel().getSelectedIndex() <= 0){
			cbPT.setDisable(true);
			lbGrupo.setText("");
			lbPlanningFolder.setText("");
			lbDescricaoPT.setText("");
			btGerarArquivo.setDisable(true);
			btAbrir.setDisable(true);
			rbNumeroPT.setSelected(false);
			rbDescricaoPT.setSelected(false);
		}else{
			cbPT.setDisable(false);
			olPT.clear();
			olPT.add("Selecione");
			for (int i = 0; i < lConfigitem.size(); i++) {
				if(lConfigitem.get(i).getCentroCusto().equals(cbCentroCusto.getSelectionModel().getSelectedItem())){
					if(!olPT.contains(lConfigitem.get(i).getPt())){
						olPT.add(lConfigitem.get(i).getPt());
					}
				}
			}
			cbPT.setItems(olPT);
			cbPT.getSelectionModel().select(0);
			lbGrupo.setText("");
			lbPlanningFolder.setText("");
		}
	}

	@FXML
	private void SelecionaPT(Event e){
		if(cbPT.getSelectionModel().getSelectedIndex() <= 0){
			lbGrupo.setText("");
			lbPlanningFolder.setText("");
			lbDescricaoPT.setText("");
			btGerarArquivo.setDisable(true);
			btAbrir.setDisable(true);
			rbNumeroPT.setSelected(false);
			rbDescricaoPT.setSelected(false);
		}else{
			lbGrupo.setText("");
			for (int i = 0; i < lConfigitem.size(); i++) {
				if(lConfigitem.get(i).getCentroCusto().equals(cbCentroCusto.getSelectionModel().getSelectedItem())){
						lbGrupo.setText(lConfigitem.get(i).getGrupo());
						break;
				}
			}
			lbPlanningFolder.setText("");
			lbDescricaoPT.setText("");
			for (int i = 0; i < lConfigitem.size(); i++) {
				if(lConfigitem.get(i).getCentroCusto().equals(cbCentroCusto.getSelectionModel().getSelectedItem())
						&& lConfigitem.get(i).getPt().equals(cbPT.getSelectionModel().getSelectedItem())){
					lbPlanningFolder.setText(lConfigitem.get(i).getPlanningFolder());
					lbDescricaoPT.setText(lConfigitem.get(i).getDescricaoPT());
				}
			}
			btAbrir.setDisable(false);
		}
	}

	@FXML
	private void abrirArquivoCronograma(Event e){
		FileChooser fChooser = new FileChooser();
		fChooser.setInitialDirectory(new File("./"));
		File f = fChooser.showOpenDialog(null);
		csvReader = new CSVReader();
		csvReader.setfName(f.getAbsolutePath());
		csvReader.setCentroCusto(cbCentroCusto.getSelectionModel().getSelectedItem());
		csvReader.setPt(cbPT.getSelectionModel().getSelectedItem());
		csvReader.setDescricaoPT(lbDescricaoPT.getText());
		csvReader.setGroup(lbGrupo.getText());
		csvReader.setPlannedFor(lbPlanningFolder.getText());
		csvReader.readFile();
		if(taProcesso.getText().isEmpty())
			taProcesso.setText("Arquivo de Cronograma "+ f.getAbsolutePath() +" Carregado Com Sucesso!");
		else
			taProcesso.setText(taProcesso.getText() + "\n" + "Arquivo de Cronograma Carregado Com Sucesso!");
		tfArquivoCronograma.setText(f.getAbsolutePath());
		if(tfArquivoCronograma.getText().isEmpty()){
			btGerarArquivo.setDisable(true);
		}
	}

	@FXML
	private void gerarArquivo(Event e){
		String msg = new String("");

		if(cbCentroCusto.getSelectionModel().getSelectedIndex() <= 0){
			msg = msg + "Campo \"Centro de Custo\" é Obrigatório!\n";
		}

		if(cbPT.getSelectionModel().getSelectedIndex() <= 0){
			msg = msg + "Campo \"PT\" é Obrigatório!\n";
		}

		if(lbGrupo.getText().isEmpty()){
			msg = msg + "Campo \"Grupo\" é Obrigatório!\n";
		}

		if(lbPlanningFolder.getText().isEmpty()){
			msg = msg + "Campo \"Planning Folder\" é Obrigatório!\n";
		}

		if(tfArquivoCronograma.getText().isEmpty()){
			msg = msg + "Campo \"Arquivo CSV Cronograma\" é Obrigatório!\n";
		}

		if(!rbNumeroPT.isSelected() && !rbDescricaoPT.isSelected()){
			msg = msg + "Campo \"Gerar Título dos Artefatos Com\" é Obrigatório!\n";
		}

		if(!msg.isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Validação de Campos");
			alert.setHeaderText("Campos Obrigatórios Não Preenchido(s)");
			alert.setContentText(msg);
			alert.show();
		}else{
			BufferedWriter writer;
			try {
				File out = new File("./tarefas.csv");
				csvReader.setCentroCusto(cbCentroCusto.getSelectionModel().getSelectedItem());
				csvReader.setPt(cbPT.getSelectionModel().getSelectedItem());
				csvReader.setDescricaoPT(lbDescricaoPT.getText());
				csvReader.setGroup(lbGrupo.getText());
				csvReader.setPlannedFor(lbPlanningFolder.getText());
				writer = new BufferedWriter(new FileWriter(out));
				if(rbNumeroPT.isSelected())
					csvReader.setGerarComNumPT(true);
				else
					csvReader.setGerarComNumPT(false);
				writer.write(csvReader.generateCSVLines());
				writer.flush();
				taProcesso.setText(taProcesso.getText() + "\n" + "Arquivo: " + out.getAbsolutePath() + " Gerado Com Sucesso!");
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@FXML
	private void rbNumeroPTAction(Event e){
		rbDescricaoPT.setSelected(false);
		if(!rbNumeroPT.isSelected() && !rbDescricaoPT.isSelected()){
			btGerarArquivo.setDisable(true);
		}else{
			btGerarArquivo.setDisable(false);
		}
	}

	@FXML
	private void rbDescricaoPTAction(Event e){
		rbNumeroPT.setSelected(false);
		if(!rbNumeroPT.isSelected() && !rbDescricaoPT.isSelected()){
			btGerarArquivo.setDisable(true);
		}else{
			btGerarArquivo.setDisable(false);
		}
	}
}
