package view;

import javafx.scene.control.TableCell;
import javafx.util.Pair;
import model.MyValue;

public class PairValueCell extends TableCell<Pair<String, Object>, Object> {
	@Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        //this.setStyle("-fx-background-color:white");
        if (item != null) {
            if (item instanceof String) {
                setText((String) item);
            } else if (item instanceof MyValue) {
            	setGraphic(null);
            	if(((MyValue) item).getValue() != null)
					setText(((MyValue) item).getValue());
				else
					setText("");
            } else if (item instanceof MaskTextField){
                if (((MaskTextField) item).isDependingOn())
                	this.setStyle("-fx-background-color:red");
            	setGraphic((MaskTextField)item);
            }
        } else {
        	setGraphic(null);
            setText("");
        }
    }
}
