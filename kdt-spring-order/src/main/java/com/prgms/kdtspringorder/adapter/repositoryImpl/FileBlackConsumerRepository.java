package com.prgms.kdtspringorder.adapter.repositoryImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prgms.kdtspringorder.config.PathProperties;
import com.prgms.kdtspringorder.domain.model.customer.Customer;
import com.prgms.kdtspringorder.domain.model.customer.CustomerRepository;

@Repository
public class FileBlackConsumerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileBlackConsumerRepository.class);
    private static final String COMMA = ",";
    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();
    private final File file;

    public FileBlackConsumerRepository(PathProperties pathProperties) {
        String filepath = System.getProperty("user.dir") + pathProperties.getCustomerBlacklist();
        file = new File(filepath);
    }

    @Override
    public Map<UUID, Customer> findAll() {
        return storage;
    }

    @PostConstruct
    private void postConstruct() {
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] voucherInfo = line.split(COMMA);
                UUID id = UUID.fromString(voucherInfo[0]);
                String name = voucherInfo[1];
                int age = Integer.parseInt(voucherInfo[2]);

                storage.put(id, new Customer(id, name, age));
            }
        } catch (IOException e) {
            logger.error("customer blacklist 로드 실패");
            e.printStackTrace();
        }
    }
}
