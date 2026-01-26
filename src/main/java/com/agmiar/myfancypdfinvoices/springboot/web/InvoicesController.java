package com.agmiar.myfancypdfinvoices.springboot.web;

import com.agmiar.myfancypdfinvoices.springboot.dto.RequestDTO;
import com.agmiar.myfancypdfinvoices.springboot.helper.FluidJson;
import com.agmiar.myfancypdfinvoices.springboot.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

@RestController
public final class InvoicesController {

    private final InvoiceService invoiceService;

    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    public FluidJson findAll() {
        return FluidJson.convertCollectionToJson(
                invoiceService.findAll()
        );
    }

    @GetMapping("/invoices/{userId}")
    public FluidJson findByUserId(
            @PathVariable("userId") String userId) {
        return FluidJson.convertCollectionToJson(
                invoiceService.findByUserId(userId)
        );
    }

    @PostMapping("/invoice")
    public FluidJson create(
            @RequestBody RequestDTO.InvoiceDTO invoiceDTO) {
        return invoiceService.create(
                invoiceDTO.userId(),
                invoiceDTO.amount()
        ).toJson();
    }
}
