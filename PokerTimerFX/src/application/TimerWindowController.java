package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TimerWindowController implements Initializable{
	@FXML
	private Label lbTimer;
	@FXML
	private Label lbBlindsAtual;
	@FXML
	private Label lbBlindsProxima;
	@FXML
	private ProgressBar pbProgress;
	@FXML
	private Label lbProxBreak;
	@FXML
	private Label lbJogando;
	@FXML
	private Label lbRebuys;
	@FXML
	private Label lbFora;
	@FXML
	private Label lbMediaFichas;
	@FXML
	private Label lbTotalArrecadado;
	@FXML
	private Label lbPos1;
	@FXML
	private Label lbPos2;
	@FXML
	private Label lbPos3;
	@FXML
	private Label lbPos4;
	@FXML
	private Label lbPos5;
	@FXML
	private VBox vbStats;
	@FXML
	private AnchorPane mainPane;
	private ObjectProperty<Font> fontTrackingTimer = new SimpleObjectProperty<Font>(Font.getDefault());
	private ObjectProperty<Font> fontTrackingBlind = new SimpleObjectProperty<Font>(Font.getDefault());
	private ObjectProperty<Font> fontTrackingNextBlind = new SimpleObjectProperty<Font>(Font.getDefault());
	private ObjectProperty<Font> fontTrackingGeneralUp = new SimpleObjectProperty<Font>(Font.getDefault());
	private ObjectProperty<Font> fontTrackingGeneralDown = new SimpleObjectProperty<Font>(Font.getDefault());

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbTimer.fontProperty().bind(fontTrackingTimer);
		lbBlindsAtual.fontProperty().bind(fontTrackingBlind);
		lbBlindsProxima.fontProperty().bind(fontTrackingNextBlind);
		lbProxBreak.fontProperty().bind(fontTrackingBlind);
		lbFora.fontProperty().bind(fontTrackingGeneralUp);
		lbJogando.fontProperty().bind(fontTrackingGeneralUp);
		lbRebuys.fontProperty().bind(fontTrackingGeneralUp);
		lbFora.fontProperty().bind(fontTrackingGeneralUp);
		lbMediaFichas.fontProperty().bind(fontTrackingGeneralUp);
		lbTotalArrecadado.fontProperty().bind(fontTrackingGeneralUp);
		lbPos1.fontProperty().bind(fontTrackingGeneralDown);
		lbPos2.fontProperty().bind(fontTrackingGeneralDown);
		lbPos3.fontProperty().bind(fontTrackingGeneralDown);
		lbPos4.fontProperty().bind(fontTrackingGeneralDown);
		lbPos5.fontProperty().bind(fontTrackingGeneralDown);

		mainPane.heightProperty().addListener(new ChangeListener<Number>()
		{
		   @Override
		   public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight)
		   {
			   fontTrackingTimer.set(Font.font("System",FontWeight.BOLD,(newHeight.doubleValue() / 2) - 120));
			   fontTrackingBlind.set(Font.font("System",FontWeight.BOLD,(((newHeight.doubleValue() / 2) - 120)/3)));
			   fontTrackingNextBlind.set(Font.font("System",FontWeight.BOLD,(((newHeight.doubleValue() / 2) - 120)/3)-20));
			   fontTrackingGeneralUp.set(Font.font("System",FontWeight.BOLD,(((newHeight.doubleValue() * 0.6))/6)-20));
			   fontTrackingGeneralDown.set(Font.font("System",FontWeight.BOLD,(((newHeight.doubleValue() * 0.4))/5)-20));
		   }
		});
	}

	public Label getLbTimer() {
		return lbTimer;
	}

	public Label getLbBlindsAtual() {
		return lbBlindsAtual;
	}

	public Label getLbBlindsProxima() {
		return lbBlindsProxima;
	}

	public ProgressBar getPbProgress() {
		return pbProgress;
	}

	public Label getLbProxBreak() {
		return lbProxBreak;
	}

	public void setTextLbJogando(String jogando){
		lbJogando.setText("Jogando: " + jogando);
	}

	public void setTextLbRebuys(String rebuys){
		lbRebuys.setText("Rebuys: " + rebuys);
	}

	public void setTextLbFora(String fora){
		lbFora.setText("Fora: " + fora);
	}

	public void setTextLbMediaFichas(String mediaFichas){
		lbMediaFichas.setText("Média: " + mediaFichas);
	}

	public void setTextLbTotalArrecadado(String totalArrecadado){
		lbTotalArrecadado.setText("Total: " + totalArrecadado);
	}

	public void setTextLbPos1(String pos1){
		lbPos1.setText("1º: " + pos1);
	}

	public void setTextLbPos2(String pos2){
		lbPos2.setText("2º: " + pos2);
	}

	public void setTextLbPos3(String pos3){
		lbPos3.setText("3º: " + pos3);
	}

	public void setTextLbPos4(String pos4){
		lbPos4.setText("4º: " + pos4);
	}

	public void setTextLbPos5(String pos5){
		lbPos5.setText("5º: " + pos5);
	}

	public AnchorPane getMainPane() {
		return mainPane;
	}
}
