package prgms.vouchermanagementapp.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.dto.VoucherViewDTO;
import prgms.vouchermanagementapp.repository.VoucherRepository;
import prgms.vouchermanagementapp.service.VoucherFactory;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher/voucherList")
public class WebVoucherController {

    /**
     * URI Prefix: "voucher/voucherList"
     * 바우처 전체 조회 페이지 voucherList(): GET, ""
     * 바우처 상세 조회 페이지 voucherDetail(): GET, "/{voucherId}"
     * 바우처 생성 페이지 voucherAddForm(): GET, "/add"
     * 바우처 생성 addVoucher(): POST, "/add"
     * 바우처 수정 페이지 voucherEditForm(): GET, "/{voucherId}/edit
     * 바우처 수정 editVoucher(): POST, "/{voucherId}/edit
     * 바우처 삭제 deleteVoucher(): POST, "/{voucherId}/delete
     */

    // TODO: voucherService 를 이용하도록 변경
    private final VoucherRepository voucherRepository;

    public WebVoucherController(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @GetMapping
    public String voucherList(Model model) {
        List<Voucher> vouchers = voucherRepository.findAll();
        List<VoucherViewDTO> voucherViewDTOs = vouchers.stream()
                .map(VoucherViewDTO::new)
                .toList();

        model.addAttribute("vouchers", voucherViewDTOs);
        return "voucher/voucherList";
    }

    @GetMapping("/{voucherId}")
    public String voucherDetail(@PathVariable UUID voucherId, Model model) {
        Voucher foundVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() ->
                        new EmptyResultDataAccessException("cannot find voucher for voucherId=" + voucherId, 1)
                );
        VoucherViewDTO voucherViewDTO = new VoucherViewDTO(foundVoucher);

        model.addAttribute("voucher", voucherViewDTO);
        return "voucher/voucherDetail";
    }

    @GetMapping("/add")
    public String voucherAddForm() {
        return "voucher/voucherAddForm";
    }

    @PostMapping("/add")
    public String addVoucher(
            @RequestParam String voucherType,
            @RequestParam long discountLevel,
            RedirectAttributes redirectAttributes
    ) {
        Voucher newVoucher = VoucherFactory.createVoucher(voucherType, discountLevel);
        voucherRepository.save(newVoucher);

        redirectAttributes.addAttribute("voucherId", newVoucher.getVoucherId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/voucher/voucherList/{voucherId}";
    }

    @GetMapping("/{voucherId}/edit")
    public String voucherEditForm(@PathVariable UUID voucherId, Model model) {
        Voucher foundVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() ->
                        new EmptyResultDataAccessException("cannot find voucher for voucherId=" + voucherId, 1)
                );
        VoucherViewDTO voucherViewDTO = new VoucherViewDTO(foundVoucher);

        model.addAttribute("voucher", voucherViewDTO);
        return "voucher/voucherEditForm";
    }

    @PostMapping("/{voucherId}/edit")
    public String updateVoucher(
            @PathVariable UUID voucherId,
            @RequestParam long discountLevel,
            RedirectAttributes redirectAttributes
    ) {
        voucherRepository.updateDiscountLevel(voucherId, discountLevel);

        redirectAttributes.addAttribute("updateStatus", true);
        return "redirect:/voucher/voucherList/{voucherId}";
    }

    @GetMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherRepository.deleteById(voucherId);
        return "redirect:/voucher/voucherList";
    }
}
