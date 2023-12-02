import java.io.*;
import java.util.ArrayList;

public class TicketManager {
    //  TicketManager class is implemented to manage arraylist data.
    //   As load method only load data  to arrays(not to array list)
    //   menu number 7 and 8 will have bugs as they depend on array list data.
    //   This class is here to load and save arraylist data and make the program work as it should be when loading data.

    public static void saveTicketObj(ArrayList<Ticket> ticketDetails) {
        //create file to save serialized objects
        File fileTickets = new File("data.txt");
        try {
            //check whether the file exists, if not create the new file
            if (!fileTickets.exists()) {
                boolean createStatus = fileTickets.createNewFile();
            }

            //create a FileOutputStream to write data to the file
            FileOutputStream fileOut = new FileOutputStream(fileTickets, false);

            //create ObjectOutputStream to send serialized objects to the FileOutputStream
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            //save ticketDetails
            for (Ticket ticket : ticketDetails) {
                objOut.writeObject(ticket);
            }

            objOut.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void loadTicketObj(ArrayList<Ticket> ticketDetails) {
        try {
            //crate FileInputStream to read data from the saved file
            FileInputStream fileIn = new FileInputStream("data.txt");

            //create ObjectInputStream to read objects from FileInputStream
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            //clear the ticketDetails before loading new data
            ticketDetails.clear();

            //read ticket objects and add to ArrayList
            while (true) {
                try {
                    Ticket ticket = (Ticket) objIn.readObject();
                    ticketDetails.add(ticket);
                } catch (ClassNotFoundException | IOException ex) {
                    break;
                }
            }
            objIn.close();
            fileIn.close();

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
