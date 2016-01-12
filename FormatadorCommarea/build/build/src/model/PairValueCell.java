package model;

import javafx.scene.control.TableCell;
import javafx.util.Pair;
import view.MaskTextField;

public class PairValueCell extends TableCell<Pair<String, Object>, Object> {
	@Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

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
                setGraphic((MaskTextField)item);
            }
        } else {
        	setGraphic(null);
            setText("");
        }
    }
}
