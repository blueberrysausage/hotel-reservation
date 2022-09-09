package service;

import model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;


public class CustomerService {
    private static Map<String, Customer> mapOfCustomer = new HashMap<>();

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        mapOfCustomer.put(email, customer);
    }

    public Customer getCustomer(String customerEmail) {
        return mapOfCustomer.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return mapOfCustomer.values();
    }
}
