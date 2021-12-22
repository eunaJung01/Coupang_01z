package com.umc.coupang_01z.src.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umc.coupang_01z.utils.JwtService;

@Service
public class RestaurantService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestaurantDao restaurantDao;
    private final RestaurantProvider restaurantProvider;
    private final JwtService jwtService;

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao, RestaurantProvider restaurantProvider, JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.restaurantProvider = restaurantProvider;
        this.jwtService = jwtService;
    }
}
