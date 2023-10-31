package org.prgrms.vouchermanagement.wallet.domain;

import java.util.UUID;

public class WalletMapper {
    private final UUID customerId;
    private final UUID voucherId;

    public WalletMapper(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}

/*

wallet -> 클래스명을 고치자

dto -> pr..

테스트
-> 장점 중 하나, 정상 작동을 하는 지 보장하기 위해

A -> B 수정이 발생해도 ㄱㅊ
구현에 의존적인 테스트

테스트의 이점 -> pr..

-> 상태 기반 테스트 행위 기반 테스트 구현에 의존적인 테스트
 */
