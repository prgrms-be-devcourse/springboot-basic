package org.prgrms.spring_week1.repositories;

import org.prgrms.spring_week1.models.Voucher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("dev")
public class VoucherFileRepository implements VoucherRepository {
    private ResourceLoader resourceLoader;
    private Resource resource = resourceLoader.getResource("vouchers.txt");

    public VoucherFileRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void insert(Voucher voucher) {

    }


    @Override
    public void showAll() {


    }
}
