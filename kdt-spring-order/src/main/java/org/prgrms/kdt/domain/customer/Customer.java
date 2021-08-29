package org.prgrms.kdt.domain.customer;

import java.text.MessageFormat;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;
    private final boolean isBlacklisted;

    public Customer(UUID id, String name, boolean isBlacklisted) {
        this.id = id;
        this.name = name;
        this.isBlacklisted = isBlacklisted;
    }

    @Override
    public String toString() {
        String formatString = "id : {0}, name : {1}";

        if(isBlacklisted){
            return MessageFormat.format("[BlackList] " + formatString, id, name);
        }

        return MessageFormat.format(formatString, id, name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }
}
