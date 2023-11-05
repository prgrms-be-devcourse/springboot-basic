package team.marco.voucher_management_system.repository.custromer;

import team.marco.voucher_management_system.controller.customer.dto.CustomerIdAndName;

import java.util.List;

public interface BlacklistRepository {
    List<CustomerIdAndName> findAll();
}
