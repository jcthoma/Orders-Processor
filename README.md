# Orders-Processor
Multiple Thread Processing

The program will process a set of files (e.g., example1.txt) each representing a purchase order. Each file lists the items bought and the date of purchase. The possible items that can be purchased (along with the item's price) can be found in a item's data file (e.g., itemsData.txt). The program you need to write will generate a summary for each order (file). The summary includes the client id and a sorted list (by item's name) of each item bought. The list will include the item's name, the cost per item, the quantity of items bought, and the total cost associated with the item's purchase. After the sorted list, an order's total will be displayed. See the resultsExample.txt file for an example of the data format.

In addition to a report for each order, the program will generate a summary of all orders. The summary will display a sorted list (by item's name) providing information about the total number of items sold, and total revenue (see resultsExample.txt).

Threaded Processing
The program will allow users to process all the orders using a single thread or one thread per order (file). For simplicity, all the orders will use the same base filename (e.g., example in the files above). The user will provide a filename for the results.
