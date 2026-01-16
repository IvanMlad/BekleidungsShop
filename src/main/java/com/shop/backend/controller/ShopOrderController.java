package com.shop.backend.controller;

import com.shop.backend.model.ShopOrder;
import com.shop.backend.repository.ShopOrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin // nur nötig, falls du später wieder getrennte Frontends nutzt
public class ShopOrderController {

    private final ShopOrderRepository shopOrderRepository;

    public ShopOrderController(ShopOrderRepository shopOrderRepository) {
        this.shopOrderRepository = shopOrderRepository;
    }

    @GetMapping
    public List<ShopOrder> getAllOrders() {
        return shopOrderRepository.findAll();
    }

    @PostMapping
    public ShopOrder createOrder(@RequestBody ShopOrder order) {

        // Items mit der Order verknüpfen
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }

        // Gesamtpreis berechnen
        double total = 0.0;
        if (order.getItems() != null) {
            total = order.getItems().stream()
                    .mapToDouble(i -> i.getPrice() * i.getQuantity())
                    .sum();
        }
        order.setTotalPrice(total);

        return shopOrderRepository.save(order);
    }
}