package programmers.org.kdt.engine.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import programmers.org.kdt.engine.voucher.type.Voucher;
import programmers.org.kdt.engine.voucher.type.VoucherStatus;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }

    @GetMapping("/")
    public String viewMainPage(Model model) {
        model.addAttribute("serverTime", LocalDateTime.now());
        return "views/main";
    }

    // voucher 메인
    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        var allVouchers = voucherService.getAllVouchers();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("vouchers", allVouchers);
        return "views/vouchers";
    }

    // voucher 추가페이지
    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage() {
        return "views/new-vouchers";
    }

    //전체 조회기능
    @GetMapping("/api/v1/vouchers")
    @ResponseBody
    public List<Voucher> findVouchers() {
        return voucherService.getAllVouchers();
    }

    //조건별 조회기능
    @GetMapping("/api/v1/vouchers/{voucherId}")
    @ResponseBody
    public ResponseEntity<Voucher> findVouchers(@PathVariable("voucherId") UUID voucherId) {
        Optional<Voucher> voucher = Optional.ofNullable(voucherService.getVoucher(voucherId));
        return voucher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //바우처 추가기능
    @PostMapping("/vouchers/new")
    public String addNewCustomer(CreateVoucherRequest createVoucherRequest) {
        var voucherStatusValue= createVoucherRequest.status();
        VoucherStatus voucherStatus;
        switch (voucherStatusValue) {
            case 1 -> voucherStatus = VoucherStatus.FIXEDAMOUNTVOUCHER;
            case 2 -> voucherStatus = VoucherStatus.PERCENTDISCOUNTVOUCHER;
            default -> {
                return "redirect:/vouchers";
            }
        }
        voucherService.createVoucher(voucherStatus, createVoucherRequest.input_value());
        return "redirect:/vouchers";
    }

    //바우처 삭제기능
    @GetMapping("/vouchers/delete")
    public String deleteAllVouchers() {
        voucherService.deleteAllVouchers();
        return "redirect:/vouchers";
    }

    //바우처 조건별 검색기능
    @GetMapping("/api/vi/vouchers/{voucherType}")
    @ResponseBody
    public List<Voucher> findVouchers(@PathVariable("voucherType") FindVoucherRequest findVoucherRequest) {
        return voucherService.getAllVouchers().stream()
            .filter(voucher -> voucher.getVoucherStatus() == findVoucherRequest.voucherStatus())
            .collect(Collectors.toList());
    }
}
