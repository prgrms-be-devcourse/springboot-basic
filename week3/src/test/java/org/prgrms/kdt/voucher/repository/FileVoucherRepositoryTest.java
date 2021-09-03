package org.prgrms.kdt.voucher.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@Profile("local")
class FileVoucherRepositoryTest {

    private Map<UUID, Voucher> memory;
    private static final String PATH = "src/main/resources/static/";
    private static final String FILENAME = "voucher.csv";

    static FixedAmountVoucher fixedAmountVoucher;

    static FileVoucherRepository voucherRepository;

    @BeforeAll
    static void setUp() {
        voucherRepository = new FileVoucherRepository();
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
    }

    @Test
    @DisplayName("File Voucher 를 저장할 때 (postConstructor 가 동작하지 않으면) 메모리로 임시 관리한다.")
    void testSetFixedAmountVoucherInsert() throws IOException {
        //given
        voucherRepository.insert(fixedAmountVoucher);

        //when
        var retrievedVouchers = voucherRepository.findAll();

        //then (임시관리되었기 때문에 file 에서 정보를 읽어오지 않는다.)
        assertThat(retrievedVouchers.size(), is(not(0)));
        assertThat(retrievedVouchers.size(), is(1));
    }

    @Test
    @DisplayName("file voucher에서 아이디로 찾는 값이 없으면 Optional 빈 객체를 내보낸다. (따라서 NotNull)")
    void testFindByIdNullCheck() {
        //given
        //when
        var retrievedVoucher = voucherRepository.findById(fixedAmountVoucher.getVoucherId());
        var voucher = retrievedVoucher.isPresent();

        //then
        assertThat(retrievedVoucher, is(not(nullValue())));
        assertThat(voucher, is(false));
    }

    @Test
    @DisplayName("insert and findById Test")
    void testInsertAndFindById() throws IOException {
        voucherRepository.insert(fixedAmountVoucher);

        var retrievedVoucher = voucherRepository.findById(fixedAmountVoucher.getVoucherId());
        var voucher = retrievedVoucher.orElseThrow(NullPointerException::new);

        assertThat(retrievedVoucher, is(not(sameInstance(fixedAmountVoucher))));
        assertThat(voucher.getClass(), is(sameInstance(fixedAmountVoucher.getClass())));
    }

    @Test
    @DisplayName("file 에서 메모리로 정보를 가져온다.")
    void testFileToMem() {
        voucherRepository.fileToMem();
        var retrievedVoucher = voucherRepository.findAll();

        assertThat(retrievedVoucher.size(), is(not(0)));
    }

    @Test
    @DisplayName("메모리 -> 파일 -> 메모리 -> 저장됨?")
    void testMemToFile() throws IOException {
        //memory store
        voucherRepository.insert(fixedAmountVoucher);
        //mem -> file
        voucherRepository.MemToFile();
        voucherRepository.fileToMem();

        var retrievedVoucher = voucherRepository.findAll();

        //처음에 메모리에만 들어가진 1의 크기가 아니여야됨
        assertThat(retrievedVoucher.size(), is(not(1)));
    }

}