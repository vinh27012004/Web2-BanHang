package ntu.vinh.banhang.service;

import ntu.vinh.banhang.entity.Customer;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Long id);
    List<Customer> searchByPhone(String phone);
} 