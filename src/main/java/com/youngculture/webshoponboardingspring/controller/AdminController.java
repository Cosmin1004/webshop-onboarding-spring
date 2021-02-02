package com.youngculture.webshoponboardingspring.controller;

import com.youngculture.webshoponboardingspring.model.PurchaseOrderEntry;
import com.youngculture.webshoponboardingspring.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public AdminController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/allOrders")
    public String getAllOrders(ModelMap model) {
        List<PurchaseOrderEntry> purchaseOrderEntries =
                purchaseOrderService.getAllSentOrders();
        model.addAttribute("orderEntries", purchaseOrderEntries);
        return "admin";
    }

}
