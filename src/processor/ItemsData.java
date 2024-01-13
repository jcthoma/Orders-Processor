package processor;

import java.util.TreeMap;

public class ItemsData {
	private TreeMap<String, Item> items;

	public ItemsData() {
		items = new TreeMap<String, Item>();

	}

	public Item getItem(String name) {
		Item item = null;
		if (items.containsKey(name)) {
			item = items.get(name);
		}
		return item;
	}

	public boolean addItem(String name, double price) {
		boolean added = false;
		if (getItem(name) == null) {
			Item item = new Item(name, price);
			items.put(name, item);
			added = true;
		}
		return added;

	}

}