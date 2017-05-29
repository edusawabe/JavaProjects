package infoExtractor;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private static final char[] NON_PRINTABLE_EBCDIC_CHARS = new char[] {0x00, 0x01, 0x02, 0x03, 0x9C, 0x09, 0x86, 0x7F, 0x97, 0x8D, 0x8E,
		    0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11, 0x12, 0x13, 0x9D, 0x85, 0x08, 0x87, 0x18, 0x19, 0x92, 0x8F, 0x1C, 0x1D, 0x1E, 0x1F, 0x80,
		    0x81, 0x82, 0x83, 0x84, 0x0A, 0x17, 0x1B, 0x88, 0x89, 0x8A, 0x8B, 0x8C, 0x05, 0x06, 0x07, 0x90, 0x91, 0x16, 0x93, 0x94, 0x95, 0x96,
		    0x04, 0x98, 0x99, 0x9A, 0x9B, 0x14, 0x15, 0x9E, 0x1A, 0xA0 };

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainGUI.fxml"));
			Scene scene = new Scene(root,820,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("PDC Info Extractor V1.0");
			primaryStage.setScene(scene);
			primaryStage.show();
			boolean charOK;

			String s = new String("TESTE\nTESTE ENTER");
			String ret = new String("");

			char[] c = s.toCharArray();
			for (int i = 0; i < c.length; i++) {
				charOK = true;
				for (int j = 0; j < NON_PRINTABLE_EBCDIC_CHARS.length; j++){
					if(NON_PRINTABLE_EBCDIC_CHARS[j] == c[i]){
						charOK = false;
						break;
					}
				}
				if(charOK)
					ret = ret + c[i];
			}
			System.out.print(ret);
		       Workbook wb = new XSSFWorkbook();
		        XSSFSheet sheet = (XSSFSheet) wb.createSheet();

		        //Create
		        XSSFTable table = sheet.createTable();
		        table.setDisplayName("Test");
		        CTTable cttable = table.getCTTable();

		        //Style configurations
		        CTTableStyleInfo style = cttable.addNewTableStyleInfo();
		        style.setName("TableStyleMedium2");
		        style.setShowColumnStripes(false);
		        style.setShowRowStripes(true);

		        //Set which area the table should be placed in
		        AreaReference reference = new AreaReference(new CellReference(0, 0),
		                new CellReference(2,2));
		        cttable.setRef(reference.formatAsString());
		        cttable.setId(1);
		        cttable.setName("Test");
		        cttable.setTotalsRowCount(1);

		        CTTableColumns columns = cttable.addNewTableColumns();
		        columns.setCount(3);
		        CTTableColumn column;
		        XSSFRow row;
		        XSSFCell cell;
		        for(int i=0; i<3; i++) {
		            //Create column
		            column = columns.addNewTableColumn();
		            column.setName("Column");
		            column.setId(i+1);
		            //Create row
		            row = sheet.createRow(i);
		            for(int j=0; j<3; j++) {
		                //Create cell
		                cell = row.createCell(j);
		                if(i == 0) {
		                    cell.setCellValue("Column"+j);
		                } else {
		                    cell.setCellValue("0");
		                }
		            }
		        }

		        FileOutputStream fileOut = new FileOutputStream("c:\\export\\ooxml-table.xlsx");
		        wb.write(fileOut);
		        fileOut.close();
		        wb.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
