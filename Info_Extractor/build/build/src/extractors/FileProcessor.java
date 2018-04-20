package extractors;

import java.io.File;
import java.util.LinkedList;

import javafx.application.Platform;
import util.ExcelManager;

public class FileProcessor {
	private File[] toProcess;
	private int processed;
	private int totalFiles;
	private String result;

	public FileProcessor() {
		result = new String();
		processed = 0;
		totalFiles = 0;
	}

	public void process() {
		totalFiles = toProcess.length;

		if(toProcess[0].getName().charAt(4) == 'I' || toProcess[0].getName().charAt(4) == 'i')
			processTxaDir(toProcess);
		else
			processDclgenDir(toProcess);
	}

	private void processDclgenDir(File[] f) {
		DclgenFileReader reader = new DclgenFileReader(null);

		ExcelManager excelManager = new ExcelManager();
		excelManager.setFileName("./DclgenInfos.xlsx");
		excelManager.getlConlumns().add("DCLGEN");
		excelManager.getlConlumns().add("Nome da Tabela");

		for (int i = 0; i < f.length; i++) {
			reader.setDclgenFileName(f[i]);

			if(reader.getFileInfos()){
				if(result.isEmpty())
					result = reader.getDclgenName() + "\t" + reader.getDclgenTableName();
				else
					result = result + "\n" + reader.getDclgenName() + "\t" + reader.getDclgenTableName();
				excelManager.getlRowValue().add(new LinkedList<String>());
				excelManager.getlRowValue().getLast().add(reader.getDclgenName());
				excelManager.getlRowValue().getLast().add(reader.getDclgenTableName());
			}else{
				if(result.isEmpty())
					result = (f[i].getName() + "Não Processado Corretamente");
				else
					result = result + "\n" + f[i].getName() + "Não Processado Corretamente";
			}
			processed++;
		}
		excelManager.generateExcelFile();
	}

	private void processTxaDir(File[] f) {
		TxaExecutionFlowFileReader reader = new TxaExecutionFlowFileReader(null);

		ExcelManager excelManager = new ExcelManager();
		excelManager.setFileName("./FRWK.xlsx");
		excelManager.getlConlumns().add("Descricao Resumida Fluxo");
		excelManager.getlConlumns().add("Nome do Fluxo");
		excelManager.getlConlumns().add("Fluxo");
		excelManager.getlConlumns().add("Coordenador");

		for (int i = 0; i < f.length; i++) {
			reader.setTxaExecutionFlowFile(f[i]);
			if(reader.getFileInfos()){
				if (result.isEmpty())
					result = reader.getFlowDescription() + "\t" + reader.getFlowName() + "\t" + reader.getFlowID()
							+ "\t" + reader.getFlowCoordinatorProgram();
				else
					result = result + "\n" + reader.getFlowDescription() + "\t" + reader.getFlowName() + "\t"
							+ reader.getFlowID() + "\t" + reader.getFlowCoordinatorProgram();

				excelManager.getlRowValue().add(new LinkedList<String>());
				excelManager.getlRowValue().getLast().add(reader.getFlowDescription());
				excelManager.getlRowValue().getLast().add(reader.getFlowName());
				excelManager.getlRowValue().getLast().add(reader.getFlowID());
				excelManager.getlRowValue().getLast().add(reader.getFlowCoordinatorProgram());
			}
			else{
				if(result.isEmpty())
					result = (f[i].getName() + "Não Processado Corretamente");
				else
					result = result + "\n" + f[i].getName() + "Não Processado Corretamente";
			}
			processed++;
		}
		excelManager.generateExcelFile();
	}

	public File[] getToProcess() {
		return toProcess;
	}

	public void setToProcess(File[] toProcess) {
		this.toProcess = toProcess;
	}

	public int getProcessed() {
		return processed;
	}

	public void setProcessed(int processed) {
		this.processed = processed;
	}

	public int getTotalFiles() {
		return totalFiles;
	}

	public void setTotalFiles(int totalFiles) {
		this.totalFiles = totalFiles;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
