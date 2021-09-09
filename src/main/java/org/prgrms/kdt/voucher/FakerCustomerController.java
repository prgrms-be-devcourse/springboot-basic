package org.prgrms.kdt.voucher;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerDto;
import org.prgrms.kdt.customer.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by yhh1056
 * Date: 2021/09/09 Time: 5:49 오후
 */

@Controller
public class FakerCustomerController {

    private final CustomerRepository customerRepository;

    public FakerCustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * 임의로 랜덤한 고객을 생성한다.
     */
    @GetMapping("/admin/voucher/customer")
    public String addRandomCustomer(RedirectAttributes attributes) {
        Faker faker = new Faker();
        String name = faker.name().lastName();
        Customer fakerCustomer = new Customer(UUID.randomUUID(), name, name + "@email.com", LocalDateTime.now());

        customerRepository.insert(fakerCustomer);

        attributes.addFlashAttribute("message", name + " 고객이 생성되었습니다.");
        return "redirect:/admin";
    }

    @GetMapping("/admin/voucher/customer/deletion")
    public String deleteAll(RedirectAttributes attributes) {
        customerRepository.deleteAll();

        attributes.addFlashAttribute("message", "고객이 전부 삭제되었습니다.");
        return "redirect:/admin";
    }

}
