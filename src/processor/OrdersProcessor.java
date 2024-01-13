package processor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class OrdersProcessor {

	static final String YES_VALUE = "y";
	static final String SUMMARY_ORDER = "summary";

	public static void main(final String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		TreeMap<String, ClientOrder> totalClients = new TreeMap<String, ClientOrder>();

		Scanner scan = new Scanner(System.in);

		System.out.print("Enter item's data filename: ");
		String filename = scan.nextLine();

		System.out.print(
				"\nEnter 'y' for multiple threads, any other character otherwise: ");
		String threads = scan.nextLine();

		System.out.println("\nEnter number of orders to process: ");
		int orders = scan.nextInt();
		scan.nextLine();

		System.out.println("\nEnter order's base filename ");
		String baseName = scan.nextLine();

		System.out.println("\nEnter result's filename ");
		String result = scan.nextLine();
		scan.close();

		TreeMap<String, Item> itemTable = lookupTable(filename);
		long startTime = System.currentTimeMillis();

		Thread[] allThreads;

		if ((orders > 1) && threads.toLowerCase().equals(YES_VALUE)) {
			allThreads = new Thread[orders];
			for (int i = 1; i <= allThreads.length; i++) {
				String baseFilename = baseName + i;
				allThreads[i - 1] = new Thread(new ProcessClientData(
						baseFilename, 1, itemTable, totalClients));

			}
		} else {
			allThreads = new Thread[1];
			String baseFilename = baseName;
			allThreads[0] = new Thread(new ProcessClientData(baseFilename,
					orders, itemTable, totalClients));
		}

		for (int i = 0; i < allThreads.length; i++) {
			allThreads[i].start();
		}

		for (int i = 0; i < allThreads.length; i++) {
			allThreads[i].join();
		}

		long endTime = System.currentTimeMillis();

		System.out.println("Processing time (msec): " + (endTime - startTime));

		extracted(totalClients, result);
	}

	private static void extracted(TreeMap<String, ClientOrder> totalClients,
			String fileName) {
		StringBuilder summery = new StringBuilder();
		ClientOrder sumOrder = new ClientOrder(SUMMARY_ORDER);

		for (ClientOrder client : totalClients.values()) {
			for (LineItem item : client.getItems().values()) {
				sumOrder.add(item.getName(), item.getPrice(),
						item.getQuantity());
			}
			summery.append(client.toString());
		}

		summery.append(sumOrder);
		String summary = summery.toString();
		System.out.println(summary);

		try (PrintWriter resultFile = new PrintWriter(fileName)) {
			resultFile.print(summary);
		} catch (FileNotFoundException e) {
			System.err.println("file not found: " + fileName);
			e.printStackTrace();
		}
	}

	private static TreeMap<String, Item> lookupTable(String fileName) {
		TreeMap<String, Item> lookup = new TreeMap<>();
		try (BufferedReader reader = new BufferedReader(
				new FileReader(fileName))) {
			String line = reader.readLine();
			while (line != null) {
				String[] array = line.split(" ");
				Item item = new Item(array[0], Double.parseDouble(array[1]));
				lookup.put(item.getName(), item);
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			System.err.println("file not found: " + fileName);
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.err.println("IO Exception: " + fileName);
			e.printStackTrace();
			return null;
		}
		return lookup;
	}

}
