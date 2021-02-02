package com.youngculture.webshoponboardingspring.service.impl;

import com.youngculture.webshoponboardingspring.model.*;
import com.youngculture.webshoponboardingspring.repository.CartEntryRepository;
import com.youngculture.webshoponboardingspring.repository.CartRepository;
import com.youngculture.webshoponboardingspring.repository.PurchaseOrderEntryRepository;
import com.youngculture.webshoponboardingspring.repository.PurchaseOrderRepository;
import com.youngculture.webshoponboardingspring.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    public final PurchaseOrderRepository purchaseOrderRepository;
    public final PurchaseOrderEntryRepository purchaseOrderEntryRepository;
    public final CartRepository cartRepository;
    public final CartEntryRepository cartEntryRepository;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                    PurchaseOrderEntryRepository purchaseOrderEntryRepository,
                                    CartRepository cartRepository,
                                    CartEntryRepository cartEntryRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.cartRepository = cartRepository;
        this.cartEntryRepository = cartEntryRepository;
        this.purchaseOrderEntryRepository = purchaseOrderEntryRepository;
    }

    @Override
    public PurchaseOrder sendOrder(User user) {
        Cart userCart = cartRepository.findByUserId(user.getId());
        List<CartEntry> cartEntries = userCart.getCartEntries();
        return purchaseOrderRepository.save(setOrder(user, cartEntries));
    }

    @Override
    public List<PurchaseOrder> getAllByUser(User user) {
        return purchaseOrderRepository.findAllByUser(user);
    }

    @Override
    public List<PurchaseOrderEntry> getAllSentOrders() {
        return purchaseOrderEntryRepository.findAllByPurchaseOrderStatus(Status.SENT);
    }

    /*
     * create order from cart entries
     * */
    private PurchaseOrder setOrder(User user, List<CartEntry> cartEntries) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setReference(Calendar.getInstance().getTimeInMillis());
        purchaseOrder.setStatus(Status.SENT);
        purchaseOrder.setUser(user);

        List<PurchaseOrderEntry> purchaseOrderEntries = new ArrayList<>();
        double subTotal = 0.0;

        for (CartEntry cartEntry : cartEntries) {
            PurchaseOrderEntry purchaseOrderEntry = new PurchaseOrderEntry();
            purchaseOrderEntry.setPurchaseOrder(purchaseOrder);
            purchaseOrderEntry.setProduct(cartEntry.getProduct());
            purchaseOrderEntry.setQuantity(cartEntry.getQuantity());
            purchaseOrderEntry.setPrice(cartEntry.getProduct().getPrice());
            subTotal += cartEntry.getProduct().getPrice() * cartEntry.getQuantity();

            purchaseOrderEntries.add(purchaseOrderEntry);
        }

        purchaseOrder.setSubTotal(subTotal);
        purchaseOrder.setOrderEntries(purchaseOrderEntries);

        return purchaseOrder;
    }
}
