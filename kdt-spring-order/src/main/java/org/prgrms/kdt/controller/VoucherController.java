package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.InputType;
import org.prgrms.kdt.fileIO.FileIOStream;
import org.prgrms.kdt.io.IoConsole;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private static InputType inputType;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private FileIOStream fileIOStream;

    @Autowired
    public void run(AnnotationConfigApplicationContext applicationContext) {
        IoConsole ioConsole = new IoConsole();

        while (true) {
            System.out.println();
            //init message 출력
            ioConsole.init();

            String s = ioConsole.input();
            try {
                var type = inputType.valueOf(s.toUpperCase());
                switch (type){
                    case CREATE:
                        String val = ioConsole.inputText("생성하고자하는 Voucher를 선택해주세요 : [1] Fixed, [2] Percent");
                        switch (val) {
                            case "1":
                                // fixed 생성
                                ioConsole.message("FixedAmountVoucher를 생성합니다.");
                                String discount = ioConsole.inputText("할인하고자 하는 금액을 입력해 주세요");
                                fileIOStream.fileInputStream(voucherService.createVoucher(UUID.randomUUID(), Long.parseLong(discount), 1));
                                ioConsole.message("fixed 바우처 생성이 완료되었습니다.");
                                break;
                            case "2":
                                // percent 생성
                                ioConsole.message("PercentAmountVoucher를 생성합니다.");
                                String percent = ioConsole.inputText("할인하고자 하는 %를 입력해 주세요");
                                fileIOStream.fileInputStream(voucherService.createVoucher(UUID.randomUUID(), Long.parseLong(percent), 2));
                                ioConsole.message("percent 바우처 생성이 완료되었습니다.");
                                break;
                            default:
                                //다시 입력
                                ioConsole.message("다시 입력 하세요~");
                                break;
                        }
                        break;
                    case LIST:
                        ioConsole.message("지금까지 생성한 바우처를 출력합니다.");
                        //조회 기능 사용
                        List<String> list = fileIOStream.fileOutputStream();
                        ioConsole.outputList_file(list);
                        break;
                    case EXIT:
                        ioConsole.exit();
                        return;
                }
            }catch (Exception e){
//                e.printStackTrace();
                ioConsole.inputError();
                continue;
            }


        }

    }

}
