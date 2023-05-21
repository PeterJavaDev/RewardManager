package com.reward.repository;

import com.reward.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByCustomerId(Long customerId);

    @Query("SELECT p FROM Purchase p WHERE p.customerId = :customerId AND EXTRACT(MONTH FROM p.createDate) = :month")
    List<Purchase> findByCustomerIdAndMonth(@Param("customerId") Long customerId, @Param("month") int month);

}