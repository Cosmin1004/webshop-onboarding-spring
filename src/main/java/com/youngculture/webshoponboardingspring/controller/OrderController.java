package com.youngculture.webshoponboardingspring.controller;

import com.youngculture.webshoponboardingspring.model.Category;
import com.youngculture.webshoponboardingspring.model.PurchaseOrder;
import com.youngculture.webshoponboardingspring.model.User;
import com.youngculture.webshoponboardingspring.service.CategoryService;
import com.youngculture.webshoponboardingspring.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.youngculture.webshoponboardingspring.util.Const.ORDER;

@Controller
public class OrderController {

    private final PurchaseOrderService purchaseOrderService;
    private final CategoryService categoryService;

    @Autowired
    public OrderController(PurchaseOrderService purchaseOrderService,
                           CategoryService categoryService) {
        this.purchaseOrderService = purchaseOrderService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("user")
    public User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();

        return (User) session.getAttribute("currentSessionUser");
    }

    @PostMapping("/sendOrder")
    public @ResponseBody
    String sendOrder(@ModelAttribute("user") User user) {

        PurchaseOrder purchaseOrder = purchaseOrderService.sendOrder(user);

        return "The order (" + purchaseOrder.getReference() + " - "
                + purchaseOrder.getSubTotal() + " Lei) has been sent!";

    }

    @GetMapping("/orders")
    public String getUserOrders(@ModelAttribute("user") User user, ModelMap model) {
        List<PurchaseOrder> purchaseOrders =
                purchaseOrderService.getAllByUser(user);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("orders", purchaseOrders);
        model.addAttribute("categories", categories);

        return ORDER;
    }

}
