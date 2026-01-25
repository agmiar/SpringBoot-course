package com.agmiar.myfancypdfinvoices.springboot.service;

import com.agmiar.myfancypdfinvoices.springboot.model.Invoice;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public final class InvoiceService {

    private UserService userService;
    private String cdnUrl;
    private CopyOnWriteArrayList<Invoice> invoices;

    public InvoiceService(UserService userService, @Value("${cdn.url}") String cdnUrl) {

        // lista thread-safe
        this.invoices = new CopyOnWriteArrayList<>();

        this.userService = userService;
        this.cdnUrl = cdnUrl;
    }

    @PostConstruct
    public void init() {
        System.out.println("Fetching PDF Template from S3...");
        // TODO download from s3 and save locally
    }



    public Invoice create(String userId, Integer amount) {
        // TODO real pdf creation and storing it on network server
//        if (userService.findById(userId) == null){
//            throw new IllegalStateException();
//        }
        Invoice invoice = new Invoice(userId, amount, cdnUrl);
        invoices.add(invoice);
        return invoice;
    }

    public List<Invoice> findAll() {
        return invoices;
    }

    public List<Invoice> findByUserId(String userId) {
        var invoicesByUser = new CopyOnWriteArrayList<Invoice>();
        for (Invoice invoice : invoices) {
            if (invoice.hasSameUserId(userId))
                invoicesByUser.add(invoice);
        }
        return invoicesByUser;
    }

}
