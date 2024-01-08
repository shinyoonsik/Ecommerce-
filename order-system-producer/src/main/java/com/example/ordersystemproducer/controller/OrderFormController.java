package com.example.ordersystemproducer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class OrderFormController {


    @GetMapping("/order/form")
    public String getOrderFrom(Model model){
        log.info("order-form진입");
        return "orderForm";
    }
}
