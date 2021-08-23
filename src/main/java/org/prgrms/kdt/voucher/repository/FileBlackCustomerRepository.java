package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.blackCustomer.domain.BlackCustomer;
import org.prgrms.kdt.util.BlackCustomerFileManager;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileBlackCustomerRepository {

    private final BlackCustomerFileManager blackCustomerFileManager;
    private final ConcurrentHashMap<UUID, BlackCustomer> blackUserMap = new ConcurrentHashMap<>();
    private static final String FILE_PATH = "./data/black_customer.csv";

    public FileBlackCustomerRepository(BlackCustomerFileManager blackCustomerFileManager) {
        this.blackCustomerFileManager = blackCustomerFileManager;
    }

    public List<BlackCustomer> findAll() {
        return new ArrayList<>(blackUserMap.values());
    }

    @PostConstruct
    public void postConstruct() {
        blackUserMap.putAll(blackCustomerFileManager.fileToMemory(FILE_PATH));
    }

}
