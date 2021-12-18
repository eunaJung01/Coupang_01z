package com.umc.coupang_01z.src.order;

import com.umc.coupang_01z.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderDao orderDao;
    private final OrderProvider orderProvider;
    private final JwtService jwtService;


    @Autowired
    public OrderService(OrderDao orderDao, OrderProvider orderProvider, JwtService jwtService) {
        this.orderDao = orderDao;
        this.orderProvider = orderProvider;
        this.jwtService = jwtService;

    }
}
