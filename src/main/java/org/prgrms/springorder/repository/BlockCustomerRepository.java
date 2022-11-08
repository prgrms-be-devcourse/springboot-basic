package org.prgrms.springorder.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springorder.domain.BlockCustomer;

public interface BlockCustomerRepository {

    Optional<BlockCustomer> findById(UUID blockId);

    BlockCustomer insert(BlockCustomer blockCustomer);

    List<BlockCustomer> findAll();

    void deleteAll();

}
