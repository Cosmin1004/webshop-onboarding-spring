package com.youngculture.webshoponboardingspring.controller;

import com.youngculture.webshoponboardingspring.model.PurchaseOrder;
import com.youngculture.webshoponboardingspring.model.User;
import com.youngculture.webshoponboardingspring.service.PurchaseOrderService;
import com.youngculture.webshoponboardingspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.youngculture.webshoponboardingspring.util.Const.ADMIN;

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

        return ADMIN;
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
        return purchaseOrderService.manageOrder(id, action);
    }

}
