package devcourse.springbootbasic.dto;


import devcourse.springbootbasic.domain.voucher.Voucher;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherCreateResponse {

    private final UUID id;

    public VoucherCreateResponse(Voucher voucher) {
        this.id = voucher.getId();
    }

    @Override
    public String toString() {
        return """
                id = %s
                """.formatted(id);
    }
}
