package repository;

import domain.OrderDetail;

import java.util.Collection;

public interface OrderDetailRepository {
    OrderDetail addOrderDetail(String orderCode);
    OrderDetail findOrderDetail(String orderCode);
    OrderDetail updateOrderDetail(OrderDetail orderDetail);
    Collection<OrderDetail> allOrderDetails();
    void removeOrderDetail(String orderCode);
}
