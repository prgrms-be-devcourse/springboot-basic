package org.prgrms.kdtspringdemo.service.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VoucherServiceTest {

    @Test
    @DisplayName("바우처 한건을 저장하고 저장된 바우처를 반환합니다.")
    public void save() {
    }

    @Test
    @DisplayName("바우처를 전체 조회합니다.")
    public void findAll() {

    }

    /*
    바우처 생성 시에 발생하는 예외가 VoucherService에 전달 되는지 확인
     */
    @Test
    @DisplayName("FixedAmountVoucher 생성 시에 할인 금액이 마이너스 일 경우 예외가 발생합니다.")
    public void saveWithMinusDiscountFixAmount() {
    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성 시에 할인 금액이 마이너스 일 경우 예외가 발생합니다.")
    public void saveWithMinusDiscountPercent() {
    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성 시에 PercentDiscountVoucher 할인율이 100을 넘는다면 예외가 발생합니다.")
    public void saveWithDiscountPercentOver100() {
    }


    /*
    VoucherFileRepository -> FileUtils 에서 발생하는 예외가 VoucherService 까지 전달 되는지 확인
     */
    @Test
    @DisplayName("바우처 한건 저장 시에 VoucherFileRepository 예외 발생 시 예외가 발생합니다.")
    public void saveWithVoucherFileRepositoryException() {
    }

    @Test
    @DisplayName("바우처 전체 조회 시에 VoucherFileRepository 예외 발생 시 예외가 발생합니다.")
    public void findAllWithVoucherFileRepositoryException() {
    }
}
