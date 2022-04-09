package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.model.Voucher;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VoucherApplicationTest {

    @Test
    @DisplayName("해시맵에 바우처 저장 기능")
    public void saveTest(Voucher voucer){
    }

    @Test
    @DisplayName("해시맵 findAll 테스트")
    public void findAllTest(){}

    @Test
    @DisplayName("FixedAmountVoucher 생성기능")
    public  void fixedAmountVoucherCreateTest(Voucher voucher){

    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성기능")
    public  void percentDiscountVoucherCreateTest(Voucher voucher){

    }

    @Test
    @DisplayName("바우처 목록 조회 확인")
    public void voucherListTest(){
    }

    @Test
    @DisplayName("바우처 목록이 비어있을 경우")
    public void voucherListEmptyTest(){}

    @Test
    @DisplayName("PercentDistcoutVoucher에 0미만 100초과 입력시 실패")
    public void percentInputTest(){}

    @Test
    @DisplayName("FixedAmountVoucher 생성시 Amount에 음수 입력시 실패")
    public void amountInputTest(){}

    @Test
    @DisplayName("메뉴 입력시 유효하지 않은 명령어")
    public void menuInputTest(){}

    @Test
    @DisplayName("유효하지 않은 바우처 타입 선택")
    public void selectVoucherTest(){}
}
