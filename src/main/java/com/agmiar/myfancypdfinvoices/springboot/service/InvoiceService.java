package com.agmiar.myfancypdfinvoices.springboot.service;

import com.agmiar.myfancypdfinvoices.springboot.dao.InvoiceDAO;
import com.agmiar.myfancypdfinvoices.springboot.model.Invoice;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public final class InvoiceService {

    private UserService userService;
    private InvoiceDAO invoiceDAO;
    private List<Invoice> invoices;

    public InvoiceService(UserService userService, InvoiceDAO invoiceDAO) {
        this.userService = userService;
        this.invoiceDAO = invoiceDAO;
    }

    @PostConstruct
    public void init() {
        System.out.println("Fetching PDF Template from S3...");
        // TODO download from s3 and save locally
        invoices = invoiceDAO.findAll();
    }



    public Invoice create(String userId, Integer amount) {
        // TODO real pdf creation and storing it on network server
//        if (userService.findById(userId) == null){
//            throw new IllegalStateException();
//        }
        var invoice = invoiceDAO.create(userId, amount);
        invoices.add(invoice);
        return invoice;
    }

    public List<Invoice> findAll() {
        return invoiceDAO.findAll();
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
