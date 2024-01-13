package processor;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;

public class ClientOrder {
	private String clientId;
	private TreeMap<String, LineItem> items;

	public ClientOrder(String id) {
		// System.out.println("creaeting Client Order : "+id);
		clientId = id;
		items = new TreeMap<String, LineItem>();
	}

	public String getClientId() {
		return this.clientId;
	}

	public TreeMap<String, LineItem> getItems() {
		return new TreeMap<String, LineItem>(items);
	}

	public void add(String itemName, double price, int count) {
		if (items.containsKey(itemName)) {
			LineItem item = items.get(itemName);
			items.remove(itemName);
			item.setQuantity(item.getQuantity() + count);
			items.put(itemName, item);
		} else {
			LineItem item = new LineItem(itemName, price, count);
			items.put(itemName, item);
		}
	}

	public double getOrderTotal() {
		double total = 0.0;
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>(items.values());
		for (LineItem item : lineItems) {
			total += item.getCost();
		}
		return total;

	}

	public String toString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		StringBuilder message = new StringBuilder();

		if (!clientId.equalsIgnoreCase("summary")) {
			message.append("----- Order details for client with Id: ")
					.append(clientId).append(" -----\n");
			double total = appendLineItems(message, items, formatter);
			message.append(String.format("Order Total: %s\n",
					formatter.format(total)));
		} else {
			message.append("***** Summary of all orders *****\n");
			double total = appendSummaryItems(message, items, formatter);
			message.append(String.format("Summary Grand Total: %s\n",
					formatter.format(total)));
		}

		return message.toString();
	}

	private double appendLineItems(StringBuilder message,
			TreeMap<String, LineItem> lineItems, NumberFormat formatter) {
		double total = 0.0;

		for (LineItem item : lineItems.values()) {
			total += item.getCost();
			message.append(item);
		}

		return total;
	}

	private double appendSummaryItems(StringBuilder message,
			TreeMap<String, LineItem> lineItems, NumberFormat formatter) {
		double total = 0.0;

		for (LineItem item : lineItems.values()) {
			total += item.getCost();
			message.append(String.format(
					"Summary - Item's name: %s, Cost per item: %s, Number sold: "
					+ "%d, Item's Total: %s\n",
					item.getName(), formatter.format(item.getPrice()),
					item.getQuantity(), formatter.format(item.getCost())));
		}

		return total;
	}
}
