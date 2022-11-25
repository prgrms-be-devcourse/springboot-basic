//package com.programmers.voucher.repository;
//
//import com.programmers.voucher.voucher.Voucher;
//import com.programmers.voucher.voucher.VoucherType;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Profile;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static com.programmers.voucher.voucher.VoucherFactory.createVoucher;
//import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
//import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//@Profile("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class FileVoucherRepositoryTest {
//
//    @Autowired
//    VoucherRepository repository;
//
//    @AfterAll
//    void setup() {
//        repository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("바우처가 성공적으로 저장된다.")
//    void 저장_테스트() {
//        UUID id = UUID.randomUUID();
//        VoucherType type = FixedAmount;
//        Voucher voucher = createVoucher(id, type, 2000);
//
//        Assertions.assertDoesNotThrow(() -> repository.registerVoucher(voucher));
//    }
//
//    @Test
//    @DisplayName("id로 바우처를 성공적으로 찾는다.")
//    void 조회_테스트() {
//        UUID id = UUID.randomUUID();
//        VoucherType type = FixedAmount;
//        Voucher voucher = createVoucher(id, type, 2000);
//
//        repository.registerVoucher(voucher);
//
//        Optional<Voucher> findOne = repository.findById(id);
//        assertTrue(findOne.isPresent());
//
//        assertEquals(voucher, findOne.get());
//    }
//
//    @Test
//    @DisplayName("바우처를 저장한 뒤 전체 조회 결과의 size는 저장 횟수와 일치해야 한다.")
//    void 모든_바우처_조회() {
//        repository.deleteAll();
//
//        List<Voucher> allVouchers = repository.findAllVouchers();
//        assertThat(allVouchers.size()).isEqualTo(0);
//
//        Voucher voucher1 = createVoucher(FixedAmount, 5000);
//        Voucher voucher2 = createVoucher(PercentDiscount, 5);
//
//        repository.registerVoucher(voucher1);
//        repository.registerVoucher(voucher2);
//
//        allVouchers = repository.findAllVouchers();
//        assertThat(allVouchers.size()).isEqualTo(2);
//        assertThat(allVouchers).contains(voucher1, voucher2);
//    }
//}
