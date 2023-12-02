import java.io.Serializable;

//implement the Serializable interface to make the Ticket object serializable
public class Ticket implements Serializable {
    private static final long serialVersionUID=1L;
    private final int row;
    private final int seat;
    private final double price;
    private final Person person;



    public Ticket(int row, int seat, double price, Person person){
        this.row=row;
        this.seat=seat;
        this.price=price;
        this.person=person;
    }



    //getters  of the ticket class

    public int getRow(){
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }



    //method to print the ticket with all information
    public  void print(){
        System.out.println("first name :"+person.getName());
        System.out.println("surname : "+person.getSurname());
        System.out.println("Email : "+person.getEmail());
        System.out.println("Row number : "+getRow());
        System.out.println("Seat number: "+getSeat());
        System.out.println("Price: "+getPrice());

    }








}
