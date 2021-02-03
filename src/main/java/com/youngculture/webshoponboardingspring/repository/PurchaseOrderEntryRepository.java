package com.youngculture.webshoponboardingspring.repository;

import com.youngculture.webshoponboardingspring.model.PurchaseOrderEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderEntryRepository extends JpaRepository<PurchaseOrderEntry, Long> {

}
