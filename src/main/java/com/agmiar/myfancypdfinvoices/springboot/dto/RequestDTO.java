package com.agmiar.myfancypdfinvoices.springboot.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RequestDTO {
    public record InvoiceDTO(
            @NotBlank String pdfResource,
            @NotBlank String userId,
            @Min(10) @Max(50) Integer amount
    ){ }

}