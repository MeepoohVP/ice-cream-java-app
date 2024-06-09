package repository;

import domain.Customer;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileCustomerRepository implements CustomerRepository {
    private static final String PATH = "customers.txt";
    private static int nextId = 0;
    private Map<String, Customer> repo;
    public FileCustomerRepository(){
        File file = new File(PATH);
        if (file.exists()){
            try (
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    ObjectInputStream ois = new ObjectInputStream(bis)){
                nextId = ois.readInt();
                repo = (Map<String, Customer>) ois.readObject();
            }catch (IOException | ClassNotFoundException e){
                System.err.println(e.getMessage());
            }
        }
        else {
            nextId = 0;
            repo = new HashMap<>();
        }
    }
    private void writeToFile(){
        try (FileOutputStream fos = new FileOutputStream(PATH);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeInt(nextId);
            oos.writeObject(repo);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public Customer addCustomer() {
        String CustomerId = "C" + ++nextId;
        Customer c = new Customer(CustomerId);
        if (repo.putIfAbsent(CustomerId, c) == null) {
            writeToFile();
            return c;
        }
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

    @Override
    public void removeCustomer(String queue) {
        if (repo.remove(queue) != null) {
            writeToFile();
        }
    }
}