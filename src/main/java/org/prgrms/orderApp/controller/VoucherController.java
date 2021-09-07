package org.prgrms.orderApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/voucher", method = {RequestMethod.POST, RequestMethod.GET})
public class VoucherController {

    private static Logger logger = LoggerFactory.getLogger(VoucherController.class);

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getAllList(Model model){
        model.addAttribute("title", "VOUCHER LIST PAGE");
        model.addAttribute("detail_location", "details/");

        return "list";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String getdetails(@PathVariable("id") String id, Model model){
        logger.info("id -> {}", id);
        model.addAttribute("title", "VOUCHER DETAIL PAGE");
        model.addAttribute("id", id);
        return "details";
    }

    @RequestMapping(value = "/create", method = {RequestMethod.GET, RequestMethod.POST})
    public String create(HttpServletRequest req, Model model){
        logger.info("create");

        if (req.getMethod().equals("GET")){
            model.addAttribute("title", "VOUCHER CREATE PAGE");

            return "create";
        }else {
            // 저장 로직 추가
            return "redirect:/voucher/list";
        }

    }




}
