package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import com.sun.javafx.tk.Toolkit.Task;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

public class SVNExportController {
	@FXML
	private TextArea taArea;
	@FXML
	private Button btConfirm;
	@FXML
	private ProgressBar pbProgresso;

	private Timeline exportProcess;
	private int i;
	private String content;
	private Task exportTask;

	@FXML
	private void processar(Event event) throws IOException{
		String texto = taArea.getText();
		String [] linhas = texto.split("\n");
		String currentRevision = null;
		String dir = null;
		String res = new String();

		//res = "rmdir C:\\export /s /q \n";
		File exportDir = new File("C:\\exp");
		if(exportDir.exists())
			delete(exportDir);
		exportDir.delete();
		LinkedList<String[]> commandlist = new LinkedList<String[]>();
		for (int i = 0; i < linhas.length; i++) {
			//obtendo revision
			if (linhas[i].contains("Revision: ")){
				String [] tmp = linhas[i].split("Revision: ");
				currentRevision = tmp[1];
			}
			if(linhas[i].contains("Added :") || linhas[i].contains("Modified :")){
				String [] tmp2 = linhas[i].split(" : ");
				//Criando estrutura de diretorio
				dir = tmp2[1].substring(0, tmp2[1].lastIndexOf(47));
				dir = dir.replace('/', '\\');
				dir = "C:\\exp" + dir;
				File newDir = new File(dir);
				newDir.mkdirs();

				//incluindo aspas no diretorio
				dir =  "\"" +  dir + "\"";

				//criando parametro de execução
				String[] toAdd = new String[8];
				toAdd[0] = "cmd.exe";
				toAdd[1] = "/c";
				toAdd[2] = "svn";
				toAdd[3] = "export";
				toAdd[4] = "-r"+currentRevision;
				toAdd[5] = "--force";
				toAdd[6] = "\"https://scm-coconet.capgemini.com/svn/repos/br-cpmb-bd-pf-rfin" + tmp2[1] + "\"";
				toAdd[7] = dir;
				commandlist.add(toAdd);
			}
		}
		Process process = null;
		taArea.setText("");
		pbProgresso = new ProgressBar();
		pbProgresso.setProgress(0);
		pbProgresso.progressProperty().unbind();

		exportProcess = new Timeline();
		exportProcess.setCycleCount(Timeline.INDEFINITE);
		i = 0;
		exportProcess.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new EventHandler() {
			// KeyFrame event handler
			@Override
			public void handle(Event event) {
				try {
					doExportProcess(process, commandlist, i);
					i++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}));
		exportProcess.playFromStart();
	}

	private void doExportProcess(Process process, LinkedList<String[]> commandlist, int i) throws Exception{
		if (i > commandlist.size()){
			taArea.setText(taArea.getText() + "\n" + "====================================================\n" +
					"\t\tEXPORTACAO REALIZADA COM SUCESSO!\n" +
					"\t\tConteúdo disponivel em:\n"+
					"\t\t\tC:\\exp\n"+
					"====================================================\n");
			return;
		}
		process = Runtime.getRuntime().exec(commandlist.get(i));
		BufferedReader stdInput;
		BufferedReader stdError;
		// Get input streams
		stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		// Read command standard output
		String s;
		while ((s = stdInput.readLine()) != null) {
			taArea.appendText(s + "\n");
		}
		// Read command errors
		while ((s = stdError.readLine()) != null) {
			taArea.appendText(s + "\n");
		}
		  if (Platform.isFxApplicationThread()) {
		        taArea.appendText("");
		    } else {
		        Platform.runLater(() -> taArea.appendText(""));
		    }
	}

	private void delete(File f) throws IOException {
		  if (f.isDirectory()) {
			    for (File c : f.listFiles())
			      delete(c);
			  }
			  if (!f.delete())
			    throw new FileNotFoundException("Failed to delete file: " + f);
			}
}
