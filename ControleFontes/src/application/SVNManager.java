package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;
import com.collabnet.ce.soap60.webservices.ClientSoapStubFactory;
import com.collabnet.ce.soap60.webservices.cemain.CollabNetSoapStub;
import com.collabnet.ce.soap60.webservices.cemain.ICollabNetSoap;


public class SVNManager {
	 private String url;
	 private String user;
	 private String password;
	 private static final Logger log = Logger.getLogger(SVNManager.class);

	public String login() throws IOException {
		url = "https://scm-coconet.capgemini.com/svn/repos/rfin/ControleFontes/ListaProgramas.txt";
		File f = new File("C:\\Fontes\\ListaProgramas.txt");
		String commandLine = new String("svn export " + url +  " C:\\Fontes --username " + user  +" --password " + password);
		boolean success = false;
		String result;
		Process p;
		BufferedReader input;
		StringBuffer cmdOut = new StringBuffer();
		String lineOut = null;
		int numberOfOutline = 0;

		try {
			f.delete();
			p = Runtime.getRuntime().exec(commandLine);
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((lineOut = input.readLine()) != null) {
				if (numberOfOutline > 0) {
					cmdOut.append("\n");
				}
				cmdOut.append(lineOut);
				numberOfOutline++;
			}
			result = cmdOut.toString();
			success = true;
			input.close();
		} catch (IOException e) {
			result = String.format("Falha ao executar comando %s. Erro: %s", commandLine, e.toString());
		}
		// Se não executou com sucesso, lança a falha
		if (!success) {
			throw new IOException(result);
		}
		return result;
	}

	public void connect() {
		url = "https://scm-coconet.capgemini.com/svn/repos/rfin/";
		CollabNetSoapStub c = new CollabNetSoapStub(url);
		ICollabNetSoap s = (ICollabNetSoap) ClientSoapStubFactory.getSoapStub(ICollabNetSoap.class, url);

		try {
			String sessionID = s.login(user, password);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test(){
		 user = "esawabe";
		 password = "Educap123457";
	 }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
