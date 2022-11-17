package org.prgrms.springorder.domain.customer.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.domain.customer.repository.BlockCustomerRepository;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BlockCustomerService {

    private final BlockCustomerRepository blockCustomerRepository;

    public BlockCustomerService(
        BlockCustomerRepository blockCustomerRepository) {
        this.blockCustomerRepository = blockCustomerRepository;
    }

    public List<BlockCustomer> findAll() {
        List<BlockCustomer> blockCustomers = blockCustomerRepository.findAll();

        if (blockCustomers.isEmpty()) {
            throw new EntityNotFoundException(BlockCustomer.class, "all");
        }

        return blockCustomers;
    }

    public List<String> findAllConvertedToString() {
        return this.findAll().stream()
            .map(Objects::toString)
            .collect(Collectors.toList());
    }

}
