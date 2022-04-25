package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.voucher.dto.RequestVoucherDto;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter {
    private final VoucherTypeConverter voucherTypeConverter;

    public MessageConverter(VoucherTypeConverter voucherTypeConverter) {
        this.voucherTypeConverter = voucherTypeConverter;
    }

    public RequestVoucherDto convert(PostRequest postRequest) {
        if (postRequest == null) {
            throw new IllegalArgumentException();
        }

        final var type = voucherTypeConverter.convert(postRequest.type());
        final var value = Integer.parseInt(postRequest.value());
        return new RequestVoucherDto(type, value);
    }
}
