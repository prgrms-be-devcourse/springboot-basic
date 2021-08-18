package org.prgrms.kdt;

import org.prgrms.kdt.configure.AppConfiguration;
import org.prgrms.kdt.configure.Voucher;
import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.entity.InputType;
import org.prgrms.kdt.io.IoConsole;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

public class CommandLineApplication {
      private static InputType inputType;
    public static void main(String[] args) {

        // FLOW
        //1. initMessage 출력
        //2. create, list, exit 중 하나 입력
        //  2.1 잘못 입력시 다시 입력 요구!
        //3. 입력값마다 함수 호출하기
        //  3.1 exit의 경우 : 바로종료
        //  3.2 create의 경우 바우처 생성(1.percent 2.fixed)
        //      3.2.2 선택한 입력값으로 바우처를 생성한다.(생성한 바우처는 list에 저장)
        //      3.2.3 바우처에 할인할 값/퍼센트를 입력한다.
        //  3.3 list의 경우 : 리스트에 있는 바우처 들을 출력한다.

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherController.run(applicationContext);

    }
}
