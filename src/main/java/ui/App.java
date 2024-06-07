package ui;

import domain.Customer;
import service.ShopService;

import java.util.Scanner;

public class App {
    public static void start(){
        ShopService sh = new ShopService();
        System.out.println("Welcome to Ice-cream shop!");
        Scanner sc = new Scanner(System.in);
        System.out.println("What would you like to order?(y/n): ");
        String queue = sc.next();
        if(queue.equals("y")){

        }
    }
}
