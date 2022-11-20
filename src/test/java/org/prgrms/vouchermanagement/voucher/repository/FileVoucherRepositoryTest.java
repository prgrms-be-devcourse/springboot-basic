package org.prgrms.vouchermanagement.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class FileVoucherRepositoryTest {

    private final String path = "src/test/resources/test_vouchers.csv";
    FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(path);

    @BeforeEach
    void init() {
        try {
            Files.write(Paths.get(path), "".getBytes());
        } catch (IOException e) {
        }
    }

    @Test
    @DisplayName("바우처 저장 성공")
    void saveVoucher() {
        // given
        Voucher fixedAmountVoucher = createFixedAmountVoucher(1000, UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(50, UUID.randomUUID());

        List<Voucher> vouchers = List.of(fixedAmountVoucher, percentDiscountVoucher);

        // when
        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentDiscountVoucher);

        // then
        List<String> csvFileLines = new ArrayList<>();
        try {
            csvFileLines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
        }

        assertThat(csvFileLines).hasSize(2);
        for (int i = 0; i < csvFileLines.size(); i++) {

            String[] voucherInfos = csvFileLines.get(i).split(",");
            UUID uuid = UUID.fromString(voucherInfos[0]);
            String voucherType = voucherInfos[1];
            int discountAmount = Integer.parseInt(voucherInfos[2]);

            assertThat(uuid).isEqualTo(vouchers.get(i).getVoucherId());
            assertThat(voucherType).isEqualTo(vouchers.get(i).getVoucherType().name());
            assertThat(discountAmount).isEqualTo(vouchers.get(i).getDiscountAmount());
        }
    }

    @Test
    @DisplayName("바우처 id로 바우처 찾기 성공")
    void findById() {

        // given
        Voucher fixedAmountVoucher = createFixedAmountVoucher(1000, UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(50, UUID.randomUUID());

        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentDiscountVoucher);

        // when
        Voucher findFixedAmountVoucher = fileVoucherRepository.findById(fixedAmountVoucher.getVoucherId()).get();
        Voucher findPercentDiscountVoucher = fileVoucherRepository.findById(percentDiscountVoucher.getVoucherId()).get();

        // then
        assertThat(fixedAmountVoucher)
                .usingRecursiveComparison()
                .isEqualTo(findFixedAmountVoucher);

        assertThat(findPercentDiscountVoucher)
                .usingRecursiveComparison()
                .isEqualTo(percentDiscountVoucher);
    }

    @Test
    @DisplayName("바우처 저장소에 찾는 아이디가 없는 경우")
    void findByIdNotExist() {

        // given
        Voucher fixedAmountVoucher = createFixedAmountVoucher(1000, UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(50, UUID.randomUUID());

        fileVoucherRepository.save(fixedAmountVoucher);

        // when
        Optional<Voucher> findFixedAmountVoucher = fileVoucherRepository.findById(percentDiscountVoucher.getVoucherId());

        // then
        assertThat(findFixedAmountVoucher).isEmpty();
    }

    @Test
    @DisplayName("바우처 저장소에 있는 모든 바우처 조회")
    void findAll() {

        // given
        Voucher fixedAmountVoucher = createFixedAmountVoucher(1000, UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(50, UUID.randomUUID());

        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentDiscountVoucher);

        // when
        List<Voucher> allVouchers = fileVoucherRepository.findAll();
        System.out.println(allVouchers);

        // then
        assertThat(allVouchers).hasSize(2);
        assertThat(allVouchers)
                .extracting("voucherId", "discountAmount", "voucherType")
                .contains(tuple(fixedAmountVoucher.getVoucherId(), fixedAmountVoucher.getDiscountAmount(), fixedAmountVoucher.getVoucherType()),
                        tuple(percentDiscountVoucher.getVoucherId(), percentDiscountVoucher.getDiscountAmount(), percentDiscountVoucher.getVoucherType()));
    }

    @Test
    @DisplayName("바우처 저장소에 아무 값도 없는 경우 모든 바우처 조회")
    void findAllWhenRepositoryIsEmpty() {

        // when
        List<Voucher> allVouchers = fileVoucherRepository.findAll();

        // then
        assertThat(allVouchers).isEmpty();
    }

    @Test
    @DisplayName("바우처 저정소 비우기")
    void clearVoucherRepository() {

        // given
        try {
            Files.write(Paths.get(path), "test".getBytes());
        } catch (IOException e) {
        }

        // when
        fileVoucherRepository.clear();

        // then
        List<String> allLines = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
        }

        assertThat(allLines).isEmpty();
    }

    @Test
    @DisplayName("CustomerId로 바우처 리스트 조회")
    void findVouchersByCustomerId() {
        // given
        UUID customerId = UUID.randomUUID();
        Voucher fixedAmountVoucher = createFixedAmountVoucher(1000, customerId);
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(50, customerId);
        Voucher anotherVoucher = createFixedAmountVoucher(500, UUID.randomUUID());

        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentDiscountVoucher);
        fileVoucherRepository.save(anotherVoucher);

        // when
        List<Voucher> vouchers = fileVoucherRepository.findVouchersByCustomerId(customerId);

        // then
        assertThat(vouchers).hasSize(2);
        assertThat(vouchers)
                .extracting("voucherId", "discountAmount", "voucherType", "customerId")
                .contains(tuple(fixedAmountVoucher.getVoucherId(), fixedAmountVoucher.getDiscountAmount(), fixedAmountVoucher.getVoucherType(), fixedAmountVoucher.getCustomerId()),
                        tuple(percentDiscountVoucher.getVoucherId(), percentDiscountVoucher.getDiscountAmount(), percentDiscountVoucher.getVoucherType(), percentDiscountVoucher.getCustomerId()));
    }

    @Test
    @DisplayName("CustomerId로 바우처 삭제")
    void deleteVouchersByCustomerId() {
        // given
        UUID customerId = UUID.randomUUID();
        Voucher fixedAmountVoucher = createFixedAmountVoucher(1000, customerId);
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(50, customerId);
        Voucher anotherVoucher = createFixedAmountVoucher(500, UUID.randomUUID());

        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentDiscountVoucher);
        fileVoucherRepository.save(anotherVoucher);

        // when
        fileVoucherRepository.deleteVoucherByCustomerId(customerId);
        List<Voucher> vouchers = fileVoucherRepository.findAll();

        // then
        assertThat(vouchers).hasSize(1);
        assertThat(vouchers)
                .extracting("voucherId", "discountAmount", "voucherType", "customerId")
                .contains(tuple(anotherVoucher.getVoucherId(), anotherVoucher.getDiscountAmount(), anotherVoucher.getVoucherType(), anotherVoucher.getCustomerId()));
    }

    private Voucher createFixedAmountVoucher(int discountAmount, UUID customerId) {
        return new FixedAmountVoucher(UUID.randomUUID(), discountAmount, customerId);
    }

    private Voucher createPercentDiscountVoucher(int discountAmount, UUID customerId) {
        return new PercentDiscountVoucher(UUID.randomUUID(), discountAmount, customerId);
    }
}