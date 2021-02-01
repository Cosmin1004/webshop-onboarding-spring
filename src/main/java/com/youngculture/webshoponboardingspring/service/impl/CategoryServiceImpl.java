package com.youngculture.webshoponboardingspring.service.impl;

import com.youngculture.webshoponboardingspring.model.Category;
import com.youngculture.webshoponboardingspring.repository.CategoryRepository;
import com.youngculture.webshoponboardingspring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

}
