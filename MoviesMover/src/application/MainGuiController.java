package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class MainGuiController {
	@FXML
	private TextField tfDir;
	@FXML
	private TextArea taProcess;

	@FXML
	private void btSelecionarAction(Event evt){
		DirectoryChooser dChooser = new DirectoryChooser();
		dChooser.setInitialDirectory(new File("./"));
		File f = dChooser.showDialog(null);
		String ret = recursivePrint(f.listFiles(), 0, "");
		taProcess.setText(ret);
	}
    private static String recursivePrint(File[] arr,int index, String in)
    {
    	String ret = new String("");
    	ret = ret + in;

        // terminate condition
        if(index == arr.length)
            return ret;

        // for files
        if(arr[index].isFile())
            ret = ret + arr[index].getName() + "\n";

        // for sub-directories
        else if(arr[index].isDirectory())
        {
            // recursion for sub-directories
            ret = recursivePrint(arr[index].listFiles(), 0, ret);
        }
        ret = recursivePrint(arr,++index, ret);
        return ret;
   }
}
