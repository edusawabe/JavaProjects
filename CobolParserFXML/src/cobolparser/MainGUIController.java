package cobolparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

public class MainGUIController implements Initializable{
	@FXML
	private MenuItem miOpen;
	@FXML
	private MenuItem miClose;
	@FXML
	private Label lbDir;
	@FXML
	private TableView<ProgramsTableLine> tvTabProgs;
	@FXML
	private TableColumn<ProgramsTableLine, String> tcId;
	@FXML
	private TableColumn<ProgramsTableLine, String> tcColunaArquivo;
	@FXML
	private TableColumn<ProgramsTableLine, String> tcColunaNome;
	@FXML
	private TableColumn<ProgramsTableLine, String> tcColunaStatus;
	@FXML
	private RadioButton rbGerarXMLSim;
	@FXML
	private RadioButton rbGerarXMLNao;
	@FXML
	private Button btProcessar;
	@FXML
	private Button btDetProg;
	@FXML
	private Label lbProcessados;
	@FXML
	private ProgressBar pbProcessados;
	@FXML
	private TextArea taAreatexto;

	private ObservableList<ProgramsTableLine> olTabelaProgramas = FXCollections.observableArrayList();
	private File dir;
	private FolderToXMLProcessor folderProcessor;
	//private DBDriver dbDriver;
	private Timeline processWorker;
	private Timeline oPenProcess;
	private int fileInProcess;
	private int indFile;
	private File[] files;
	private LinkedList<File> lFiles;
	private Double di = new Double("0");
	private Double ds = new Double("0");
	private int totalFiles;
	private File configFile;
	private String initDir;
    final static Logger logger = Logger.getLogger(MainGUIController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.info("Inicializando!");
		tcId.setCellValueFactory(new PropertyValueFactory<ProgramsTableLine, String>("id"));
		tcColunaArquivo.setCellValueFactory(new PropertyValueFactory<ProgramsTableLine, String>("arquivo"));
		tcColunaNome.setCellValueFactory(new PropertyValueFactory<ProgramsTableLine, String>("nomePrograma"));
		tcColunaStatus.setCellValueFactory(new PropertyValueFactory<ProgramsTableLine, String>("status"));
		tvTabProgs.getItems().setAll(olTabelaProgramas);
		//dbDriver = new DBDriver();
		//dbDriver.openConnection();
		configFile = new File("./config.txt");
		try {
			if(configFile.exists()){
				BufferedReader reader = new BufferedReader(new FileReader(configFile));
				initDir = reader.readLine();
				reader.close();
			} else {
				BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
				initDir = "C:\\";
				writer.write(initDir);
				writer.flush();
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unchecked")
	@FXML
	private void abrirPasta(Event event){
		logger.info("Abrindo Pasta!");
		olTabelaProgramas.clear();
		taAreatexto.setText("");
		DirectoryChooser dch = new DirectoryChooser();
		try {
			dch.setInitialDirectory(new File(initDir));
			dir = dch.showDialog(lbDir.getScene().getWindow());
		} catch (IllegalArgumentException e) {
			dch.setInitialDirectory(new File("C:\\"));
			dir = dch.showDialog(lbDir.getScene().getWindow());
		}
		indFile = 0;
		totalFiles = 0;

		if (dir == null)
			return;
		configFile = new File("./config.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
			initDir = dir.getAbsolutePath();
			writer.write(dir.getAbsolutePath());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lbDir.setText("Diretorio: " + dir.getAbsolutePath());

		files = dir.listFiles();
		lFiles = new LinkedList<File>();

		for (int i = 0; i < files.length; i++) {
			if ((!files[i].getName().contains(".xml")) && files[i].isFile() && (!(files[i].getName().charAt(4) == 'W')))
				lFiles.add(files[i]);
		}
		totalFiles = lFiles.size();

		oPenProcess = new Timeline();
		oPenProcess.setCycleCount(Timeline.INDEFINITE);
		oPenProcess.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), new EventHandler() {
			// KeyFrame event handler
			@Override
			public void handle(Event event) {
				try {
					doOpenDir();
				} catch (Exception e) {
					logger.error("Erro no Processo de Open", e);
					oPenProcess.stop();
				}
			}
		}));
		oPenProcess.playFromStart();
	}

	@FXML
	private void rbGerarXMLNaoAction(Event event){
		rbGerarXMLSim.setSelected(false);
	}

	@FXML
	private void rbGerarXMLSimAction(Event event){
		rbGerarXMLNao.setSelected(false);
	}

	@SuppressWarnings("unchecked")
	@FXML
	private void processarArquivos(Event event){
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle("Selecionar Opção de XML");
		a.setContentText("Favor Selecionar Uma das opções de Gerar XML.");
		fileInProcess = 0;
		if(!rbGerarXMLNao.isSelected() && !rbGerarXMLSim.isSelected()){
			a.show();
			return;
		}

		tvTabProgs.scrollTo(0);
		tvTabProgs.refresh();

		for (int i = 0; i < olTabelaProgramas.size(); i++) {
			olTabelaProgramas.get(i).setStatus("");
		}

		tvTabProgs.refresh();

		taAreatexto.setText("");
		if (dir != null) {
			if (dir.exists()) {
				lbProcessados.setText("0/" + olTabelaProgramas.size());
				folderProcessor = new FolderToXMLProcessor();
				folderProcessor.setFolder(dir);
				folderProcessor.setOlTabelaProgramas(olTabelaProgramas);
				if (rbGerarXMLSim.isSelected())
					folderProcessor.setSkipExistingFile(false);
				if (rbGerarXMLNao.isSelected())
					folderProcessor.setSkipExistingFile(true);
				folderProcessor.setDb2Driver(new DBDriver());

				processWorker = new Timeline();
				processWorker.setCycleCount(Timeline.INDEFINITE);
				processWorker.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), new EventHandler() {
					// KeyFrame event handler
					@Override
					public void handle(Event event) {
						try {
							doProcess();
						} catch (Exception e) {
							logger.error("Erro no Processo de Open", e);
							processWorker.stop();
						}
					}
				}));
				processWorker.playFromStart();
			}
		}
	}

	private void doProcess() throws Exception {
		if (fileInProcess >= olTabelaProgramas.size()) {
			processWorker.stop();
			String[] tmp = taAreatexto.getText().split("\n");
			taAreatexto.setText("");
			for (int i = 0; i < tmp.length; i++) {
				if(!tmp[i].equals(""))
					taAreatexto.setText(taAreatexto.getText().concat(tmp[i] + "\n"));
			}
			return;
		} else {
			if ((!tvTabProgs.getItems().get(fileInProcess).getArquivo().contains(".xml"))
					&& (!(tvTabProgs.getItems().get(fileInProcess).getArquivo().charAt(4) == 'W'))) {

				ds = Double.parseDouble("" + olTabelaProgramas.size());
				taAreatexto.setText(taAreatexto.getText() + "\n" +folderProcessor.processAndCreateFile(fileInProcess));
				di = Double.parseDouble("" + fileInProcess);
				di = di + 1;
				pbProcessados.setProgress(di / ds);
				lbProcessados.setText("Arquivos Processados: " + fileInProcess + "/" + (olTabelaProgramas.size()-1));
				tvTabProgs.scrollTo(fileInProcess);
				tvTabProgs.refresh();
			}
			fileInProcess++;
		}
	}

	private void doOpenDir() throws Exception {
		if (indFile > (totalFiles-1)) {
			oPenProcess.stop();
			logger.info("Fim Abrir Pasta!");
			return;
		} else {
			if ( (lFiles.get(indFile).getName().contains(".txt") || lFiles.get(indFile).getName().contains(".cbl")
			   || (!lFiles.get(indFile).getName().contains("."))) && lFiles.get(indFile).isFile()) {
				ProgramsTableLine pgmLine = new ProgramsTableLine();
				pgmLine.setId("" + (indFile + 1));
				pgmLine.setArquivo(lFiles.get(indFile).getAbsolutePath());
				Path path = Paths.get(lFiles.get(indFile).getAbsolutePath());
				String p1, p2, toWrite;
				String[] splitContent;
				int i1 = 0, i2 = 0;
				try {
					List<String> contents = FileUtils.readLines(new File(lFiles.get(indFile).getAbsolutePath()), StandardCharsets.UTF_8);
					BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"));
					// Read from the stream
					for (String content : contents) {
						if(content.contains("I#") && content.contains(".") && !content.contains("'") && !content.contains("*")){
							toWrite = "";
							i1 = content.indexOf("I#");
							i2 = content.indexOf(".");
							splitContent = content.split("I#");
							p1 = splitContent[0];
							p2 = content.substring(i1, i2);
							toWrite = p1 + "'" + p2 + "'" + ".";
							logger.info("Programa: " + lFiles.get(indFile).getName() + " Corrigindo linha: " + content);
							content = toWrite;
						}
						writer.write(content+"\n");
					}
					writer.flush();
					writer.close();
				} catch (IOException ex) {
					logger.info("doOpenDir Exception: ", ex);
				}
				olTabelaProgramas.add(pgmLine);
				tvTabProgs.setItems(olTabelaProgramas);

				ds = Double.parseDouble("" + totalFiles);
				di = Double.parseDouble("" + indFile);
				di = di + 1;
				pbProcessados.setProgress(di / ds);
				lbProcessados.setText("Carregando Arquivos:" + (indFile+1) + "/" + (totalFiles));
				tvTabProgs.scrollTo(indFile);
			}
			indFile++;
		}
	}
}
