package com.youngculture.webshoponboardingspring.controller;

import com.youngculture.webshoponboardingspring.model.Category;
import com.youngculture.webshoponboardingspring.model.Product;
import com.youngculture.webshoponboardingspring.service.CategoryService;
import com.youngculture.webshoponboardingspring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    private List<Product> products;
    private boolean categoryRendered;

    @Autowired
    ProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @GetMapping(value = "/products")
    public String getAllProductsByCategory(ModelMap model,
                                           @RequestParam(value = "category") String category) {
        products = handleGetProducts(category);
        setAttributes(model, categoryRendered);
        return "home";
    }

    private void setAttributes(ModelMap model, boolean categoryRendered) {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("categoryRendered", categoryRendered);
    }

    public List<Product> handleGetProducts(String category) {
        List<Product> products;
        if (category == null || category.equals("all")) {
            products = productService.getAll();
            categoryRendered = true;
        } else {
            products = productService.getAllByCategory(category);
            categoryRendered = false;
        }
        return products;
    }
}
