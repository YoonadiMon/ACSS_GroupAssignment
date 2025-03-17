package MainProgram;

import java.util.Arrays;
import java.util.Scanner;


public class Customer {
    static Scanner s = new Scanner(System.in); // class scope
    public static void main(String[] Args) {
        boolean running = true;
        while(running){
            int choice = -1;

            System.out.println("-----------------------------------------");
            System.out.println("           Customer's Features");
            System.out.println("-----------------------------------------");

            System.out.println("0. EXIT");
            System.out.println("1. Register an account");
            System.out.println("2. Edit your profile");
            System.out.println("3. View details of available cars");
            System.out.println("4. Give feedback regarding your purchases");
            System.out.println("5. View history");

            System.out.println("-----------------------------------------");
            while (!Arrays.asList( 1, 2, 3, 4, 5, 0).contains(choice)){
                System.out.print("Enter your choice (0-5): ");
                choice = s.nextInt();
            }

            switch (choice){
                case 0 -> running = false;
                case 1 -> registerAccount();
                case 2 -> editProfile();
                case 3 -> viewCars();
                case 4 -> giveFeedback();
                case 5 -> viewHistory();
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    static void registerAccount(){
        String name;
        String pwd;

        System.out.println("-----------------------------------------");
        System.out.println("Registering an account...");
        System.out.println("-----------------------------------------");
        s.nextLine(); // // Consume left over newline from main menu choice input
        System.out.print("Enter your name: ");
        name = s.nextLine();
        System.out.print("Enter your pwd: ");
        pwd = s.nextLine();

        System.out.printf("Account created: %s%s\n", name, pwd);
    }
    static void editProfile() {
        System.out.println("Edit your profile...");
    }
    static void viewCars() {
        System.out.println("View details of available cars...");
    }
    static void giveFeedback() {
        System.out.println("Give feedback regarding your purchases...");
    }
    static void viewHistory() {
        while (true) {
            int choice = -1;
            System.out.println("-----------------------------------------");
            System.out.println("0. EXIT");
            System.out.println("1. View purchase history");
            System.out.println("2. View feedback history");
            System.out.println("-----------------------------------------");
            while (!Arrays.asList( 1, 2, 0).contains(choice)){
                System.out.print("Enter your choice (0-2): ");
                choice = s.nextInt();
            }
            switch (choice){
                case 0 -> {
                    return;
                }
                case 1 -> viewPurchaseHistory();
                case 2 -> viewFeedbackHistory();
            }
        }

    }
    static void viewPurchaseHistory() {
        System.out.println("Viewing purchase history...");
    }
    static void viewFeedbackHistory() {
        System.out.println("Viewing feedback history...");
    }
}
