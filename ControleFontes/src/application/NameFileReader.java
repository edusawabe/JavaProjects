package application;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import model.NameFileItem;
import model.NameFileItemList;

public class NameFileReader {
	private File f;
	private java.util.HashMap<String, NameFileItemList> map;

	public NameFileReader(File f) {
		this.f = f;
	}

	public void loadFile(){
		map = new HashMap<String, NameFileItemList>();

		NameFileItemList map2 = new NameFileItemList();

		NameFileItem item = new NameFileItem();
		item.setFree(true);
		item.setName("RFIN100I");
		item.setType("I");

		NameFileItem item2 = new NameFileItem();
		item2.setFree(false);
		item2.setName("RFIN100A");
		item2.setType("A");

		map2.getMap().put(item.getType(), item);
		map2.getMap().put(item2.getType(), item2);

		map.put("00", map2);

		Set<String> s = map.keySet();

		for (String object2 : s) {
			for (int i = 0; i < map.get(object2).getMap().size(); i++) {
				Set<String> s2 = map.get(object2).getMap().keySet();
				for (String string : s2) {
					if (map.get(object2).getMap().get(string).isFree())
						System.out.println(map.get(object2).getMap().get(string).getName() + " - " + map.get(object2).getMap().get(string).getType() + " - " + "Livre");
					else
						System.out.println(map.get(object2).getMap().get(string).getName() + " - " + map.get(object2).getMap().get(string).getType() + " - " + "Em Uso");
				}
			}

		}
	}

	public java.util.HashMap<String, NameFileItemList> getMap() {
		return map;
	}
}
