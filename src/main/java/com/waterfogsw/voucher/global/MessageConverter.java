package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.voucher.dto.VoucherDto;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter {
    private final VoucherTypeConverter voucherTypeConverter;

    public MessageConverter(VoucherTypeConverter voucherTypeConverter) {
        this.voucherTypeConverter = voucherTypeConverter;
    }

    public VoucherDto convert(RequestMessage requestMessage) {
        if (requestMessage == null) {
            throw new IllegalArgumentException();
        }

        var type = voucherTypeConverter.convert(requestMessage.type());
        var value = Integer.parseInt(requestMessage.value());
        return new VoucherDto(type, value);
    }
}
