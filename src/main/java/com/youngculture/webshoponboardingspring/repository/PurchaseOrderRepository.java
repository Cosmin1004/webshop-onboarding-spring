package com.youngculture.webshoponboardingspring.repository;

import com.youngculture.webshoponboardingspring.model.PurchaseOrder;
import com.youngculture.webshoponboardingspring.model.Status;
import com.youngculture.webshoponboardingspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findAllByUser(User user);

    List<PurchaseOrder> findAllByUserAndStatus(User user, Status status);

}
