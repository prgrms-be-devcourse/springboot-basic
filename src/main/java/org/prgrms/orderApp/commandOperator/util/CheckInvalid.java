package org.prgrms.orderApp.commandOperator.util;

import org.springframework.stereotype.Component;

@Component
public class CheckInvalid {
    // 문자값이 숫자로 이루어졌는지 확인하는 메서드입니다.
    public boolean checkInteger(String targetChecked){
        // true : 숫자로 이루어져 있지 않음 / false : 숫자로 이루어져 있음.
        boolean flag = true;
        if(targetChecked.matches("[+-]?\\d*(\\.\\d+)?") && targetChecked !=""){
            flag = false;
        }
        return flag;
    }
}
