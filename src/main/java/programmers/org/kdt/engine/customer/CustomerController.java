package programmers.org.kdt.engine.customer;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
