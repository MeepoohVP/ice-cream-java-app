package repository;

import domain.Order;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemOrderRepository implements OrderRepository {
    private static int nextCode = 0;
    private final Map<String, Order> repo = new HashMap<>();

    @Override
    public Order addOrder(String ownerId) {
        String OrderCode = "" + ++nextCode;
        Order c = new Order(OrderCode,ownerId,"wait payment");
        if (repo.putIfAbsent(OrderCode, c) == null) return c;
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
        repo.remove(orderCode);
    }

    @Override
    public void clearOrder() {
        repo.clear();
    }
}
