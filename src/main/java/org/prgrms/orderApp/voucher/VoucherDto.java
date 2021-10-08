package org.prgrms.orderApp.voucher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class VoucherDto {
    private UUID voucherId;
    private long amount ;
    private String available, voucherType, name;
    private LocalDate expireAt, createdAt;
    private int customerCount;

    private static Logger logger = LoggerFactory.getLogger(VoucherDto.class);


    public VoucherDto(long amount, String voucherType, String name,  LocalDate expireAt){
        this.amount = amount;
        this.voucherType = voucherType;
        this.name = name;
        this.expireAt = expireAt;
    }

    public VoucherDto(UUID voucherId, long amount, String available, String voucherType, String name,  LocalDateTime expireAt, LocalDateTime createdAt, int customerCount){
        this.voucherId = voucherId;
        this.amount = amount;
        this.available = available;
        this.voucherType = voucherType;
        this.name = name;
        this.expireAt = expireAt.toLocalDate();
        this.createdAt = createdAt.toLocalDate();
        this.customerCount = customerCount;
    }


    // setter test 하기
    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = LocalDate.parse(expireAt, DateTimeFormatter.ISO_DATE);;
    }
}
