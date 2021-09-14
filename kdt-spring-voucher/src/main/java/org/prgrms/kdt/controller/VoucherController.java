package org.prgrms.kdt.controller;

import org.prgrms.kdt.CommandLineApplication;
import org.prgrms.kdt.domain.CreateCustomerRequest;
import org.prgrms.kdt.domain.CreateVoucherRequest;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherEntity;
import org.prgrms.kdt.enumType.InputStatus;
import org.prgrms.kdt.enumType.VoucherStatus;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.utill.FileIoStream;
import org.prgrms.kdt.utill.IO.IoConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private VoucherService voucherService;

    private ConfigurableApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);


    public VoucherController(VoucherService voucherService, ConfigurableApplicationContext applicationContext) {
        this.voucherService = voucherService;
        this.applicationContext = applicationContext;
    }

    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        List<VoucherEntity> allVouchers = voucherService.findAll();
        model.addAttribute("vouchers", allVouchers);
        return "views/vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewNewCustomerPage() {
        return "views/items/voucherForm";
    }

    @PostMapping("/vouchers/new")
    public String addNewCustomer(CreateVoucherRequest createVoucherRequest) {
        voucherService.createVoucher(createVoucherRequest);
        return "redirect:/vouchers";
    }

    @PostMapping("/vouchers/{voucherId}/remove")
    public String removeVoucher(@PathVariable(name = "voucherId") UUID voucherId){
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }

    public void run() {
        IoConsole ioConsole = new IoConsole();
        while (true) {
            System.out.println();
            //init message 출력
            ioConsole.init();
            String s = ioConsole.input();
            try {
                var type = InputStatus.valueOf(s.toUpperCase());
                switch (type) {
                    case CREATE:
                        var typeValue = getVoucherStatus(ioConsole);
                        var voucherEntity = voucherService.commandCreateVoucher(typeValue);
                        break;
                    case LIST:
                        ioConsole.message("지금까지 생성한 바우처를 출력합니다.");
                        var voucherList = voucherService.findAll();
                        ioConsole.outputList(voucherList);
                        break;
                    case REMOVE:
                        ioConsole.message("생성한 모든 바우처를 삭제합니다.");
                        voucherService.removeAll();
                        break;
                    case EXIT:
                        ioConsole.exit();
                        applicationContext.close();
                        System.exit(0);
                }
            } catch (Exception throwables) {
                logger.error("입력 오류 발생 다시 입력하세요 ");
            }
        }
    }

    public int getVoucherStatus(IoConsole ioConsole) {
        String s1 = ioConsole.inputText("생성하고자하는 Voucher를 입력해주세요 : [1] Fixed, [2] Percent");
        int type = Integer.parseInt(s1);
        if (type != 1 && type != 2) {
            logger.error("잘못된 형식의 입력입니다. 다시 입력해 주새요!! ");
            return getVoucherStatus(ioConsole);
        }
        return type;
    }

}
