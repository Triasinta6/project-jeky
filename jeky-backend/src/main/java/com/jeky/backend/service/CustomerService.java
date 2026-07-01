package com.jeky.backend.service;

import com.jeky.backend.dto.CustomerRequest;
import com.jeky.backend.dto.CustomerResponse;
import com.jeky.backend.exception.ResourceNotFoundException;
import com.jeky.backend.model.Customer;
import com.jeky.backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::new)
                .toList();
    }

    public CustomerResponse getById(Long id) {
        return new CustomerResponse(findEntityById(id));
    }

    public CustomerResponse create(CustomerRequest request) {
        Customer customer = new Customer();
        applyRequest(customer, request);
        return new CustomerResponse(customerRepository.save(customer));
    }

    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = findEntityById(id);
        applyRequest(customer, request);
        return new CustomerResponse(customerRepository.save(customer));
    }

    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer tidak ditemukan");
        }
        customerRepository.deleteById(id);
    }

    private Customer findEntityById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer tidak ditemukan"));
    }

    private void applyRequest(Customer customer, CustomerRequest request) {
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setNoHp(request.getNoHp());
        customer.setAddress(request.getAddress());
        customer.setAktif(request.getAktif() == null ? true : request.getAktif());
    }
}
