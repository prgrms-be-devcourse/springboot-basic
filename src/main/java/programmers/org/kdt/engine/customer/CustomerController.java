package programmers.org.kdt.engine.customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

//    @RequestMapping(value = "/customers", method = RequestMethod.GET)
//    @GetMapping("/customers")
//    public ModelAndView findCustomers() {
//        List<Customer> allCustomers = customerService.getAllCustomers();
//        return new ModelAndView("views/customers", Map.of("serverTime", LocalDateTime.now(), "customers", allCustomers));
//    }

    @GetMapping("/customers")
    public String findCustomers(Model model) {
        List<Customer> allCustomers = customerService.getAllCustomers();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("customers", allCustomers);
        return "views/customers";
    }

}
