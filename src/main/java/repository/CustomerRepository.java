package repository;

import domain.Customer;

import java.util.Collection;

public interface CustomerRepository {
    Customer addCustomer();
    Customer findCustomer(String id);
    Collection<Customer> allCustomers();

}
