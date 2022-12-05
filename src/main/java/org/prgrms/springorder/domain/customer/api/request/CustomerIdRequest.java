package org.prgrms.springorder.domain.customer.api.request;

import java.util.UUID;
import lombok.Getter;
import org.prgrms.springorder.console.io.Request;
import org.prgrms.springorder.util.UUIDValidator;

@Getter
public class CustomerIdRequest implements Request {

    private final UUID customerId;

    public CustomerIdRequest(String customerId) {
        UUIDValidator.validateString(customerId);

        this.customerId = UUID.fromString(customerId);
    }

}
