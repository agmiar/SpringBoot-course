package com.agmiar.myfancypdfinvoices.springboot.model;

import com.agmiar.myfancypdfinvoices.springboot.helper.FluidJson;
import com.agmiar.myfancypdfinvoices.springboot.helper.JsonRenderable;
import jakarta.persistence.*;

@Entity
@Table(name = "INVOICES")
public final class Invoice implements JsonRenderable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "PDF_URL")
    private String pdfUrl;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "AMOUNT")
    private Integer amount;

    // requerido por JPA
    protected Invoice() {}

    public Invoice(String id, String pdfUrl, String userId, Integer amount) {
        this.id = id;
        this.pdfUrl = pdfUrl;
        this.userId = userId;
        this.amount = amount;
    }

    // factory de dominio
    public static Invoice create(String pdfUrl, String userId, Integer amount) {
        return new Invoice(
                null, // autocompletado por la DB
                pdfUrl,
                userId,
                amount
        );
    }

    public boolean hasSameUserId(String userId) {
        return this.userId.equals(userId);
    }

    @Override
    public FluidJson toJson() {
        return FluidJson.rootObject()
                .put("id", id)
                .put("user_id", userId)
                .put("amount", amount)
                .put("pdf_url", pdfUrl)
                .build();
    }
}
