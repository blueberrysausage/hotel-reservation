import api.*;

import model.Room;
import model.RoomType;

import java.util.Collections;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = new AdminResource();
        public static void printAdminMenu() {
        System.out.println("""
                Admin Menu
                ---------------------------------
                1. See all customers
                2. See all rooms
                3. See all reservations
                4. Add a room
                5. Back to main menu
                ---------------------------------
                Please select a number for the menu option""");
    }

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        printAdminMenu();
        String userInput = scanner.nextLine();
        switch (userInput) {
            case "1" -> seeAllCustomers();
            case "2" -> seeAllRooms();
            case "3" -> seeAllReservations();
            case "4" -> addARoom();
            case "5" -> MainMenu.mainMenu();
            default -> {
                System.out.println("Invalid input!");
                adminMenu();
            }
        }
    }

    public static void seeAllCustomers() {
        if (adminResource.getAllCustomers().isEmpty()) {
            System.out.println("No customers found.");
            adminMenu();
        }
        else {
            System.out.println(adminResource.getAllCustomers());
            adminMenu();
        }
    }

    public static void seeAllRooms() {
        if (adminResource.getAllRooms().isEmpty()) {
            System.out.println("No rooms found.");
            adminMenu();
        }
        else {
            System.out.println(adminResource.getAllRooms());
            adminMenu();
        }
    }

    public static void seeAllReservations() {
        adminResource.displayAllReservations();
        adminMenu();
    }

    public static void addARoom() {
        RoomType enumeration = null;
        Room room = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();

        System.out.println("Enter price per night:");
        Double d = getRoomPrice(scanner);

        System.out.println("Enter room type: 1 for single bed, 2 for double bed");
        String roomType = scanner.nextLine();
        while (!(roomType.equals("1") || roomType.equals("2"))) {
            System.out.println("Invalid input!\nEnter room type: 1 for single bed, 2 for double bed");
            roomType = scanner.nextLine();
        }
        if (roomType.equals("1")) {
            enumeration = RoomType.SINGLE;
        }
        else if (roomType.equals("2")) {
            enumeration = RoomType.DOUBLE;
        }
        if (d == 0.0) {
            room = new Room(roomNumber, d, enumeration, true);
        }
        else {
            room = new Room(roomNumber, d, enumeration, false);
        }
        adminResource.addRoom(Collections.singletonList(room));
        adminMenu();
    }

    public static Double getRoomPrice(Scanner scanner) {
        try {
            return Double.valueOf(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid price.");
            return getRoomPrice(scanner);
        }
    }
}
