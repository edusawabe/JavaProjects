package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
			AnchorPane root = (AnchorPane) fxmlLoader.load();
			Scene scene = new Scene(root,230,110);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			LoginController l = fxmlLoader.<LoginController>getController();
			l.setStage(primaryStage);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void gerarNomes() {
		LinkedList<String> l1 = new LinkedList<String>();
		LinkedList<String> l2 = new LinkedList<String>();
		LinkedList<String> lTipoBook = new LinkedList<String>();
		LinkedList<String> lTipoPgmCoord = new LinkedList<String>();
		LinkedList<String> lTipoPgmBatch = new LinkedList<String>();
		LinkedList<String> lTipoPgmFunc = new LinkedList<String>();
		LinkedList<String> lTipoPgmBasico = new LinkedList<String>();

		String result = new String();

		l1.add("0");
		l1.add("1");
		l1.add("2");
		l1.add("3");
		l1.add("4");
		l1.add("5");
		l1.add("6");
		l1.add("7");
		l1.add("8");
		l1.add("9");
		l1.add("A");
		l1.add("B");
		l1.add("C");
		l1.add("D");
		l1.add("E");
		l1.add("F");
		l1.add("G");
		l1.add("H");
		l1.add("I");
		l1.add("J");
		l1.add("K");
		l1.add("L");
		l1.add("M");
		l1.add("N");
		l1.add("O");
		l1.add("P");
		l1.add("Q");
		l1.add("R");
		l1.add("S");
		l1.add("T");
		l1.add("U");
		l1.add("V");
		l1.add("X");
		l1.add("Y");
		l1.add("Z");
		l1.add("W");

		l2.add("0");
		l2.add("1");
		l2.add("2");
		l2.add("3");
		l2.add("4");
		l2.add("5");
		l2.add("6");
		l2.add("7");
		l2.add("8");
		l2.add("9");
		l2.add("A");
		l2.add("B");
		l2.add("C");
		l2.add("D");
		l2.add("E");
		l2.add("F");
		l2.add("G");
		l2.add("H");
		l2.add("I");
		l2.add("J");
		l2.add("K");
		l2.add("L");
		l2.add("M");
		l2.add("N");
		l2.add("O");
		l2.add("P");
		l2.add("Q");
		l2.add("R");
		l2.add("S");
		l2.add("T");
		l2.add("U");
		l2.add("V");
		l2.add("X");
		l2.add("Y");
		l2.add("Z");
		l2.add("W");

		lTipoBook.add("A");
		lTipoBook.add("B");
		lTipoBook.add("C");
		lTipoBook.add("E");
		lTipoBook.add("I");
		lTipoBook.add("K");
		lTipoBook.add("S");
		lTipoBook.add("W");

		lTipoPgmBatch.add("B");
		lTipoPgmBatch.add("C");
		lTipoPgmBatch.add("E");
		lTipoPgmBatch.add("F");
		lTipoPgmBatch.add("O");
		lTipoPgmBatch.add("P");
		lTipoPgmBatch.add("T");
		lTipoPgmBatch.add("R");
		lTipoPgmBatch.add("V");

		lTipoPgmCoord.add("A");
		lTipoPgmCoord.add("C");
		lTipoPgmCoord.add("E");
		lTipoPgmCoord.add("F");
		lTipoPgmCoord.add("I");
		lTipoPgmCoord.add("L");
		lTipoPgmCoord.add("M");
		lTipoPgmCoord.add("O");

		lTipoPgmFunc.add("A");
		lTipoPgmFunc.add("C");
		lTipoPgmFunc.add("E");
		lTipoPgmFunc.add("I");
		lTipoPgmFunc.add("L");
		lTipoPgmFunc.add("K");
		lTipoPgmFunc.add("O");

		lTipoPgmBasico.add("U");
		lTipoPgmBasico.add("D");
		lTipoPgmBasico.add("I");
		lTipoPgmBasico.add("S");



		for (int j = 0; j < l1.size(); j++) {
			for (int j2 = 0; j2 < l2.size(); j2++) {

				// Gerar coordenadores
				for (int i = 0; i < lTipoPgmCoord.size(); i++) {
					result = result + "RFIN1" + l1.get(j) + l2.get(j2) + lTipoPgmCoord.get(i) + "\tL" + "\n";

				}

				// Gerar Batchs
				for (int i = 0; i < lTipoPgmBatch.size(); i++) {
					result = result + "RFIN2" + l1.get(j) + l2.get(j2) + lTipoPgmBatch.get(i) + "\tL" + "\n";
				}

				// Gerar Funcionais
				for (int i = 0; i < lTipoPgmFunc.size(); i++) {
					result = result + "RFIN3" + l1.get(j) + l2.get(j2) + lTipoPgmFunc.get(i) + "\tL" + "\n";
				}

				// Gerar Basicos
				for (int i = 0; i < lTipoPgmBasico.size(); i++) {
					result = result + "RFIN4" + l1.get(j) + l2.get(j2) + lTipoPgmBasico.get(i) + "\tL" + "\n";
				}

				// Gerar Books
				for (int i = 0; i < lTipoBook.size(); i++) {
					result = result + "RFINW" + l1.get(j) + l2.get(j2) + lTipoBook.get(i) + "\tL" + "\n";
				}
			}
		}

		System.out.print(result);

		//https://scm-coconet.capgemini.com/svn/repos/rfin/ControleFontes

		File confgFile  = new File("ListaProgramas.txt");
        BufferedWriter writer;
        try {
				writer = new BufferedWriter(new FileWriter(confgFile));
				writer.write(result);
				writer.flush();
				writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
