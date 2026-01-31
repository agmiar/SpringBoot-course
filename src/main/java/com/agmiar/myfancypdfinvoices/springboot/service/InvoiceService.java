package com.agmiar.myfancypdfinvoices.springboot.service;

import com.agmiar.myfancypdfinvoices.springboot.model.Invoice;
import com.agmiar.myfancypdfinvoices.springboot.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public final class InvoiceService {

    private UserService userService;
    private InvoiceRepository invoiceRepository;
    private @Value("${cdn.url}") String pdfBaseUrl;
//    private Iterable<Invoice> invoices;

    public InvoiceService(UserService userService, InvoiceRepository invoiceRepository) {
        this.userService = userService;
        this.invoiceRepository = invoiceRepository;
    }

//    @PostConstruct
//    public void init() {
//        System.out.println("Fetching PDF Template from S3...");
//        // TODO download from s3 and save locally
//        invoices = invoiceRepository.findAll();
//    }


    public Invoice create(String pdfResource, String userId, Integer amount) {
//        if (userService.findById(userId) == null){
//            throw new IllegalStateException();
//        }
        String fullPdfUrl = pdfBaseUrl + "/" + pdfResource;
        var invoice = Invoice.create(fullPdfUrl, userId, amount);
        return invoiceRepository.save(invoice);
    }

    public Iterable<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

//    public Iterable<Invoice> findByUserId(String userId) {
//        var invoicesByUser = new CopyOnWriteArrayList<Invoice>();
//        for (Invoice invoice : invoices) {
//            if (invoice.hasSameUserId(userId))
//                invoicesByUser.add(invoice);
//        }
//        return invoicesByUser;
//    }

    // implementación de Pageable
    public Page<Invoice> getPaginatedInvoices(
            String userId, int page, int size, String sortBy){
        Pageable p = PageRequest.of(page, size, Sort.by(sortBy));
        if (userId != null) {
            return invoiceRepository.findByUserId(userId, p);
        }
        return invoiceRepository.findAll(p);
    }

}
