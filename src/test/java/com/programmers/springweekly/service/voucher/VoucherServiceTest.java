package com.programmers.springweekly.service.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.dto.voucher.request.VoucherCreateRequest;
import com.programmers.springweekly.dto.voucher.request.VoucherUpdateRequest;
import com.programmers.springweekly.dto.voucher.response.VoucherListResponse;
import com.programmers.springweekly.dto.voucher.response.VoucherResponse;
import com.programmers.springweekly.service.VoucherService;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    @Test
    @DisplayName("바우처를 저장할 수 있다.")
    void save() {
        // given
        VoucherCreateRequest voucherExpected = new VoucherCreateRequest(3000, VoucherType.FIXED);

        // when
        voucherService.save(voucherExpected);
        VoucherListResponse voucherActual = voucherService.findAll();

        // then
        assertThat(voucherActual.getVoucherList().get(0).getDiscountAmount()).isEqualTo(3000);
    }

    @Test
    @DisplayName("바우처를 업데이트 할 수 있다.")
    void update() {
        // given
        VoucherCreateRequest voucher = new VoucherCreateRequest(3000, VoucherType.FIXED);
        voucherService.save(voucher);
        VoucherListResponse voucherList = voucherService.findAll();

        // when
        voucherService.update(new VoucherUpdateRequest(voucherList.getVoucherList().get(0).getVoucherId(), 100, VoucherType.PERCENT));
        VoucherListResponse voucherListUpdated = voucherService.findAll();

        // then
        assertThat(voucherListUpdated.getVoucherList().get(0).getDiscountAmount()).isEqualTo(100);
    }

    @Test
    @DisplayName("바우처 ID를 가지고 바우처를 가져올 수 있다.")
    void findById() {
        // given
        VoucherCreateRequest voucher = new VoucherCreateRequest(3000, VoucherType.FIXED);
        voucherService.save(voucher);
        VoucherListResponse voucherList = voucherService.findAll();

        // when
        VoucherResponse voucherActual = voucherService.findById(voucherList.getVoucherList().get(0).getVoucherId());

        // then
        assertThat(voucherActual.getDiscountAmount()).isEqualTo(3000);
    }

    @Test
    @DisplayName("바우처를 모두 조회할 수 있다.")
    void getVoucherList() {
        // given
        VoucherCreateRequest voucher1 = new VoucherCreateRequest(3000, VoucherType.FIXED);
        VoucherCreateRequest voucher2 = new VoucherCreateRequest(100, VoucherType.PERCENT);
        voucherService.save(voucher1);
        voucherService.save(voucher2);

        // when
        VoucherListResponse voucherList = voucherService.findAll();

        // then
        assertThat(voucherList.getVoucherList().get(0).getDiscountAmount()).isEqualTo(3000);
        assertThat(voucherList.getVoucherList().get(1).getDiscountAmount()).isEqualTo(100);
    }

    @Test
    @DisplayName("바우처 ID를 가지고 바우처를 삭제할 수 있다.")
    void deleteById() {
        // given
        VoucherCreateRequest voucher = new VoucherCreateRequest(3000, VoucherType.FIXED);
        voucherService.save(voucher);
        VoucherListResponse voucherList = voucherService.findAll();

        // when
        voucherService.deleteById(voucherList.getVoucherList().get(0).getVoucherId());

        // then
        assertThatThrownBy(() -> voucherService.findById(voucherList.getVoucherList().get(0).getVoucherId()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("바우처를 모두 삭제할 수 있다.")
    void deleteAll() {
        // given
        VoucherCreateRequest voucher1 = new VoucherCreateRequest(3000, VoucherType.FIXED);
        VoucherCreateRequest voucher2 = new VoucherCreateRequest(100, VoucherType.PERCENT);

        voucherService.save(voucher1);
        voucherService.save(voucher2);

        // when
        voucherService.deleteAll();

        // then
        assertThatThrownBy(() -> voucherService.findAll())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("바우처가 저장되어 있지 않습니다.");
    }
}
