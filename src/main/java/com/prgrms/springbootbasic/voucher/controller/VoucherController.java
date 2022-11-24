package com.prgrms.springbootbasic.voucher.controller;

import com.prgrms.springbootbasic.common.controller.ResponseStatus;
import com.prgrms.springbootbasic.common.exception.AmountOutOfBoundException;
import com.prgrms.springbootbasic.common.exception.InvalidVoucherTypeException;
import com.prgrms.springbootbasic.voucher.service.VoucherService;
import com.prgrms.springbootbasic.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.prgrms.springbootbasic.voucher.controller.VoucherResponse.*;
import static org.springframework.http.HttpStatus.*;

@Profile("prod | test")
@Controller
@RequestMapping("/api/v1/voucher")
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherService voucherService;

    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }


    @GetMapping
    public String list(Model model){
        List<VoucherShortcut> vouchers = voucherService.list();
        model.addAttribute("vouchers", vouchers);
        logger.info("List up all Vouchers. voucher shortcuts {}", vouchers);
        return "voucher/list";
    }

    @GetMapping("/new")
    public String saveForm(Model model){
        Map<String, Object> attributes = Map.of(
                "voucherForm", new VoucherForm(),
                "voucherTypes", VoucherType.values(),
                "responseStatus", new ResponseStatus(OK.value(), OK.getReasonPhrase()));
        model.addAllAttributes(attributes);
        return "voucher/newForm";
    }

    @PostMapping("/new")
    public String save(@ModelAttribute VoucherForm voucherForm, Model model){
        try{
            voucherService.create(voucherForm);
        } catch(InvalidVoucherTypeException | AmountOutOfBoundException e){
            Map<String, Object> attributes = Map.of(
                    "voucherForm", voucherForm,
                    "voucherTypes", VoucherType.values(),
                    "responseStatus", new ResponseStatus(BAD_REQUEST.value(), e.getMessage()));
            model.addAllAttributes(attributes);
            return "voucher/newForm";
        }
        logger.info("New Voucher created. input info : {}", voucherForm);
        return "redirect:/api/v1/voucher";
    }

    @GetMapping("/{uuid}")
    public String detail(@PathVariable String uuid, Model model){
        VoucherDetails details = voucherService.findById(UUID.fromString(uuid));
        model.addAttribute("voucherTypes", VoucherType.values());
        model.addAttribute("details", details);
        logger.info("Voucher Detail. voucher id is {}", uuid);
        return "voucher/details";
    }

    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable String uuid){
        voucherService.delete(uuid);
        logger.info("Delete voucher. voucher id was {}", uuid);
        return "redirect:/api/v1/voucher";
    }
}
