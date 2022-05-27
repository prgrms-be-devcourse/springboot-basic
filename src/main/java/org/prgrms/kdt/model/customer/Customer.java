package org.prgrms.kdt.model.customer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.prgrms.kdt.model.customer.CustomerType.NORMAL;

public class Customer {
    private UUID id;
    private String name;
    private CustomerType type;

    public Customer(UUID id, String name, CustomerType type) {
        checkArgument(id != null, "customerId must be provided.");
        checkArgument(isNotEmpty(name), "customerName must be provided.");

        this.id = id;
        this.name = name;
        this.type = defaultIfNull(type, NORMAL);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomerType getType() {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("customerId", id)
            .append("name", name)
            .append("customerType", type)
            .toString();
    }
}
