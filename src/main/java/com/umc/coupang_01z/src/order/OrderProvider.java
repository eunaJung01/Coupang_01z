package com.umc.coupang_01z.src.order;

import com.umc.coupang_01z.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProvider {
    private final OrderDao orderDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderProvider(OrderDao orderDao, JwtService jwtService) {
        this.orderDao = orderDao;
        this.jwtService = jwtService;
    }
}
