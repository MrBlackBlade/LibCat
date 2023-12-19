package libcat.util;

import libcat.Library;

import java.util.ArrayList;

public class Cart {
    private final Customer customer;

    private ArrayList<Order> pendingOrders = new ArrayList<Order>();
    private ArrayList<Transaction> pendingTransactions = new ArrayList<Transaction>();

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public boolean addPurchase(Book book, int quantity) {
        if (book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)) {
            pendingOrders.add(new Order(customer, book, quantity));
            return true;
        } else {
            return false;
        }
    }

    public boolean addBorrow(Book book) {
        if (book.getPurchaseStatus().get(Book.Availablity.BORROWABLE)) {
            pendingTransactions.add(new Transaction(customer, book));
            return true;
        } else {
            return false;
        }
    }

    public boolean deletePurchase(Book book) {
        boolean deleteSuccessful = false;
        for (Order order : pendingOrders) {
            if ((order.getBook().equals(book))) {
                pendingOrders.remove(order);
                deleteSuccessful = true;
                break;
            }
        }
        return deleteSuccessful;
    }

    public boolean deleteBorrow(Book book) {
        boolean deleteSuccessful = false;
        for (Transaction transaction : pendingTransactions) {
            if (transaction.getBook().equals(book)) {
                pendingTransactions.remove(transaction);
                deleteSuccessful = true;
                break;
            }
        }
        return deleteSuccessful;
    }

    public ArrayList<Order> getPendingOrders() {
        return pendingOrders;
    }

    public ArrayList<Transaction> getPendingTransactions() {
        return pendingTransactions;
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

