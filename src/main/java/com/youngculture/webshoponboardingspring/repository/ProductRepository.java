package com.youngculture.webshoponboardingspring.repository;

import com.youngculture.webshoponboardingspring.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll(Sort sort);

    List<Product> findAllByCategoryName(String category, Sort sort);

    Product findByName(String name);

}
