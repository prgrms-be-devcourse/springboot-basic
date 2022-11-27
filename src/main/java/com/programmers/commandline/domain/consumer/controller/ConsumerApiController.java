package com.programmers.commandline.domain.consumer.controller;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.service.ConsumerService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConsumerApiController {
    private final ConsumerService consumerService;

    public ConsumerApiController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping()
    public String findAll(Model model) {
        List<Consumer> consumers = consumerService.findAll();
        model.addAttribute("consumers", consumers);
        return "consumer/consumerIndex";
    }
}
