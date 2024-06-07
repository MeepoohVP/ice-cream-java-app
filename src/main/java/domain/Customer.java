package domain;

import java.io.Serializable;

public class Customer implements Serializable {
    private final String queue;
    public Customer(String queue) {
        this.queue = queue;
    }
    public String getQueue() {
        return queue;
    }
    @Override
    public String toString() {
        return "Customer [queue=" + queue + "]";
    }
}
