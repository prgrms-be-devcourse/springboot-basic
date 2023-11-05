package team.marco.voucher_management_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherCreateRequest;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.domain.voucher.VoucherType;
import team.marco.voucher_management_system.repository.voucher.VoucherRepository;
import team.marco.voucher_management_system.service.voucher.VoucherCreateServiceRequest;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.FIXED;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.PERCENT;

@SpringBootTest
@ActiveProfiles("test")
class VoucherServiceTest {
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        String query = "DELETE FROM vouchers";
        jdbcTemplate.update(query);
    }

    @DisplayName("고정 할인 쿠폰을 생성할 수 있습니다.")
    @Test
    void createFixedVoucher() {
        // given
        int amount = 1000;
        VoucherCreateServiceRequest request = new VoucherCreateServiceRequest(
                FIXED,
                amount);

        // when
        Voucher returned = voucherService.createVoucher(request);

        // then
        assertThat(returned.getVoucherType()).isEqualTo(FIXED);
        assertThat(returned.getDiscountValue()).isEqualTo(amount);
    }

    @DisplayName("퍼센트 할인 쿠폰을 생성할 수 있습니다.")
    @Test
    void createPercentVoucher() {
        // given
        int percent = 10;
        VoucherCreateServiceRequest request = new VoucherCreateServiceRequest(
                PERCENT,
                percent);

        // when
        Voucher returned = voucherService.createVoucher(request);

        // then
        assertThat(returned.getVoucherType()).isEqualTo(PERCENT);
        assertThat(returned.getDiscountValue()).isEqualTo(percent);
    }

    @DisplayName("전체 쿠폰 목록을 조회할 수 있습니다.")
    @Test
    void getVouchers() {
        // given
        Voucher voucher = createFixedVoucher(1L, 1000);
        Voucher voucher2 = createPercentVoucher(2L,10);
        voucherRepository.save(voucher);
        voucherRepository.save(voucher2);

        // when
        List<Voucher> returned = voucherService.getVouchers();

        // then
        assertThat(returned).hasSize(2);
    }

    @DisplayName("특정 쿠폰 유형에 해당하는 쿠폰 목록을 조회할 수 있습니다.")
    @Test
    void getVouchersByVoucherType() {
        // given
        VoucherType voucherType = FIXED;
        Voucher fixedVoucher = createFixedVoucher(1L, 1000);
        Voucher percentVoucher = createPercentVoucher(2L, 10);
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(percentVoucher);

        // when
        List<Voucher> found = voucherService.getVouchersByVoucherType(voucherType);

        // then
        assertThat(found).hasSize(1);
    }

    @DisplayName("쿠폰 아이디로 쿠폰을 조회할 수 있습니다.")
    @Test
    void getVoucher() {
        // given
        Voucher voucher = createFixedVoucher(1L, 1000);
        voucherRepository.save(voucher);

        // when
        Voucher found = voucherService.getVoucher(voucher.getId());

        // then
        assertThat(found.getId()).isEqualTo(voucher.getId());
    }

    @DisplayName("쿠폰 아이디로 쿠폰을 삭제할 수 있습니다.")
    @Test
    void deleteVoucher() {
        // given
        Voucher voucher = createFixedVoucher(1L, 1000);
        voucherRepository.save(voucher);

        // when
        voucherService.deleteVoucher(voucher.getId());

        // then
        List<Voucher> vouchers =  voucherRepository.findAll();
        assertThat(vouchers).hasSize(0);
    }

    private static VoucherCreateRequest createFixedVoucherRequest(int amount) {
        return new VoucherCreateRequest(FIXED, amount);
    }

    private static VoucherCreateRequest createPercentVoucherRequest(int percent) {
        return new VoucherCreateRequest(PERCENT, percent);
    }

    private static Voucher createFixedVoucher(Long id, int amount) {
        return new Voucher.Builder(id, FIXED, amount).build();
    }

    private static Voucher createPercentVoucher(Long id, int percent) {
        return new Voucher.Builder(id, PERCENT, percent).build();
    }
}