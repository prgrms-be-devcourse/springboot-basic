package team.marco.vouchermanagementsystem.repository.custromer;

import java.util.List;

public interface BlacklistRepository {
    List<CustomerIdAndName> findAll();
}
