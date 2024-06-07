package repository;

import domain.Customer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileCustomerRepository implements CustomerRepository {
    private static final String PATH = "../customers.txt";
    private static int nextId = 0;
    private Map<String, Customer> repo = new HashMap<>();
    private FileCustomerRepository(){
        try (
                FileInputStream fis = new FileInputStream(PATH);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);){
            repo = (Map<String, Customer>) ois.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
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
