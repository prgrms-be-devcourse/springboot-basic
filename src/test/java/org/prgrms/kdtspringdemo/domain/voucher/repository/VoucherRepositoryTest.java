package org.prgrms.kdtspringdemo.domain.voucher.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * 인터페이스에 요구되는 repository 기능이 같다면 하나의 VoucherRepositoryTest로 구현체를 바꿔서 테스트
 * 만약 인터페이스에 없는 추가된 로직이 있는 구현체라면 별도의 테스트로 테스트.
 */
public class VoucherRepositoryTest {
    @Test
    @DisplayName("바우처 저장을 테스트합니다.")
    public void save(){

    }

    @Test
    @DisplayName("바우처 전체 조회를 테스트합니다.")
    public void findAll(){

    }
}
