package domain;

import exception.OrderDetailException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OrderDetail implements Serializable {
    //banana split 99
    //strawberry sundae 169
    //rocky road 129
    //cookie and cream cone 69
    //vanilla cone 69
    private String orderCode;
    private Map<String,Integer> iceCream = null;
    private int total;
    public OrderDetail(String orderCode) {
        this.orderCode = orderCode;
        iceCream = new HashMap<>();
        total = 0;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public double getTotal() {
        return total;
    }

    public Map<String, Integer> getIceCream() {
        return iceCream;
    }

    public void updateTotal(String menu, int quantity) {
        if (menu.equalsIgnoreCase("banana split")) {
            total += 99 * quantity;
        }
        if (menu.equalsIgnoreCase("strawberry sundae")) {
            total += 169 * quantity;
        }
        if (menu.equalsIgnoreCase("rocky road")) {
            total += 129 * quantity;
        }
        if (menu.toLowerCase().matches("cookie and cream cone|vanilla cone")) {
            total += 69 * quantity;
        }
    }
    public boolean addIceCream(String menu, int quantity) {
        if(!menu.toLowerCase().matches("banana split|strawberry sundae|rocky road|cookie and cream cone|vanilla cone")){
            return false;
        }
        iceCream.put(menu, quantity);
        updateTotal(menu, quantity);
        return true;
    }

    @Override
    public String toString() {
        return "OrderDetail [orderCode=" + orderCode + ", iceCream=" + iceCream + "]";
    }
}
