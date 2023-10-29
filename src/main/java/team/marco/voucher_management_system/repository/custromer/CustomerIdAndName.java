package team.marco.voucher_management_system.repository.custromer;

import java.util.UUID;

public class CustomerIdAndName {
    private final UUID id;
    private final String name;

    public CustomerIdAndName(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
