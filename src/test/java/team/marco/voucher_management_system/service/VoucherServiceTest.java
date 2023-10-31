package team.marco.voucher_management_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.domain.voucher.FixedAmountVoucher;
import team.marco.voucher_management_system.domain.voucher.PercentDiscountVoucher;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.repository.voucher.MemoryVoucherRepository;
import team.marco.voucher_management_system.repository.voucher.VoucherRepository;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherServiceTest {
    private VoucherService voucherService;
    private VoucherRepository voucherRepository;

    @BeforeEach
    @DisplayName("각 테스트는 빈 메모리 저장소를 가지고 테스트를 시작합니다.")
    void VoucherService_초기화() {
        voucherRepository = new MemoryVoucherRepository();
        voucherService = new VoucherService(voucherRepository);
    }

    @Test
    void 고정_할인_쿠폰_생성() {
        // 할인 금액이 주어 졌을 때
        int amount = 1000;

        // 고정 금액 쿠폰 생성 요청
        Voucher returned = voucherService.createFixedAmountVoucher(amount);

        // 1. 생성된 쿠폰이 FixedAmountVoucher 타입
        assertThat(returned).isInstanceOf(FixedAmountVoucher.class);
        // 2. 생성된 쿠폰의 할인 금액이 amount와 동일
        FixedAmountVoucher voucher = (FixedAmountVoucher) returned;
        assertThat(voucher.getAmount()).isEqualTo(amount);
    }

    @Test
    void 퍼센트_할인_쿠폰_생성() {
        // 할인율이 주어 졌을 때
        int percent = 10;

        // % 금액 쿠폰 생성 요청
        Voucher returned = voucherService.createPercentDiscountVoucher(percent);

        // 1. 생성된 쿠폰이 PercentDiscountVoucher 타입
        assertThat(returned).isInstanceOf(PercentDiscountVoucher.class);
        // 2. 생성된 쿠폰의 할인율이 percent와 동일
        PercentDiscountVoucher voucher = (PercentDiscountVoucher) returned;
        assertThat(voucher.getPercent()).isEqualTo(percent);
    }

    @Test
    void 전체_쿠폰_목록_조회() {
        // 리포지토리에 쿠폰 2개가 저장되어 있을 때
        Voucher voucher = new FixedAmountVoucher(1000);
        Voucher voucher2 = new PercentDiscountVoucher(10);
        voucherRepository.save(voucher);
        voucherRepository.save(voucher2);

        // 전체 쿠폰 목록 요청
        List<Voucher> returned = voucherService.getVouchers();

        // 쿠폰 목록의 크기가 2
        assertThat(returned).hasSize(2);
    }

    @Test
    void 쿠폰_목록_조회() {
        // 리포지토리에 쿠폰 2개가 저장되어 있을 때
        Voucher voucher = new FixedAmountVoucher(1000);
        Voucher voucher2 = new PercentDiscountVoucher(10);
        voucherRepository.save(voucher);
        voucherRepository.save(voucher2);

        // 전체 쿠폰 목록 요청
        List<Voucher> returned = voucherService.getVouchers();

        // 쿠폰 목록의 크기가 2
        assertThat(returned).hasSize(2);
    }

    @Test
    void 특정_사용자의_쿠폰_목록_조회() {
        // 쿠폰 소지자가 존재하는 쿠폰 저장
        UUID customerId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(1000, customerId);
        voucherRepository.save(voucher);

        // 쿠폰 소지자가 존재하지 않는 쿠폰 저장
        Voucher voucher2 = new PercentDiscountVoucher(10);
        voucherRepository.save(voucher2);

        // 사용자 UUID로 쿠폰 목록 요청
        List<Voucher> returned = voucherService.getVouchers(customerId);

        // 쿠폰 목록의 크기가 1
        assertThat(returned).hasSize(1);
    }

    @Test
    void 쿠폰_소지자_할당() {
        // 쿠폰 생성
        Voucher voucher = new FixedAmountVoucher(1000);
        voucherRepository.save(voucher);

        // 쿠폰에 쿠폰 소지자 할당 요청
        UUID customerId = UUID.randomUUID();
        voucherService.assignVoucherOwner(voucher.getId(), customerId);

        // 쿠폰 소지자가 할당한 소지자와 동일
        Voucher returned = voucherRepository.findById(voucher.getId()).get();
        assertThat(returned.getOwnerId()).isEqualTo(customerId);
    }

    @Test
    void 쿠폰_아이디로_조회() {
        // 쿠폰 생성
        Voucher voucher = new FixedAmountVoucher(1000);
        voucherRepository.save(voucher);

        // UUID로 쿠폰 조회
        Voucher found = voucherService.getVoucher(voucher.getId());

        // 조회된 쿠폰의 UUID가 인자로 넘겨준 UUID와 같음
        assertThat(found.getId()).isEqualTo(voucher.getId());
    }

    @Test
    void 쿠폰_아이디로_삭제() {
        // 쿠폰 생성
        Voucher voucher = new FixedAmountVoucher(1000);
        voucherRepository.save(voucher);

        // UUID로 쿠폰 삭제
        voucherService.deleteVoucher(voucher.getId());

        // 쿠폰이 삭제됨
        List<Voucher> vouchers =  voucherRepository.findAll();
        assertThat(vouchers).hasSize(0);
    }
}