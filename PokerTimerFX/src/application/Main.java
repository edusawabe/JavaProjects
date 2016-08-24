package application;

import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("PokerTimerFX.fxml"));
			Scene scene = new Scene(myPane,1024,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//scene.getStylesheets().add(getClass().getResource("applicationBlack.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Poker Timer 4.0");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
