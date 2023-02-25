package com.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.driver.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

    @Query(value = "select max(id) from customers",nativeQuery = true)
    int getMaxId();

    @Query(value = "select min(id) from customers",nativeQuery = true)
    int getMinId();
}
