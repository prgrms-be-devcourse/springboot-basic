package com.example.voucherproject.voucher.dto;

import com.example.voucherproject.voucher.model.VoucherType;
import org.springframework.web.bind.annotation.RequestParam;

public record VoucherQueryDTO (
        VoucherType type, String from, String to
){
}
