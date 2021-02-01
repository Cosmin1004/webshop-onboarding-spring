package com.youngculture.webshoponboardingspring.controller;

import com.youngculture.webshoponboardingspring.model.Category;
import com.youngculture.webshoponboardingspring.model.Product;
import com.youngculture.webshoponboardingspring.model.User;
import com.youngculture.webshoponboardingspring.service.CategoryService;
import com.youngculture.webshoponboardingspring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    private List<Category> categories;
    private List<Product> products;

    @Autowired
    HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping(value = {"/", "", "/yc", "/product", "/home"})
    public String getHomeInfos(ModelMap model) {
        products = productService.getAll();
        categories = categoryService.getAll();
        setAttributes(model);
        return "product";
    }

    @PostMapping(value = { "/home"})
    public String loginRedirect(ModelMap model) {
        products = productService.getAll();
        categories = categoryService.getAll();
        setAttributes(model);
        return "product";
    }

    private void setAttributes(ModelMap model) {
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("categoryRendered", true);
    }

}
