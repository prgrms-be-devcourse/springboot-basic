package devcourse.springbootbasic.dto.wallet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherAssignRequest {

    private final UUID customerId;

    @JsonCreator
    public VoucherAssignRequest(@JsonProperty("customerId") UUID customerId) {
        this.customerId = customerId;
    }
}
