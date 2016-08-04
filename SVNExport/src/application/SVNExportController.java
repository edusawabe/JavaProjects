package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

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
			if (linhas[i].contains("Revision: ")){
				String [] tmp = linhas[i].split("Revision: ");
				currentRevision = tmp[1];
			}
			if(linhas[i].contains("Added :") || linhas[i].contains("Modified :")){
				String [] tmp2 = linhas[i].split(" : ");
				dir = tmp2[1].substring(0, tmp2[1].lastIndexOf(47));
				dir = dir.replace('/', '\\');
				//dir = "\"" + "C:\\exp" + dir + "\"";
				//res = res + "mkdir " + dir + " \n";
				dir = "C:\\exp" + dir;
				File newDir = new File(dir);
				newDir.mkdirs();
				dir =  "\"" +  dir + "\"";
				res = res + "C:\\Program Files\\TortoiseSVN\\bin\\svn.exe export -r" + currentRevision + " --force \"https://scm-coconet.capgemini.com/svn/repos/br-cpmb-bd-pf-rfin" + tmp2[1] + "\" "+ dir + "\n";
				String[] toAdd = new String[6];
				toAdd[0] = "svn";
				toAdd[1] = "export";
				toAdd[2] = "-r"+currentRevision;
				toAdd[3] = "--force";
				toAdd[4] = "\"https://scm-coconet.capgemini.com/svn/repos/br-cpmb-bd-pf-rfin" + tmp2[1] + "\" ";
				toAdd[5] = dir;
				commandlist.add(toAdd);
			}
		}

		try {
			FileWriter writer = new FileWriter(new File("C:\\export.bat"));
			writer.append(res);
			writer.close();

			Process process = null;
			BufferedReader stdInput;
			BufferedReader stdError;
			String[] cmd = res.split("\n");
			String content = new String();

			for (int i = 0; i < commandlist.size(); i++) {
				String[] toCall = cmd[i].split(" ");
				process = Runtime.getRuntime().exec(commandlist.get(i));
				//content = content + commandlist.get(i).toString()+ "\n";
				// Get input streams
				stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

				// Read command standard output
				String s;
				content = content + "======> Outputs:" + "\n";
				while ((s = stdInput.readLine()) != null) {
					content = content + s + "\n";
				}

				content = content + "======> Erros:" + "\n";
				// Read command errors
				while ((s = stdError.readLine()) != null) {
					content = content + s + "\n";
				}
				taArea.setText(content);
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		taArea.setText(res);
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
