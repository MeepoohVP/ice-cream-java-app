package ui;
import domain.Customer;
import domain.Order;
import repository.MemCustomerRepository;
import repository.MemOrderDetailRepository;
import repository.MemOrderRepository;
import service.ShopService;
import java.io.Console;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {
    private static void receiveOrder() {
        MemCustomerRepository c = new MemCustomerRepository();
        MemOrderRepository o = new MemOrderRepository();
        MemOrderDetailRepository od = new MemOrderDetailRepository();
        ShopService sh = new ShopService(c, o, od);
        while (true){
            System.out.println("Menu");
            Stream.of("Banana split 99.-","strawberry sundae 169.-",
                    "rocky road 129.-","cookie and cream cone 69.-",
                    "vanilla cone 69.-").forEach(System.out::println);
            Scanner sc = new Scanner(System.in);
            Customer cust = sh.registerCustomer();
            System.out.print("What would you like to order? (exit or q to back to option page): ");
            String menuName = sc.nextLine();
            if (menuName.matches("q|exit")) {
                break;
            }
            System.out.print("How many cups do you need?: ");
            Order order = sh.addOrder(cust.getQueue());
            sh.createOrderDetail(order.getCode());
            int quantity = sc.nextInt();
            sh.addIceCream(order.getCode(), menuName, quantity);
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
            }
            System.out.println("This is your bill");
            System.out.println("queue: "+ cust.getQueue());
            System.out.println("order code: " + order.getCode());
            sh.findOrderDetail(order.getCode()).getIceCream()
                    .entrySet().stream()
                    .forEach(s -> System.out.println(s.getKey() + " " + s.getValue()));
            System.out.println(sh.findOrderDetail(order.getCode()).getTotal() + ".-");
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
    public static void start(){
        Console cs = System.console();
        String user =  cs.readLine("Enter your username: ");
        char[] password = cs.readPassword("Enter your password: ");
        String pw = "";
        for (int i = 0; i < password.length; i++) {
            pw+=password[i];
        }
        System.out.println("Welcome to Ice-cream shop!");
        while (pw.equals("icecream123")) {
            Scanner input = new Scanner(System.in);
            //receive order, showDetail
            System.out.print("Choose option: ");
            String option = input.nextLine();
            if(option.equals("receive order")){
                App.receiveOrder();
            }
        }
    }
}
