package org.prgms.voucher.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@Builder
@RequiredArgsConstructor
@Getter
public class BlackCustomerResponseDto {
    private final long id;
    private final String name;

    @Override
    public String toString() {
        return MessageFormat.format("id: {0}, name: {1}", id, name);
    }
}
