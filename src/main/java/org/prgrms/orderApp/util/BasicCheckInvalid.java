package org.prgrms.orderApp.util;

import org.springframework.stereotype.Component;

@Component
public class BasicCheckInvalid {

    public boolean checkInvalidAmount(Long min, Long target, Long max){
        // 값이 유효하면 true, 그렇지 않으면 false
        return min < target && target < max;
    }

}
