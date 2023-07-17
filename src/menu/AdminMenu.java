package menu;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    public static void adminMenu() {
        List<String> menuList = new ArrayList<>();
        menuList.add("\n=================================");
        menuList.add("Admin Panel");
        menuList.add("==================================");
        menuList.add("1. See all customers");
        menuList.add("2. See all rooms");
        menuList.add("3. See all reservation");
        menuList.add("4. Add rooms");
        menuList.add("5. Back to Main Menu");
        menuList.add("----------------------------------");
        menuList.add("Please select an option 👆");
        for(String str:menuList)
            System.out.println(str);
    }
    public static boolean executeAdminMenuOption(Scanner scanner, int option) {
        boolean showMenu = true;
        switch (option) {
            case 1:
                seeAllCustomers();
                break;
            case 2:
                seeAllRooms();
                break;
            case 3:
                seeAllReservations();
                break;
            case 4:
                addRooms(scanner);
                break;
            case 5:
                showMenu=false;
                break;
            default:
                System.out.println("Please enter a number between 1 to 5");
        }
        return showMenu;
    }
    public static void seeAllCustomers() {
        Collection<Customer> customers = AdminResource.getAllCustomer();
        if(customers.isEmpty()) {
            System.out.println("There is no customer.");
            return;
        }
        for(Customer customer:customers) {
            System.out.println(customer);
        }
    }
    public static void seeAllRooms() {
        Collection<IRoom> rooms = AdminResource.getAllRooms();
        if(rooms.isEmpty()) {
            System.out.println("Till now no room is added.");
            return;
        }
        for(IRoom room:rooms) {
            System.out.println(room);
        }
    }
    public static void seeAllReservations() {
        AdminResource.displayAllReservation();
    }
    public static void addRooms(Scanner scanner) {
        List<IRoom> rooms = new ArrayList<>();
        boolean continueAdding=true;
        do {
            IRoom room = createRoom(scanner);
            rooms.add(room);
            System.out.println("Do you want to add another room?");
            continueAdding = takeChoice(scanner);
        } while(continueAdding);
        AdminResource.addRoom(rooms);
    }
    private static IRoom createRoom(Scanner scanner) {
        String roomNumber = getRoomNumber(scanner);
        double roomPrice = getRoomPrice(scanner);
        RoomType roomType = getRoomType(scanner);
        Room room = new Room(roomNumber, roomPrice, roomType);
        return room;
    }
    private static RoomType getRoomType(Scanner scanner) {
        RoomType roomType = null;
        Integer roomTypeValue=0;
        boolean validRoomType = false;
        while(!validRoomType) {
            System.out.println("Enter room type: 1 (for Single bed) or 2 (for Double bed):");
            try {
                roomTypeValue = Integer.parseInt(scanner.nextLine());
                if(roomTypeValue>0 && roomTypeValue<3) {
                    validRoomType=true;
                }
                else {
                    System.out.println("Please enter either 1 or 2");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid room type");
            }
        }
        switch(roomTypeValue) {
            case 1:
                roomType = RoomType.SINGLE;
                break;
            case 2:
                roomType = RoomType.DOUBLE;
                break;
        }
        return roomType;
    }
    private static double getRoomPrice(Scanner scanner) {
        double roomPrice = 0.0;
        boolean validPrice=false;
        while(!validPrice) {
            System.out.println("Enter room price/night: ");
            try {
                roomPrice = Double.parseDouble(scanner.nextLine());
                if(roomPrice<0) {
                    System.out.println("Price must be positive");
                } else {
                    validPrice = true;
                }
            } catch(NumberFormatException e) {
                System.out.println("Please enter valid price in number");
            }
        }
        return roomPrice;
    }
    private static String getRoomNumber(Scanner scanner) {
        String roomNumber = null;
        boolean validNumber = false;
        boolean update = true;
        while(!validNumber) {
            System.out.println("Enter room number(like 123): ");
            roomNumber = scanner.nextLine();
            try {
                Integer.parseInt(roomNumber);
                IRoom room = HotelResource.getRoom(roomNumber);
                if(room!=null) {
                    System.out.println("That room already exists. Do you want to update the room? Y(yes)/N(no");
                    update = takeChoice(scanner);
                }
                if(room==null || update) {
                    validNumber = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid room number(like 123");
            }
        }
        return roomNumber;
    }
    private static boolean takeChoice(Scanner scanner) {
        boolean validInput=false;
        String choice = null;
        boolean choiceValue = false;
        while(!validInput) {
            System.out.println("Please enter Y(yes) or N(no) :");
            choice = scanner.nextLine();
            if(choice.equalsIgnoreCase("y")||choice.equalsIgnoreCase("yes")) {
                choiceValue=true;
                validInput=true;
            } else if(choice.equalsIgnoreCase("n")||choice.equalsIgnoreCase("no")) {
                choiceValue=false;
                validInput=true;
            }
        }
        return choiceValue;
    }
}
