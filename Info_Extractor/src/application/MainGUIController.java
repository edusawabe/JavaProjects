package application;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import extractors.DclgenFileReader;
import extractors.TxaExecutionFlowFileReader;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import util.ExcelManager;

public class MainGUIController implements Initializable{
	@FXML
	private TextArea taOutput;

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
		if(f[0].getName().charAt(4) == 'I' || f[0].getName().charAt(4) == 'i')
			processTxaDir(f);
		else
			processDclgenDir(f);
	}

	private void processDclgenDir(File[] f) {
		DclgenFileReader reader = new DclgenFileReader(null);

		ExcelManager excelManager = new ExcelManager();
		excelManager.setFileName("./DclgenInfos.xslx");
		excelManager.getlConlumns().add("DCLGEN");
		excelManager.getlConlumns().add("Nome da Tabela");

		for (int i = 0; i < f.length; i++) {
			reader.setDclgenFileName(f[i]);

			if(reader.getFileInfos()){
				if(taOutput.getText().isEmpty())
					taOutput.setText(reader.getDclgenName() + "\t" + reader.getDclgenTableName());
				else
					taOutput.setText(taOutput.getText() + "\n" + reader.getDclgenName() + "\t" + reader.getDclgenTableName());
				excelManager.getlRowValue().add(new LinkedList<String>());
				excelManager.getlRowValue().getLast().add(reader.getDclgenName());
				excelManager.getlRowValue().getLast().add(reader.getDclgenTableName());
			}else{
				if(taOutput.getText().isEmpty())
					taOutput.setText(f[i].getName() + "Não Processado Corretamente");
				else
					taOutput.setText(taOutput.getText() + "\n" + f[i].getName() + "Não Processado Corretamente");
			}
		}
		excelManager.generateExcelFile();
	}

	private void processTxaDir(File[] f) {
		TxaExecutionFlowFileReader reader = new TxaExecutionFlowFileReader(null);

		ExcelManager excelManager = new ExcelManager();
		excelManager.setFileName("./FRWK.xslx");
		excelManager.getlConlumns().add("Descricao Resumida Fluxo");
		excelManager.getlConlumns().add("Nome do Fluxo");
		excelManager.getlConlumns().add("Fluxo");
		excelManager.getlConlumns().add("Coordenador");

		for (int i = 0; i < f.length; i++) {
			reader.setTxaExecutionFlowFile(f[i]);
			if(reader.getFileInfos()){
				if (taOutput.getText().isEmpty())
					taOutput.setText(reader.getFlowDescription() + "\t" + reader.getFlowName() + "\t"
							+ reader.getFlowID() + "\t" + reader.getFlowCoordinatorProgram());
				else
					taOutput.setText(
							taOutput.getText() + "\n" + reader.getFlowDescription() + "\t" + reader.getFlowName() + "\t"
									+ reader.getFlowID() + "\t" + reader.getFlowCoordinatorProgram());
				excelManager.getlRowValue().add(new LinkedList<String>());
				excelManager.getlRowValue().getLast().add(reader.getFlowDescription());
				excelManager.getlRowValue().getLast().add(reader.getFlowName());
				excelManager.getlRowValue().getLast().add(reader.getFlowID());
				excelManager.getlRowValue().getLast().add(reader.getFlowCoordinatorProgram());
			}
			else{
				if(taOutput.getText().isEmpty())
					taOutput.setText(f[i].getName() + "Não Processado Corretamente");
				else
					taOutput.setText(taOutput.getText() + "\n" + f[i].getName() + "Não Processado Corretamente");
			}
		}
		excelManager.generateExcelFile();
	}
}
