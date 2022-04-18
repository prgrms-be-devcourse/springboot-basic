package org.prgrms.vouchermanager.blocklist;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BlockCustomerRepository {

    Optional<BlockCustomer> findById(UUID blockCustomerId) {
        return Optional.empty();
    }
}


