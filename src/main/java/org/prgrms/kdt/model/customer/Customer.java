package org.prgrms.kdt.model.customer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class Customer {
    private UUID customerId;
    private String name;

    public Customer(UUID customerId, String name) {
        checkArgument(customerId != null, "customerId must be provided.");
        checkArgument(isNotEmpty(name), "customerName must be provided.");

        this.customerId = customerId;
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("customerId", customerId)
            .append("name", name)
            .toString();
    }
}
