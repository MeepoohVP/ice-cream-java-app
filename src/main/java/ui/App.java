package ui;

import domain.Customer;
import domain.Order;
import domain.OrderDetail;
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
        MemCustomerRepository c = new MemCustomerRepository();
        MemOrderRepository o = new MemOrderRepository();
        MemOrderDetailRepository od = new MemOrderDetailRepository();
        ShopService sh = new ShopService(c, o, od);
        System.out.println("Welcome to Ice-cream shop!");
        System.out.println("Menu");
        Stream.of("Banana split 99.-","strawberry sundae 169.-",
                "rocky road 129.-","cookie and cream cone 69.-",
                "vanilla cone 69.-");
        if (pw.equals("icecream123")) {
            Stream.of("Banana split 99.-","strawberry sundae 169.-",
                    "rocky road 129.-","cookie and cream cone 69.-",
                    "vanilla cone 69.-").forEach(System.out::println);
        }
        while(pw.equals("icecream123")){
            Scanner sc = new Scanner(System.in);
            Customer cust = sh.registerCustomer();
            System.out.print("What would you like to order?: ");
            String menuName = sc.nextLine();
            System.out.print("How many cups do you need?: ");
            Order order = sh.addOrder(cust.getQueue());
            OrderDetail orderDetail = sh.createOrderDetail(order.getCode());
            int quantity = sc.nextInt();
            orderDetail.addIceCream(menuName, quantity);
            while (sc.hasNextLine()){
                System.out.print("Anything more(n if not): ");
                String more = sc.nextLine();
                if (more.equals("n")){
                    break;
                }
                orderDetail.addIceCream(more, 1);
            }
            System.out.println(orderDetail);
            System.out.println(orderDetail.getTotal());
        }
    }
}
