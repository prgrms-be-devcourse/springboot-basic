package com.example.demo.domain.voucher;

import com.example.demo.dto.VoucherDto;
import java.util.UUID;

public interface Voucher {

    double disCount(int beforeAmount);

    VoucherDto convertToVoucherDto();

    UUID getUUID();
}
