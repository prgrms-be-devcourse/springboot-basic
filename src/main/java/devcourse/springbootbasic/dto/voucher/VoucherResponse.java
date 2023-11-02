package devcourse.springbootbasic.dto.voucher;


import devcourse.springbootbasic.domain.voucher.Voucher;
import lombok.Getter;

@Getter
public class VoucherResponse {

    private final String id;

    public VoucherResponse(Voucher voucher) {
        this.id = voucher.getId().toString();
    }

    @Override
    public String toString() {
        return """
                id = %s
                """.formatted(id);
    }
}
