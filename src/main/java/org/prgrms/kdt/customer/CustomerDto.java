package org.prgrms.kdt.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import org.prgrms.kdt.voucher.VoucherDto;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 12:46 오후
 */
public class CustomerDto {

    private String customerId;

    private String name;

    private String email;

    private String createdAt;

    private String lastLoginAt;

    private String customerType;

    @JsonProperty("vouchers")
    private List<VoucherDto> voucherDtos;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public List<VoucherDto> getVoucherDtos() {
        return voucherDtos;
    }

    public void setVoucherDtos(List<VoucherDto> voucherDtos) {
        this.voucherDtos = voucherDtos;
    }
}
