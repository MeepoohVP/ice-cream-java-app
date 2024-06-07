package domain;

public class Customer {
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
