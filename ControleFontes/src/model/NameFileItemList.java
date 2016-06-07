package model;

import java.util.HashMap;

public class NameFileItemList {

	HashMap<String, NameFileItem> map;

	public NameFileItemList(){
		map = new HashMap<>();
	}

	public HashMap<String, NameFileItem> getMap() {
		return map;
	}

}
