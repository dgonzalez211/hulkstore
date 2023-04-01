package com.diegodev.hulkstore.data.service;

import com.diegodev.hulkstore.data.entity.shop.Customer;
import com.diegodev.hulkstore.data.service.repository.CustomerRepository;
import com.diegodev.hulkstore.data.service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService extends CrudBaseService {
    private final UserRepository userRepository;
    CustomerRepository repository;

    public CustomerService(CustomerRepository repository, UserRepository userRepository) {
        super(repository);
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Customer> findAllCustomers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.search(stringFilter);
        }
    }

    public long countCustomers() {
        return repository.count();
    }

    public void deleteCustomer(Customer Customer) {
        repository.delete(Customer);
    }

    public void saveCustomer(Customer Customer) {
        if (Customer == null) {
            System.err.println("Customer is null. Are you sure you have connected your form to the application?");
            return;
        }
        repository.save(Customer);
    }
}
