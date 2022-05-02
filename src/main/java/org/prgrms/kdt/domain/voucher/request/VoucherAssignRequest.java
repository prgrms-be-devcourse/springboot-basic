package org.prgrms.kdt.domain.voucher.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class VoucherAssignRequest {
    @NotBlank(message = "이메일은 필수입력입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    @Size(max = 50, message = "이메일은 50자 이하로 입력해주세요.")
    private String email;
    @NotNull
    private UUID voucherId;

    public VoucherAssignRequest() {
    }

    public String getEmail() {
        return email;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }
}
