package com.waterfogsw.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VoucherApplicationTests {

    //바우처 서비스
    @Test
    @DisplayName("바우처 생성 - FixedAmount")
    public void createFixedAmountVoucher() {

    }

    @Test
    @DisplayName("바우처 생성 - PercentDiscount")
    public void createPercentAmountVoucher() {

    }

    //바우처 레포지토리
    @Test
    @DisplayName("인메모리 - 바우처 레포지토리에 저장")
    public void memoryRepositoryVoucherSave() {

    }

    @Test
    @DisplayName("인메모리 - 바우처 리스트 조회")
    public void memoryRepositoryVoucherListLookUp() {

    }


    @Test
    @DisplayName("파일 - 바우처 레포지토리에 저장")
    public void fileRepositoryVoucherSave() {

    }

    @Test
    @DisplayName("파일 - 바우처 리스트 조회")
    public void fileRepositoryVoucherListLookUp() {

    }

    @Test
    @DisplayName("파일 - 잘못된 경로")
    public void fileRepositoryWrongPath() {

    }

    //콘솔 입출력
    @Test
    @DisplayName("메뉴 입력")
    public void inputMenu() {

    }

    @Test
    @DisplayName("바우처 타입 입력")
    public void inputVoucherType() {

    }

    @Test
    @DisplayName("percent || amount 입력")
    public void inputAmount() {

    }

    @Test
    @DisplayName("FixedAmountVoucher - 음수인 경우")
    public void testAmountMinus() {

    }

    @Test
    @DisplayName("PercentAmountVoucher - 음수인 경우")
    public void testPercentMinus() {

    }

    @Test
    @DisplayName("PercentAmountVoucher - 100을 넘는 경우")
    public void testPercentHundred() {

    }

    @Test
    @DisplayName("존재하지 않는 메뉴인경우")
    public void menuCheck() {

    }

    @Test
    @DisplayName("존재하지 않는 바우처 타입인경우")
    public void voucherTypeCheck() {

    }

    //멤버 서비스
    @Test
    @DisplayName("블랙리스트 조회")
    public void blackListLookUp() {

    }

    @Test
    @DisplayName("블랙리스트 파일이 잘못된 경로일경우")
    public void blackListWrongPath() {

    }
}
