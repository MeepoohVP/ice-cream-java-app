package repository;

import domain.OrderDetail;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileOrderDetailRepository implements OrderDetailRepository {
    private static final String PATH = "order-details.txt";
    private Map<String, OrderDetail> repo;
    public FileOrderDetailRepository(){
        File file = new File(PATH);
        if (file.exists()){
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 ObjectInputStream ois = new ObjectInputStream(bis)){
                repo = (Map<String, OrderDetail>) ois.readObject();
            }catch (IOException | ClassNotFoundException e){
                System.err.println(e.getMessage());
            }
        }
        else {
            repo = new HashMap<>();
        }
    }
    private void writeToFile(){
        try (FileOutputStream fos = new FileOutputStream(PATH);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeObject(repo);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public OrderDetail addOrderDetail(String orderCode) {
        OrderDetail od = new OrderDetail(orderCode);
        if (repo.putIfAbsent(orderCode, od) == null) {
            writeToFile();
            return od;
        }
        return null;
    }
    @Override
    public OrderDetail findOrderDetail(String orderCode){
        return  repo.get(orderCode);
    }
    @Override
    public Collection<OrderDetail> allOrderDetails(){
        return repo.values();
    }

    @Override
    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        try {
            repo.replace(orderDetail.getOrderCode(), orderDetail);
            writeToFile();
        } catch (Exception e) {
            return null;
        }
        return orderDetail;
    }

    @Override
    public void removeOrderDetail(String orderCode) {
        if (repo.remove(orderCode) != null) {
            writeToFile();
        }
    }
}
