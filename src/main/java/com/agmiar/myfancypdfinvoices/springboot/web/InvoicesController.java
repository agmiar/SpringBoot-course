package com.agmiar.myfancypdfinvoices.springboot.web;

import com.agmiar.myfancypdfinvoices.springboot.dto.RequestDTO;
import com.agmiar.myfancypdfinvoices.springboot.helper.FluidJson;
import com.agmiar.myfancypdfinvoices.springboot.service.InvoiceService;
import org.springframework.http.MediaType;
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

    @PostMapping("/invoice")
    public FluidJson create(
            @RequestBody RequestDTO.InvoiceDTO invoiceDTO){
        return invoiceService.create(
                invoiceDTO.userId(),
                invoiceDTO.amount()
        ).toJson();
    }

    @GetMapping(
            value = "/ping",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public FluidJson ping() {
        return FluidJson.rootObject()
                .put("pong", true);
    }
}
