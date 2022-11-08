package org.prgrms.springorder.service;

import java.util.List;
import org.prgrms.springorder.domain.BlockCustomer;
import org.prgrms.springorder.repository.BlockCustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class BlockCustomerService {

    private final BlockCustomerRepository blockCustomerRepository;

    public BlockCustomerService(
        BlockCustomerRepository blockCustomerRepository) {
        this.blockCustomerRepository = blockCustomerRepository;
    }

    public List<BlockCustomer> findAll() {
        return blockCustomerRepository.findAll();
    }

}
