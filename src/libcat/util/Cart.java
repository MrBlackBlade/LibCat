package libcat.util;

import libcat.Library;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cart {
	private final Customer customer;
	private double totalPrice;
	private ArrayList<Order> pendingOrders = new ArrayList<Order>();
	private ArrayList<Transaction> pendingTransactions = new ArrayList<Transaction>();

	public Cart(Customer customer) {
		this.customer = customer;
	}

	public double updateTotalPrice() {
		totalPrice = 0;

		for (Order order : pendingOrders) {
			totalPrice += order.getTotalPrice();
		}

		return totalPrice;
	}

	public boolean addPurchase(Book book, int quantity) {
		if (book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)) {
			Order newOrder = new Order(customer, book, quantity, pendingOrders);
			pendingOrders.add(newOrder);
			updateTotalPrice();
			return true;
		} else {
			return false;
		}
	}

	public void modifyPurchase(Order order, int quantity) {
		order.setQuantity(quantity);
		updateTotalPrice();
		if (quantity == 0) {
			deletePurchase(order);
		}
	}

	public boolean addBorrow(Book book) {
		if (book.getPurchaseStatus().get(Book.Availablity.BORROWABLE)) {
			pendingTransactions.add(new Transaction(customer, book, pendingTransactions));
			return true;
		} else {
			return false;
		}
	}

	public boolean deletePurchase(Order order) {
		boolean deleteSuccessful = false;
		if (pendingOrders.contains(order)) {
			pendingOrders.remove(order);
			deleteSuccessful = true;
		}
		return deleteSuccessful;
	}

	public boolean deleteBorrow(Transaction transaction) {
		boolean deleteSuccessful = false;
		if (pendingTransactions.contains(transaction)) {
			pendingTransactions.remove(transaction);
			deleteSuccessful = true;
		}
		return deleteSuccessful;
	}

	public double getTotalPrice() {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		return Double.parseDouble(decimalFormat.format(totalPrice));
	}

	public ArrayList<Order> getPendingOrders() {
		return pendingOrders;
	}

	public ArrayList<Transaction> getPendingTransactions() {
		return pendingTransactions;
	}

	public boolean checkout() {
		if (!getPendingOrders().isEmpty()) {
			for (Order order : getPendingOrders()) {
				for (Reservation reservation : Library.getUserPurchaseReservations(customer)) {
					if (reservation.getBook().equals(order.getBook())) {
						Library.removeReservation(reservation);
					}
				}
				Library.createOrder(order);

				totalPrice += order.getBook().getSalePrice() * order.getQuantity();
			}

			getPendingOrders().clear();
		}

		if (!getPendingTransactions().isEmpty()) {
			for (Transaction transaction : getPendingTransactions()) {
				for (Reservation reservation : Library.getUserBorrowReservations(customer)) {
					if (reservation.getBook().equals(transaction.getBook())) {
						Library.removeReservation(reservation);
					}
				}
				Library.createTransaction(transaction);
			}

			getPendingTransactions().clear();
		}

		return getPendingOrders().isEmpty() && getPendingTransactions().isEmpty();
	}

	@Override
	public String toString() {
		return "Cart{" +
				"customer=" + customer +
				", pendingOrders=" + pendingOrders +
				", pendingTransactions=" + pendingTransactions +
				'}';
	}
}

