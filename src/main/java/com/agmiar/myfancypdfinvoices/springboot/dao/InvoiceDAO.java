package com.agmiar.myfancypdfinvoices.springboot.dao;

import com.agmiar.myfancypdfinvoices.springboot.model.Invoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Component
public class InvoiceDAO {
    private final JdbcTemplate jdbcTemplate;
    private String cdnUrl;

    // constructor injection
    public InvoiceDAO(JdbcTemplate templ, @Value("${cdn.url}") String cdnUrl){
        this.jdbcTemplate = templ;
        this.cdnUrl = cdnUrl;
    }

    @Transactional
    public List<Invoice> findAll() {
        System.out.println(
                "¿Hay una transacción de BBDD abierta? "
                        + TransactionSynchronizationManager.isActualTransactionActive()
        );

        return jdbcTemplate.query(
                "select id, user_id, pdf_url, amount from invoices",
                (resultSet, rowNum) -> {
                    Invoice invoice = new Invoice(
                        resultSet.getObject("id").toString(),
                        resultSet.getString("user_id"),
                            resultSet.getInt("amount"),
                        resultSet.getString("pdf_url")
                    );
                    return invoice;
                }
        );
    }

    @Transactional
    public Invoice create(String userId, Integer amount){
        System.out.println(
                "¿Hay una transacción de BBDD abierta? "
                        + TransactionSynchronizationManager.isActualTransactionActive()
        );

        String generatedPdfUrl = cdnUrl +
                "/images/default/sample.pdf";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into invoices (user_id, pdf_url, amount) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, userId);
            ps.setString(2, generatedPdfUrl);
            ps.setInt(3, amount);
            return ps;
            }, keyHolder
        );

        // devuelve el UUID o null si no se pudo insertar
        String uuid = !keyHolder.getKeys().isEmpty() ?
                ((UUID) keyHolder.getKeys().values()
                        .iterator().next()
                ).toString()
                : null;

        Invoice invoice = new Invoice(
                uuid, userId, amount, generatedPdfUrl);

        return invoice;
    }
}
