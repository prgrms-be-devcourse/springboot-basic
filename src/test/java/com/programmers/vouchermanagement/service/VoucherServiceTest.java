package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.voucher.tmp.Voucher;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherServiceTest {
    private VoucherService voucherService;
    private VoucherRepository voucherRepository;
    private List<Voucher> voucherFixtures;
    private UUID uuidFixture;


/*    @BeforeEach
    void setUp() {
        voucherFixtures = Arrays.asList(
                new FixedAmountVoucher(UUID.fromString("00000000-0000-0000-0000-000000000000"), 1000L),
                new PercentDiscountVoucher(UUID.fromString("11111111-1111-1111-1111-111111111111"), 2000L)
        );
        voucherRepository = new StubVoucherRepository(voucherFixtures);

        uuidFixture = UUID.fromString("22222222-2222-2222-2222-222222222222");
        voucherService = new VoucherService(voucherRepository, new StubUuidProvider(uuidFixture));
    }

    @Test
    @DisplayName("바우처를 생성할 수 있다.")
    void create() {
        // given
        Long amount = 3000L;
        // when
        voucherService.createVoucher(VoucherType.FIXED_AMOUNT, amount);
        // then
        List<Voucher> vouchers = voucherRepository.findAll();
        Voucher createdVoucher = vouchers.get(2);
        assertThat(vouchers).hasSize(3);
        assertThat(createdVoucher.getId()).isEqualTo(uuidFixture);
        assertThat(createdVoucher.getAmount()).isEqualTo(amount);
        assertThat(createdVoucher.getType()).isEqualTo(VoucherType.FIXED_AMOUNT);
    }

    @Test
    @DisplayName("바우처 목록을 조회할 수 있다.")
    void list() {
        // given
        // when
        List<Voucher> vouchers = voucherService.getVouchers();
        // then
        assertThat(vouchers).hasSize(2);
        assertThat(vouchers.get(0).getId()).isEqualTo((voucherFixtures.get(0).getId()));
        assertThat(vouchers.get(1).getId()).isEqualTo((voucherFixtures.get(1).getId()));
        assertThat(vouchers.get(0).getAmount()).isEqualTo(voucherFixtures.get(0).getAmount());
        assertThat(vouchers.get(1).getAmount()).isEqualTo(voucherFixtures.get(1).getAmount());
    }*/
}
