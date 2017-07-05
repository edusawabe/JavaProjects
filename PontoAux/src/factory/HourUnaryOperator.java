package factory;

import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

import java.util.function.UnaryOperator;

public class HourUnaryOperator implements UnaryOperator<TextFormatter.Change>{

	@Override
	public Change apply(Change t) {
        if (t.isReplaced())
            if(t.getText().matches("[^0-9]"))
                t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

        if (t.isAdded() || t.isContentChange()) {
        	if(t.getControlNewText().length() > 5){
        		t.setText("");
        		return t;
        	}
        	if(t.getControlText().isEmpty()){
        		if(t.getText().matches("[^0-9]")){
        			t.setText("");
        			return t;
        		}
        		else{
        			if(t.getText().matches("[3-9]")){
        				t.setText("");
        				return t;
        			}
        		}
        	}
        	if(t.getControlNewText().length() == 3){
        		if(t.getText().matches("[^0-9]")){
        			t.setText("");
        			return t;
        		}
        		else{
        			if(t.getText().matches("[6-9]")){
        				t.setText("");
        				return t;
        			}
        		}
        		if(!t.getText().isEmpty()){
	        		t.setText(":" + t.getText());
	        		t.setAnchor(t.getControlNewText().length());
	        		t.setCaretPosition(t.getControlNewText().length());
        		}
        		return t;
        	}

            if (t.getControlText().contains(":")) {
            	if(t.getControlText().endsWith(":")){
            		if(t.getText().matches("[^0-9]")){
            			t.setText("");
            			return t;
            		}
            		else{
            			if(t.getText().matches("[6-9]")){
            				t.setText("");
            				return t;
            			}
            		}
            		return t;
            	}
                if (t.getText().matches("[^0-9]")) {
                    t.setText("");
                }
            } else {
            	if (t.getText().equals(":")) {
            		if(t.getControlNewText().charAt(1) == ':'){
            			t.setText("0" + t.getControlText() + ":");
            		}
                    return t;
            	}
                if (t.getText().matches("[^0-9]")) {
                    t.setText("");
                }
            }
        }
        return t;
    }
}
