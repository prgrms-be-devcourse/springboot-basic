package com.example.voucherproject.voucher;

import com.example.voucherproject.voucher.model.Voucher;
import com.example.voucherproject.voucher.model.VoucherType;
import com.example.voucherproject.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class VoucherController {

    private final VoucherService voucherService;

    // 바우처 생성
    @GetMapping("/vouchers/new")
    public String addVoucherView(){
        return "voucher-new";
    }

    // 바우처 생성
    @PostMapping("/vouchers/new")
    public String addVoucherRedirect(@RequestParam String type, @RequestParam Long amount){
       voucherService.createVoucher(type, amount);
        return "redirect:../vouchers/";
    }

    // 바우처 목록 조회
    @GetMapping("/vouchers")
    public String vouchersView(Model model){
        var vouchers = voucherService.findAll();
        vouchers.sort(Comparator.comparing(Voucher::getCreatedAt));
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    // 바우처 상세 조회
    @GetMapping("/voucher/{id}")
    public String voucherDetailView(@PathVariable UUID id, Model model){
        var maybeVoucher = voucherService.findById(id);

        if (maybeVoucher.isPresent()){
            model.addAttribute("voucher", maybeVoucher.get());
            return "voucher/voucher-detail";
        }
        else{
            return "basic/404";
        }
    }

    // 바우처 삭제
    @DeleteMapping("/voucher/{id}")
    public String deleteVoucher(@PathVariable UUID id){
        voucherService.deleteById(id);
        return "redirect:../vouchers/";
    }

    // 바우처 수정
    @GetMapping("/vouchers/{id}/edit")
    public String editVoucherView(@PathVariable UUID id, Model model){
        var maybeVoucher = voucherService.findById(id);
        model.addAttribute("voucher", maybeVoucher.get());
        return "voucher/voucher-edit";
    }

    // 바우처 수정
    @PostMapping("/voucher/{id}")
    public String editVoucher(@PathVariable UUID id){



        return "redirect:../vouchers/";
    }

    @PostMapping("/voucher/query")
    public String queryVoucher(@RequestParam("type") String type,
                             @Nullable @RequestParam("startDate") String from,
                             @Nullable @RequestParam("endDate") String to,
                             Model model){
        if(from.isEmpty()) { from = "2022-01-01"; }
        if(to.isEmpty()) { to = "2222-12-29"; }

        List<Voucher> vouchers = voucherService.findByTypeAndDate(VoucherType.valueOf(type),from, to);
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }
}
