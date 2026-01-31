package com.agmiar.myfancypdfinvoices.springboot.model;

import com.agmiar.myfancypdfinvoices.springboot.helper.FluidJson;
import com.agmiar.myfancypdfinvoices.springboot.helper.JsonRenderable;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "invoices")
public final class Invoice implements JsonRenderable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "pdf_url")
    private String pdfUrl;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "amount")
    private Integer amount;

    // requerido por JPA
    protected Invoice() {}

    public Invoice(UUID id, String pdfUrl, String userId, Integer amount) {
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
