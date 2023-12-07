package libcat.Book;

public class Order {

   private int orderID;
   private Book Cbook;
   private double orderPrice;
   private int quantity;


    public void setOrderID(int id){
        this.orderID = id;
    }

    public void setCbook (Book bok){
        this.Cbook = bok;
    }

    public void setOrderPrice (double price){
        this.orderPrice = price;
    }

    public void setQuantity (int quan){
        this.quantity = quan;
    }

    public int getOrderID(){
        return this.orderID;
    }

    public Book getCbook(){
        return this.Cbook;
    }

    public double getOrderPrice(){
        return this.orderPrice;
    }

    public int getQuantity(){
        return this.quantity;
    }
}
