package factory;

import javafx.util.StringConverter;

public class HourStringConverter extends StringConverter<String>{

	@Override
	public String toString(String object) {
		if(object == null)
			return new String("");
		if(object.contains(":"))
			return object;
		if(object.length() == 3)
			return "0"+object.substring(0, 1) + ":" + object.substring(1, 3);
		else
			return object.substring(0, 2) + ":" + object.substring(2, 4);
	}

	@Override
	public String fromString(String string) {
		// TODO Auto-generated method stub
		return string;
	}


}
