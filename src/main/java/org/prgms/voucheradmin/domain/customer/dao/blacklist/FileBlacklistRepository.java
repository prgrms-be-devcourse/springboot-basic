package org.prgms.voucheradmin.domain.customer.dao.blacklist;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.customer.entity.BlackListCustomer;
import org.prgms.voucheradmin.global.properties.VoucherAdminProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * customer_blacklist.csv에 저장된 블랙리스트 고객들을 반환하는 클래스 입니다.
 */
@Repository
public class FileBlacklistRepository implements BlackListRepository {
    private final VoucherAdminProperties voucherAdminProperties;
    private final ResourceLoader resourceLoader;

    public FileBlacklistRepository(VoucherAdminProperties voucherAdminProperties, ResourceLoader resourceLoader) {
        this.voucherAdminProperties = voucherAdminProperties;
        this.resourceLoader = resourceLoader;
    }

    /**
     * customer_blacklist.csv에 저장된 블랙리스트 고객들을 entity에 매핑하고 반환하는 메서드입니다.
     */
    @Override
    public List<BlackListCustomer> getAll() throws IOException {
        Resource resource = resourceLoader.getResource(voucherAdminProperties.getBlacklistFilePath());
        Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8);
        String[] records = FileCopyUtils.copyToString(reader).split("\n");

        List<BlackListCustomer> blackListCustomers = new ArrayList<>();
        for(String record : records) {
            String[] columns = record.split(",");
            blackListCustomers.add(new BlackListCustomer(UUID.fromString(columns[0]), columns[1]));
        }

        return blackListCustomers;
    }
}
