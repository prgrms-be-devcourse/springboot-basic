package com.programmers.vouchermanagement.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.programmers.vouchermanagement.voucher.controller.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

public class MvcControllerResource {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static final List<VoucherResponse> VOUCHERS = List.of(
            VoucherResponse.from(new Voucher("FIXED", 3000)),
            VoucherResponse.from(new Voucher("PERCENT", 10))
    );
    public static final List<VoucherResponse> FIXED_AMOUNT_VOUCHERS = List.of(
            VoucherResponse.from(new Voucher("FIXED", 3000)),
            VoucherResponse.from(new Voucher("FIXED", 3000))
    );
    public static final List<VoucherResponse> VOUCHERS_CREATED_NOW = List.of(
            VoucherResponse.from(new Voucher("FIXED", 3000)),
            VoucherResponse.from(new Voucher("PERCENT", 50))
    );
    public static final CreateVoucherRequest CREATE_VOUCHER_REQUEST = new CreateVoucherRequest("FIXED", 5000);

    public static final Voucher VOUCHER = new Voucher(CREATE_VOUCHER_REQUEST.typeName(), CREATE_VOUCHER_REQUEST.discountValue());

    public static final UUID VOUCHER_ID = VOUCHER.getId();
    public static final VoucherResponse VOUCHER_RESPONSE = VoucherResponse.from(VOUCHER);
}
