package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.voucher.dto.VoucherDto;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter {
    private final VoucherTypeConverter voucherTypeConverter;

    public MessageConverter(VoucherTypeConverter voucherTypeConverter) {
        this.voucherTypeConverter = voucherTypeConverter;
    }

    public VoucherDto convert(RequestVoucherMessage requestMessage) {
        if (requestMessage == null) {
            throw new IllegalArgumentException();
        }

        final var type = voucherTypeConverter.convert(requestMessage.type());
        final var value = Integer.parseInt(requestMessage.value());
        return new VoucherDto(type, value);
    }
}
