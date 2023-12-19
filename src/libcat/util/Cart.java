package libcat.util;

import libcat.Library;

import java.util.ArrayList;

public class Cart {
    private final Customer customer;
    private double totalPrice;
    private ArrayList<Order> pendingOrders = new ArrayList<Order>();
    private ArrayList<Transaction> pendingTransactions = new ArrayList<Transaction>();

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public boolean addPurchase(Book book, int quantity) {
        if (book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)) {
            pendingOrders.add(new Order(customer, book, quantity, pendingOrders));
            return true;
        } else {
            return false;
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

    public boolean deletePurchase(Book book) {
        boolean deleteSuccessful = false;
        for (Order order : pendingOrders) {
            if (order.getBook().equals(book)) {
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<Order> getPendingOrders() {
        return pendingOrders;
    }

    public ArrayList<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }

    public boolean checkout() {
        boolean checkoutDone = false;

        if (!getPendingOrders().isEmpty()) {
            for (Order order : getPendingOrders()) {
                Library.createOrder(order);

                totalPrice += order.getBook().getPrice() * (1 - order.getBook().getSalePercent()) * order.getQuantity();
            }

            getPendingOrders().clear();
        }

        if (!getPendingTransactions().isEmpty()) {
            for (Transaction transaction : getPendingTransactions()) {
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

