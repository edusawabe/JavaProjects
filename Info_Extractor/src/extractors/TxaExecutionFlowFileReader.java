package extractors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TxaExecutionFlowFileReader {
	private File txaExecutionFlowFile;
	private String flowID;
	private String flowName;
	private String flowDescription;
	private String flowCoordinatorProgram;

	public TxaExecutionFlowFileReader(File txaExecutionFlowFile) {
		this.setTxaExecutionFlowFile(txaExecutionFlowFile);
		flowID = null;
		flowName = null;
		flowDescription = null;
	}

	public boolean getFileInfos(){
		String line = null;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(txaExecutionFlowFile));
			line = reader.readLine();
			line = reader.readLine();
			if(line != null){
				flowID = line.substring(31, 31+8);
				flowName = line.substring(64, 64+80);
				flowDescription = line.substring(144, 144 + 40);
				flowCoordinatorProgram = line.substring(310, 310+8);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public File getTxaExecutionFlowFile() {
		return txaExecutionFlowFile;
	}

	public void setTxaExecutionFlowFile(File txaExecutionFlowFile) {
		this.txaExecutionFlowFile = txaExecutionFlowFile;
	}

	public String getFlowID() {
		return flowID;
	}

	public String getFlowName() {
		return flowName;
	}

	public String getFlowDescription() {
		return flowDescription;
	}

	public String getFlowCoordinatorProgram() {
		return flowCoordinatorProgram;
	}
}
