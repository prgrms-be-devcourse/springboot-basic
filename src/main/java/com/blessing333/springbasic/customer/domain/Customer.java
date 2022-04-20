package com.blessing333.springbasic.customer.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "customerId")
@Builder(builderClassName = "Builder",builderMethodName = "customerBuilder")
@Setter(AccessLevel.PRIVATE)
public class Customer {
    private UUID customerId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public Customer(UUID id,String name,String email,LocalDateTime createdAt,LocalDateTime lastLoginAt) throws IllegalArgumentException {
        validateName(name);
        this.customerId = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    private static void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name should not be blank");
        }
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void changeLastLoginDate(LocalDateTime time) {
        this.lastLoginAt = time;
    }

    public void changeEmail(String email){
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String createDate = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String lastLogin = this.lastLoginAt == null ? "이력 없음" : this.lastLoginAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        sb.append("고객 ID : ").append(this.customerId)
                .append("\n이름 : ").append(this.name)
                .append("\n이메일 : ").append(this.email)
                .append("\n생성 날짜 : ").append(createDate)
                .append("\n최종 로그인 날짜 : " ).append(lastLogin);

        return sb.toString();
    }

    public void generateCustomerId(){
        this.customerId = UUID.randomUUID();
    }

    public void renewCreatedDate(){
        this.createdAt = LocalDateTime.now();
    }
}
