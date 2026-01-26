package com.agmiar.myfancypdfinvoices.springboot.model;

import com.agmiar.myfancypdfinvoices.springboot.helper.FluidJson;
import com.agmiar.myfancypdfinvoices.springboot.helper.JsonRenderable;

import java.util.UUID;

public final class Invoice implements JsonRenderable {
    private String invoiceId, userId, pdfUrl;
    private Integer amount;

    public Invoice(String id, String userId, Integer amount, String pdfUrl) {
        this.invoiceId = id;
        this.userId = userId;
        this.amount = amount;
        this.pdfUrl = pdfUrl;
    }

    public boolean hasSameUserId(String userId) {
        return this.userId.equals(userId);
    }

    @Override
    public FluidJson toJson() {
        return FluidJson.rootObject()
                .put("id", invoiceId)
                .put("user_id", userId)
                .put("amount", amount)
                .put("pdf_url", pdfUrl)
                .build();
    }
}
