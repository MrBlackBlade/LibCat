package libcat.util;
import java.time.LocalDate;

public class Transaction {

    Book Cbook;
    Borrower borrower;
    LocalDate Tdate;
    LocalDate Rdate;

    public static void main (String[] args){
        LocalDate Tdate = LocalDate.now();
        LocalDate Rdate = Tdate.plusDays(14);
        Book Cbook = null;
        Borrower borrower = null;
        bookReturned (Rdate, Cbook, borrower);
    }

    public static void bookReturned(LocalDate Rdate, Book Cbook, Borrower borrower){
        boolean returned = false ;
        //if (Cbook.getStatus())
        // returned or available ^
        //so i added a new variable in book class
       if(Cbook.isReturned()) {
           returned = true;
        }
       else applyfine(Rdate, Cbook, borrower);
        /*
        borrower w book
        # borrower must have ids of borrowed books
        then check id fi book objs and check its status
        to change the status in the borrower class and book class -> if there's only one book
        */
        }


    public static void applyfine(LocalDate Rdate, Book Cbook, Borrower borrower){
        /*check local date with rdate -> is it passed or not
        passed => fine
        there should be value to return the fine to in the borrower class
        original price for the book;
        return the fine to the borrower class in :
        fine variable then added to the price
        */
        int fine = 0;
        double Fprice = Cbook.getBookPrice();
        LocalDate Fdate = LocalDate.now();

        if(Fdate.isAfter(Rdate)){
            Fprice += Cbook.getBookPrice()*(15/100);
            //borrower.setfine(fine);
            //NEEDED IN BORROWER CLASS
        }

    }

}
