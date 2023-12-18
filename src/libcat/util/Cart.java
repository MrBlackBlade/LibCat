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
            pendingTransactions.add(new Transaction((Borrower) customer, book));
            return true;
        } else {
            return false;
        }
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
