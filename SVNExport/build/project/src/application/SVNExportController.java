package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class SVNExportController {
	@FXML
	private TextArea taArea;
	@FXML
	private Button btConfirm;

	@FXML
	private void processar(Event event){
		String texto = taArea.getText();
		String [] linhas = texto.split("\n");
		String currentRevision = null;
		String dir = null;
		String res = new String();

		res = "rmdir C:\\export /s /q \n";
		for (int i = 0; i < linhas.length; i++) {
			if (linhas[i].contains("Revision: ")){
				String [] tmp = linhas[i].split("Revision: ");
				currentRevision = tmp[1];
			}
			if(linhas[i].contains("Added :") || linhas[i].contains("Modified :")){
				String [] tmp2 = linhas[i].split(" : ");
				dir = tmp2[1].substring(0, tmp2[1].lastIndexOf(47));
				dir = dir.replace('/', '\\');
				dir = "\"" + "C:\\export" + dir + "\"";
				res = res + "mkdir " + dir + " \n";
				res = res + "svn export -r" + currentRevision + "--force \"https://scm-coconet.capgemini.com/svn/repos/br-cpmb-bd-pf-rfin" + tmp2[1] + "\" "+ dir + "\n";
			}
		}
		linhas = res.split("\n");
		Process process = null;
		BufferedReader stdInput;
		BufferedReader stdError;

		for (int i = 0; i < linhas.length; i++) {
			try {
				process = Runtime.getRuntime().exec(linhas[i]);

				// Get input streams
	            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

	            // Read command standard output
	            String s;
	            while ((s = stdInput.readLine()) != null) {
	                taArea.setText(taArea.getText() + s + "\n");
	            }

	            // Read command errors
	            while ((s = stdError.readLine()) != null) {
	            	taArea.setText(taArea.getText() + s + "\n");
	            }

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		taArea.setText(res);
	}
}
