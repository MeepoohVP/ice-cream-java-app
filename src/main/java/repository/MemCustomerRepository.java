package repository;

import domain.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemCustomerRepository implements CustomerRepository {
    private static int nextId = 0;
    private final Map<String, Customer> repo = new HashMap<>();

    @Override
    public Customer addCustomer() {
        String CustomerId = "C" + ++nextId;
        Customer c = new Customer(CustomerId);
        if (repo.putIfAbsent(CustomerId, c) == null) return c;
        return null;
    }

    @Override
    public Customer findCustomer(String CustomerId) {
        return repo.get(CustomerId);
    }
    @Override
    public Collection<Customer> allCustomers() {
        return  repo.values();
    }






}
