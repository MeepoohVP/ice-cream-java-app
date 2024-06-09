package ui;
import domain.Customer;
import domain.Order;
import domain.OrderDetail;
import repository.*;
import service.ShopService;
import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {
    private static ShopService sh;
    private static void receiveOrder() {
        while (true){
            System.out.println("Menu");
            Stream.of("Banana split 99.-","strawberry sundae 169.-",
                    "rocky road 129.-","cookie and cream cone 69.-",
                    "vanilla cone 69.-").forEach(System.out::println);
            Scanner sc = new Scanner(System.in);
            System.out.print("What would you like to order? (exit or q to back to option page): ");
            String menuName = sc.nextLine();
            if (menuName.matches("q|exit")) {
                break;
            }
            Customer cust = sh.registerCustomer();
            System.out.print("How many cups do you need?: ");
            Order order = sh.addOrder(cust.getQueue());
            OrderDetail orderDetail = sh.createOrderDetail(order.getCode());
            int quantity = sc.nextInt();
            sh.addIceCream(order.getCode(), menuName, quantity);
            orderDetail.addIceCream(menuName, quantity);
            while (true){
                Scanner sc2 = new Scanner(System.in);
                System.out.print("Anything more(n if not): ");
                String more = sc2.nextLine();
                if (more.equals("n")){
                    break;
                }
                System.out.print("How many cups do you need?: ");
                int q = sc2.nextInt();
                sh.addIceCream(order.getCode(), more, q);
                orderDetail.addIceCream(more, q);
            }
            System.out.println("This is your bill");
            System.out.println("queue: "+ cust.getQueue());
            System.out.println("order code: " + order.getCode());
            orderDetail.getIceCream()
                            .entrySet().stream()
                    .forEach(s -> System.out.println(s.getKey() + " " + s.getValue()));
            System.out.println(orderDetail.getTotal() + ".-");
            System.out.print("Confirm for payment (y/n): ");
            String confirm = sc.next();
            if (confirm.equals("y")){
                order.setStatus("success");
                sh.updateOrder(order);
            } else if (confirm.equals("n")) {
                order.setStatus("cancel");
                sh.updateOrder(order);
            }
            System.out.println(order.getStatus());
        }
    }
    public static void access(){
        while (true){
            System.out.println("In this option you can manage" +
                    "\n- customer" +
                    "\n- order" +
                    "\n- order detail");
            Scanner sc = new Scanner(System.in);
            System.out.print("What will you manage?(exit or q to back to option page): ");
            String m = sc.nextLine();
            if (m.matches("q|exit")) {
                break;
            }
            if (m.equals("customer")) {
                System.out.print("you want to find, remove or list all customer: ");
                String select = sc.nextLine();
                if (select.equals("find")){
                    System.out.print("Enter customer queue: ");
                    String queue = sc.nextLine();
                    System.out.println(sh.findCustomer(queue));
                } else if (select.equals("list all")) {
                    sh.allCustomers().stream().sorted((a,b) -> a.getQueue().compareTo(b.getQueue())).forEach(s -> System.out.println("     " +s.getQueue()));
                } else if (select.equals("remove")) {
                    System.out.print("Enter customer queue: ");
                    String queue = sc.nextLine();
                    sh.removeCustomer(queue);
                } else {
                    System.out.println("Invalid option");
                }
            } else if (m.equals("order")) {
                System.out.print("you want to find, remove or list all order: ");
                String select = sc.nextLine();
                if (select.equals("find")){
                    System.out.print("Enter order code: ");
                    String code = sc.nextLine();
                    System.out.println(sh.findOrder(code));
                } else if (select.equals("list all")) {
                    sh.allOrders().forEach(s-> System.out.println("      " +s));
                } else if (select.equals("remove")) {
                    System.out.print("Enter order code: ");
                    String code = sc.nextLine();
                    sh.removeOrder(code);
                } else {
                    System.out.println("Invalid option");
                }
            } else if (m.equals("order detail")) {
                System.out.print("you want to find, remove or list all order detail: ");
                String select = sc.nextLine();
                if (select.equals("find")){
                    System.out.print("Enter order code: ");
                    String code = sc.nextLine();
                    System.out.println(sh.findOrderDetail(code));
                } else if (select.equals("list all")) {
                    sh.allOrderDetails().forEach(s -> System.out.println("      " +s));
                } else if (select.equals("remove")) {
                    System.out.print("Enter order code: ");
                    String code = sc.nextLine();
                    sh.removeOrderDetail(code);
                } else {
                    System.out.println("Invalid option");
                }
            }else {
                System.out.println("Invalid option");
            }
        }
    }
    public static void start(){
        Scanner way = new Scanner(System.in);
        System.out.println("Welcome to Ice-cream shop!");
        System.out.println("- memory" +
                "\n- file" + "\n- database");
        System.out.print("choose way to save data: ");
        String choose = way.nextLine();
        if (choose.equals("memory")) {
            sh = new ShopService(
                    new MemCustomerRepository(),
                    new MemOrderRepository(),
                    new MemOrderDetailRepository()
            );
        }else if (choose.equals("file")) {
            sh = new ShopService(
                    new FileCustomerRepository(),
                    new FileOrderRepository(),
                    new FileOrderDetailRepository()
            );
        } else if (choose.equals("database")) {
            Scanner db = new Scanner(System.in);
            System.out.print("URL: ");
            String url = db.nextLine();
            System.out.print("username: ");
            String username = db.next();
            char[] DbArr = System.console().readPassword("password: ");
            String password = new String(DbArr);
            sh = new ShopService(
                    new DbCustomerRepository(url, username, password),
                    new DbOrderRepository(url, username, password),
                    new DbOrderDetailRepository(url, username, password)
            );
        }
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("What do you want to do?");
            System.out.println("- to receive order -> receive");
            System.out.println("- to access information -> access");
            System.out.print("Choose option[exit or q to quit the program]: ");
            String option = input.nextLine();
            if (option.matches("q|exit")) {
                System.out.println("\nEnd.");
                break;
            }
            else if(option.equals("receive")){
                App.receiveOrder();
            }
            else if(option.equals("access")){
                App.access();
            }
        }
    }
}
