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

    // implementación con Pageable
    @GetMapping("/invoices")
    public FluidJson getUsers(
            @RequestParam(required = false) String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        var pageableInvoices = invoiceService.getPaginatedInvoices(
                userId,page, size, sortBy
        );
        return FluidJson.convertPageToJson(pageableInvoices);
    }

    @PostMapping("/invoice")
    public FluidJson create(
            @RequestBody RequestDTO.InvoiceDTO invoiceDTO) {
        return invoiceService.create(
                invoiceDTO.pdfResource(),
                invoiceDTO.userId(),
                invoiceDTO.amount()
        ).toJson();
    }

    //    @GetMapping("/invoices")
//    public FluidJson findAll() {
//        return FluidJson.convertCollectionToJson(
//                invoiceService.findAll()
//        );
//    }

    //    @GetMapping("/invoices/{userId}")
//    public FluidJson findByUserId(
//            @PathVariable("userId") String userId) {
//        return FluidJson.convertCollectionToJson(
//                invoiceService.findByUserId(userId)
//        );
//    }
}
