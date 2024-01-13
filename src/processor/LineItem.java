package processor;

import java.text.NumberFormat;

public class LineItem {
	private String name;
	private double price;
	private int quantity;

	public LineItem(String name, double price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getCost() {
		return quantity * price;
	}
//	message += String.format(
//			"Summary - Item's name: %s, Cost per item: %s, Number sold: %d, Item's Total: %s\n",
//			item.getName(),
//			formatter.format(item.getPrice()), item.getQuantity(),
//					formatter.format(item.getCost()));
//}

	@Override
	public String toString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String message = String.format(
				"Item's name: %s, Cost per item: %s, Quantity: %d, Cost: %s\n",
				name, formatter.format(price), quantity,
				formatter.format(getCost()));
		return message;
	}

}
