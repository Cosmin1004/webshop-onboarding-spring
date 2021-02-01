package com.youngculture.webshoponboardingspring.service.impl;

import com.youngculture.webshoponboardingspring.model.Product;
import com.youngculture.webshoponboardingspring.repository.ProductRepository;
import com.youngculture.webshoponboardingspring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll(Sort.by("name"));
    }

    @Override
    public List<Product> getAllByCategory(String category) {
        return productRepository.findAllByCategoryName(category, Sort.by("name"));
    }

    @Override
    public Product getByName(String name) {
        return productRepository.findByName(name);
    }

}
