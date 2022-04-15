package org.voucherProject.voucherProject.entity.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CustomerDto {

    private UUID customerId;

    private String customerName;

    private String customerEmail;

    private String password;

    private List<UUID> vouchers = new ArrayList<>();

}
