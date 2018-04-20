package br.com.utils.excel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

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

public class ExcelManager {
	private String fileName;
	private LinkedList<String> lConlumns;
	private LinkedList<LinkedList<String>> lRowValue;
	private Workbook wb;

	public ExcelManager() {
		lConlumns = new LinkedList<String>();
		lRowValue = new LinkedList<LinkedList<String>>();
	}

	public void generateExcelFile() {
		wb = new XSSFWorkbook();
		XSSFSheet sheet = (XSSFSheet) wb.createSheet();

		/* Create an object of type XSSFTable */
		XSSFTable my_table = sheet.createTable();

		/* get CTTable object */
		CTTable cttable = my_table.getCTTable();

		/* Let us define the required Style for the table */
		CTTableStyleInfo table_style = cttable.addNewTableStyleInfo();
		table_style.setName("TableStyleMedium9");

		/* Set Table Style Options */
		table_style.setShowColumnStripes(false); // showColumnStripes=0
		table_style.setShowRowStripes(true); // showRowStripes=1

		/* Define the data range including headers */
		AreaReference my_data_range = new AreaReference(new CellReference(0, 0),
				new CellReference(lRowValue.size(), lConlumns.size()-1));

		/* Set Range to the Table */
		cttable.setRef(my_data_range.formatAsString());
		cttable.setDisplayName(
				"MYTABLE"); /* this is the display name of the table */
		cttable.setName("Test"); /*
									 * This maps to "displayName" attribute in
									 * <table>, OOXML
									 */
		cttable.setId(1L); // id attribute against table as long value

		CTTableColumns columns = cttable.addNewTableColumns();
		columns.setCount(lConlumns.size()+1); // define number of columns

		/* Define Header Information for the Table */
		for (int i = 0; i < lConlumns.size(); i++) {
			CTTableColumn column = columns.addNewTableColumn();
			column.setName(lConlumns.get(i));
			column.setId(i + 1);
		}

		/* Create a Row */
		XSSFRow row = sheet.createRow(0);
		/* Add Headers */
		for (int i = 0; i < lConlumns.size(); i++) // add header values
		{
			XSSFCell localXSSFCell = row.createCell(i);
			localXSSFCell.setCellValue(lConlumns.get(i));
		}

		/* Add remaining Table Data */
		for (int i = 0; i < lRowValue.size(); i++) // add row values
		{
			/* Create a Row */
			XSSFRow row2 = sheet.createRow(i+1);
			//inout the cell value
			for (int j = 0; j < lRowValue.get(i).size(); j++)
			{
				XSSFCell localXSSFCell = row2.createCell(j);
				localXSSFCell.setCellValue(lRowValue.get(i).get(j));
			}
		}

		/* Write output as File */
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LinkedList<String> getlConlumns() {
		return lConlumns;
	}

	public LinkedList<LinkedList<String>> getlRowValue() {
		return lRowValue;
	}

	public void setSheetName(String sheetName) {
	}

	public void createSheet(){
		wb.createSheet();
	}
}
