package com.programmers.commandline.domain.consumer.controller;

import com.programmers.commandline.domain.consumer.dto.ConsumerInsertRequestDto;
import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.service.ConsumerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/consumer")
public class ConsumerController {
    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping()
    public String findAll(Model model) {
        List<Consumer> consumers = consumerService.findAll();
        model.addAttribute("consumers", consumers);
        return "consumer/consumerIndex";
    }

    @PostMapping()
    public String insert(ConsumerInsertRequestDto requestDto) {
        consumerService.insert(requestDto.name(), requestDto.email());
        return "redirect:/consumer";
    }

    @PostMapping("/delete")
    public String deleteById(String id) {
        consumerService.deleteById(id);
        return "redirect:/consumer";
    }

}
