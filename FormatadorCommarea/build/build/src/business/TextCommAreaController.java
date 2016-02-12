package business;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class TextCommAreaController implements Initializable{
	@FXML
	private TextArea txtArea;

	public void setText(String text){
		if(txtArea == null)
			txtArea = new TextArea();
		txtArea.setText(text);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
