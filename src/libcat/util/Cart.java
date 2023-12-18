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
            pendingTransactions.add(new Transaction( customer, book));
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
    public boolean deletePurchase(Book book){
        Order orderToRemove=null;
        for (Order order : pendingOrders){
            if ((order.getBook().equals(book))){
                orderToRemove=order;
                break;
            }
        }
        if (orderToRemove != null){
            pendingOrders.remove(orderToRemove);
            return true;
        }
        else {
            return false;
        }

        }
    public boolean deleteBorrow(Book book){
        Transaction transactionToRemove = null;
        for(Transaction transaction : pendingTransactions){
            if (transaction.getBook().equals(book)){
                transactionToRemove=transaction;
                break;
            }
        }
        if (transactionToRemove !=null) {
            pendingTransactions.remove(transactionToRemove);
            return true;
        }
        else{
            return false;
        }
    }
    }

