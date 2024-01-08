package com.example.ordersystemproducer.controller;

import com.example.ordersystemproducer.dto.OrderDTO;
import com.example.ordersystemproducer.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderConfirmController {

    private final PurchaseService purchaseService;

    @PostMapping("/order/confirm")
    public String getOrderConfirm(OrderDTO orderDTO, Model model){
        log.info("주문 상세: {}",orderDTO.toString());

        OrderDTO purchasedProduct = purchaseService.saveOrderDTO(orderDTO);
        model.addAttribute("orderFormId", purchasedProduct.getOrderId());
        model.addAttribute("memberId", purchasedProduct.getMemberId());
        model.addAttribute("productId", purchasedProduct.getProductId());
        model.addAttribute("amount", purchasedProduct.getAmount());
        model.addAttribute("shippingAddress", purchasedProduct.getShippingAddress());

        return "orderConfirmForm";
    }
}
