package team.marco.voucher_management_system.repository.custromer;

import java.util.List;

public interface BlacklistRepository {
    List<CustomerIdAndName> findAll();
}
