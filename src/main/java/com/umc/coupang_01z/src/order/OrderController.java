package com.umc.coupang_01z.src.order;

import com.umc.coupang_01z.src.order.model.*;
import com.umc.coupang_01z.src.user.UserProvider;
import com.umc.coupang_01z.src.user.UserService;
import com.umc.coupang_01z.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.umc.coupang_01z.config.BaseException;
import com.umc.coupang_01z.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupang-eats/orders")
public class OrderController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;

    public OrderController(OrderProvider orderProvider, OrderService orderService, JwtService jwtService) {
        this.orderProvider = orderProvider;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }
}
