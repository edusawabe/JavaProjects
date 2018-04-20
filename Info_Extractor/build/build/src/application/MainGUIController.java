package application;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import extractors.DclgenFileReader;
import extractors.FileProcessor;
import extractors.TxaExecutionFlowFileReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import util.ExcelManager;

public class MainGUIController implements Initializable{
	@FXML
	private TextArea taOutput;
	@FXML
	private ProgressBar pbProgress;
	@FXML
	private Label lbProgresso;

	private Timeline tlUpdateGUI;
	private int totalFiles;
	private int processed;
	private Thread extractionThread;
	private FileProcessor fileProcessor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taOutput.setText("Selecione em Arquivo->Abrir "
				+ "\nO Diretorio com os arquivos de DCLGEN"
				+ "\nOu arquivos TXA de Fluxos FRWK");

	}

	@FXML
	private void openDir(Event evt){
		taOutput.setText("");
		DirectoryChooser dc = new DirectoryChooser();
		File initialDir = new File("C:\\E!SuperCopia");
		File dir = null;

		dc.setTitle("selecione diretorio com os arquivos txt das DCLGENs");

		if(initialDir.exists())
			dc.setInitialDirectory(initialDir);
		else
			dc.setInitialDirectory(new File("C:\\"));
		dir = dc.showDialog(null);

		if(dir != null)
			extractInfos(dir);
	}

	private void extractInfos(File dir) {
		File[] f = dir.listFiles();
		totalFiles = f.length - 1;
		processed = 0;
		pbProgress.setProgress(0);
		fileProcessor = new FileProcessor();

		if(tlUpdateGUI != null)
			tlUpdateGUI.stop();

		tlUpdateGUI = new Timeline();
		tlUpdateGUI.setCycleCount(Timeline.INDEFINITE);
		tlUpdateGUI.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), new EventHandler() {
			// KeyFrame event handler
			@Override
			public void handle(Event event) {
				updateStatus();
			}
		}));
		tlUpdateGUI.playFromStart();

		extractionThread = new Thread(() -> {
			this.fileProcessor.setToProcess(f);
			this.fileProcessor.process();
		});
		extractionThread.start();
	}

	private void updateStatus() {
		processed = this.fileProcessor.getProcessed();
		totalFiles = this.fileProcessor.getTotalFiles();

		if(processed >= totalFiles){
			tlUpdateGUI.stop();
			extractionThread.stop();
		}

		if (Platform.isFxApplicationThread()) {
			lbProgresso.setText("Arquivos Processados: " + processed + " / " + totalFiles);
			pbProgress.setProgress(Double.parseDouble("" + processed) / Double.parseDouble("" + totalFiles));
		} else {
			Platform.runLater(() -> lbProgresso.setText("Arquivos Processados: " + processed + " / " + totalFiles));
			Platform.runLater(() -> pbProgress
					.setProgress(Double.parseDouble("" + processed) / Double.parseDouble("" + totalFiles)));
		}
		taOutput.setText(fileProcessor.getResult());
	}
}
