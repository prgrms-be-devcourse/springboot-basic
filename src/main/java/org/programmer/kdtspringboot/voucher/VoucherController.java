package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    //초기 메인 화면
    @GetMapping(value = "/")
    public String home(){
        return "views/home";
    }

    //Voucher 목록
    @GetMapping(value = "/vouchers")
    public String viewVouchersPage(Model model) {
        var allCustomers = voucherService.findAllVouchers();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("vouchers",allCustomers);
        return "views/vouchers";
    }

    //새로 생성
    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage(){
        return "views/new-vouchers";
    }

    @GetMapping("/vouchers/search")
    public String searchVoucherPage(){
        return "views/search-vouchers";
    }

    @GetMapping("/vouchers/delete")
    public String deleteVoucherPage(){
        return "views/delete-vouchers";
    }

    //Voucher 생성
    @PostMapping("/vouchers/new")
    public String addNewVoucher(CreateVoucherRequest createVoucherRequest){
        logger.info("CreateVoucherRequest: {} and {}",createVoucherRequest.getType(),createVoucherRequest.getValue());
        voucherService.createVoucher(createVoucherRequest.getType(),createVoucherRequest.getValue());
        return "redirect:/";
    }

    //Voucher 삭제
    @PostMapping("/vouchers/delete")
    public String deleteVoucher(SearchDeleteVoucherRequest searchDeleteVoucherRequest){
        logger.info("SDVoucherRequest: {} ",searchDeleteVoucherRequest.getVoucherId());
        voucherService.deleteVoucher(searchDeleteVoucherRequest.getVoucherId());
        return "redirect:/";
    }

    //Voucher 찾기
    @PostMapping("/vouchers/search")
    public String searchVoucher(Model model,SearchDeleteVoucherRequest searchDeleteVoucherRequest){
        logger.info("SDVoucherRequest: {} ",searchDeleteVoucherRequest.getVoucherId());
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("vouchers",voucherService.findByIdVoucher(searchDeleteVoucherRequest.getVoucherId()));
        return "views/vouchers";
    }

    @GetMapping("/api/v1/vouchers")
    @ResponseBody
    public List<Voucher> findVouchers(){
        return voucherService.findAllVouchers();
    }


}
