package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import model.Player;
import util.Constants;

public class GraficoAnualController implements Initializable{
	private final String[] mes = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
	private LineChart graficoLinha;

	@FXML
	private AnchorPane mainPane;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graficoLinha = new LineChart(
				new CategoryAxis(), new NumberAxis());
		ConfigManager configManager = new ConfigManager();
		configManager.setConfigFileName(Constants.CONFIG_FILE_NAME);
		configManager.readFile();
		configManager.getPlayers();
		LinkedList<Player> list = configManager.getListPlayer();

		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getPontuacaoTotal() > 0){
				XYChart.Series prod1 = new XYChart.Series();
				prod1.setName(list.get(i).getPlayerName());
				double acumulado = 0;
				for (int j = 0; j < mes.length; j++) {
					acumulado = acumulado + list.get(i).getResultados().get(j).getPontuacaoEtapa();
					prod1.getData().add(new XYChart.Data(mes[j], acumulado));
					Tooltip t = new Tooltip(prod1.getName() + "\n" + mes[j] + "\n" +acumulado);
			        Tooltip.install(prod1.getNode(), t);
				}
				graficoLinha.getData().add(prod1);
			}
		}
		graficoLinha.setPrefSize(1200, 700);
		mainPane.getChildren().add(graficoLinha);

        /**
         * Browsing through the Data and applying ToolTip
         * as well as the class on hover
         */
        for (int i = 0; i < graficoLinha.getData().size(); i++) {
        	XYChart.Series s = ((XYChart.Series) graficoLinha.getData().get(i));
            for (int j = 0; j < s.getData().size(); j++) {
            	@SuppressWarnings("rawtypes")
				XYChart.Data data = ((XYChart.Data) s.getData().get(j));
				Tooltip.install(data.getNode(), new Tooltip(
						"Nome: " + s.getName() + "\n" + "Mês: " + data.getXValue().toString() + "\n" + "Pontuação Acumulada: " + data.getYValue()));

                //Adding class on hover
                data.getNode().setOnMouseEntered(event -> data.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
                data.getNode().setOnMouseExited(event -> data.getNode().getStyleClass().remove("onHover"));
            }
        }
	}

}
