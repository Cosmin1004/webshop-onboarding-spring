package com.youngculture.webshoponboardingspring.service;

import com.youngculture.webshoponboardingspring.model.PurchaseOrder;
import com.youngculture.webshoponboardingspring.model.User;

import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrder sendOrder(User user);

    List<PurchaseOrder> getAllByUser(User user);

    List<PurchaseOrder> getAllSentOrdersByUser(User user);

    String manageOrder(Long id, String action);

}
