package domain;

import exception.OrderException;

import java.io.Serializable;

public class Order implements Serializable {
    private final String code;
    private final String ownerId;
    private String status;
    //wait payment
    //in progress
    //success
    //cancel
    public Order(String code, String ownerId, String status) {
        this.code = code;
        this.ownerId = ownerId;
        this.status = status;
    }
    public String getCode() {
        return code;
    }
    public String getOwnerId() {
        return ownerId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        if (!status.matches("wait payment|in progress|success|cancel")){
            throw new OrderException();
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" + "code=" + code + ", ownerId=" + ownerId + ", status=" + status + '}';
    }
}
