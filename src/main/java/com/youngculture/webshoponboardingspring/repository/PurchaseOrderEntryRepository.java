package com.youngculture.webshoponboardingspring.repository;

import com.youngculture.webshoponboardingspring.model.PurchaseOrderEntry;
import com.youngculture.webshoponboardingspring.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderEntryRepository extends JpaRepository<PurchaseOrderEntry, Long> {

    List<PurchaseOrderEntry> findAllByPurchaseOrderStatus(Status status);

}
