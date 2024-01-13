package processor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class ProcessClientData implements Runnable {

	private String filename;
	private int fileNum;
	private TreeMap<String, ClientOrder> orders;
	private TreeMap<String, Item> items;

	public ProcessClientData(String filename, int fileNum,
			TreeMap<String, Item> items, TreeMap<String, ClientOrder> orders) {
		this.filename = filename;
		this.fileNum = fileNum;
		this.items = items;
		this.orders = orders;

	}

	public void run() {
		ArrayList<ClientOrder> clientOrders = new ArrayList<>();

		for (int i = 1; i <= this.fileNum; i++) {
			String name = this.filename + (fileNum == 1 ? "" : i) + ".txt";
			ClientOrder process = processOrder(name);
			if (process != null) {
				clientOrders.add(process);
			} else {
				System.out.println("Unable to process order: " + name);
			}
		}

		synchronized (orders) {
			for (ClientOrder order : clientOrders) {
				orders.put(order.getClientId(), order);
			}
		}
	}


	private ClientOrder processOrder(String filename) {
		ClientOrder process = null;
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(filename));
			String line = reader.readLine();
			if (line != null) {
				String[] array = line.trim().split(" ");
				process = new ClientOrder(array[1].trim());
			}

			line = reader.readLine();
			while (line != null) {
				String[] array = line.trim().split(" ");
				String name = array[0];
				Item item = this.items.get(name);
				if (item != null) {
					process.add(item.getName(), item.getPrice(), 1);
				} else {
					System.err.println(
							"Requested Item from Order " + item + " not found");
				}
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return process;
	}

}