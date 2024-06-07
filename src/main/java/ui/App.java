package ui;

import domain.Customer;
import repository.MemCustomerRepository;
import repository.MemOrderDetailRepository;
import repository.MemOrderRepository;
import service.ShopService;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {
    public static void start(){
        Console cs = System.console();
        String user =  cs.readLine("Enter your username: ");
        char[] password = cs.readPassword("Enter your password: ");
        String pw = "";
        for (int i = 0; i < password.length; i++) {
            pw+=password[i];
        }
        System.out.println(pw);
        MemCustomerRepository c = new MemCustomerRepository();
        MemOrderRepository o = new MemOrderRepository();
        MemOrderDetailRepository od = new MemOrderDetailRepository();
        ShopService sh = new ShopService(c, o, od);
        System.out.println("Welcome to Ice-cream shop!");
        System.out.println("Menu");
        Stream.of("Banana split 99.-","strawberry sundae 169.-",
                "rocky road 129.-","cookie and cream cone 69.-",
                "vanilla cone 69.-");
        while(pw.equals("icecream123")){
            Scanner sc = new Scanner(System.in);
            System.out.println("What would you like to order?: ");
            String menu = sc.next();
            if (menu.equalsIgnoreCase("Banana split")){

            }
        }
    }
}
