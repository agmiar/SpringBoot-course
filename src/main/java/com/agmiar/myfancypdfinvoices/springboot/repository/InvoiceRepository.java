package com.agmiar.myfancypdfinvoices.springboot.repository;

import com.agmiar.myfancypdfinvoices.springboot.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    public Page<Invoice> findByUserId(String userId, Pageable p);
}
