package view;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import model.RankingLine;;


public class ColumnCell extends TableCell<RankingLine, String> {
	@Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
           super.setText(item);
        }
        if(item.equals("00/0/000.0")){
        	this.setTextFill(Color.DARKRED);
        }else{
        	this.setTextFill(Color.DARKGRAY);
        }
    }
}
