package prgms.vouchermanagementapp.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.dto.VoucherDTO;
import prgms.vouchermanagementapp.repository.VoucherRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher/vouchers")
public class WebVoucherController {

    /**
     * URI Prefix: "voucher/vouchers"
     * 바우처 전체 조회 페이지 vouchers(): GET, ""
     * 바우처 상세 조회 페이지 voucher(): GET, "/{voucherId}"
     * 바우처 생성 페이지 addForm() : GET, "/add"
     * 바우처 생성 addVoucher() : POST, "/add"
     * 바우처 수정 페이지 editForm() : GET, "/{voucherId}/edit
     * 바우처 수정 editVoucher() : POST, "/{voucherId}/edit
     * 바우처 삭제 deleteVoucher() : POST, "/{voucherId}/delete
     */

    private final VoucherRepository voucherRepository;

    public WebVoucherController(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @GetMapping
    public String vouchers(Model model) {
        List<Voucher> vouchers = voucherRepository.findAll();
        List<VoucherDTO> voucherDTOs = vouchers.stream()
                .map(VoucherDTO::new)
                .toList();

        model.addAttribute("voucherDTOs", voucherDTOs);
        return "voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String voucher(@PathVariable UUID voucherId, Model model) {
        Voucher foundVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() ->
                        new EmptyResultDataAccessException("cannot find voucher for voucherId=" + voucherId, 1)
                );
        VoucherDTO voucherDTO = new VoucherDTO(foundVoucher);

        model.addAttribute("voucher", voucherDTO);
        return "voucher/voucher";
    }
}
