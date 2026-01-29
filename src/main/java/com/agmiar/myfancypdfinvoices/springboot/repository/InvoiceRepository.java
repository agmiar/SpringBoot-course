package com.agmiar.myfancypdfinvoices.springboot.repository;

import com.agmiar.myfancypdfinvoices.springboot.model.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, String> {
}
