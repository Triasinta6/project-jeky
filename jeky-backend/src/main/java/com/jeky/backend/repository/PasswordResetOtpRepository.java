package com.jeky.backend.repository;

import com.jeky.backend.model.Customer;
import com.jeky.backend.model.PasswordResetOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetOtp, Long> {
    Optional<PasswordResetOtp> findTopByCustomerOrderByExpiryDateDesc(Customer customer);
}
