package com.youngculture.webshoponboardingspring.service.impl;

import com.youngculture.webshoponboardingspring.model.*;
import com.youngculture.webshoponboardingspring.repository.CartEntryRepository;
import com.youngculture.webshoponboardingspring.repository.CartRepository;
import com.youngculture.webshoponboardingspring.repository.PurchaseOrderRepository;
import com.youngculture.webshoponboardingspring.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.youngculture.webshoponboardingspring.util.Const.CONFIRM;
import static com.youngculture.webshoponboardingspring.util.Const.DECLINE;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    public final PurchaseOrderRepository purchaseOrderRepository;
    public final CartRepository cartRepository;
    public final CartEntryRepository cartEntryRepository;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                    CartRepository cartRepository,
                                    CartEntryRepository cartEntryRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.cartRepository = cartRepository;
        this.cartEntryRepository = cartEntryRepository;
    }

    @Override
    public PurchaseOrder sendOrder(User user) {
        Cart userCart = cartRepository.findByUserId(user.getId());
        List<CartEntry> cartEntries = userCart.getCartEntries();
        return purchaseOrderRepository.save(
                createOrderFromCart(user, cartEntries));
    }

    @Override
    public List<PurchaseOrder> getAllByUser(User user) {
        return purchaseOrderRepository.findAllByUser(user, Sort.by("status"));
    }

    @Override
    public List<PurchaseOrder> getAllSentOrdersByUser(User user) {
        return purchaseOrderRepository
                .findAllByUserAndStatus(user, Status.SENT, Sort.by("reference"));
    }

    @Override
    public String manageOrder(Long id, String action) {
        PurchaseOrder order =
                purchaseOrderRepository.findById(id).get();
        String actionMessage = null;
        if (action.equals(CONFIRM)) {
            order.setStatus(Status.CONFIRMED);
            actionMessage = "The order with reference " + order.getReference()
                    + " has been successfully confirmed!";
        } else if (action.equals(DECLINE)) {
            order.setStatus(Status.DECLINED);
            actionMessage = "The order with reference " + order.getReference()
                    + " has been successfully declined!";
        }
        purchaseOrderRepository.save(order);
        return actionMessage;

    }

    /*
     * create order from cart entries
     * */
    private PurchaseOrder createOrderFromCart(User user, List<CartEntry> cartEntries) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setReference(Calendar.getInstance().getTimeInMillis());
        purchaseOrder.setStatus(Status.SENT);
        purchaseOrder.setUser(user);

        List<PurchaseOrderEntry> purchaseOrderEntries = new ArrayList<>();

        double subTotal = 0.0;

        //set list of order entries and subTotal
        subTotal = addOrderEntriesAndGetSubTotal(cartEntries,
                purchaseOrder, purchaseOrderEntries, subTotal);

        purchaseOrder.setSubTotal(subTotal);
        purchaseOrder.setOrderEntries(purchaseOrderEntries);

        return purchaseOrder;
    }

    private double addOrderEntriesAndGetSubTotal(List<CartEntry> cartEntries, PurchaseOrder purchaseOrder, List<PurchaseOrderEntry> purchaseOrderEntries, double subTotal) {
        //set list of order entries and subTotal
        for (CartEntry cartEntry : cartEntries) {
            PurchaseOrderEntry purchaseOrderEntry = new PurchaseOrderEntry();
            purchaseOrderEntry.setPurchaseOrder(purchaseOrder);
            purchaseOrderEntry.setProduct(cartEntry.getProduct());
            purchaseOrderEntry.setQuantity(cartEntry.getQuantity());
            purchaseOrderEntry.setPrice(cartEntry.getProduct().getPrice());

            subTotal += cartEntry.getProduct().getPrice() * cartEntry.getQuantity();
            purchaseOrderEntries.add(purchaseOrderEntry);
        }
        return subTotal;
    }
}
