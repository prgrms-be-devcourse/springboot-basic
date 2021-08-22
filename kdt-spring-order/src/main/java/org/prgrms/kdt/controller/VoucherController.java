package org.prgrms.kdt.controller;

import org.prgrms.kdt.CommandLineApplication;
import org.prgrms.kdt.KdtApplication;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.InputType;
import org.prgrms.kdt.domain.VoucherType;
import org.prgrms.kdt.fileutil.FileIOStream;
import org.prgrms.kdt.io.IoConsole;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {


    @Autowired
    private VoucherService voucherService;

    @Autowired
    private FileIOStream fileIOStream;

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    @Autowired
    public void run() {
        IoConsole ioConsole = new IoConsole();
        while (true) {
            System.out.println();
            //init message 출력
            ioConsole.init();
            String s = ioConsole.input();
            try {
                var type = InputType.valueOf(s.toUpperCase());
                switch (type){
                    case CREATE:
                        String s1 = ioConsole.inputText("생성하고자하는 Voucher를 입력해주세요 : Fixed, Percent");
                        var typeValue = VoucherType.valueOf(s1.toUpperCase());
                        Voucher createVoucher = voucherService.createVoucher(typeValue);
                        fileIOStream.fileInputStream(createVoucher);
                        break;
                    case LIST:
                        ioConsole.message("지금까지 생성한 바우처를 출력합니다.");
                        //조회 기능 사용
                        List<String> list = fileIOStream.fileOutputStream();
                        ioConsole.outputListFile(list);
                        break;
                    case EXIT:
                        ioConsole.exit();
                        System.exit(0);
//                        break;
                }
            }catch (Exception e){
                logger.error("\nERROR MESSAGE -> {}",e.getMessage());
                ioConsole.inputError();
                continue;
            }
        }

    }

}
