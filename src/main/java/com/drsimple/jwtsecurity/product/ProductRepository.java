package com.drsimple.jwtsecurity.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

//public interface ProductRepository extends JpaRepository<Product, Long> {
//    Optional<Product> findByName(String name);
//    Page<Product> findByNameContainingIgnoreCase(String nameFilter, Pageable pageable);
//}


public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String name);
    Page<Product> findByNameContainingIgnoreCase(String nameFilter, Pageable pageable);
}