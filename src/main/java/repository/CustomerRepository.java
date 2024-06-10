package repository;

import domain.Customer;

import java.util.Collection;

public interface CustomerRepository {
    Customer addCustomer();
    Customer findCustomer(String queue);
    Collection<Customer> allCustomers();
    void removeCustomer(String queue);
    void clearCustomers();
}
