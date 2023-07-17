package menu;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    public static void mainMenu() {
        List<String> menuList = new ArrayList<>();
        menuList.add("\n=================================");
        menuList.add("Welcome to Hotel Reservation App");
        menuList.add("=================================");
        menuList.add("1. Find and reserve a room");
        menuList.add("2. See my reservation");
        menuList.add("3. Create an account");
        menuList.add("4. Admin");
        menuList.add("5. Exit");
        menuList.add("----------------------------------");
        menuList.add("Please select an option 👆");
        for(String str:menuList)
            System.out.println(str);
    }
    public static boolean executeMainMenuOption(Scanner scanner, Integer option) {
        boolean showMenu=true;
        switch(option) {
            case 1:
                findAndReserveRoom(scanner);
                break;
            case 2:
                getCustomerReservations(scanner);
                break;
            case 3:
                createAccount(scanner);
                break;
            case 4:
                showAdminPanel(scanner);
                break;
            case 5:
                showMenu=false;
                break;
            default:
                System.out.println("Please choose a number between 1 to 5");
        }
        return showMenu;
    }

    public static void findAndReserveRoom(Scanner scanner) {
        Date checkInDate = getvalidCheckInDate(scanner);
        Date checkOutDate = getValidCheckOutDate(scanner, checkInDate);

        Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);
        boolean wantToBook=false;
        if(availableRooms.isEmpty()) {
            int recommend=1;
            while(recommend++<8) {
                Date newCheckInDate = getRecommendedDate(checkInDate, recommend);
                Date newCheckOutDate = getRecommendedDate(checkOutDate, recommend);
                availableRooms = HotelResource.findARoom(newCheckInDate, newCheckOutDate);
                if(!availableRooms.isEmpty()) {
                    System.out.println("No room available for those days. Rooms available for alternate days.");
                    checkInDate = newCheckInDate;
                    checkOutDate = newCheckOutDate;
                    break;
                }
            }
            if(availableRooms.isEmpty()) {
                System.out.println("No room available for those days. Please look for another days.");
            }
        }
        if(!availableRooms.isEmpty()) {
            System.out.println("Rooms available. Check-in on "+checkInDate+" and Check-out on "+checkOutDate);
            wantToBook = showRoomAndBook(scanner, availableRooms);
        }
        if(!wantToBook)
            return;
        String email = getCustomerAccountEmail(scanner);
        try {
            Customer customer = HotelResource.getCustomer(email);
        } catch(IllegalArgumentException e) {
            System.out.println("There is no account with this email.");
            return;
        }
        IRoom room = selectRoomForReservation(scanner, availableRooms);
        Reservation reservation = HotelResource.bookARoom(email, room, checkInDate, checkOutDate);
        System.out.println("Thank You! Your booking is successful. Please see your reservation details below.");
        System.out.println(reservation);
    }
    private static IRoom selectRoomForReservation(Scanner scanner, Collection<IRoom> availableRooms) {
        IRoom room = null;
        boolean validNumber = false;
        String roomNumber = null;
        System.out.println("Please the the room number you want to book: ");
        while(!validNumber) {
            roomNumber = scanner.nextLine();
            room = HotelResource.getRoom(roomNumber);
            if(room==null||!availableRooms.contains(room)) {
                System.out.println("Please enter room number from available options:");
            }
            else {
                validNumber=true;
            }
        }
        return room;
    }
    private static String getCustomerAccountEmail(Scanner scanner) {
        boolean hasAccount=false;
        String email = null;
        System.out.println("Do you already have an account with us? Y(yes)/N(no)");
        String choice = scanner.nextLine();
        if(choice.equalsIgnoreCase("y")||choice.equalsIgnoreCase("yes")) {
            hasAccount=true;
        } else if(!(choice.equalsIgnoreCase("n")||choice.equalsIgnoreCase("no"))) {
            hasAccount = takeChoice(scanner);
        }
        if(hasAccount) {
            System.out.println("Enter your registered email address: ");
            email = scanner.nextLine();
        }
        else {
            email = createAccount(scanner);
        }
        return email;
    }
    public static boolean takeChoice(Scanner scanner) {
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
    private static boolean showRoomAndBook(Scanner scanner, Collection<IRoom> availableRooms) {
        boolean wantToBook=false;
        for(IRoom room:availableRooms) {
            System.out.println(room);
        }
        System.out.println("Would you like to book a room? Y(yes)/N(no) :");
        String choice = scanner.nextLine();
        if(choice.equalsIgnoreCase("y")||choice.equalsIgnoreCase("yes")) {
            wantToBook=true;
        } else if (choice.equalsIgnoreCase("n")||choice.equalsIgnoreCase("no")) {
            wantToBook=false;
        } else {
            wantToBook = takeChoice(scanner);
        }
        return wantToBook;
    }
    private static Date getRecommendedDate(Date currDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        calendar.add(calendar.DATE, days);
        return calendar.getTime();
    }
    private static Date getValidCheckOutDate(Scanner scanner, Date checkInDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date checkOutDate = null;
        boolean validDate = false;
        while(!validDate) {
            System.out.println("Check-out Date(dd/mm/yyyy):");
            String dateInput = scanner.nextLine();
            try {
                checkOutDate = dateFormat.parse(dateInput);
                if(checkOutDate.before(checkInDate)) {
                    System.out.println("Check-out date can't be before Check-in date.");
                }
                else {
                    validDate=true;
                }
            } catch(ParseException e) {
                System.out.println("Invalid date format. Please give in dd/mm/yyyy");
            }
        }
        return checkOutDate;
    }
    private static Date getvalidCheckInDate(Scanner scanner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date checkInDate = null;
        boolean validDate = false;
        while(!validDate) {
            System.out.println("Check-in Date (dd/mm/yyyy):");
            String dateInput = scanner.nextLine();
            try {
                checkInDate = dateFormat.parse(dateInput);
                Date today = new Date();
                if(checkInDate.before(today)) {
                    System.out.println("Check-in date can't be in past");
                }
                else {
                    validDate = true;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please give in dd/mm/yyyy");
            }
        }
        return checkInDate;
    }
    public static void getCustomerReservations(Scanner scanner) {
        System.out.println("Enter your registered email address (name@example.com)");
        Customer customer=null;
        String email = null;
        while(customer==null) {
            email = scanner.nextLine();
            if(email.length()==1 && (email.charAt(0)-'0')==0)
                return;
            try {
                customer = CustomerService.getCustomer(email);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no account with this email");
                System.out.println("Please enter a registered email address or enter 0 for Main Menu");
            }
        }
        Collection<Reservation> customerReservations = HotelResource.getCustomerReservation(email);
        if(customerReservations.isEmpty()) {
            System.out.println("Sorry! You don't have any reservation at this moment.");
            return;
        }
        for(Reservation reservation:customerReservations)
            System.out.println(reservation.toString());
    }
    public static String createAccount(Scanner scanner) {
        System.out.println("Please enter your details 👇");
        System.out.println("First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String lastName = scanner.nextLine();
        String email = null;
        boolean isValid=false;
        while(!isValid) {
            try {
                System.out.println("Email(name@example.com): ");
                email = scanner.nextLine();
                HotelResource.createACustomer(email, firstName, lastName);
                System.out.println("Account created successfully!!");
                isValid=true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        return email;
    }
    public static void showAdminPanel(Scanner scanner) {
        boolean keepRunning = true;
        while(keepRunning) {
            try {
                AdminMenu.adminMenu();
                Integer option = Integer.parseInt(scanner.nextLine());
                keepRunning = AdminMenu.executeAdminMenuOption(scanner, option);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number between 1 to 5");
            }
        }
    }
}
