package org.prgrms.springorder.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.FixedAmountVoucher;
import org.prgrms.springorder.domain.PercentDiscountVoucher;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.service.VoucherFactory;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
class FileVoucherRepositoryTest {

    private FileVoucherRepository fileVoucherRepository;

    private final String path = "test/store/";
    private final String fileName = "voucher";
    private final String fileExtension = ".csv";

    @BeforeAll
    public void init() {
        fileVoucherRepository = new FileVoucherRepository(path, fileName,
            fileExtension);
    }

    @BeforeEach
    void after() {
        fileVoucherRepository.deleteAll();
    }

    @DisplayName("조회 테스트 - id로 객체를 정상적으로 찾는다.")
    @Test
    void findByIdSuccess() {
        //given
        UUID randomUUID = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(randomUUID, 50L);

        fileVoucherRepository.insert(voucher);

        //when
        Optional<Voucher> optionalVoucher = fileVoucherRepository.findById(randomUUID);

        //then
        assertTrue(optionalVoucher.isPresent());
        Voucher findVoucher = optionalVoucher.get();
        assertEquals(voucher, findVoucher);
        assertEquals(findVoucher.getVoucherId(), randomUUID);
    }

    @DisplayName("조회 테스트 - id로 객체를 찾지만 없으면 빈 옵셔널을 반환한다. ")
    @Test
    void findByIdReturnEmptyOptional() {
        //given
        UUID randomUUID = UUID.randomUUID();

        //when
        Optional<Voucher> optionalBlockCustomer = fileVoucherRepository.findById(randomUUID);

        //then
        assertTrue(optionalBlockCustomer.isEmpty());
    }

    @DisplayName("insert 테스트 - file 에 정상 저장되고 저장된 BlockCustomer 를 리턴한다.")
    @Test
    void insertTest() throws IOException {
        //given
        UUID randomUUID = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(randomUUID, 50L);

        //when
        Voucher savedVoucher = fileVoucherRepository.insert(voucher);

        //then
        Optional<Voucher> voucherOptional = fileVoucherRepository.findById(randomUUID);
        assertTrue(voucherOptional.isPresent());

        Voucher findVoucher = voucherOptional.get();

        assertNotNull(findVoucher);
        assertEquals(savedVoucher, findVoucher);
    }

    @DisplayName("findAll 테스트 - 저장된 BlockCustomer 가 모두 리턴된다.")
    @Test
    void findAllTest() {
        //given

        int saveCount = 5;
        IntStream.range(0, saveCount).forEach(i -> {
            Voucher voucher;

            if (i % 2 == 0) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), i);
            } else {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), i);
            }

            fileVoucherRepository.insert(voucher);
        });

        //when
        List<Voucher> vouchers = fileVoucherRepository.findAll();
        //then
        assertNotNull(vouchers);
        assertEquals(saveCount, vouchers.size());
    }

    @DisplayName("findAll 테스트 - 저장된 BlockCustomer 가 없다면 빈 리스트가 반환된다.")
    @Test
    void findAllReturnEmptyListTest() {
        //given & when
        List<Voucher> vouchers = fileVoucherRepository.findAll();
        //then
        assertNotNull(vouchers);
        assertTrue(vouchers.isEmpty());
    }

    private List<Voucher> readAll(File file) throws IOException {

        List<Voucher> blockCustomers = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            String line = "";
            while (((line = bufferedReader.readLine()) != null)) {
                String[] split = line.split(",");

                VoucherType voucherType = VoucherType.of(split[0].trim());

                UUID voucherId = UUID.fromString(split[1].trim());

                long amount = Long.parseLong(split[2].trim());

                Voucher voucher = VoucherFactory.toVoucher(voucherType, voucherId, amount);
                blockCustomers.add(voucher);
            }

        }

        return blockCustomers;
    }
}