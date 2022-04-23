package org.prgrms.kdt.model.customer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.prgrms.kdt.model.customer.CustomerGrade.NORMAL;

public class Customer {
    private UUID customerId;
    private String name;
    private CustomerGrade customerGrade;

    public Customer(UUID customerId, String name, CustomerGrade customerGrade) {
        checkArgument(customerId != null, "customerId must be provided.");
        checkArgument(isNotEmpty(name), "customerName must be provided.");

        this.customerId = customerId;
        this.name = name;
        this.customerGrade = defaultIfNull(customerGrade, NORMAL);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public CustomerGrade getCustomerGrade() {
        return customerGrade;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("customerId", customerId)
            .append("name", name)
            .append("customerGrade", customerGrade)
            .toString();
    }
}
