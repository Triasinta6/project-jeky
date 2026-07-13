package com.jeky.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeky.backend.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByNoHp(String noHp);

    Optional<Customer> findByEmailOrNoHp(String email, String noHp);

    boolean existsByEmail(String email);

    boolean existsByNoHp(String noHp);
}