package com.youngculture.webshoponboardingspring.service;

import com.youngculture.webshoponboardingspring.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    List<Product> getAllByCategory(String category);

    Product getByName(String name);

}
