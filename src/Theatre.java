import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Theatre {

    public static void main(String[] args) {

        System.out.println("Welcome to the New Theatre");


        //Scanner to take user input
        Scanner scan = new Scanner(System.in);

        //Multidimensional array is used to handle row and seat data
        int[][] seatPlan = new int[3][];
        seatPlan[0] = new int[12];
        seatPlan[1] = new int[16];
        seatPlan[2] = new int[20];

        //ArrayList to save tickets
        ArrayList<Ticket> ticketDetails = new ArrayList<>();

        //Array to store menu numbers
        int[] menuNumbers = {1, 2, 3, 4, 5, 6, 7, 8,};

        //Array to store menu options
        String[] menuOptions = {" Buy a ticket", " Print seating area", " Cancel ticket", " List available seats", " Save to file", " Load from file", " Print ticket information and total price", " Sort tickets by price"};

        //getMenu method will display the menu and get a user input.
        int choice = getMenu(menuNumbers, menuOptions, scan);

        while (choice != 0) {

            //choice value is checked by using enhanced switch case statement.
            switch (choice) {

                case 1: {
                    int rowNum = getRowNumber(scan);
                    int seatNum = getSeatNumber(seatPlan, rowNum, scan);

                    buy_tickets(rowNum, seatNum, seatPlan, scan, ticketDetails);
                    break;
                }

                case 2: {
                    //calculate padding of the stage
                    int paddingStage = seatPlan[2].length - seatPlan[0].length;
                    String spaces = " ";

                    //This for-loop is used to print only the stage (excluding seating plan).
                    for (int stageLine = 1; stageLine < 4; stageLine++) {

                        // get stage area to center of the display.
                        System.out.print(spaces.repeat(paddingStage));

                        //print stars in the 1st and 3rd lines
                        if (stageLine != 2) {
                            int firstRowLength = seatPlan[0].length;
                            String star = "*";
                            //printing stars relative to width of the first row
                            System.out.print(star.repeat(firstRowLength));
                            System.out.println();
                        } else {
                            //print the 2nd line
                            System.out.println("*   STAGE  *");
                        }
                    }

                    //print seat status
                    print_seating_area(seatPlan);
                    break;
                }

                case 3: {
                    System.out.println("Please Enter the details of the booking that needs to be canceled");

                    int row_no = getRowNumber(scan);
                    int seat_no = getSeatNumber(seatPlan, row_no, scan);

                    cancel_ticket(row_no, seat_no, seatPlan, ticketDetails);
                    break;
                }

                case 4: {
                    // this will give available seats at the moment
                    show_available(seatPlan);
                    break;
                }

                case 5: {
                    //method will save current details to a file
                    save(seatPlan, ticketDetails);
                    break;
                }

                case 6: {
                    //method will load data from the saved file
                    load(seatPlan, ticketDetails);
                    break;
                }

                case 7: {
                    show_ticket_info(ticketDetails);
                    break;
                }

                case 8: {
                    sort_ticket_info(ticketDetails);
                    break;
                }


                default: {
                    System.out.println("Enter a valid option from the menu");
                    break;
                }
            }
            System.out.println();

            //get choice from the user
            choice = getMenu(menuNumbers, menuOptions, scan);

        }

        System.out.println("Thank you for using our service !");
        scan.close();
    }


    
    public static void buy_tickets(int rowNum, int seatNum, int[][] seatAllocation, Scanner scan, ArrayList<Ticket> ticketDetails) {

        //initialize of price variable
        double price = 0;
        boolean match;

        //going to new line
        if (seatAllocation[rowNum - 1][seatNum - 1] == 0) {
            //get user details in order to book seat
            System.out.print("Please Enter your first name : ");
            String name = scan.next();
            System.out.print("Please enter your last name : ");
            String lastName = scan.next();
            System.out.print("please enter you email : ");
            String email = scan.next();

            do {
                match = false;
                try {
                    System.out.print("Enter the price of the ticket : ");
                    price = scan.nextInt();
                    System.out.println();

                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input!");
                    scan.next(); //consuming the previous input
                    match = true;
                }
            } while (match);

            seatAllocation[rowNum - 1][seatNum - 1] = 1;
            System.out.printf("you have booked seat no: %d  in the row %d successfully ! \n", seatNum,rowNum);
            System.out.println();

            Person person = new Person(name, lastName, email);

            Ticket ticket = new Ticket(rowNum, seatNum, price, person);

            ticketDetails.add(ticket);//adding to array list in the main method

            ticket.print();//print the details of the relevant ticket


        } else {

            System.out.println("Seat was occupied");

        }
        System.out.println();//going to new line
    }


    //method implement to print seating area
    public static void print_seating_area(int[][] seatingPlan) {

        int paddingStage = seatingPlan[2].length - seatingPlan[0].length;
        String spaces = " ";


        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {

            //following code lines will add padding and centralize each row with respective to stage

            if (rowIndex == 0) {
                System.out.print(spaces.repeat(paddingStage));
            } else if (rowIndex == 1) {
                System.out.print(spaces.repeat((paddingStage) - 2));
            } else {
                System.out.print(spaces.repeat((paddingStage) - 4));
            }

            //printing of seats

            for (int seatIndex = 0; seatIndex < seatingPlan[rowIndex].length; seatIndex++) {

                if (seatingPlan[rowIndex][seatIndex] == 0) {

                    System.out.print("O");

                } else {

                    System.out.print("X");
                }

                if (seatIndex == (seatingPlan[rowIndex].length) / 2) {
                    System.out.print(" ");
                }

            }

            System.out.println();//new line for next row
        }
    }


    //method implement to cancel tickets
    public static void cancel_ticket(int rowNum, int seatNum, int[][] array, ArrayList<Ticket> ticketDetails) {

        if (array[rowNum - 1][seatNum - 1] == 1) {

            //setting the index  position of the given seat to 0 resulting in canceling of the seat
            array[rowNum - 1][seatNum - 1] = 0;

            System.out.printf("you have canceled the booking of seat no : %d\n", seatNum);
            System.out.println();

            //removing the ticket from the ticketDetails array lis
            for (Ticket ticket : ticketDetails) {

                if ((ticket.getRow() == rowNum) && (ticket.getSeat() == seatNum)) {

                    ticketDetails.remove(ticket);
                    ticket.print();//details of the ticket that has been removed
                    System.out.println();
                    break;
                }
            }
            System.out.println("Above Ticket has been successfully removed");

            System.out.println();
        } else System.out.println("Seat was not booked yet");
        System.out.println();
    }


    public static void show_available(int[][] array) {

        //nested for-loop to loop through each row and seat to check weather they are available

        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {

            System.out.printf("Seats available in row %d :", rowIndex + 1);

            for (int seatIndex = 0; seatIndex < array[rowIndex].length; seatIndex++) {

                if (array[rowIndex][seatIndex] == 0) {

                    System.out.printf("%d,", seatIndex + 1);
                }

            }

            System.out.println(".");
        }
        System.out.println();//printing a new line for next row available seats

    }


    //method to save existing data  to text file
    public static void save(int[][] seatingAllocation, ArrayList<Ticket> ticketDetails) {

        //save seat information to a file
        try {
            FileWriter infoWrite = new FileWriter("information.txt");

            for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
                for (int seat_index = 0; seat_index < seatingAllocation[rowIndex].length; seat_index++) {

                    infoWrite.write(seatingAllocation[rowIndex][seat_index] + " ");
                }

                infoWrite.write("\n");
            }

            infoWrite.close();
            System.out.println("file saved successfully");
        } catch (IOException e) {

            System.out.println("Error occurred while saving the file");
        }

        //save ticket information to a file using a helper method
        TicketManager.saveTicketObj(ticketDetails);

    }


    public static void load(int[][] seatingAllocation, ArrayList<Ticket> ticketDetails) {

        try {
            FileReader read = new FileReader("information.txt");
            Scanner scanner = new Scanner(read);

            for (int rowIndex = 0; rowIndex < seatingAllocation.length; rowIndex++) {
                String[] values = scanner.nextLine().split(" ");//split the string and adding to values array
                for (int seatIndex = 0; seatIndex < values.length; seatIndex++) {

                    seatingAllocation[rowIndex][seatIndex] = parseInt(values[seatIndex]);
                }
            }
            scanner.close();

        } catch (IOException e) {
            System.out.println("Error occurred while  loading/locating the file");
        }

        TicketManager.loadTicketObj(ticketDetails);
        System.out.println("Saved data loaded to the program successfully");

    }


    //Following method will help to get a valid row number.
    public static int getRowNumber(Scanner scan) {

        int rowNumber;

        do {
            System.out.print("Enter row number(0-3) : ");

            try {
                rowNumber = scan.nextInt();
            } catch (InputMismatchException e) {

                System.out.println("Invalid Input ! Please enter an integer.");

                scan.nextLine(); //past the current input buffer

                rowNumber = -1; //setting row number to minus value to go to next iteration when an exception is caught
            }

        } while (rowNumber < 0 || rowNumber > 3);

        return rowNumber;
    }


    public static int getSeatNumber(int[][] array, int row_no, Scanner scan) {

        int seat_no = 21; // default value of seat_no is set to 21 because in case of an exception,while condition always will be true.

        int rowLength = array[row_no - 1].length;

        do {
            System.out.printf("This row has %d seats\n", (array[row_no - 1].length));

            System.out.print("Enter seat number : ");
            try {
                seat_no = scan.nextInt();
            } catch (InputMismatchException e) {

                System.out.println("Invalid Input! Please enter an integer");

                scan.next();//consume the line
            }

        } while (rowLength < seat_no || seat_no < 0);

        return seat_no;
    }


    public static int getMenu(int[] menuOptions, String[] menuNumbers, Scanner scan) {
        //method implement to menu display and get choice

        int choice = 0;

        boolean match;/*This boolean value will set to false if an exception occurred, and condition of do while will set to
         true resulting another iteration in the loop*/


        do {
            System.out.println("__________________________________________________");
            match = true;

            System.out.println("Please select an option from below :");

            //following for loop will loop through menuOptions and menuNumbers arrays and display them respectively.
            for (int menuIndex = 0; menuIndex < menuOptions.length; menuIndex++) {
                System.out.printf("%d). %s\n", menuOptions[menuIndex], menuNumbers[menuIndex]);
            }
            System.out.println("0).  Quit");
            System.out.println("__________________________________________________");


            try {
                System.out.print("Enter option : ");
                choice = scan.nextInt();

            } catch (InputMismatchException ex) {

                System.out.println("Invalid Input");
                scan.next();//consume the line

                match = false;
            }
        } while (!match);

        return choice;
    }


    public static void show_ticket_info(ArrayList<Ticket> ticketDetails) {
        //this method will show details of all tickets
        double total = 0;
        for (Ticket ticket : ticketDetails) {

            ticket.print();//printing details of an individual ticket
            System.out.println();

            double price = ticket.getPrice();
            total = total + price;
        }
        System.out.println("Total : " + total);
    }


    public static void sort_ticket_info(ArrayList<Ticket> ticketDetails) {
        boolean swapped;

        do {
            swapped = false;

            for (int j = 0; j < ticketDetails.size() - 1; j++) {
                if (ticketDetails.get(j).getPrice() > ticketDetails.get(j + 1).getPrice()) {
                    // swapping the ticket objects based on their price
                    Ticket temp = ticketDetails.get(j);
                    ticketDetails.set(j, ticketDetails.get(j + 1));
                    ticketDetails.set(j + 1, temp);

                    swapped = true;
                }
            }

        } while (swapped);

        // printing sorted tickets
        for (Ticket ticket : ticketDetails) {
            ticket.print();
            System.out.println();
        }
    }



}




