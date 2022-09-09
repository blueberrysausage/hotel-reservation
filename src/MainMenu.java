import api.*;
import model.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    private static final HotelResource hotelResource = new HotelResource();
    static String emailAns = null;
    static Date checkInDate = null;
    static Date checkOutDate = null;
    public static void printMainMenu() {
        System.out.println("""
                Welcome to the Hotel Reservation Application
                ---------------------------------
                1. Find and reserve a room
                2. See my reservations
                3. Create an account
                4. Admin
                5. Exit
                ---------------------------------
                Please select a number for the menu option""");
    }

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        printMainMenu();
        String userInput = scanner.nextLine();
        switch (userInput) {
            case "1" -> findAndReserveARoom();
            case "2" -> seeMyReservations();
            case "3" -> CreateAnAccount();
            case "4" -> AdminMenu.adminMenu();
            case "5" -> System.out.println("Exit");
            default -> {
                System.out.println("Invalid input!");
                mainMenu();
                }
        }
    }

    public static void findAndReserveARoom() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            System.out.println("Enter check in date: (format mm/dd/yyyy)");
            checkInDate = formatter.parse(scanner.nextLine());
            System.out.println("Enter check out date: (format mm/dd/yyyy)");
            checkOutDate = formatter.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Invalid input!");
            findAndReserveARoom();
        }
        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        //System.out.println("availableRooms: mainmenu2" + availableRooms);
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available at this date.");
            availableRooms = hotelResource.findAltRoom(checkInDate, checkOutDate);
            if (availableRooms.isEmpty()) {
                System.out.println("No rooms available at alternative date.");
                mainMenu();
            }
            else {
                System.out.println("Found room available at 1 week later.");
                for (IRoom rooms : availableRooms) {
                    System.out.println(rooms);
                }
                bookAlt();
            }
        }
        for (IRoom rooms : availableRooms) {
            System.out.println(rooms);
        }
        System.out.println("Would you like to book a room? y/n");
        String ans = scanner.nextLine();
        if (ans.equals("y") || ans.equals("Y")) {
            System.out.println("Do you have an account with us? y/n");
            String accountAns = scanner.nextLine();
            if (accountAns.equals("y") || accountAns.equals("Y")) {
                System.out.println("Enter email: format name@domain.com");
                String emailAns = scanner.nextLine();
                if (hotelResource.getCustomer(emailAns) == null) {
                    System.out.println("No account found. Please create an account.");
                    mainMenu();
                }
                System.out.println("What room number would you like to reserve?");
                String roomAns = scanner.nextLine();
                hotelResource.bookARoom(emailAns, hotelResource.getRoom(roomAns), checkInDate, checkOutDate);
                System.out.println("Reservation created.");
                mainMenu();
            }
            else if (accountAns.equals("n") || accountAns.equals("N")) {
                NoAcctReserveRoom();
            }
            else {
                System.out.println("Invalid input!");
                mainMenu();
            }
        }
        else if (ans.equals("n") || ans.equals("N")) {
            mainMenu();
        }
        else {
            System.out.println("Invalid input!");
            mainMenu();
        }
    }

    public static void bookAlt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to book a room for 1 week later? y/n");
        String ans = scanner.nextLine();
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (ans.equals("y") || ans.equals("Y")) {
            System.out.println("Do you have an account with us? y/n");
            String accountAns = scanner.nextLine();
            if (accountAns.equals("y") || accountAns.equals("Y")) {
                System.out.println("Enter email: format name@domain.com");
                String emailAns = scanner.nextLine();
                if (pattern.matcher(emailAns).matches()) {
                    if (hotelResource.getCustomer(emailAns) == null) {
                        NoAcctReserveRoomAlt();
                    }
                    System.out.println("What room number would you like to reserve?");
                    String roomAns = scanner.nextLine();
                    //System.out.println(hotelResource.getRoom(roomAns));
                    hotelResource.bookAltRoom(emailAns, hotelResource.getRoom(roomAns), checkInDate, checkOutDate);
                    //System.out.println("2");
                    System.out.println("Reservation created.");
                    mainMenu();
                }
                else {
                    System.out.println("Invalid email.");
                    bookAlt();
                }
            }
            else if (accountAns.equals("n") || accountAns.equals("N")) {
                NoAcctReserveRoomAlt();
            }
            else {
                System.out.println("Invalid input!");
                mainMenu();
            }
        }
        else if (ans.equals("n") || ans.equals("N")) {
            mainMenu();
        }
        else {
            System.out.println("Invalid input!");
            mainMenu();
        }
    }
    public static void seeMyReservations() {
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter email: (format name@domain.com)");
        String email = scanner.nextLine();
        if (pattern.matcher(email).matches()) {
            if (hotelResource.getCustomer(email) == null) {
                System.out.println("No account found. Please create an account.");
                mainMenu();
            }
            if (hotelResource.getCustomersReservations(email).isEmpty()) {
                System.out.println("No reservation found.");
                mainMenu();
            }
            else {
                System.out.println(hotelResource.getCustomersReservations(email));
                mainMenu();
            }
        }
        else {
            System.out.println("Invalid email.");
            seeMyReservations();
        }
    }

    public static void CreateAnAccount() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter email: (format name@domain.com)");
            emailAns = scanner.nextLine();
            System.out.println("First name:");
            String firstName = scanner.nextLine();
            System.out.println("Last name:");
            String lastName = scanner.nextLine();
            hotelResource.createACustomer(emailAns, firstName, lastName);
            System.out.println("Account created.");
            mainMenu();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            CreateAnAccount();
        }
    }

    public static void NoAcctReserveRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please create an account.");
        try {
            System.out.println("Enter email: (format name@domain.com)");
            emailAns = scanner.nextLine();
            System.out.println("First name:");
            String firstName = scanner.nextLine();
            System.out.println("Last name:");
            String lastName = scanner.nextLine();
            hotelResource.createACustomer(emailAns, firstName, lastName);
            System.out.println("Account created.");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            NoAcctReserveRoom();
        }
        System.out.println("What room number would you like to reserve?");
        String roomAns = scanner.nextLine();
        hotelResource.bookARoom(emailAns, hotelResource.getRoom(roomAns), checkInDate, checkOutDate);
        System.out.println("Reservation created.");
        mainMenu();
    }

    public static void NoAcctReserveRoomAlt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please create an account.");
        try {
            System.out.println("Enter email: (format name@domain.com)");
            emailAns = scanner.nextLine();
            System.out.println("First name:");
            String firstName = scanner.nextLine();
            System.out.println("Last name:");
            String lastName = scanner.nextLine();
            hotelResource.createACustomer(emailAns, firstName, lastName);
            System.out.println("Account created.");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            NoAcctReserveRoom();
        }
        System.out.println("What room number would you like to reserve?");
        String roomAns = scanner.nextLine();
        hotelResource.bookAltRoom(emailAns, hotelResource.getRoom(roomAns), checkInDate, checkOutDate);
        System.out.println("Reservation created.");
        mainMenu();
    }
}
