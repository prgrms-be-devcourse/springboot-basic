package com.dojinyou.devcourse.voucherapplication.customer.domain;

import com.dojinyou.devcourse.voucherapplication.customer.dto.CustomerCreateRequest;
import com.dojinyou.devcourse.voucherapplication.customer.dto.CustomerDefaultResponse;
import com.dojinyou.devcourse.voucherapplication.customer.dto.CustomerUpdateRequest;
import com.dojinyou.devcourse.voucherapplication.customer.entity.CustomerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Customer {
    private final long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Customer(long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Customer from(CustomerCreateRequest createRequest) {
        return new Customer(0, createRequest.getName(), null, null);
    }

    public static Customer from(CustomerUpdateRequest updateRequest) {
        return new Customer(updateRequest.getId(), updateRequest.getName(), null, null);
    }

    public static Customer from(CustomerEntity entity) {
        return new Customer(entity.getId(), entity.getName(),
                            entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static Customer from(ResultSet rs) throws SQLException {
        return new Customer(rs.getLong("id"),
                            rs.getString("name"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at").toLocalDateTime());
    }

    public CustomerEntity toEntity() {
        return new CustomerEntity(id, name, createdAt, updatedAt);
    }

    public CustomerDefaultResponse toDefaultResponse() {
        return new CustomerDefaultResponse(id, name, createdAt, updatedAt);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
