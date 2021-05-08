package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.repository.CostumerRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CostumerRepository customerRepository;

    public Long save(Customer customer) {
        customerRepository.save(customer);
        return customer.getUserId();
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).get();
    }
}
