package repository;

import domain.Customer;
import domain.Order;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileOrderRepository implements OrderRepository {
        private static final String PATH = "orders.txt";
        private static int nextCode = 0;
        private Map<String, Order> repo;
        public FileOrderRepository() {
                File file = new File(PATH);
                if (file.exists()) {
                        try (FileInputStream fis = new FileInputStream(file);
                             BufferedInputStream bis = new BufferedInputStream(fis);
                             ObjectInputStream ois = new ObjectInputStream(bis)){
                                nextCode = ois.readInt();
                                repo = (Map<String, Order>) ois.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                                System.err.println(e.getMessage());
                        }
                }
                else {
                        nextCode = 0;
                        repo = new HashMap<>();
                }
        }
        private void writeToFile(){
                try (FileOutputStream fos = new FileOutputStream(PATH);
                     BufferedOutputStream bos = new BufferedOutputStream(fos);
                     ObjectOutputStream oos = new ObjectOutputStream(bos)){
                        oos.writeInt(nextCode);
                        oos.writeObject(repo);
                }catch (IOException e){
                        System.err.println(e.getMessage());
                }
        }

        @Override
        public Order addOrder(String ownerId) {
                String OrderCode = "" + ++nextCode;
                Order c = new Order(OrderCode,ownerId,"wait payment");
                if (repo.putIfAbsent(OrderCode, c) == null) {
                        writeToFile();
                        return c;
                }
                return null;
        }
        @Override
        public Order findOrder(String orderCode){
                return  repo.get(orderCode);
        }
        @Override
        public Order updateOrder(Order order){
                try {
                        repo.replace(order.getCode(), order);
                        writeToFile();
                } catch (Exception e) {
                        return null;
                }
                return order;
        }
        @Override
        public Collection<Order> allOrder(){
                return repo.values();
        }

        @Override
        public void removeOrder(String orderCode) {
                if (repo.remove(orderCode) != null) {
                        writeToFile();
                }
        }
}
