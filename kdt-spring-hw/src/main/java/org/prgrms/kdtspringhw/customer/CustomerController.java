package org.prgrms.kdtspringhw.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class CustomerController {

    //private final CustomerService customerService;

    @RequestMapping(value = "/customers",method = RequestMethod.GET)
    public ModelAndView findCustomers(){
     return new ModelAndView("customers",
             Map.of("serverTime", LocalDateTime.now()));
    }

    private class CustomerService {
    }
}
