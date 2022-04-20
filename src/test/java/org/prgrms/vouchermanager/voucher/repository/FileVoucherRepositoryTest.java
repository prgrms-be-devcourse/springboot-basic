package org.prgrms.vouchermanager.voucher.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.exception.IllegalResourceAccessException;
import org.prgrms.vouchermanager.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanager.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanager.voucher.domain.Voucher;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileVoucherRepositoryTest {

    private static final String SAVE_PATH = "src/main/resources/voucher";

    @BeforeAll
    static void makeFolder() {
        File folderPath = new File(SAVE_PATH);
        if (!folderPath.exists()) folderPath.mkdirs();
    }

    @AfterAll
    static void deleteVouchers() {
        File save_path = new File(SAVE_PATH);
        File[] files = save_path.listFiles();
        if (files != null)
            for (File file : files) {
                file.delete();
            }
        save_path.delete();
    }

    @Test
    @DisplayName("insert를 하면 파일로 voucher가 로컬에 저장된다.")
    void insert() {
        //given
        FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(SAVE_PATH);
        Voucher voucher = new FixedAmountVoucher(10L);

        //when
        // insert 실패시, exception 던지도록 작성했으므로 exception이 발생하지 않으면 성공했다고 판단한다?
        Voucher inserted = fileVoucherRepository.insert(voucher);

        //then
        assertEquals(voucher, inserted);
    }

    @Test
    @DisplayName("voucherId로 저장된 voucher를 찾아서 반환한다.")
    void findById() {
        //given
        FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(SAVE_PATH);
        Voucher voucher = new FixedAmountVoucher(10L);


        //when
        fileVoucherRepository.insert(voucher);

        //then
        assertEquals(voucher, fileVoucherRepository.findById(voucher.getVoucherId()).get());
    }

    @Test
    @DisplayName("FileRepository에 저장된 모튼 voucher를 List로 반환한다.")
    void getAll() {
        //given
        FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(SAVE_PATH);
        List<Voucher> mockVouchers = List.of(new FixedAmountVoucher(10L), new PercentDiscountVoucher(10L));

        //when
        mockVouchers.forEach(fileVoucherRepository::insert);
        List<Voucher> findVouchers = fileVoucherRepository.getAll();

        //then
        assertTrue((mockVouchers.size() == findVouchers.size()) && findVouchers.containsAll(mockVouchers) && mockVouchers.containsAll(findVouchers));
    }

    @Test
    @DisplayName("저장할 FilePath가 적절하지 않은 경우 IllgalResourceException을 던진다")
    void testWithInsertException() {
        //given
        FileVoucherRepository fileVoucherRepository = new FileVoucherRepository("Invalid path Test");
        Voucher voucher = new FixedAmountVoucher(10L);

        //then
        assertThrows(IllegalResourceAccessException.class, () -> fileVoucherRepository.insert(voucher));
    }

    @Test
    @DisplayName("삽입하려는 voucher가 이미 존재하는 경우 IllgalArgumentException을 던진다.")
    void testWithDeplicatedInsertion() {
        //given
        FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(SAVE_PATH);
        Voucher voucher = new FixedAmountVoucher(10L);

        //when
        fileVoucherRepository.insert(voucher);

        //then
        assertThrows(IllegalArgumentException.class, () -> fileVoucherRepository.insert(voucher));
    }

    @Test
    @DisplayName("findById의 voucher가 존재하지 않는 경우 Optional.empty를 반환한다.")
    void testWithFindById_NoVoucherID() {
        //given
        FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(SAVE_PATH);

        //then
        assertEquals(Optional.empty(), fileVoucherRepository.findById(UUID.randomUUID()));
    }
}