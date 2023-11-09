package org.programmers.springorder.voucher.model;

import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private UUID customerId;

    private Voucher(UUID voucherId, long discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    private Voucher(UUID voucherId, long discountValue, VoucherType voucherType, UUID customerId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    private Voucher(UUID voucherId, long discountValue, VoucherType voucherType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private Voucher(UUID voucherId, VoucherRequestDto voucherRequestDto) {
        this.voucherId = voucherId;
        this.discountValue = voucherRequestDto.discountValue();
        this.voucherType = voucherRequestDto.voucherType();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public static Voucher toNewVoucher(UUID voucherId, long discountValue, VoucherType voucherType){
        return new Voucher(voucherId, discountValue, voucherType);
    }

    public static Voucher getFromDbVoucher(UUID voucherId, long discountValue, VoucherType voucherType, UUID customerId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Voucher(voucherId, discountValue, voucherType, customerId, createdAt, updatedAt);
    }
    public static Voucher getFromDbVoucherNoOwner(UUID voucherId, long discountValue, VoucherType voucherType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Voucher(voucherId, discountValue, voucherType, createdAt, updatedAt);
    }

    public static Voucher of(UUID voucherId, VoucherRequestDto requestDto) {
        return new Voucher(voucherId, requestDto);
    }

    public void updateOwner(Customer customer){
        this.customerId = customer.getCustomerId();
    }

    public boolean voucherRange(LocalDateTime startedAt, LocalDateTime endedAt){
        return this.createdAt.isAfter(startedAt) && this.createdAt.isBefore(endedAt);
    }

    public boolean comparingCustomer(UUID customerId){
        if(this.customerId == null) {
            return false;
        }
        return this.customerId.equals(customerId);
    }
    public String insertVoucherDataInFile() {
        StringBuilder data = new StringBuilder();
        data.append(this.voucherId).append(",")
                .append(this.discountValue).append(",")
                .append(this.voucherType.name()).append(",")
                .append(this.createdAt).append(",")
                .append(this.updatedAt);
        if(this.customerId != null){
            data.append(",").append(this.customerId);
        }
        return data.toString();
    }

    public boolean isSameId(UUID comparingId){
        return this.voucherId.equals(comparingId);
    }

    public boolean isSameCustomerId(UUID comparingId){
        if (this.customerId == null) {
            return false;
        }
        return this.customerId.equals(comparingId);
    }

    public long getCalculate(long beforeDiscount) {
        return this.voucherType.calculate(beforeDiscount, this.discountValue);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return discountValue == voucher.discountValue && Objects.equals(voucherId, voucher.voucherId) && voucherType == voucher.voucherType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountValue, voucherType);
    }

}
