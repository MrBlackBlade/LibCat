package libcat.util;

import libcat.Library;

public class Customer extends User {

    private Cart cart;

    public Customer(int id, String name) {
        super(id, name);
        cart = new Cart(this);
    }



    @Override
    public String getType() {
        return "customer";
    }
    public String toString() {
        return String.format("Customer ID: %d, Customer Username: %s", getID(), getName());
    }
    public Cart getCart() {return cart;}
}
