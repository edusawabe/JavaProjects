package view;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.RankingLine;

public class ColumnFactory implements Callback<TableColumn<RankingLine, String>, TableCell<RankingLine,String>> {

	@SuppressWarnings("rawtypes")
	@Override
	public TableCell call(TableColumn param) {
		return new TableCell<TableRow, String>() {
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				this.setText(item);
				if(item!=null){
					if(item.equals("00/0/000.0")){
			        	this.setTextFill(Color.RED);
			        	this.setStyle("Bold");
			        }else{
			        	this.setTextFill(Color.BLACK);
			        	this.setStyle("Bold");
			        }
		        }
			}
		};
	}
}
