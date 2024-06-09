package repository;

import domain.Order;

import java.util.Collection;

public interface OrderRepository {
    Order addOrder(String ownerId);
    Order findOrder(String orderCode);
    Order updateOrder(Order order);
    Collection<Order> allOrder();
    void removeOrder(String orderCode);
}
