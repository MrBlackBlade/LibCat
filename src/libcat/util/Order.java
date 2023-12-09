package libcat.util;

import java.time.LocalDate;

public class Order {
    private static int counter = 0;
    private int orderID;
    private static Book Cbook;
    private double TotalPrice;
    private static int quantity;

    // private boolean for purchase or borrow
    public Order(int orderID, Book Cbook, double TotalPrice, int quantity) {
        this.orderID = orderID;
        this.Cbook = Cbook;
        this.TotalPrice = TotalPrice;
        this.quantity = quantity;
        counter++;

    }

    /*  public void setOrderID(int id){
          this.orderID = id;
      }

      public void setCbook (Book bok){
          this.Cbook = bok;
      }

      public void setTotalPrice (double price){
          this.TotalPrice = price;
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
          return this.TotalPrice;
      }

      public int getQuantity(){
          return this.quantity;
      }
  */
    public static void main(String[] args) {
        bookdata();
        //if condition should be made for the final price to declare whether the following
        //transaction is borrowing or purshacing
        //if (boolean == 1 )--> purshace final price function if not final price borrowing
        double y = finalpriceP(Cbook.getBookPrice(), Cbook.getSalePercent());
        System.out.println("price after discount: " + y);
        System.out.println("total price of all quantities " + finalpricequantity(y, quantity));


    }

    public static void bookdata() {
        System.out.println("ID: " + Cbook.getBookID());
        System.out.println("Name" + Cbook.getBookTitle());
        System.out.println("Price" + Cbook.getBookPrice());
        if (Cbook.getSalePercent() > 0)
            System.out.println("Discount" + Cbook.getSalePercent() + "%");
    }

    public static double finalpriceP(double price, double sale) {
        double x = 0;
        x = price * sale;
        return x;
    }

    /*  public double finalpriceb(double price, boolean variable from the borrowing class stating the book returned late or not )
      {
          double x = 0;
          if condition for boolean value of return late
          x = price + (price * percentage of fine);
          return x;
      }*/
    public static double finalpricequantity(double price, int quantity) {
        return price * quantity;
    }
}

