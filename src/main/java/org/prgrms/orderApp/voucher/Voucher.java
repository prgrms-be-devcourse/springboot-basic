package org.prgrms.orderApp.voucher;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Voucher {

    private UUID voucherId;
    private long amount;
    private String available, voucherType, name;
    private LocalDateTime expireAt, createdAt;
    private int customerCount;

    public Voucher(UUID voucherId){
        this.voucherId = voucherId;
    }

    // save에만 사용. 아래의 생성자만 특별하게 저장할 때 사용하면, 추후 본 코드를 사용할 개발자분들이 불편해 질 것 같다.
    public Voucher(String name,
                   long amount,
                   String voucherType,
                   LocalDate expireAt){
        this.voucherId = UUID.randomUUID(); // 밖에서 UUID를 생성하지 못하게 하였습니다.
        this.name = name.trim().replace(" ","");
        this.amount = amount;
        this.voucherType = voucherType;
        this.expireAt = expireAt.atTime(23,59, 00, 000000);
        this.available = "T";
    }

    public  Voucher(UUID voucherId,
                    long amount,
                    String available,
                    String voucherType,
                    String name,
                    LocalDateTime expireAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.available = available.trim();
        this.voucherType = voucherType;
        this.name = name.trim().replace(" ","");;
        this.expireAt = expireAt;

    }

    public  Voucher(UUID voucherId,
                   long amount,
                   String available,
                   String voucherType,
                   String name,
                   LocalDateTime expireAt,
                   LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.available = available.trim();
        this.voucherType = voucherType;
        this.name = name.trim().replace(" ","");;
        this.expireAt = expireAt;
        this.createdAt = createdAt;

    }
    public  Voucher(UUID voucherId,
                    long amount,
                    String available,
                    String voucherType,
                    String name,
                    LocalDateTime expireAt,
                    LocalDateTime createdAt,
                    int customerCount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.available = available.trim();
        this.voucherType = voucherType;
        this.name = name.trim().replace(" ","");;
        this.expireAt = expireAt;
        this.createdAt = createdAt;
        this.customerCount = customerCount;

    }

    public boolean checkPercentVoucherOver100(){
        if ( voucherType.equals("Percent") && amount>100) return true;
        return false;
    }
}
