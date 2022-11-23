package org.prgrms.springbootbasic.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringBootTest
@ActiveProfiles("dev")
class FileVoucherRepositoryTest {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    FileVoucherRepository fileVoucherRepository;

    @BeforeEach
    public void cleanup() {
        Resource resource = resourceLoader.getResource("fileVoucherRepository.csv");

        StringBuffer initialState = new StringBuffer();

        initialState.append("FIXED,9f98f1e6-679f-4d41-b0d8-5d6d089832c0,100").append("\n")
                .append("PERCENT,f6eed13d-4344-4d38-8cd5-81e1ebec79e0,10").append("\n")
                .append("PERCENT,66675623-801b-47de-8197-8b502b7d9f08,20");

        try {
            Files.writeString(resource.getFile().toPath(), "", TRUNCATE_EXISTING);
            Files.writeString(resource.getFile().toPath(), initialState, WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("저장 성공")
    void testInsert() {
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 100L);

        fileVoucherRepository.insert(fixedAmountVoucher);

        assertThat(fileVoucherRepository.findById(voucherId).isPresent(), is(true));
        assertThat(fileVoucherRepository.findById(voucherId).get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @DisplayName("모두 조회")
    void testFindAll() {
        List<Voucher> all = fileVoucherRepository.findAll();

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.fromString("9f98f1e6-679f-4d41-b0d8-5d6d089832c0"), 100L);
        PercentAmountVoucher percentAmountVoucher1 = new PercentAmountVoucher(UUID.fromString("f6eed13d-4344-4d38-8cd5-81e1ebec79e0"), 10L);
        PercentAmountVoucher percentAmountVoucher2 = new PercentAmountVoucher(UUID.fromString("66675623-801b-47de-8197-8b502b7d9f08"), 20L);

        assertThat(all.get(0), samePropertyValuesAs(fixedAmountVoucher));
        assertThat(all.get(1), samePropertyValuesAs(percentAmountVoucher1));
        assertThat(all.get(2), samePropertyValuesAs(percentAmountVoucher2));
    }

}