import menu.MainMenu;

import java.util.Scanner;

public class HotelApplication {
    public static void main(String[] args) {
        boolean keepRunning = true;
        Scanner scanner = new Scanner(System.in);
        while(keepRunning) {
            try {
                MainMenu.mainMenu();
                Integer option = Integer.parseInt(scanner.nextLine());
                keepRunning = MainMenu.executeMainMenuOption(scanner, option);
            } catch(NumberFormatException e) {
                System.out.println("Please enter a number between 1 and 5");
            }
        }
    }
}
