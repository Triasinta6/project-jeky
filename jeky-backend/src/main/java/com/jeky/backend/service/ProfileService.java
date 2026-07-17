package com.jeky.backend.service;

import com.jeky.backend.dto.CustomerProfileResponse;
import com.jeky.backend.dto.ProfileRequest;
import com.jeky.backend.model.Customer;
import com.jeky.backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final CustomerRepository customerRepository;

    public ProfileService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerProfileResponse getProfile(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            return new CustomerProfileResponse(false, "Customer tidak ditemukan", null);
        }

        return new CustomerProfileResponse(true, "Profile berhasil diambil", customer);
    }

    public CustomerProfileResponse updateProfile(Long customerId, ProfileRequest request) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            return new CustomerProfileResponse(false, "Customer tidak ditemukan", null);
        }

        String name = request.getName() == null ? "" : request.getName().trim();
        String noHp = request.getNoHp() == null ? "" : request.getNoHp().trim();
        String address = request.getAddress() == null ? "" : request.getAddress().trim();

        if (name.isBlank()) {
            return new CustomerProfileResponse(false, "Nama wajib diisi", null);
        }

        if (noHp.isBlank()) {
            return new CustomerProfileResponse(false, "Nomor HP wajib diisi", null);
        }

        customerRepository.findByNoHp(noHp).ifPresent(existingCustomer -> {
            if (!existingCustomer.getId().equals(customerId)) {
                throw new RuntimeException("Nomor HP sudah digunakan");
            }
        });

        customer.setName(name);
        customer.setNoHp(noHp);
        customer.setAddress(address);

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerProfileResponse(true, "Profile berhasil diperbarui", savedCustomer);
    }
}