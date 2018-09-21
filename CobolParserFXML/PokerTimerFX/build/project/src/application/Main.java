package application;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	private final static Logger logger = Logger.getLogger(Main.class);

	@Override
	public void start(Stage primaryStage) {
		logger.info("Inicando App");
		try {
			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("PokerTimerFX.fxml"));
			Scene scene = new Scene(myPane,1024,600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Poker Timer 9.0");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
