package org.prgrms.orderApp.controller;

import lombok.AllArgsConstructor;
import org.prgrms.orderApp.common.InputMsgDto;
import org.prgrms.orderApp.common.ModalDto;
import org.prgrms.orderApp.voucher.VoucherDto;
import org.prgrms.orderApp.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/voucher")
public class VoucherController {

    private static Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private VoucherService voucherService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getAllList(HttpServletRequest req, Model model,
                             @RequestParam @Nullable String status,
                             @RequestParam @Nullable String type,
                             @RequestParam @Nullable String message
                             ){


        // create 또는 delete 후, 성공적으로 처리되었는지 알려주는 알림 설정 부분입니다.
        if (status==null) {
            model.addAttribute("alert", new HashMap<String, String>() {
                        {
                            put("alert_css", "None");
                            put("status", "None");
                            put("message", "None");
                        }
                    }
            );

        }else {
            model.addAttribute("alert", new HashMap<String, String>() {
                        {
                            put("alert_css", (status.equals("fail"))? "danger": status );
                            put("status", status);
                            put("message", String.format("[%s] %s" , type, message));
                        }
                    }
            );
        }


        model.addAttribute("title", "VOUCHER LIST PAGE");
        model.addAttribute("detail_location", "details/");
        model.addAttribute("column_list",  Arrays.asList( "Name", "Type", "Amount"));
        model.addAttribute("data_list", voucherService.getAllVouchers());
        return "list";
    }

    @RequestMapping(value = "/details/{voucher_id}", method = RequestMethod.GET)
    public String getdetails(@PathVariable("voucher_id") String voucherId, Model model){

        model.addAttribute("data", voucherService.getVoucherById(UUID.fromString(voucherId)));
        model.addAttribute("location", "/kdt/voucher/delete/");
        model.addAttribute("title", "VOUCHER DETAIL PAGE");
        model.addAttribute("column_list",  Arrays.asList( "Voucher Name", "Voucher Type", "Voucher Expire At",
                                                              "Voucher Created At", "Customer Count","Delete Button"));
        model.addAttribute("modal", new ModalDto("Voucher Delete Page", "ARE YOU SURE TO DELETE?", "/kdt/voucher/delete/"+voucherId));

        return "details";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteVoucherById(@RequestParam String id, Model model){
        voucherService.deleteById(UUID.fromString(id));

        model.addAttribute("title", "VOUCHER DETAIL PAGE");
        model.addAttribute("column_list",  Arrays.asList( "Voucher Name", "Voucher Type", "Voucher Expire At",
                "Voucher Created At", "Customer Count"));

        return "redirect:/voucher/list?type=DELETE&status=success&message="+id;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createPage(Model model) {
        model.addAttribute("title", "VOUCHER CREATE PAGE");
        model.addAttribute("column_list", Arrays.asList("Type", "Input Data"));
        model.addAttribute("input_column_list", Arrays.asList(new InputMsgDto("Name", "name", "text", "바우처 이름을 기입해 주세요."),
                new InputMsgDto("Amount", "amount", "number", "Voucher Discount 값을 기입해 주세요."),
                new InputMsgDto("Expire Date", "expireAt", "date", "Voucher 유효성이 끝나는 날짜를 선택해 주세요.")
        ));
        return "create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(VoucherDto voucherDto){
        var test = voucherDto.getName();
        var test1 = voucherDto.getVoucherType();
        var test3 = voucherDto.getVoucherId();

        voucherService.saveVoucher(voucherDto);
        return "redirect:/voucher/list?type=CREATE&status=success&message="+voucherDto.getName();
    }







}
