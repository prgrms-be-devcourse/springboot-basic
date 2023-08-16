package com.prgrms.voucher.controller.mapper;

import com.prgrms.voucher.controller.dto.VoucherListRequest;
import com.prgrms.voucher.controller.dto.VoucherCreateRequest;
import com.prgrms.voucher.service.dto.VoucherServiceCreateRequest;
import com.prgrms.voucher.service.dto.VoucherServiceListRequest;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class VoucherControllerConverter {

    private final DateTimeConverter dateTimeConverter;

    public VoucherControllerConverter(DateTimeConverter dateTimeConverter) {
        this.dateTimeConverter = dateTimeConverter;
    }

    public VoucherServiceListRequest ofVoucherServiceListRequest (
            VoucherListRequest voucherListRequest) {

        LocalDateTime createdAt = dateTimeConverter.parseToDate(
                voucherListRequest.startCreatedAt());

        return new VoucherServiceListRequest(voucherListRequest.voucherType(), createdAt);
    }

    public VoucherServiceCreateRequest ofVoucherServiceCreateRequest ( VoucherCreateRequest voucherCreateRequest) {
        return new VoucherServiceCreateRequest(voucherCreateRequest.voucherType(), voucherCreateRequest.discountAmount());
    }

}
