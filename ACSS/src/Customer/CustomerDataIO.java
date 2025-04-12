package Customer;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerDataIO {
    public static ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    private static final String FILE_NAME = "CustomersTxtFile.txt";
    public static void writeCustomer(){
        try(PrintWriter writer = new PrintWriter(FILE_NAME);){
            for (Customer customer : allCustomers){
                writer.print(customer.name + ",");
                writer.println(customer.password);
            }
        }catch(Exception e){
            System.out.printf("Error writing to %s file ...", FILE_NAME);
        }
    }
    public static void readCustomer(){
        try(Scanner s = new Scanner(new File(FILE_NAME))){
            while(s.hasNextLine()){
                String name = s.nextLine();
                if (!s.hasNextLine()) break;
                String password = s.nextLine();
                s.nextLine();
            }
        }catch(Exception e){
            System.out.println("Error reading from file...");
        }
    }
    public static Customer searchName(String name){
        for(Customer customer : allCustomers){
            if(name.equals(customer.name)){
                return customer;
            }
        }
        return null;
    }
}
