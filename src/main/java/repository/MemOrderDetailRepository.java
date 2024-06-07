package repository;

import domain.OrderDetail;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemOrderDetailRepository implements OrderDetailRepository {
    private final Map<String, OrderDetail> repo = new HashMap<>();

    @Override
    public OrderDetail addOrderDetail(String orderCode) {
        OrderDetail od = new OrderDetail(orderCode);
        if (repo.putIfAbsent(orderCode, od) != null) {return od;}
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
        } catch (Exception e) {
            return null;
        }
        return orderDetail;
    }
}
