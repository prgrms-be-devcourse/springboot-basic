package devcourse.springbootbasic.dto.voucher;


import devcourse.springbootbasic.domain.voucher.Voucher;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherResponse {

    private final UUID id;

    public VoucherResponse(Voucher voucher) {
        this.id = voucher.getId();
    }

    @Override
    public String toString() {
        return """
                id = %s
                """.formatted(id);
    }
}
