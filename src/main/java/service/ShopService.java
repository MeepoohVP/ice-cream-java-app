package service;

import domain.Customer;
import domain.Order;
import domain.OrderDetail;
import repository.CustomerRepository;
import repository.OrderDetailRepository;
import repository.OrderRepository;

import java.util.Collection;

public class ShopService {
    private final CustomerRepository customers;
    private final OrderRepository orders;
    private final OrderDetailRepository orderDetails;
    public ShopService(CustomerRepository customers, OrderRepository orders, OrderDetailRepository orderDetails) {
        this.customers = customers;
        this.orders = orders;
        this.orderDetails = orderDetails;
    }
    public Customer registerCustomer() {
        return customers.addCustomer();
    }
    public Customer findCustomer(String custId) {
        if (custId == null) { return null; }
        return customers.findCustomer(custId);
    }
    public Collection<Customer> allCustomers() {
        return customers.allCustomers();
    };
    public Order addOrder(String ownerId) {
        if (ownerId == null) { return null; }
        return orders.addOrder(ownerId);
    }
    public Order findOrder(String orderCode) {
        if (orderCode == null) { return null; }
        return orders.findOrder(orderCode);
    }
    public Collection<Order> allOrders() {
        return orders.allOrder();
    }
    public OrderDetail createOrderDetail(String orderCode){
        if (orderCode == null) { return null; }
        return orderDetails.addOrderDetail(orderCode);
    }
    public OrderDetail updateOrderDetail(OrderDetail od){
        return orderDetails.updateOrderDetail(od);
    }
    public OrderDetail findOrderDetail(String orderCode){
        if (orderCode == null) { return null; }
        return orderDetails.findOrderDetail(orderCode);
    }
    public Collection<OrderDetail> allOrderDetails() {
        return orderDetails.allOrderDetails();
    }
}
