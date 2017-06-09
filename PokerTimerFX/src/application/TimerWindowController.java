package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
		lbMediaFichas.setText("M�dia de Fichas: " + mediaFichas);
	}

	public void setTextLbTotalArrecadado(String totalArrecadado){
		lbTotalArrecadado.setText("Total Arrecadado: " + totalArrecadado);
	}

	public void setTextLbPos1(String pos1){
		lbPos1.setText("1�: " + pos1);
	}

	public void setTextLbPos2(String pos2){
		lbPos2.setText("2�: " + pos2);
	}

	public void setTextLbPos3(String pos3){
		lbPos3.setText("3�: " + pos3);
	}

	public void setTextLbPos4(String pos4){
		lbPos4.setText("4�: " + pos4);
	}

	public void setTextLbPos5(String pos5){
		lbPos5.setText("5�: " + pos5);
	}
}
