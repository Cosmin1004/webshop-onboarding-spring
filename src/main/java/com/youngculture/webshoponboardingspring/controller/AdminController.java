package com.youngculture.webshoponboardingspring.controller;

import com.youngculture.webshoponboardingspring.model.PurchaseOrder;
import com.youngculture.webshoponboardingspring.model.User;
import com.youngculture.webshoponboardingspring.service.PurchaseOrderService;
import com.youngculture.webshoponboardingspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private final PurchaseOrderService purchaseOrderService;
    private final UserService userService;

    @Autowired
    public AdminController(PurchaseOrderService purchaseOrderService, UserService userService) {
        this.purchaseOrderService = purchaseOrderService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllUsers(ModelMap model) {
        List<User> users =
                userService.getAllNonAdminUsers();
        model.addAttribute("users", users);

        return "admin";
    }

    @GetMapping("/allOrders")
    public @ResponseBody
    List<PurchaseOrder> getAllSentOrders(
            @RequestParam("email") String email) {

        return purchaseOrderService
                .getAllSentOrdersByUser(userService.getByEmail(email));
    }

    @PatchMapping("/manageOrder")
    public @ResponseBody
    String manageOrder(@RequestParam(value = "id") Long id,
                       @RequestParam(value = "action") String action) {
        purchaseOrderService.manageOrder(id, action);

        String reference = String.valueOf(
                purchaseOrderService.getById(id).getReference());

        return getActionMessage(action, reference);
    }

    private String getActionMessage(String action, String reference) {
        String actionMessage = null;
        if (action.equals("Confirm")) {
            actionMessage = "The order with reference " + reference
                    + " has been successfully confirmed!";
        } else if (action.equals("Decline")) {
            actionMessage = "The order with reference " + reference
                    + " has been successfully declined!";
        }

        return actionMessage;
    }

}
