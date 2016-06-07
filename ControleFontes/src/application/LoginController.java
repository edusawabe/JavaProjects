package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class LoginController implements Initializable{

	@FXML
	private PasswordField passworField;
	@FXML
	private TextField userField;
	private static final Logger log = Logger.getLogger(LoginController.class);
	private Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userField.setText("esawabe");
		passworField.setText("Educap123457");
	}

	@FXML
	public void doLogin(Event event) {
		SVNManager svnManager = new SVNManager();

		try {
			if (userField.getText().isEmpty() && passworField.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Favor informar o Usuário e Senha do Coconet!" );
				alert.show();
				return;
			}
			else if (userField.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Favor informar o Usuário do Coconet!" );
				alert.show();
				return;
			}
			else if (passworField.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Favor informar a Senha do Coconet!" );
				alert.show();
				return;
			}

			svnManager.setUser(userField.getText());
			svnManager.setPassword(passworField.getText());

			//svnManager.connect();

			String result = svnManager.login();
			if (!result.contains("Exportação completa.")) {
				if (result.equals("")) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Erro de Login SVN" + result);
					alert.show();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Erro no carregamento do Arquivo de Fontes:\n" + result);
					alert.show();
				}
			} else {
				try {
					AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("MainGUI.fxml"));
					stage.close();
					Scene scene = new Scene(root, 400, 400);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					stage.setScene(scene);
					stage.setTitle("Controle de Nome de Fontes");
					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
